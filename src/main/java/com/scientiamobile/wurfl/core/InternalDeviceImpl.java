package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Implementation of Internal Device  Implementation.
 */

class InternalDeviceImpl implements InternalDevice, Serializable {
    @Serial
    private static final long serialVersionUID = 101L;
    private final String id;
    private final String wurflUserAgent;
    private final boolean actualDeviceRoot;
    private final String deviceRootId;
    private final transient CapabilitiesHolder capabilitiesHolder;
    private ModelDevice ancestorModelDevice;

    protected InternalDeviceImpl(ModelDevice modelDevice, String ancestorId, CapabilitiesHolder capabilitiesHolder) {
        this(modelDevice.getID(), modelDevice.getUserAgent(), modelDevice.isActualDeviceRoot(), ancestorId, capabilitiesHolder);
        this.ancestorModelDevice = modelDevice.getAncestor();
    }

    private InternalDeviceImpl(String id, String wurflUserAgent, boolean actualDeviceRoot, String deviceRootId, CapabilitiesHolder capabilitiesHolder) {
        Validate.notNull(capabilitiesHolder, "The capabilitiesHolder must be not null");
        this.id = id;
        this.wurflUserAgent = wurflUserAgent;
        this.actualDeviceRoot = actualDeviceRoot;
        this.deviceRootId = deviceRootId;
        this.capabilitiesHolder = capabilitiesHolder;
    }

    @Override
/**
 * Returns the id.
 */

    public String getId() {
        return this.id;
    }

    @Override
/**
 * Returns the wurfluse rgent.
 */

    public String getWURFLUserAgent() {
        return this.wurflUserAgent;
    }

    @Override
/**
 * Returns the capability.
 */

    public String getCapability(String capabilityName) {
        return this.capabilitiesHolder.getCapability(capabilityName);
    }

    final ModelDevice getAncestorModelDevice() {
        return this.ancestorModelDevice;
    }

    @Override
/**
 * Returns the capabilit y snt.
 */

    public int getCapabilityAsInt(String capabilityName) {
        return this.capabilitiesHolder.getCapabilityAsInt(capabilityName);
    }

    @Override
/**
 * Returns the capabilit y sool.
 */

    public boolean getCapabilityAsBool(String capabilityName) {
        String originalCapabilityName = capabilityName;
        capabilityName = this.capabilitiesHolder.getCapability(capabilityName);
        if (capabilityName != null && capabilityName.toLowerCase(Locale.ENGLISH).equals("true")) {
            return true;
        } else if (capabilityName != null && capabilityName.toLowerCase(Locale.ENGLISH).equals("false")) {
            return false;
        } else {
            throw new NumberFormatException("WURFL invalid capability value: " + originalCapabilityName + " expected \"true\" or \"false\", received: \"" + capabilityName + "\"");
        }
    }


    @Override
/**
 * Returns the capabilities.
 */

    public Map<String, String> getCapabilities() {
        Map<String, String> allCapabilities = this.capabilitiesHolder.getCapabilities();
        HashMap<String, String> filteredCapabilities = new HashMap<>(allCapabilities.size());

        for (String s : allCapabilities.keySet()) {
            String capabilityName;
            capabilityName = s;
            if (!capabilityName.startsWith("controlcap_")) {
                filteredCapabilities.put(capabilityName, allCapabilities.get(capabilityName));
            }
        }

        return filteredCapabilities;
    }

    @Override
/**
 * Returns whether this i sctua levic eoot.
 */

    public boolean isActualDeviceRoot() {
        return this.actualDeviceRoot;
    }

    @Override
/**
 * Returns the devic eoo td.
 */

    public String getDeviceRootId() {
        String rootId = this.deviceRootId;
        if (this.deviceRootId.equals("generic")) {
            rootId = "";
        }

        return rootId;
    }

    @Override
/**
 * Indicates whether some other object is equal to this one.
 * @param obj the reference object with which to compare
 * @return true if this object is the same as the obj argument
 */

    public boolean equals(Object other) {
        EqualsBuilder eb;
        eb = new EqualsBuilder();
        eb.appendSuper(this.getClass().isInstance(other));
        if (eb.isEquals()) {
            eb.append(this.id, ((InternalDeviceImpl) other).id);
        }

        return eb.isEquals();
    }

    @Override
/**
 * Returns whether this has hode.
 */

    public int hashCode() {
        return (new HashCodeBuilder(63, 89)).append(this.getClass()).append(this.id).toHashCode();
    }

    @Override
/**
 * Returns a string representation of this object.
 */

    public String toString() {
        return "[" + this.id + ", match=, matcher=]";
    }
}
