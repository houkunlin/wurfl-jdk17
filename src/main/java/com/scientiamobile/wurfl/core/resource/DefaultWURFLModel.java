package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.Constants;
import com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.exc.DeviceNotDefinedException;
import com.scientiamobile.wurfl.core.exc.GroupNotDefinedException;
import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.resource.exc.DeviceNotInModelException;
import com.scientiamobile.wurfl.core.resource.exc.GenericNotDefinedException;
import com.scientiamobile.wurfl.core.resource.exc.OrphanHierarchyException;
import com.scientiamobile.wurfl.core.utils.CollectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * WURFL 模型的默认实现。
 * <p>管理内存中的 WURFL 设备数据库，提供设备查询、继承链遍历、
 * 能力获取、分组管理等功能。支持从主资源文件和补丁资源文件加载数据，
 * 并在加载过程中进行完整的一致性校验。</p>
 */

public class DefaultWURFLModel implements WURFLModel {
    private static final Logger log = LoggerFactory.getLogger(DefaultWURFLModel.class);
    /**
     * 设备 ID 到祖先设备 ID 的缓存，加速祖先查找
     */
    private final Map<String, String> deviceIdToAncestorIdCache;
    /**
     * 家族设备（fallback 为 generic 或 generic_mobile）的 ID 集合
     */
    private final Set<String> familyDeviceIds;
    /**
     * 设备 ID 到设备对象的映射
     */
    private Map<String, ModelDevice> devicesById;
    /**
     * 按插入顺序排列的设备 ID 列表
     */
    private List<String> deviceIdsByInsertionOrder;
    /**
     * 缓存的 generic 设备实例
     */
    private ModelDevice genericDevice;
    /**
     * 模型的版本号
     */
    private String version;
    /**
     * WURFL SMID（安全模型标识符）
     */
    private String smid;
    /**
     * 缓存的能力总数
     */
    private Integer capabilityCount;

    /**
     * 使用主资源构造 WURFL 模型（不带补丁资源）。
     *
     * @param rootResource         主 WURFL 资源
     * @param includedCapabilities 可选的功能点过滤列表
     */
    public DefaultWURFLModel(WURFLResource rootResource, String... includedCapabilities) {
        this(rootResource, new WURFLResources(), includedCapabilities);
    }

    /**
     * 使用主资源和补丁资源构造 WURFL 模型。
     * <p>构造函数会立即加载主资源并应用补丁资源，完成后模型即可用于查询。</p>
     *
     * @param rootResource         主 WURFL 资源
     * @param patchResources       补丁资源集合
     * @param includedCapabilities 可选的功能点过滤列表
     */
    @SuppressWarnings("this-escape")
    public DefaultWURFLModel(WURFLResource rootResource, WURFLResources patchResources, String... includedCapabilities) {
        this.deviceIdToAncestorIdCache = CollectionFactory.createConcurrentHashMap();
        this.familyDeviceIds = new HashSet<>();

        this.loadFromRootResource(rootResource, patchResources, includedCapabilities);
    }

    /**
     * 为设备集合中的每个设备设置祖先设备。
     * <p>遍历设备集合，如果设备的 fall_back 存在于同一集合中，
     * 则将其祖先设置为 fall_back 对应的设备对象。</p>
     *
     * @param devices 设备集合
     */

    private static void setAncestors(ModelDevices devices) {
        if (devices != null) {
            for (ModelDevice device : devices.getDevices()) {
                if (device.getFallBack() != null && devices.containsId(device.getFallBack())) {
                    device.setAncestor(devices.getById(device.getFallBack()));
                }
            }

        }
    }

    /**
     * Loa dro moo tesource.
     */

