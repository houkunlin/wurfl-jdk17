package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * 完整设备名称虚拟能力评估器。
 * <p>将 WURFL 设备数据库中的 {@code brand_name}、{@code model_name} 和
 * {@code marketing_name} 组合为完整的设备名称。
 * 格式为 "品牌 型号 (营销名称)"，每种信息在可用时才会被包含。</p>
 */

public class CompleteDeviceName implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = -65030764132400949L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        StringBuilder builder = new StringBuilder(device.getCapability("brand_name"));
        String namePart;
        namePart = device.getCapability("model_name");
        if (!namePart.isEmpty()) {
            builder.append(" ").append(namePart);
        }

        namePart = device.getCapability("marketing_name");
        if (!namePart.isEmpty()) {
            builder.append(" (").append(namePart).append(")");
        }

        return builder.toString();
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "complete_device_name";
    }
}
