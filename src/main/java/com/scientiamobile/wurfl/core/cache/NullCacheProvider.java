package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;

/**
 * Provides Null Cache functionality.
 */

public class NullCacheProvider implements CacheProvider {
    @Override
    public void clear() {
    }

    @Override
/**
 * Pu tevice.
 */

    public void putDevice(String deviceId, InternalDevice device) {
    }

    @Override
    public InternalDevice getDevice(String deviceId) {
        return null;
    }

    /**
     * Returns the interna levic ero mevic ed.
     */

    public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
        return null;
    }
}
