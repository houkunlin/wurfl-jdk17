package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.VirtualCapabilityDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityUserAgentTool;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * 广告投放所需的浏览器名称虚拟能力评估器。
 * <p>委托 {@link VirtualCapabilityUserAgentTool} 从 User-Agent 中解析
 * 更准确的浏览器名称（如 "Chrome"、"Safari"、"Firefox"），
 * 而非直接使用 WURFL 数据库中的原始 {@code mobile_browser} 值。
 * 结果会经过 {@link VirtualCapabilityHandler#applyControlCapOverride} 的覆盖检查。</p>
 */

public class BrowserName implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 5571205014159290107L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        VirtualCapabilityDevice virtualCapabilityDevice = VirtualCapabilityUserAgentTool.getInstance().assignProperties(request, device);
        return VirtualCapabilityHandler.applyControlCapOverride("advertised_browser", virtualCapabilityDevice.getBrowserPairName(), device);
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "advertised_browser";
    }
}
