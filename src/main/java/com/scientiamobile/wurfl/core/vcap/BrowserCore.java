package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.VirtualCapabilityDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityUserAgentTool;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * 浏览器内核名称虚拟能力评估器。
 * <p>当浏览器基于其他浏览器内核（如 QQ Browser 基于 Chrome）时，
 * 返回底层内核浏览器的名称，如 {@code "Chrome"}。
 * 若本身就是原生浏览器（Chrome / Safari / Firefox 等），返回空字符串。</p>
 */
public class BrowserCore implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 2870156998172706816L;

    private static final Set<String> PRIMARY_BROWSERS = Set.of(
            "Chrome", "Chrome Mobile", "Mobile Safari", "Safari",
            "Firefox", "Firefox Mobile", "Firefox on iOS",
            "Opera", "Opera Mobile", "IE", "Edge",
            "Chromium", "Android Webkit"
    );

    @Override
    public String eval(Device device, WURFLRequest request) {
        VirtualCapabilityDevice vcd = VirtualCapabilityUserAgentTool.getInstance().assignProperties(request, device);

        String advertisedBrowser = vcd.getBrowserPairName();
        if (advertisedBrowser != null && PRIMARY_BROWSERS.contains(advertisedBrowser)) {
            return "";
        }

        String name = vcd.getBrowserCore();
        return name != null ? name : "";
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "browser_core";
    }
}
