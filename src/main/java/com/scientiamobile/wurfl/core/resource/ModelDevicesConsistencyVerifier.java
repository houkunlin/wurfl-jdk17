package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.resource.exc.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.scientiamobile.wurfl.core.Constants.GENERIC;

/**
 * 设备集合一致性校验器。
 * <p>在设备加载和合并完成后，对设备集合进行全面校验，包括：</p>
 * <ul>
 *   <li>generic 设备是否存在</li>
 *   <li>User-Agent 是否唯一</li>
 *   <li>设备继承链是否存在孤儿节点或循环引用</li>
 *   <li>设备引用的组和功能点是否在 generic 中定义</li>
 *   <li>功能点的分组是否正确</li>
 *   <li>补丁设备是否尝试重定义基础设备的标识属性</li>
 * </ul>
 */

final class ModelDevicesConsistencyVerifier {
    private ModelDevicesConsistencyVerifier() {
    }

    /**
     * 对设备集合进行完整的一致性校验。
     *
     * @param devices 待校验的设备集合
     * @throws GenericNotDefinedException    如果缺少 generic 设备
     * @throws UserAgentNotUniqueException   如果 User-Agent 不唯一
     * @throws OrphanHierarchyException      如果发现孤儿继承节点
     * @throws CircularHierarchyException    如果发现循环继承
     * @throws InexistentGroupException      如果引用不存在的组
     * @throws InexistentCapabilityException 如果引用不存在的功能点
     * @throws BadCapabilityGroupException   如果功能点分组错误
     */
    public static void verifyModelDevices(ModelDevices devices) {
        // 1. 必须存在 generic 根设备
        if (!devices.containsId(GENERIC)) {
            throw new GenericNotDefinedException();
        }
        HashMap<String, ModelDevice> deviceByUserAgent = new HashMap<>();
        HashSet<String> visitedDeviceIds = new HashSet<>();

        for (ModelDevice device : devices) {
            assert device != null : "device is null";

            // 2. 检查 User-Agent 唯一性
            ModelDevice existing = deviceByUserAgent.put(device.getUserAgent(), device);
            if (existing != null) {
                throw new UserAgentNotUniqueException(device, device.getUserAgent(), existing);
            }
            // 3. 检查继承链合法性
            verifyHierarchy(device, devices, visitedDeviceIds);
            visitedDeviceIds.add(device.getID());
            // 4. 检查组合法性
            verifyGroups(device, devices);
            // 5. 检查功能点合法性
            verifyCapabilities(device, devices);
        }
    }

    /**
     * 验证单个设备的继承链是否合法。
     * <p>沿着 fall_back 链向上追溯，直到遇到 generic 或已访问过的设备为止。
     * 检测孤儿节点（fall_back 指向不存在的设备）和循环引用。</p>
     *
     * @param device  待验证的设备
     * @param devices 设备集合
     * @param visited 已访问过的设备 ID 集合
     * @throws OrphanHierarchyException   如果发现孤儿节点
     * @throws CircularHierarchyException 如果发现循环引用
     */

    private static void verifyHierarchy(ModelDevice device, ModelDevices devices, Set<String> visited) {
        String currentDeviceId = device.getID();
        assert !StringUtils.isEmpty(currentDeviceId);

        ArrayList<ModelDevice> hierarchy = new ArrayList<>(10);
        hierarchy.add(devices.getById(currentDeviceId));

        while (!GENERIC.equals(currentDeviceId)) {
            currentDeviceId = devices.getById(currentDeviceId).getFallBack();
            assert !StringUtils.isEmpty(currentDeviceId);

            // 如果当前 fall_back 指向已访问过的设备，说明之前的校验已确认该路径合法
            if (visited.contains(currentDeviceId)) {
                return;
            }
            // fall_back 指向了不存在的设备
            if (!devices.containsId(currentDeviceId)) {
                throw new OrphanHierarchyException(hierarchy);
            }

            // 检测循环引用
            int circularIndex = hierarchy.indexOf(devices.getById(currentDeviceId));
            if (circularIndex != -1) {
                LinkedList<ModelDevice> circularHierarchy = new LinkedList<>(hierarchy.subList(circularIndex, hierarchy.size()));
                throw new CircularHierarchyException(circularHierarchy);
            }
            hierarchy.add(devices.getById(currentDeviceId));
        }
    }

