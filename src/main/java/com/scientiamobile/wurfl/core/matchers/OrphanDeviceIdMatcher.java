package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

import java.util.HashSet;
import java.util.Set;

/**
 * Matcher implementation for identifying Orphan Device Id devices and browsers.
 */

public class OrphanDeviceIdMatcher extends MatcherBase {
    public OrphanDeviceIdMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        return false;
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add("opwv_v6_generic");
        requiredDeviceIds.add("opwv_v7_generic");
        requiredDeviceIds.add("opwv_v72_generic");
        requiredDeviceIds.add("upgui_generic");
        requiredDeviceIds.add("uptext_generic");
        requiredDeviceIds.add("generic_netfront_ver3");
        requiredDeviceIds.add("generic_netfront_ver3_1");
        requiredDeviceIds.add("generic_netfront_ver3_2");
        requiredDeviceIds.add("generic_netfront_ver3_3");
        requiredDeviceIds.add("generic_netfront_ver3_4");
        requiredDeviceIds.add("generic_netfront_ver3_5");
        requiredDeviceIds.add("generic_netfront_ver4_0");
        return requiredDeviceIds;
    }
}
