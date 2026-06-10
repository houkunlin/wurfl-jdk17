package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * 设备名称虚拟能力评估器。
 * <p>将 WURFL 设备数据库中的 {@code brand_name} 和显示名称拼接为
 * 简洁的设备名称。优先使用 {@code marketing_name} 作为显示名称，
 * 如果 marketing_name 为空则回退到 {@code model_name}。</p>
 */

public class DeviceName implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 6339082037173595673L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        StringBuilder builder = new StringBuilder(device.getCapability("brand_name"));
        String namePart;
        namePart = device.getCapability("marketing_name");
        if (namePart.isEmpty()) {
            namePart = device.getCapability("model_name");
        }

        builder.append(" ").append(namePart);
        return builder.toString().trim();
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "device_name";
    }
}
