package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.VirtualCapabilityDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityUserAgentTool;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * 完整设备名称虚拟能力评估器。
 * <p>将 WURFL 设备数据库中的 {@code brand_name}、{@code model_name} 和
 * {@code marketing_name} 组合为完整的设备名称。
 * 格式为 "品牌 型号 (营销名称)"，每种信息在可用时才会被包含。</p>
 * <p>对于 Generic 开头的通用设备，会自动将名称中的老旧版本号替换为
 * 从 User-Agent 解析得到的实际 OS 版本号。</p>
 */

public class CompleteDeviceName implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = -65030764132400949L;
    private static final Pattern VERSION_PREFIX = Pattern.compile("\\b(Android|iOS|Windows Phone|webOS|Symbian|Bada)\\s+\\d+(?:\\.\\d+)?");

    @Override
    public String eval(Device device, WURFLRequest request) {
        String brandName = device.getCapability("brand_name");
        StringBuilder builder = new StringBuilder(brandName);
        String modelName = device.getCapability("model_name");
        if (!modelName.isEmpty()) {
            // 对于 Generic 通用设备，用实际 UA 解析的 OS 版本替换名称中的老旧版本号
            if (brandName.startsWith("Generic")) {
                modelName = replaceStaleVersion(modelName, device, request);
            }
            builder.append(" ").append(modelName);
        }

        String marketingName = device.getCapability("marketing_name");
        if (!marketingName.isEmpty()) {
            builder.append(" (").append(marketingName).append(")");
        }

        return builder.toString();
    }

    /**
     * 将通用设备名称中的老旧版本号替换为实际 OS 版本。
     * <p>仅替换 OS 名称之后的版本号，如 "Android 1.5 Tablet" → "Android 14 Tablet"。</p>
     */
    private static String replaceStaleVersion(String namePart, Device device, WURFLRequest request) {
        VirtualCapabilityDevice vcd = VirtualCapabilityUserAgentTool.getInstance().assignProperties(request, device);
        String actualOsVersion = vcd.getOsPairVersion();
        if (actualOsVersion != null && !actualOsVersion.isEmpty()) {
            return VERSION_PREFIX.matcher(namePart).replaceFirst("$1 " + actualOsVersion);
        }
        return namePart;
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "complete_device_name";
    }
}
