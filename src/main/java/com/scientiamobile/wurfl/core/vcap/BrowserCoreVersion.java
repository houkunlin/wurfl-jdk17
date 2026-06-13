package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.VirtualCapabilityDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityUserAgentTool;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * 浏览器内核版本号虚拟能力评估器。
 * <p>返回浏览器底层内核引擎的版本号，如 {@code "120.0.0.0"}。
 * 原生浏览器（Chrome / Safari 等）也返回自身内核版本号。</p>
 *
 * @see BrowserCore
 */
public class BrowserCoreVersion implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 5735370437539742665L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        VirtualCapabilityDevice vcd = VirtualCapabilityUserAgentTool.getInstance().assignProperties(request, device);

        String version = vcd.getBrowserCoreVersion();
        return version != null ? version : "";
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "browser_core_version";
    }
}
