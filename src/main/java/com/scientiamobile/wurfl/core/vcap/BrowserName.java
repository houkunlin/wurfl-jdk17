package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.VirtualCapabilityDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityUserAgentTool;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serializable;

public class BrowserName implements VirtualCapabilityEvaluator, Serializable {
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
