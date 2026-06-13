package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.VirtualCapabilityDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityUserAgentTool;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * 浏览器内核名称虚拟能力评估器。
 * <p>返回浏览器底层内核引擎的名称。当浏览器基于其他浏览器内核（如 QQ Browser 基于 Chrome）时，
 * 返回底层内核浏览器的名称，如 {@code "Chrome"}。原生浏览器（Chrome / Safari 等）也返回自身内核名称，
 * 如 {@code "Chrome"} / {@code "WebKit"}。</p>
 */
public class BrowserCore implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 2870156998172706816L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        VirtualCapabilityDevice vcd = VirtualCapabilityUserAgentTool.getInstance().assignProperties(request, device);

        String name = vcd.getBrowserCore();
        return name != null ? name : "";
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "browser_core";
    }
}
