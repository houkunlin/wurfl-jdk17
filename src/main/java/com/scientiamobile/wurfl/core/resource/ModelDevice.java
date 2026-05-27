package com.scientiamobile.wurfl.core.resource;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class ModelDevice implements Serializable {
    @Serial
    private static final long serialVersionUID = 10L;
    private static final boolean ASSERTIONS_DISABLED = !ModelDevice.class.desiredAssertionStatus();
    private String userAgent;
    private String id;
    private String fallBack;
    private boolean actualDeviceRoot;
    private Map<String, String> capabilities;
    private Map<String, String> groupsByCapability;
    private ModelDevice ancestor;

    protected ModelDevice() {
    }

    public ModelDevice(String userAgent, String id, String fallBack, boolean actualDeviceRoot, Map<String, String> capabilities, Map<String, String> groupsByCapability) {
        Validate.notEmpty(id, "The id must be not null");
        Validate.notEmpty(fallBack, "The fallBack must be not null");
        Validate.notEmpty(userAgent, "The userAgent must be not null");
        Validate.notNull(capabilities, "The capabilities must be not null");
        Validate.notNull(groupsByCapability, "The groupsByCapability must be not null");
        Validate.noNullElements(capabilities.values(), "The capabilities can not contain null value");
        Validate.noNullElements(groupsByCapability.values(), "The capabilities can not contain null value");
        Validate.isTrue(capabilities.keySet().equals(groupsByCapability.keySet()), "The capabilities and groups must be same Set");
        this.userAgent = userAgent;
        this.id = id;
        this.fallBack = fallBack;
        this.actualDeviceRoot = actualDeviceRoot;
        this.capabilities = Collections.unmodifiableMap(capabilities);
        this.groupsByCapability = Collections.unmodifiableMap(groupsByCapability);
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    final void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getID() {
        return this.id;
    }

    public String getFallBack() {
        return this.fallBack;
    }

    final void setFallBack(String fallBack) {
        this.fallBack = fallBack;
    }

    public boolean isActualDeviceRoot() {
        return this.actualDeviceRoot;
    }

    final void setActualDeviceRoot(boolean actualDeviceRoot) {
        this.actualDeviceRoot = actualDeviceRoot;
    }

    public Map<String, String> getCapabilities() {
        return this.capabilities;
    }

    final void setCapabilities(Map<String, String> capabilities) {
        this.capabilities = capabilities;
    }

    public Map<String, String> getGroupsByCapability() {
        return this.groupsByCapability;
    }

    final void setGroupsByCapability(Map<String, String> groupsByCapability) {
        this.groupsByCapability = groupsByCapability;
    }

    public boolean defineCapability(String capabilityName) {
        return this.capabilities.containsKey(capabilityName);
    }

    public String getCapability(String capabilityName) {
        if (!ASSERTIONS_DISABLED && !this.defineCapability(capabilityName)) {
            throw new AssertionError(this.id + " do not define " + capabilityName);
        } else {
            return this.capabilities.get(capabilityName);
        }
    }

    public boolean defineGroup(String groupId) {
        return this.groupsByCapability.containsValue(groupId);
    }

    public Set<String> getGroups() {
        return new HashSet<>(this.groupsByCapability.values());
    }

    public String getGroupForCapability(String capabilityName) {
        if (!ASSERTIONS_DISABLED && !this.defineCapability(capabilityName)) {
            throw new AssertionError();
        } else {
            return this.groupsByCapability.get(capabilityName);
        }
    }

    public Set<String> getCapabilitiesNamesForGroup(String groupId) {
        if (!ASSERTIONS_DISABLED && !this.defineGroup(groupId)) {
            throw new AssertionError();
        } else {
            HashSet<String> capabilityNames = new HashSet<>();
            Iterator<Map.Entry<String, String>> iterator = this.groupsByCapability.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (entry.getValue().equals(groupId)) {
                    capabilityNames.add(entry.getKey());
                }
            }

            return capabilityNames;
        }
    }

    public Map<String, String> getCapabilitiesForGroup(String groupId) {
        HashMap<String, String> capabilities = new HashMap<>();

        for (String capabilityName : this.getCapabilitiesNamesForGroup(groupId)) {
            capabilities.put(capabilityName, this.capabilities.get(capabilityName));
        }

        return capabilities;
    }

    public ModelDevice getAncestor() {
        return this.ancestor;
    }

    public void setAncestor(ModelDevice ancestor) {
        this.ancestor = ancestor;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(11, 45);
        hashCodeBuilder.append(this.getClass()).append(this.id);
        return hashCodeBuilder.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof ModelDevice)) {
            return false;
        } else {
            ModelDevice other = (ModelDevice) obj;
            return (new EqualsBuilder()).append(this.id, other.id).isEquals();
        }
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this);
        toStringBuilder.append(this.id);
        return toStringBuilder.toString();
    }

    final void setId(String id) {
        this.id = id;
    }
}
