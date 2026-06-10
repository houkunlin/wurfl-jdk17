package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.matchers.MatchType;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * Provides Device functionality.
 */

public interface DeviceProvider {
    InternalDevice getInternalDevice(String deviceId);

    Device buildDevice(InternalDevice device, String userAgent, MatchType matchType, String matcherName, String bucketMatcherName);

    Device buildDevice(InternalDevice device, WURFLRequest request, MatchType matchType, String matcherName, String bucketMatcherName);
}