    /**
     * 验证设备的组引用是否都存在于 generic 设备中。
     *
     * @param device  待验证的设备
     * @param devices 设备集合
     * @throws InexistentGroupException 如果引用了不存在的组
     */

    private static void verifyGroups(ModelDevice device, ModelDevices devices) {
        ModelDevice genericDevice = devices.getById(GENERIC);
        Set<String> genericGroups = genericDevice.getGroups();
        for (String group : device.getGroups()) {
            if (!genericGroups.contains(group)) {
                throw new InexistentGroupException(device, group);
            }
        }
    }

    /**
     * 验证设备的功能点定义是否合法。
     * <p>检查功能点是否在 generic 中定义，以及功能点所属的组是否与 generic 一致。</p>
     *
     * @param device  待验证的设备
     * @param devices 设备集合
     * @throws InexistentCapabilityException 如果引用了不存在的功能点
     * @throws BadCapabilityGroupException   如果功能点分组与 generic 不一致
     */

    private static void verifyCapabilities(ModelDevice device, ModelDevices devices) {
        assert devices.containsId(GENERIC) : "device do not containing generic";

        ModelDevice genericDevice = devices.getById(GENERIC);
        Map<String, String> genericCapabilities = genericDevice.getCapabilities();
        for (String capabilityName : device.getCapabilities().keySet()) {
            // 功能点必须在 generic 中定义
            if (!genericCapabilities.containsKey(capabilityName)) {
                throw new InexistentCapabilityException(device, capabilityName);
            }
            // 功能点必须属于与 generic 中相同的组
            String deviceGroup = device.getGroupForCapability(capabilityName);
            String genericGroup = genericDevice.getGroupForCapability(capabilityName);
            if (!deviceGroup.equals(genericGroup)) {
                throw new BadCapabilityGroupException(device, capabilityName, deviceGroup, genericGroup);
            }
        }
    }

    /**
     * 验证补丁设备集合中没有重定义基础设备中的标识属性。
     * <p>补丁设备如果与基础设备 ID 相同，则必须保持 User-Agent 和 fall_back 不变。</p>
     *
     * @param patchDevices 补丁设备集合
     * @param baseDevices  基础设备集合
     * @throws RedefinedDeviceException 如果补丁设备重定义了基础设备的标识属性
     */

    public static void verifyNoRedefinedDevices(ModelDevices patchDevices, ModelDevices baseDevices) {
        for (ModelDevice patchDevice : patchDevices.getDevices()) {
            String patchUserAgent = patchDevice.getUserAgent();
            String patchDeviceId = patchDevice.getID();
            String patchFallBack = patchDevice.getFallBack();
            ModelDevice baseDevice = baseDevices.getById(patchDeviceId);
            if (baseDevice == null) {
                return;
            }

            // 补丁设备必须提供 fall_back 值
            if (StringUtils.isEmpty(patchFallBack)) {
                throw new RedefinedDeviceException("Patched device with id " + patchDeviceId + "does not provide a value for fall_back");
            }

            // 补丁设备不能修改基础设备的 User-Agent
            if (!patchUserAgent.equals(baseDevice.getUserAgent())) {
                throw new RedefinedDeviceException(patchDevice, baseDevice, "user agent");
            }
            // 补丁设备不能修改基础设备的 fall_back
            if (!patchFallBack.equals(baseDevice.getFallBack())) {
                throw new RedefinedDeviceException(patchDevice, baseDevice, "fall_back");
            }
        }

    }
}
