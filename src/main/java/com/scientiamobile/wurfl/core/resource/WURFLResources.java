package com.scientiamobile.wurfl.core.resource;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

/**
 * Implementation of WURFL Resources.
 */

public final class WURFLResources {
    private final List<WURFLResource> resources = new ArrayList<>();

    public WURFLResources() {
    }

    public WURFLResources(WURFLResource... resources) {
        Validate.notNull(resources, "The resources is null");
        this.resources.addAll(Arrays.asList(resources));
    }

    public WURFLResources(Collection<WURFLResource> resources) {
        Validate.notNull(resources, "The resources is null");
        Validate.noNullElements(resources, "The resources contains null value");
        this.resources.addAll(resources);
    }

    /**
     * Size.
     */

    public final int size() {
        return this.resources.size();
    }

    public final WURFLResource get(int index) {
        return this.resources.get(index);
    }

    /**
     * Inde xf.
 */

    public final int indexOf(WURFLResource resource) {
        Validate.notNull(resource, "The resource is null");
        return this.resources.indexOf(resource);
    }

    /**
     * Release.
 */

    public final void release() {

        for (WURFLResource resource : this.resources) {
            resource.release();
        }

    }

    /**
     * Add.
 */

    public final void add(WURFLResource resource) {
        Validate.notNull(resource, "The resource must be not null");
        this.resources.add(resource);
    }

    /**
     * Remove.
 */

    public final void remove(WURFLResource resource) {
        Validate.notNull(resource, "The resource must be not null");
        this.resources.remove(resource);
    }

    /**
     * Returns an iterator over elements of this collection.
 */

    public final Iterator<WURFLResource> iterator() {
        return this.resources.iterator();
    }

    @Override
/**
 * Returns a string representation of this object.
 */

    public String toString() {
        StringBuilder builder = new StringBuilder("[");

        for (int i = 0; i < this.resources.size(); ++i) {
            WURFLResource resource = this.resources.get(i);
            builder.append(resource).append("(").append(resource.getInfo()).append(" version: ").append(resource.getVersion()).append(")");
            if (i < this.resources.size() - 1) {
                builder.append(" - ");
            }
        }

        builder.append("]");
        return builder.toString();
    }

    @Override
/**
 * Indicates whether some other object is equal to this one.
 * @param obj the reference object with which to compare
 * @return true if this object is the same as the obj argument
 */

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof WURFLResources other)) {
            return false;
        } else {
            return (new EqualsBuilder()).append(this.resources, other.resources).isEquals();
        }
    }

    @Override
/**
 * Returns whether this has hode.
 */

    public int hashCode() {
        HashCodeBuilder hashCodeBuilder;
        hashCodeBuilder = new HashCodeBuilder(53, 79);
        hashCodeBuilder.append(this.getClass()).append(this.resources);
        return hashCodeBuilder.toHashCode();
    }
}
