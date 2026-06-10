package com.scientiamobile.wurfl.core;

import java.util.Map;

/**
 * Implementation of Internal Device.
 */

public interface InternalDevice {
    String getId();

    String getWURFLUserAgent();

    String getCapability(String capabilityName);

    int getCapabilityAsInt(String capabilityName);

    boolean getCapabilityAsBool(String capabilityName);

    Map<String, String> getCapabilities();

    boolean isActualDeviceRoot();

    String getDeviceRootId();
}
