package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.VirtualCapabilityDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityUserAgentTool;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * 设备名称虚拟能力评估器。
 * <p>将 WURFL 设备数据库中的 {@code brand_name} 和显示名称拼接为
 * 简洁的设备名称。优先使用 {@code marketing_name} 作为显示名称，
 * 如果 marketing_name 为空则回退到 {@code model_name}。</p>
 * <p>对于 Generic 开头的通用设备，会自动将名称中的老旧版本号替换为
 * 从 User-Agent 解析得到的实际 OS 版本号（如 "Generic Android 1.5 Tablet"
 * 在实际为 Android 14 设备时显示为 "Generic Android 14 Tablet"）。</p>
 */

public class DeviceName implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 6339082037173595673L;
    private static final Pattern VERSION_PREFIX = Pattern.compile("\\b(Android|iOS|Windows Phone|webOS|Symbian|Bada)\\s+\\d+(?:\\.\\d+)?");

    @Override
    public String eval(Device device, WURFLRequest request) {
        String brandName = device.getCapability("brand_name");
        StringBuilder builder = new StringBuilder(brandName);
        String namePart;
        namePart = device.getCapability("marketing_name");
        if (namePart.isEmpty()) {
            namePart = device.getCapability("model_name");
        }
        // 对于 Generic 通用设备，用实际 UA 解析的 OS 版本替换名称中的老旧版本号
        if (brandName.startsWith("Generic")) {
            namePart = replaceStaleVersion(namePart, device, request);
        }

        builder.append(" ").append(namePart);
        return builder.toString().trim();
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
        return "device_name";
    }
}