    private final synchronized void loadFromRootResource(WURFLResource rootResource, WURFLResources patchResources, String... includedCapabilities) {
        Validate.notNull(rootResource, "The root resource must be not null.");
        if (patchResources == null) {
            patchResources = new WURFLResources();
        }

        this.deviceIdToAncestorIdCache.clear();
        this.genericDevice = null;
        this.capabilityCount = null;
        ModelDevicesSnapshot snapshot = rootResource.getData(includedCapabilities);
        this.version = snapshot.getSnapshotKey();
        this.smid = snapshot.getSmid();
        ModelDevices devices = snapshot.copyDevices();
        // 复制一份用于校验，保证校验不影响原始数据
        ModelDevices devicesCopyForVerification = new ModelDevices(devices);
        this.deviceIdsByInsertionOrder = devices.getDeviceIdsByInsertionOrder();
        ModelDevicesConsistencyVerifier.verifyModelDevices(devicesCopyForVerification);
        this.applyPatchesAndRebuild(patchResources, devicesCopyForVerification, includedCapabilities);
        // 如果没有补丁资源，直接在原始设备上设置祖先关系
        if (patchResources.size() == 0) {
            setAncestors(devicesCopyForVerification);
        }

    }

    /**
     * 应用补丁资源并重建模型索引。
     * <p>遍历所有补丁资源，依次合并到设备集合中，每次合并后都进行一致性校验。
     * 最后建立设备 ID 索引、设置祖先关系，并统计根设备和家族设备数量。</p>
     *
     * @param patchResources       补丁资源集合
     * @param devices              基础设备集合（将被修改）
     * @param includedCapabilities 可选的功能点过滤列表
     */

    private final synchronized void applyPatchesAndRebuild(WURFLResources patchResources, ModelDevices devices, String... includedCapabilities) {
        int genericDevicesCount = 0;
        int rootDevicesCount = 0;

        for (int i = 0; patchResources != null && i < patchResources.size(); ++i) {
            ModelDevices patchDevices;
            ModelDevicesSnapshot patchSnapshot;
            patchSnapshot = patchResources.get(i).getData(includedCapabilities);
            patchDevices = patchSnapshot.copyDevices();
            ModelDevicesConsistencyVerifier.verifyNoRedefinedDevices(patchDevices, devices);
            String mergedVersion = new StringBuilder().append(StringUtils.defaultString(this.version)).append("; ").append(patchSnapshot.getSnapshotKey()).toString();
            devices = ModelDevicesPatchMerger.merge(devices, patchDevices);
            this.deviceIdsByInsertionOrder = devices.getDeviceIdsByInsertionOrder();
            ModelDevicesConsistencyVerifier.verifyModelDevices(devices);
            this.version = mergedVersion;
        }

        ModelDevicesConsistencyVerifier.verifyModelDevices(devices);
        this.devicesById = CollectionFactory.createConcurrentHashMap();
        this.devicesById.putAll(devices.getDevicesById());
        setAncestors(devices);

        for (ModelDevice device : this.devicesById.values()) {
            if (device.isActualDeviceRoot()) {
                ++rootDevicesCount;
            } else {
                String fallbackId;
                fallbackId = device.getFallBack();
                // 将直接回退到 generic 或 generic_mobile 的设备归类为家族设备
                if (fallbackId.equals(Constants.GENERIC) || fallbackId.equals("generic_mobile")) {
                    this.familyDeviceIds.add(device.getID());
                }
            }

            // 统计继承链根节点为 generic 的设备数
            if (this.getDeviceAncestor(device).getID().equals(Constants.GENERIC)) {
                ++genericDevicesCount;
            }
        }

        this.genericDevice = this.devicesById.get(Constants.GENERIC);
        if (log.isInfoEnabled()) {
            log.info("WURFLModel version: {}; devices: {} root devices: {}; families: {}; generic devices: {}", this.version, this.devicesById.size(), rootDevicesCount, this.familyDeviceIds.size(), genericDevicesCount);
        }

    }

    /**
     * 获取模型的版本快照键。
     *
     * @return 版本号字符串
     */
    @Override
    public String getVersion() {
        return this.version;
    }

