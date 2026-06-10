package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.resource.exc.DeviceNotInModelException;

import java.util.List;
import java.util.Set;

/**
 * WURFL 模型接口。
 * <p>定义 WURFL 设备数据库的核心操作契约，包括设备查询、
 * 设备继承链遍历、能力获取、分组管理等。
 * 实现类负责维护内存中的设备数据并提供高效的检索能力。</p>
 */

public interface WURFLModel {
    /**
     * 获取 WURFL 模型的版本号。
     *
     * @return 版本号字符串
     */
    String getVersion();

    /**
     * 根据设备 ID 获取设备。
     *
     * @param deviceId 设备 ID
     * @return 匹配的设备对象
     * @throws com.scientiamobile.wurfl.core.exc.DeviceNotDefinedException 如果设备 ID 不存在
     */
    ModelDevice getDeviceById(String deviceId);

    /**
     * 判断指定设备 ID 是否在模型中定义。
     *
     * @param deviceId 设备 ID
     * @return 如果设备存在则返回 true
     */
    boolean isDeviceDefined(String deviceId);

    /**
     * 获取模型中所有设备的集合。
     *
     * @return 按 User-Agent 排序的所有设备集合
     */
    Set<ModelDevice> getAllDevices();

    /**
     * 获取模型中所有设备的列表，按插入顺序排列。
     *
     * @return 按插入顺序排列的设备列表
     */
    List<ModelDevice> getAllDevicesAsList();

    /**
     * 获取模型中所有设备 ID 的集合。
     *
     * @return 所有设备 ID 的集合
     */
    Set<String> getAllDevicesId();

    /**
     * 根据一组设备 ID 批量获取设备。
     *
     * @param deviceIds 设备 ID 集合
     * @return 匹配的设备集合
     */
    Set<ModelDevice> getDevices(Set<String> deviceIds);

    /**
     * 获取指定设备的完整继承链（从 generic 到该设备）。
     *
     * @param device 目标设备
     * @return 从 generic 到该设备的继承链列表
     */
    List<ModelDevice> getDeviceHierarchy(ModelDevice device);

    /**
     * 获取设备的 fall_back（回退）设备。
     *
     * @param device 目标设备
     * @return fall_back 设备
     * @throws DeviceNotInModelException 如果 fall_back 不在模型中
     */
    ModelDevice getDeviceFallback(ModelDevice device);

    /**
     * 获取设备的最接近的祖先设备（设置了 actual_device_root 的设备或 generic）。
     *
     * @param device 目标设备
     * @return 祖先设备
     */
    ModelDevice getDeviceAncestor(ModelDevice device);

    /**
     * 获取模型中设备的数量。
     *
     * @return 设备数量
     */
    int size();

    /**
     * 获取模型中定义的能力总数。
     *
     * @return 能力数量
     */
    Integer getCapabilityCount();

    /**
     * 重新加载 WURFL 模型，替换当前所有数据。
     *
     * @param resource  主 WURFL 资源
     * @param resources 补丁资源集合
     * @param params    可选的能力过滤列表
     */
    void reload(WURFLResource resource, WURFLResources resources, String... params);

    /**
     * 应用补丁资源到当前模型。
     *
     * @param resources 补丁资源集合
     * @param params    可选的能力过滤列表
     */
    void applyPatches(WURFLResources resources, String... params);

    /**
     * 获取模型中定义的所有能力的名称集合。
     *
     * @return 所有能力名称的集合
     */
    Set<String> getAllCapabilities();

    /**
     * 判断指定能力是否在模型中定义。
     *
     * @param capabilityName 能力名称
     * @return 如果能力存在则返回 true
     */
    boolean isCapabilityDefined(String capabilityName);

    /**
     * 获取指定能力所属的组名称。
     *
     * @param capabilityName 能力名称
     * @return 所属的组名称
     */
    String getGroupByCapability(String capabilityName);

    /**
     * 在设备的继承链中查找定义了指定能力的设备。
     *
     * @param device         起始设备
     * @param capabilityName 能力名称
     * @return 定义了该能力的设备
     */
    ModelDevice getDeviceWhereCapabilityIsDefined(ModelDevice device, String capabilityName);

    /**
     * 获取模型中定义的所有组的名称集合。
     *
     * @return 所有组名称的集合
     */
    Set<String> getAllGroups();

    /**
     * 判断指定组是否在模型中定义。
     *
     * @param groupId 组名称
     * @return 如果组存在则返回 true
     */
    boolean isGroupDefined(String groupId);

    /**
     * 获取指定组中的所有能力名称。
     *
     * @param groupId 组名称
     * @return 该组中的能力名称集合
     */
    Set<String> getCapabilitiesForGroup(String groupId);

    /**
     * 获取所有根设备（标记为 actual_device_root 的设备）的 ID 集合。
     *
     * @return 根设备 ID 集合
     */
    Set<String> getRootDevicesIds();
}
