package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.resource.exc.DeviceNotInModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备能力提供者，通过遍历设备继承层次结构来获取完整的能力数据。
 * <p>对于单个能力查询，优先从缓存中查找；缓存未命中时，沿着设备继承链向上查找
 * 直到找到定义该能力的设备节点。对于全部能力查询，遍历整个设备继承链合并所有能力。</p>
 */

class DeviceCapabilitiesProvider implements CapabilitiesProvider {
    private static final Logger log = LoggerFactory.getLogger(DeviceCapabilitiesProvider.class);
    /**
     * WURFL 数据模型
     */
    private final WURFLModel wurflModel;
    /**
     * 目标设备模型
     */
    private final ModelDevice modelDevice;

    public DeviceCapabilitiesProvider(ModelDevice modelDevice, WURFLModel wurflModel) {
        this.wurflModel = wurflModel;
        this.modelDevice = modelDevice;
    }

    /**
     * 获取该设备的所有能力映射。
     * <p>遍历设备的继承层次结构，从最具体的设备开始向上合并每层设备定义的能力，
     * 最通用的祖先设备的能力会被子设备的能力覆盖。</p>
     *
     * @return 能力名称到值的映射
     */
    @Override
    public Map<String, String> getAllCapabilities() {
        HashMap<String, String> capabilities = new HashMap<>(this.wurflModel.getAllCapabilities().size());

        try {
            for (ModelDevice deviceInHierarchy : this.wurflModel.getDeviceHierarchy(this.modelDevice)) {
                capabilities.putAll(deviceInHierarchy.getCapabilities());
            }
        } catch (DeviceNotInModelException e) {
            if (log.isErrorEnabled()) {
                log.error("Device: {} is not in model. Capabilities will not loaded.", this.modelDevice.getID());
            }
        }

        return capabilities;
    }

    /**
     * 从缓存或设备继承链中获取指定能力的值。
     * <p>优先从缓存映射中查找，未命中时沿着设备继承链向上搜索定义该能力的设备节点。</p>
     *
     * @param capabilities   能力缓存映射
     * @param capabilityName 能力名称
     * @return 能力值，如果未定义则返回 {@code null}
     */
    @Override
    public String getCapability(Map<String, String> capabilities, String capabilityName) {
        Map<String, String> capabilitiesMap = capabilities;
        String capabilityValue = capabilitiesMap.get(capabilityName);
        if (capabilityValue != null) {
            return capabilityValue;
        } else {
            ModelDevice currentDevice;
            currentDevice = this.modelDevice;
            for (; currentDevice != null && !currentDevice.defineCapability(capabilityName); currentDevice = currentDevice.getAncestor()) {
            }

            String resolvedValue = currentDevice != null ? currentDevice.getCapability(capabilityName) : null;
            if (resolvedValue != null) {
                capabilitiesMap.put(capabilityName, resolvedValue);
            }

            return resolvedValue;
        }
    }
}