    /**
     * 根据设备 ID 获取设备。
     *
     * @param deviceId 设备 ID
     * @return 匹配的设备对象
     * @throws com.scientiamobile.wurfl.core.exc.DeviceNotDefinedException 如果设备 ID 不存在
     */
    @Override
    public ModelDevice getDeviceById(String deviceId) {
        Validate.notEmpty(deviceId, "The id must be not null");
        ModelDevice device = this.devicesById.get(deviceId);
        if (device == null) {
            throw new DeviceNotDefinedException(deviceId);
        } else {
            return device;
        }
    }

    /**
     * 根据一组设备 ID 批量获取设备。
     *
     * @param deviceIds 设备 ID 集合
     * @return 匹配的设备集合
     */
    @Override
    public Set<ModelDevice> getDevices(Set<String> deviceIds) {
        Validate.notNull(deviceIds, "The devicesIds must be not null Set");
        Validate.noNullElements(deviceIds, "The devicesIds must not containing null elements");
        HashSet<ModelDevice> devices = new HashSet<>();
        for (String id : deviceIds) {
            devices.add(this.getDeviceById(id));
        }

        return devices;
    }

    /**
     * 获取模型中所有设备的集合，按 User-Agent 排序。
     *
     * @return 所有设备的集合
     */
    @Override
    public Set<ModelDevice> getAllDevices() {
        TreeSet<ModelDevice> devices = new TreeSet<>(ModelDeviceUserAgentComparator.INSTANCE);
        devices.addAll(this.devicesById.values());
        return devices;
    }

    /**
     * 获取模型中所有设备的列表，按插入顺序排列。
     *
     * @return 设备列表
     */
    @Override
    public List<ModelDevice> getAllDevicesAsList() {
        ArrayList<ModelDevice> devices = new ArrayList<>(this.deviceIdsByInsertionOrder.size());
        for (String id : this.deviceIdsByInsertionOrder) {
            devices.add(this.devicesById.get(id));
        }

        return devices;
    }

    /**
     * 获取模型中所有设备 ID 的集合。
     *
     * @return 设备 ID 集合
     */
    @Override
    public Set<String> getAllDevicesId() {
        return new HashSet<>(this.devicesById.keySet());
    }

    /**
     * 获取指定设备的完整继承链（从 generic 到该设备）。
     *
     * @param device 目标设备
     * @return 从 generic 到该设备的继承链列表
     */
    @Override
    public List<ModelDevice> getDeviceHierarchy(ModelDevice device) {
        Validate.notNull(device, "The device must be not null");
        // 从当前设备沿着 fall_back 链向上追溯直至 generic
        LinkedList<ModelDevice> hierarchy = new LinkedList<>();
        for (; !Constants.GENERIC.equals(device.getID()); device = this.getDeviceFallback(device)) {
            hierarchy.addFirst(device);
        }

        hierarchy.addFirst(device);
        return hierarchy;
    }

    /**
     * 获取设备的 fall_back（回退）设备。
     *
     * @param device 目标设备
     * @return fall_back 设备
     * @throws DeviceNotInModelException 如果 fall_back ID 不在模型中
     */
    @Override
    public ModelDevice getDeviceFallback(ModelDevice device) {
        Validate.notNull(device, "The device must be not null");

        try {
            return this.getDeviceById(device.getFallBack());
        } catch (DeviceNotDefinedException e) {
            throw new DeviceNotInModelException(device, e);
        }
    }

