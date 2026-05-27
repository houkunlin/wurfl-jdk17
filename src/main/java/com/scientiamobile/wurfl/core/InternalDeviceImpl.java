package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

class InternalDeviceImpl implements InternalDevice, Serializable {
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
    public String getId() {
        return this.id;
    }

    @Override
    public String getWURFLUserAgent() {
        return this.wurflUserAgent;
    }

    @Override
    public String getCapability(String capabilityName) {
        return this.capabilitiesHolder.getCapability(capabilityName);
    }

    final ModelDevice getAncestorModelDevice() {
        return this.ancestorModelDevice;
    }

    @Override
    public int getCapabilityAsInt(String capabilityName) {
        return this.capabilitiesHolder.getCapabilityAsInt(capabilityName);
    }

    @Override
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
    public Map<String, String> getCapabilities() {
        Map<String, String> allCapabilities = this.capabilitiesHolder.getCapabilities();
        HashMap<String, String> filteredCapabilities = new HashMap<>(allCapabilities.size());
        Iterator<String> it = allCapabilities.keySet().iterator();

        while (it.hasNext()) {
            String capabilityName;
            capabilityName = it.next();
            if (!capabilityName.startsWith("controlcap_")) {
                filteredCapabilities.put(capabilityName, allCapabilities.get(capabilityName));
            }
        }

        return filteredCapabilities;
    }

    @Override
    public boolean isActualDeviceRoot() {
        return this.actualDeviceRoot;
    }

    @Override
    public String getDeviceRootId() {
        String rootId = this.deviceRootId;
        if (this.deviceRootId.equals("generic")) {
            rootId = "";
        }

        return rootId;
    }

    @Override
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
    public int hashCode() {
        return (new HashCodeBuilder(63, 89)).append(this.getClass()).append(this.id).toHashCode();
    }

    @Override
    public String toString() {
        return "[" + this.id + ", match=, matcher=]";
    }
}
