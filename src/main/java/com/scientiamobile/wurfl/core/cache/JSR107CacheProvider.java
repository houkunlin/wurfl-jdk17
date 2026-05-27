package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import net.sf.jsr107cache.Cache;

public class JSR107CacheProvider implements CacheProvider {
    private Cache cache;

    public JSR107CacheProvider() {
    }

    public JSR107CacheProvider(Cache cache) {
        this.cache = cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    @Override
    public InternalDevice getDevice(String deviceId) {
        return (InternalDevice) this.cache.get(deviceId);
    }

    @Override
    public void putDevice(String deviceId, InternalDevice device) {
        this.cache.put(deviceId, device);
    }

    @Override
    public void clear() {
        this.cache.clear();
    }

    public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
        return null;
    }
}
