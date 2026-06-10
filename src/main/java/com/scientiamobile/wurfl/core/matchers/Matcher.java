package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * Matcher implementation for identifying  devices and browsers.
 */

interface Matcher {
    boolean canHandle(WURFLRequest request);

    DeviceInfo match(WURFLRequest request);

    String normalize(String userAgent);

    MatcherFilter getFilter();

    String getMatcherName();
}