    /**
     * 获取设备的最接近的祖先设备。
     * <p>沿继承链向上查找第一个标记为 actual_device_root 的设备，
     * 如果都未标记则返回 generic。结果会被缓存。</p>
     *
     * @param device 目标设备
     * @return 祖先设备
     */
    @Override
    public ModelDevice getDeviceAncestor(ModelDevice device) {
        Validate.notNull(device, "The device must be not null");
        String deviceId = device.getID();
        // 优先从缓存获取
        String ancestorId = this.deviceIdToAncestorIdCache.get(deviceId);
        if (ancestorId != null) {
            return this.getDeviceById(ancestorId);
        } else {
            ModelDevice deviceOrAncestor = device;
            ModelDevice modelDevice = this.getGenericDevice();
            List<ModelDevice> deviceHierarchy = this.getDeviceHierarchy(device);
            // 从继承链顶部向下查找第一个 actual_device_root 或 generic
            for (int i = deviceHierarchy.size() - 1; i >= 0 && !deviceOrAncestor.isActualDeviceRoot() && !modelDevice.equals(deviceOrAncestor); --i) {
                deviceOrAncestor = deviceHierarchy.get(i);
            }

            if (!deviceOrAncestor.isActualDeviceRoot() && !modelDevice.equals(deviceOrAncestor)) {
                throw new WURFLRuntimeException("Hierarchy is invalid");
            } else {
                String computedAncestorId = deviceOrAncestor.getID();
                // 缓存计算结果
                this.deviceIdToAncestorIdCache.put(deviceId, computedAncestorId);
                return deviceOrAncestor;
            }
        }
    }

    /**
     * 判断指定设备 ID 是否在模型中定义。
     *
     * @param deviceId 设备 ID
     * @return 如果设备存在则返回 true
     */
    @Override
    public boolean isDeviceDefined(String deviceId) {
        Validate.notEmpty(deviceId, "The deviceId must be not null");
        return this.devicesById.containsKey(deviceId);
    }

    /**
     * 获取模型中定义的设备总数。
     *
     * @return 设备数量
     */
    @Override
    public int size() {
        return this.devicesById.size();
    }

    /**
     * 获取模型中定义的所有组的名称集合。
     *
     * @return 所有组名称的集合
     */
    @Override
    public Set<String> getAllGroups() {
        return this.getGenericDevice().getGroups();
    }

    /**
     * 判断指定组是否在模型中定义。
     *
     * @param groupId 组名称
     * @return 如果组存在则返回 true
     */
    @Override
    public boolean isGroupDefined(String groupId) {
        Validate.notEmpty(groupId, "The groupId must be not null");
        return this.getGenericDevice().defineGroup(groupId);
    }

    /**
     * 获取指定功能点所属的组名称。
     *
     * @param capabilityName 功能点名称
     * @return 所属的组名称
     * @throws com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException 如果功能点未定义
     */
    @Override
    public String getGroupByCapability(String capabilityName) {
        Validate.notEmpty(capabilityName, "The capabilityName must be not null");
        ModelDevice modelDevice = this.getGenericDevice();
        if (!modelDevice.defineCapability(capabilityName)) {
            throw new CapabilityNotDefinedException(capabilityName);
        } else {
            return modelDevice.getGroupForCapability(capabilityName);
        }
    }

    /**
     * 重新加载 WURFL 模型，替换当前所有数据。
     *
     * @param rootResource         主 WURFL 资源
     * @param patchResources       补丁资源集合
     * @param includedCapabilities 可选的功能点过滤列表
     */
    @Override
    public void reload(WURFLResource rootResource, WURFLResources patchResources, String... includedCapabilities) {
        log.info("about to reload the WURFL Model");
        this.loadFromRootResource(rootResource, patchResources, includedCapabilities);
    }

    /**
     * 应用补丁资源到当前模型。
     *
     * @param patchResources       补丁资源集合
     * @param includedCapabilities 可选的功能点过滤列表
     */
    @Override
    public void applyPatches(WURFLResources patchResources, String... includedCapabilities) {
        this.applyPatchesAndRebuild(patchResources, new ModelDevices(this.devicesById), includedCapabilities);
    }

    /**
     * 获取模型中定义的所有功能点的名称集合。
     *
     * @return 所有功能点名称的集合
     */
    @Override
    public Set<String> getAllCapabilities() {
        ModelDevice modelDevice = this.getGenericDevice();
        return new HashSet<>(modelDevice.getCapabilities().keySet());
    }

    /**
     * 获取模型中定义的功能点总数（惰性计算并缓存）。
     *
     * @return 功能点数量
     */
    @Override
    public Integer getCapabilityCount() {
        if (this.capabilityCount == null || this.capabilityCount == 0) {
            this.capabilityCount = this.getAllCapabilities().size();
        }

        return this.capabilityCount;
    }

