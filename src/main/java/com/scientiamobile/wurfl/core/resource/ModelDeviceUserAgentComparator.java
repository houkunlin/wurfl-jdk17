package com.scientiamobile.wurfl.core.resource;

import java.io.Serializable;
import java.util.Comparator;

final class ModelDeviceUserAgentComparator implements Serializable, Comparator<ModelDevice> {
    static final ModelDeviceUserAgentComparator INSTANCE = new ModelDeviceUserAgentComparator();
    private static final long serialVersionUID = 101L;

    private ModelDeviceUserAgentComparator() {
    }

    public final int compare(ModelDevice left, ModelDevice right) {
        return left.getUserAgent().compareTo(right.getUserAgent());
    }
}

