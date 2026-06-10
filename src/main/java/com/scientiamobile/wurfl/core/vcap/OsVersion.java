package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.VirtualCapabilityDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityUserAgentTool;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * 广告投放所需的设备操作系统版本号虚拟能力评估器。
 * <p>委托 {@link VirtualCapabilityUserAgentTool} 从 User-Agent 中解析
 * 更准确的操作系统版本号（如 "4.4.2"、"10.0"），
 * 而非直接使用 WURFL 数据库中的原始 {@code device_os_version} 值。
 * 结果会经过 {@link VirtualCapabilityHandler#applyControlCapOverride} 的覆盖检查。</p>
 */

public class OsVersion implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = -1685031657455562093L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        VirtualCapabilityDevice virtualCapabilityDevice = VirtualCapabilityUserAgentTool.getInstance().assignProperties(request, device);
        return VirtualCapabilityHandler.applyControlCapOverride("advertised_device_os_version", virtualCapabilityDevice.getOsPairVersion(), device);
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "advertised_device_os_version";
    }
}
