package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import org.apache.commons.lang3.Validate;

import java.util.Set;

class DefaultCapabilitiesHolderFactory implements CapabilitiesHolderFactory {
    private static boolean assertionsDisabled = !DefaultCapabilitiesHolderFactory.class.desiredAssertionStatus();
    private WURFLModel wurflModel;

    public DefaultCapabilitiesHolderFactory(WURFLModel wurflModel) {
        if (!assertionsDisabled && wurflModel == null) {
            throw new AssertionError();
        } else {
            this.wurflModel = wurflModel;
        }
    }

    @Override
    public CapabilitiesHolder create(ModelDevice modelDevice) {
        Validate.notNull(modelDevice, "modelDevice is null");
        return new CachingCapabilitiesHolder(new DeviceCapabilitiesProvider(modelDevice, this.wurflModel), this.wurflModel.getCapabilityCount());
    }

    @Override
    public Set<String> getModelCapabilities() {
        return this.wurflModel.getAllCapabilities();
    }
}

