package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.exc.VirtualCapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.matchers.MatchType;
import com.scientiamobile.wurfl.core.vcap.VirtualCapabilityHandler;
import org.apache.commons.lang3.Validate;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Implementation of Default Device.
 */

public class DefaultDevice implements EnrichedDevice, Serializable {
    @Serial
    private static final long serialVersionUID = 11L;

    static {
        LoggerFactory.getLogger(DefaultDevice.class);
    }

    private final MatchType matchType;
    private final String bucketMatcherName;
    private final String matcherName;
    private final String normalizedUserAgent;
    private final transient MarkupResolver markupResolver;
    private final transient InternalDevice internalDevice;
    private transient MarkUp markUp;
    private final transient VirtualCapabilityHandler virtualCapabilityHandler;

    public DefaultDevice(InternalDevice internalDevice, VirtualCapabilityHandler virtualCapabilityHandler, MarkupResolver markupResolver, MatchType matchType, String matcherName, String bucketMatcherName, String normalizedUserAgent) {
        Validate.notNull(virtualCapabilityHandler, "The capabilitiesHandler must be not null");
        Validate.notNull(markupResolver, "The markupResolver must be not null");
        this.internalDevice = internalDevice;
        this.markupResolver = markupResolver;
        this.matchType = matchType;
        this.matcherName = matcherName;
        this.bucketMatcherName = bucketMatcherName;
        this.normalizedUserAgent = normalizedUserAgent;
        this.virtualCapabilityHandler = virtualCapabilityHandler;
    }

    public DefaultDevice(InternalDevice internalDevice, MarkupResolver markupResolver, MatchType matchType, String matcherName, String bucketMatcherName, String normalizedUserAgent, VirtualCapabilityHandler virtualCapabilityHandler) {
        Validate.notNull(virtualCapabilityHandler, "The capabilitiesHandler must be not null");
        Validate.notNull(markupResolver, "The markupResolver must be not null");
        this.internalDevice = internalDevice;
        this.markupResolver = markupResolver;
        this.matchType = matchType;
        this.matcherName = matcherName;
        this.bucketMatcherName = bucketMatcherName;
        this.normalizedUserAgent = normalizedUserAgent;
        this.virtualCapabilityHandler = virtualCapabilityHandler;
    }

    @Override
/**
 * Returns the virtua lapabilities.
 */

    public Map<String, String> getVirtualCapabilities() {
        return this.virtualCapabilityHandler.getAllVirtualCapabilities(this);
    }

    @Override
/**
 * Returns the virtua lapability.
 */

    public String getVirtualCapability(String virtualCapabilityName) {
        return this.virtualCapabilityHandler.getVirtualCapability(virtualCapabilityName, this);
    }

    @Override
/**
 * Returns the virtua lapabilit y snt.
 */

    public int getVirtualCapabilityAsInt(String virtualCapabilityName) {
        return this.virtualCapabilityHandler.getVirtualCapabilityAsInt(virtualCapabilityName, this);
    }

    @Override
/**
 * Returns the virtua lapabilit y sool.
 */

    public boolean getVirtualCapabilityAsBool(String virtualCapabilityName) {
        return this.virtualCapabilityHandler.getVirtualCapabilityAsBool(virtualCapabilityName, this);
    }

    @Override
/**
 * Returns the matc hype.
 */

    public MatchType getMatchType() {
        return this.matchType;
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return this.bucketMatcherName;
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return this.matcherName;
    }

    @Override
/**
 * Returns the mar kp.
 */

    public MarkUp getMarkUp() {
        if (this.markUp == null) {
            this.markUp = this.markupResolver.getMarkupForDevice(this);
        }

        return this.markUp;
    }

    @Override
/**
 * Returns a string representation of this object.
 */

    public String toString() {
        return "[" + this.getId() + ", match=" + this.getMatchType() + ']';
    }

    @Override
/**
 * Returns the capability.
 */

    public String getCapability(String capabilityName) {
        try {
            return this.internalDevice.getCapability(capabilityName);
        } catch (CapabilityNotDefinedException capabilityNotDefinedException) {
            try {
                return this.getVirtualCapability(capabilityName);
            } catch (VirtualCapabilityNotDefinedException virtualCapabilityNotDefinedException) {
                throw capabilityNotDefinedException;
            }
        }
    }

    @Override
/**
 * Returns the id.
 */

    public String getId() {
        return this.internalDevice.getId();
    }

    @Override
/**
 * Returns the wurfluse rgent.
 */

    public String getWURFLUserAgent() {
        return this.internalDevice.getWURFLUserAgent();
    }

    @Override
/**
 * Returns the capabilit y snt.
 */

    public int getCapabilityAsInt(String capabilityName) {
        try {
            return this.internalDevice.getCapabilityAsInt(capabilityName);
        } catch (CapabilityNotDefinedException capabilityNotDefinedException) {
            try {
                return this.getVirtualCapabilityAsInt(capabilityName);
            } catch (VirtualCapabilityNotDefinedException virtualCapabilityNotDefinedException) {
                throw capabilityNotDefinedException;
            }
        }
    }

    @Override
/**
 * Returns the capabilit y sool.
 */

    public boolean getCapabilityAsBool(String capabilityName) {
        try {
            return this.internalDevice.getCapabilityAsBool(capabilityName);
        } catch (CapabilityNotDefinedException capabilityNotDefinedException) {
            try {
                return this.getVirtualCapabilityAsBool(capabilityName);
            } catch (VirtualCapabilityNotDefinedException virtualCapabilityNotDefinedException) {
                throw capabilityNotDefinedException;
            }
        }
    }

    @Override
/**
 * Returns the capabilities.
 */

    public Map<String, String> getCapabilities() {
        return this.internalDevice.getCapabilities();
    }

    @Override
/**
 * Returns whether this i sctua levic eoot.
 */

    public boolean isActualDeviceRoot() {
        return this.internalDevice.isActualDeviceRoot();
    }

    @Override
/**
 * Returns the devic eoo td.
 */

    public String getDeviceRootId() {
        return this.internalDevice.getDeviceRootId();
    }

    public InternalDevice getInternalDevice() {
        return this.internalDevice;
    }

    @Override
/**
 * Returns the normalize dse rgent.
 */

    public String getNormalizedUserAgent() {
        return this.normalizedUserAgent;
    }
}
