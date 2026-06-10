package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;

/**
 * Provides Cache functionality.
 */

public interface CacheProvider {

    InternalDevice getDevice(String deviceId);

    InternalDevice getInternalDeviceFromDeviceId(String deviceId);

    void putDevice(String deviceId, InternalDevice device);

    void clear();
}
