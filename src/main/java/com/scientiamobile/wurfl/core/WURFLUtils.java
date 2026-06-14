package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Set;

/**
 * WURFL 工具类，提供设备模型查询和元数据访问的便捷方法。
 * <p>封装了 {@link WURFLModel}、{@link DeviceProvider} 和 {@link WURFLService} 的常用查询操作，
 * 包括设备 ID 查询、能力查询、分组查询、设备层次结构遍历等功能。</p>
 */

public class WURFLUtils {
    private final WURFLModel wurflModel;
    private final DeviceProvider deviceProvider;
    private final WURFLService wurflService;

    WURFLUtils(WURFLModel wurflModel, DeviceProvider deviceProvider, WURFLService wurflService) {
        this.wurflModel = wurflModel;
        this.deviceProvider = deviceProvider;
        this.wurflService = wurflService;
    }

    /**
     * 获取 WURFL 数据文件的版本信息。
     *
     * @return 版本字符串
     */

    public String getVersion() {
        return this.wurflModel.getVersion();
    }

    /**
     * 判断指定设备 ID 是否在模型中定义。
     *
     * @param deviceId 设备 ID
     * @return 如果设备已定义返回 {@code true}
     */
    public boolean isDeviceDefined(String deviceId) {
        Validate.notEmpty(deviceId, "deviceId must be not null");
        return this.wurflModel.isDeviceDefined(deviceId);
    }

    /**
     * 根据设备 ID 获取对应的模型设备对象。
     *
     * @param deviceId 设备 ID
     * @return 模型设备对象
     */

    public ModelDevice getModelDeviceById(String deviceId) {
        Validate.notEmpty(deviceId, "The id must be not null Set");
        return this.wurflModel.getDeviceById(deviceId);
    }

    /**
     * 根据设备 ID 集合获取对应的模型设备对象集合。
     *
     * @param deviceIds 设备 ID 集合
     * @return 模型设备对象集合
     */

    public Set<ModelDevice> getModelDevices(Set<String> deviceIds) {
        Validate.notNull(deviceIds, "The ids must be not null Set");
        Validate.noNullElements(deviceIds, "The ids must not containing null elements");
        return this.wurflModel.getDevices(deviceIds);
    }

    /**
     * 获取模型中所有设备的 ID 集合。
     *
     * @return 设备 ID 集合
     */

    public Set<String> getAllDevicesId() {
        return this.wurflModel.getAllDevicesId();
    }

    /**
     * 获取模型中所有模型设备对象的集合。
     *
     * @return 模型设备对象集合
     */
    public Set<ModelDevice> getAllModelDevices() {
        return this.wurflModel.getAllDevices();
    }

    /**
     * 获取指定设备的继承层次结构。
     *
     * @param rootDevice 根设备
     * @return 设备层次结构列表（从子到父）
     */

    public List<ModelDevice> getModelDeviceHierarchy(ModelDevice rootDevice) {
        Validate.notNull(rootDevice, "The root ModelDevice must be not null");
        return this.wurflModel.getDeviceHierarchy(rootDevice);
    }

    /**
     * 获取指定设备的回退（fallback）设备。
     *
     * @param targetDevice 目标设备
     * @return 回退设备对象
     */

    public ModelDevice getModelDeviceFallback(ModelDevice targetDevice) {
        Validate.notNull(targetDevice, "The target ModelDevice must be not null");
        return this.wurflModel.getDeviceFallback(targetDevice);
    }

    /**
     * 获取指定设备的祖先设备。
     *
     * @param rootDevice 目标设备
     * @return 祖先设备对象
     */

    public ModelDevice getModelDeviceAncestor(ModelDevice rootDevice) {
        Validate.notNull(rootDevice, "The root ModelDevice must be not null");
        return this.wurflModel.getDeviceAncestor(rootDevice);
    }

    /**
     * 判断指定能力名称是否在模型中定义。
     *
     * @param capabilityName 能力名称
     * @return 如果已定义返回 {@code true}
     */

