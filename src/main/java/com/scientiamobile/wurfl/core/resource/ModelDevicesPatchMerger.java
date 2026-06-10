package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.resource.exc.UserAgentOverrideException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * 设备补丁合并工具。
 * <p>将补丁设备集合合并到基础设备集合中。合并策略是：
 * 对于基础模型中已存在的设备（按设备 ID 匹配），将其功能点与补丁中的功能点合并；
 * 对于补丁中新增的设备，直接添加。该合并发生在内存层面，
 * 是 WURFL 模型构建过程中的关键步骤。</p>
 */

final class ModelDevicesPatchMerger {
    private static final Logger LOG = LoggerFactory.getLogger(ModelDevicesPatchMerger.class);

    private ModelDevicesPatchMerger() {
    }

    /**
     * 将补丁设备集合合并到基础设备集合中。
     *
     * @param baseDevices  基础设备集合
     * @param patchDevices 补丁设备集合
     * @return 合并后的新设备集合
     * @throws UserAgentOverrideException 如果补丁设备试图修改已有设备的 User-Agent
     */

    public static ModelDevices merge(ModelDevices baseDevices, ModelDevices patchDevices) {
        ModelDevices mergedDevices = new ModelDevices(baseDevices);
        for (ModelDevice patchDevice : patchDevices) {
            if (baseDevices.containsId(patchDevice.getID())) {
                ModelDevice mergedDevice = mergePatchDevice(baseDevices.getById(patchDevice.getID()), patchDevice);
                mergedDevices.remove(baseDevices.getById(patchDevice.getID()));
                mergedDevices.add(mergedDevice);
            } else {
                mergedDevices.add(patchDevice);
            }
        }
        return mergedDevices;
    }

    /**
     * 将单个补丁设备合并到基础设备上。
     * <p>合并逻辑：先验证 User-Agent 一致，然后将补丁的功能点合并到基础设备的功能点之上
     * （补丁中的功能点会覆盖或新增），同时更新 fall_back 和 actual_device_root。</p>
     *
     * @param baseDevice  基础设备
     * @param patchDevice 补丁设备
     * @return 合并后的新设备
     */

    private static ModelDevice mergePatchDevice(ModelDevice baseDevice, ModelDevice patchDevice) {
        if (patchDevice.getUserAgent() == null) {
            LOG.error("invalid patching device, not patched");
            return baseDevice;
        }
        // 验证补丁设备不能修改基础设备的 User-Agent
        if (!patchDevice.getUserAgent().equals(baseDevice.getUserAgent())) {
            throw new UserAgentOverrideException(baseDevice, patchDevice.getUserAgent(), baseDevice.getUserAgent());
        }
        HashMap<String, String> mergedCapabilities = new HashMap<>(baseDevice.getCapabilities());
        mergedCapabilities.putAll(patchDevice.getCapabilities());
        HashMap<String, String> mergedGroupsByCapability = new HashMap<>(baseDevice.getGroupsByCapability());
        mergedGroupsByCapability.putAll(patchDevice.getGroupsByCapability());
        return new ModelDeviceBuilder(baseDevice.getID(), baseDevice.getUserAgent(), patchDevice.getFallBack())
                .setActualDeviceRoot(patchDevice.isActualDeviceRoot())
                .setCapabilitiesByGroup(mergedGroupsByCapability)
                .setCapabilities(mergedCapabilities)
                .build();
    }
}
