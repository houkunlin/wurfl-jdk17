package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.VirtualCapabilityDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityUserAgentTool;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * 判断设备操作系统是否为 Android 的虚拟能力评估器。
 * <p>通过检查 WURFL 设备数据库中的 {@code device_os} 能力值是否等于 "Android"
 * 来判断设备是否运行 Android 操作系统。同时检测 UA 解析结果，若广告投放
 * 操作系统为 HarmonyOS 则不视为 Android。</p>
 */

public class IsAndroidOs implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 6129742649965950877L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        // 1. 数据文件明确为 HarmonyOS → 不视为 Android
        String deviceOs = device.getCapability("device_os");
        if ("HarmonyOS".equals(deviceOs)) {
            return "false";
        }
        // 2. 数据文件说 Android，仍需确认 UA 解析不是 HarmonyOS
        if ("Android".equals(deviceOs)) {
            VirtualCapabilityDevice vcd = VirtualCapabilityUserAgentTool.getInstance().assignProperties(request, device);
            return vcd.getOsPairName() == null || !"HarmonyOS".equals(vcd.getOsPairName()) ? "true" : "false";
        }
        // 3. 其他 OS → 不是 Android
        return "false";
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "is_android";
    }
}
