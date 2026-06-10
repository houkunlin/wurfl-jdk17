package com.scientiamobile.wurfl.core.resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * Implementation of Model Devices Snapshot.
 */

public final class ModelDevicesSnapshot implements Serializable, Comparable<ModelDevicesSnapshot> {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String info;
    private final String version;
    private final boolean patch;
    private final ModelDevices devices;
    private transient String cachedKey;
    private final String smid;

    public ModelDevicesSnapshot(String info, String version, boolean patch, ModelDevices devices, String smid) {
        this.info = info;
        this.version = version;
        this.patch = patch;
        this.devices = devices;
        this.smid = smid;
    }

    /**
     * Returns the snapsho tey.
     */

    public final String getSnapshotKey() {
        if (this.cachedKey == null) {
            StringBuilder builder = (new StringBuilder()).append(this.patch ? "Patch" : "Root").append(":").append(this.info);
            if (StringUtils.isNotBlank(this.version)) {
                builder.append(":").append(this.version);
            }

            this.cachedKey = builder.toString();
        }

        return this.cachedKey;
    }

    /**
     * Cop yevices.
 */

    public final ModelDevices copyDevices() {
        return new ModelDevices(this.devices);
    }

    @Override
/**
 * Returns whether this has hode.
 */

    public int hashCode() {
        HashCodeBuilder hashCodeBuilder;
        hashCodeBuilder = new HashCodeBuilder(33, 55);
        hashCodeBuilder.append(this.info).append(this.version);
        return hashCodeBuilder.toHashCode();
    }

    @Override
/**
 * Indicates whether some other object is equal to this one.
 * @param obj the reference object with which to compare
 * @return true if this object is the same as the obj argument
 */

    public boolean equals(Object other) {
        if (!this.getClass().isInstance(other)) {
            return false;
        }

        ModelDevicesSnapshot o = (ModelDevicesSnapshot) other;
        EqualsBuilder equalsBuilder;
        equalsBuilder = new EqualsBuilder();
        equalsBuilder.append(this.getSnapshotKey(), o.getSnapshotKey());
        return equalsBuilder.isEquals();
    }

    @Override
/**
 * Returns a string representation of this object.
 */

    public String toString() {
        ToStringBuilder toStringBuilder;
        toStringBuilder = new ToStringBuilder(this);
        toStringBuilder.append(this.info).append(this.version);
        return toStringBuilder.toString();
    }

    final String getSmid() {
        return this.smid;
    }

    /**
     * Compar eo.
 */

    public final int compareTo(ModelDevicesSnapshot other) {
        CompareToBuilder compareToBuilder;
        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(this.info, other.info).append(this.version, other.version);
        return compareToBuilder.toComparison();
    }
}