    /**
     * 判断指定功能点是否在模型中定义。
     *
     * @param capabilityName 功能点名称
     * @return 如果功能点存在则返回 true
     */
    @Override
    public boolean isCapabilityDefined(String capabilityName) {
        Validate.notEmpty(capabilityName, "The capability must be not null");
        return this.getGenericDevice().defineCapability(capabilityName);
    }

    /**
     * 获取指定组中的所有功能点名称。
     *
     * @param groupId 组名称
     * @return 该组中的功能点名称集合
     * @throws com.scientiamobile.wurfl.core.exc.GroupNotDefinedException 如果组未定义
     */
    @Override
    public Set<String> getCapabilitiesForGroup(String groupId) {
        Validate.notEmpty(groupId, "The groupId must be not null");
        ModelDevice modelDevice = this.getGenericDevice();
        if (!modelDevice.defineGroup(groupId)) {
            throw new GroupNotDefinedException(groupId);
        } else {
            return modelDevice.getCapabilitiesNamesForGroup(groupId);
        }
    }

    /**
     * 在设备的继承链中查找定义了指定功能点的设备。
     * <p>从继承链顶部（最接近 generic）向当前设备方向搜索，
     * 找到第一个定义了该功能的设备。</p>
     *
     * @param rootDevice     起始设备
     * @param capabilityName 功能点名称
     * @return 定义了该功能点的设备
     * @throws com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException 如果功能点未在任何设备中定义
     */
    @Override
    public ModelDevice getDeviceWhereCapabilityIsDefined(ModelDevice rootDevice, String capabilityName) {
        Validate.notNull(rootDevice, "The rootDevice must be not null");
        Validate.notEmpty(capabilityName, "The name must be not null");
        List<ModelDevice> deviceHierarchy = this.getDeviceHierarchy(rootDevice);
        // 从继承链顶部向下搜索
        for (int i = deviceHierarchy.size() - 1; i >= 0; --i) {
            ModelDevice device = deviceHierarchy.get(i);
            if (device.defineCapability(capabilityName)) {
                return device;
            }

            // 搜索到 generic_mobile 仍未找到则终止
            if ("generic_mobile".equals(device.getID())) {
                throw new CapabilityNotDefinedException(capabilityName);
            }
        }

        throw new OrphanHierarchyException(deviceHierarchy);
    }

    /**
     * 获取所有根设备（标记为 actual_device_root 的设备）的 ID 集合。
     *
     * @return 根设备 ID 集合
     */
    @Override
    public Set<String> getRootDevicesIds() {
        HashSet<String> rootDeviceIds = new HashSet<>();

        for (ModelDevice device : this.devicesById.values()) {
            if (device.isActualDeviceRoot()) {
                rootDeviceIds.add(device.getID());
            }
        }

        return rootDeviceIds;
    }

    /**
     * 获取 generic 基础设备。
     * <p>如果缓存为空则尝试从设备映射中查找，
     * 若设备映射不为空却找不到 generic 则抛出异常。</p>
     *
     * @return generic 设备
     */

    private @NonNull ModelDevice getGenericDevice() {
        if (this.genericDevice != null) {
            return this.genericDevice;
        } else {
            ModelDevice modelDevice = this.devicesById.get(Constants.GENERIC);
            if (modelDevice == null && !this.devicesById.isEmpty()) {
                throw new GenericNotDefinedException();
            } else if (modelDevice == null) {
                throw new GenericNotDefinedException();
            } else {
                this.genericDevice = modelDevice;
                return modelDevice;
            }
        }
    }

    /**
     * 返回模型的字符串表示。
     *
     * @return 包含版本号的字符串
     */
    @Override
    public String toString() {
        ToStringBuilder out;
        out = new ToStringBuilder(this);
        out.append(this.version);
        return out.toString();
    }

    /**
     * 获取 WURFL SMID。
     *
     * @return SMID 字符串
     */

    public String getSmid() {
        return smid;
    }
}