    public boolean isCapabilityDefined(String capabilityName) {
        Validate.notEmpty(capabilityName, "The capabilityName must be not null");
        return this.wurflModel.isCapabilityDefined(capabilityName);
    }

    /**
     * 获取模型中定义的所有能力名称集合。
     *
     * @return 能力名称集合
     */

    public Set<String> getAllCapabilities() {
        return this.wurflModel.getAllCapabilities();
    }

    /**
     * 根据能力名称获取其所属的分组名称。
     *
     * @param capabilityName 能力名称
     * @return 分组名称
     */
    public String getGroupByCapability(String capabilityName) {
        Validate.notEmpty(capabilityName, "The capabilityName must be not null");
        return this.wurflModel.getGroupByCapability(capabilityName);
    }

    /**
     * 在设备继承链上查找定义指定能力的设备节点。
     *
     * @param rootDevice     起始设备
     * @param capabilityName 能力名称
     * @return 定义了该能力的设备节点
     */

    public ModelDevice getModelDeviceWhereCapabilityIsDefined(ModelDevice rootDevice, String capabilityName) {
        Validate.notNull(rootDevice, "The rootDevice must be not null Set");
        Validate.notEmpty(capabilityName, "The capabilityName must be not null");
        return this.wurflModel.getDeviceWhereCapabilityIsDefined(rootDevice, capabilityName);
    }

    /**
     * 判断指定分组名称是否在模型中定义。
     *
     * @param groupName 分组名称
     * @return 如果已定义返回 {@code true}
     */

    public boolean isGroupDefined(String groupName) {
        Validate.notEmpty(groupName, "The groupName must be not null");
        return this.wurflModel.isGroupDefined(groupName);
    }

    /**
     * 获取模型中定义的所有分组名称集合。
     *
     * @return 分组名称集合
     */

    public Set<String> getAllGroups() {
        return this.wurflModel.getAllGroups();
    }

    /**
     * 获取指定分组中定义的所有能力名称集合。
     *
     * @param groupName 分组名称
     * @return 能力名称集合
     */
    public Set<String> getCapabilitiesForGroup(String groupName) {
        Validate.notEmpty(groupName, "The groupName must be not null");
        return this.wurflModel.getCapabilitiesForGroup(groupName);
    }

    /**
     * 根据设备 ID 获取内部设备实例。
     *
     * @param deviceId 设备 ID
     * @return 内部设备实例
     */

    public InternalDevice getInternalDeviceById(String deviceId) {
        return this.deviceProvider.getInternalDevice(deviceId);
    }

    /**
     * 根据设备 ID 获取设备实例（使用默认 User-Agent）。
     *
     * @param deviceId 设备 ID
     * @return 设备实例
     */
    public Device getDeviceById(String deviceId) {
        return this.wurflService.getDeviceById(deviceId);
    }

    /**
     * 根据设备 ID 和 WURFL 请求获取设备实例。
     *
     * @param deviceId 设备 ID
     * @param request  WURFL 请求
     * @return 设备实例
     */

    public Device getDeviceById(String deviceId, WURFLRequest request) {
        return this.wurflService.getDeviceById(deviceId, request);
    }

    /**
     * 根据设备 ID 和 HTTP Servlet 请求获取设备实例。
     *
     * @param deviceId 设备 ID
     * @param request  HTTP Servlet 请求
     * @return 设备实例
     */
    public Device getDeviceById(String deviceId, HttpServletRequest request) {
        return this.wurflService.getDeviceById(deviceId, request);
    }

    /**
     * 获取模型中所有设备的实例集合。
     * <p>直接使用 {@link WURFLModel#getAllDevices()} 返回轻量级 {@link ModelDevice} 集合，
     * 避免为每个设备创建完整的 {@link Device} 对象（含虚拟能力处理器、标记解析器等），
     * 在模型包含数万设备时性能差异显著。</p>
     *
     * @return 模型设备实例集合
     */
    public Set<ModelDevice> getAllDevices() {
        return this.wurflModel.getAllDevices();
    }
}
