package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.matchers.MatchType;

import java.util.Map;

public interface Device extends InternalDevice {
    MatchType getMatchType();

    String getVirtualCapability(String capabilityName);

    int getVirtualCapabilityAsInt(String capabilityName);

    boolean getVirtualCapabilityAsBool(String capabilityName);

    Map<String, String> getVirtualCapabilities();

    MarkUp getMarkUp();
}
