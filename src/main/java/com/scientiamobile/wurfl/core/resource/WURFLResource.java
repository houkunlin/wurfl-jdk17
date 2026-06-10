package com.scientiamobile.wurfl.core.resource;

/**
 * Implementation of WURFL Resource.
 */

public interface WURFLResource {
    ModelDevicesSnapshot getData(String... params);

    String getInfo();

    String getVersion();

    void release();

    String getOriginalPath();
}
