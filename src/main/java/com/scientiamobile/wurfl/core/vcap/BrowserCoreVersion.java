package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.VirtualCapabilityDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityUserAgentTool;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * 浏览器内核版本号虚拟能力评估器。
 * <p>当浏览器基于其他浏览器内核时，返回底层内核浏览器的版本号，如 {@code "120.0.0.0"}。
 * 若本身就是原生浏览器，返回空字符串。</p>
 *
 * @see BrowserCore
 */
public class BrowserCoreVersion implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 5735370437539742665L;

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

        String version = vcd.getBrowserCoreVersion();
        return version != null ? version : "";
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "browser_core_version";
    }
}
