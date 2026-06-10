package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.utils.CollectionFactory;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Implementation of Model Devices.
 */

public class ModelDevices implements Serializable, Iterable<ModelDevice> {
    @Serial
    private static final long serialVersionUID = 10L;
    private final Map<String, ModelDevice> devicesById;
    private LinkedList<String> deviceIdsByInsertionOrder;

    public ModelDevices() {
        this.devicesById = CollectionFactory.createConcurrentHashMap();
        this.deviceIdsByInsertionOrder = new LinkedList<>();
    }

    public ModelDevices(ModelDevices modelDevices) {
        this.devicesById = CollectionFactory.createConcurrentHashMap();
        this.deviceIdsByInsertionOrder = new LinkedList<>();
        this.devicesById.putAll(modelDevices.devicesById);
        this.deviceIdsByInsertionOrder = new LinkedList<>(modelDevices.getDeviceIdsByInsertionOrder());
    }

    public ModelDevices(Map<String, ModelDevice> devicesById) {
        this.devicesById = CollectionFactory.createConcurrentHashMap();
        this.deviceIdsByInsertionOrder = new LinkedList<>();
        Validate.notNull(devicesById, "The devicesById is null");
        Validate.noNullElements(devicesById.values(), "The devicesById contains null value");
        this.devicesById.putAll(devicesById);
    }

    public ModelDevices(Collection<ModelDevice> devices) {
        this.devicesById = CollectionFactory.createConcurrentHashMap();
        this.deviceIdsByInsertionOrder = new LinkedList<>();
        Validate.notNull(devices, "The devices is null");
        Validate.noNullElements(devices, "The devices contains null value");

        for (ModelDevice device : devices) {
            this.devicesById.put(device.getID(), device);
        }

    }

    public ModelDevices(ModelDevice[] devices) {
        this(Arrays.asList(devices));
    }

    /**
     * Size.
     */

    public int size() {
        return this.devicesById.size();
    }

    public boolean contains(ModelDevice device) {
        return this.devicesById.containsValue(device);
    }

    /**
     * Contain sd.
 */

    public boolean containsId(String deviceId) {
        return this.devicesById.containsKey(deviceId);
    }

    public Set<ModelDevice> getDevices() {
        return new HashSet<>(this.devicesById.values());
    }

    /**
     * Returns the device s yd.
 */

    public Map<String, ModelDevice> getDevicesById() {
        return Collections.unmodifiableMap(this.devicesById);
    }

    @Override
/**
 * Returns an iterator over elements of this collection.
 */

    public Iterator<ModelDevice> iterator() {
        return this.getDevicesById().values().iterator();
    }

    public ModelDevice getById(String deviceId) {
        return this.devicesById.get(deviceId);
    }

    /**
     * Add.
 */

    public void add(ModelDevice device) {
        this.devicesById.put(device.getID(), device);
        if (this.devicesById.size() > this.deviceIdsByInsertionOrder.size()) {
            this.deviceIdsByInsertionOrder.add(device.getID());
        }

    }

    /**
     * Returns the devic ed s ynsertio nrder.
 */

    public List<String> getDeviceIdsByInsertionOrder() {
        return this.deviceIdsByInsertionOrder;
    }

    public void addAll(Collection<ModelDevice> devices) {
        Validate.notNull(devices, "The devices is null");
        Validate.noNullElements(devices, "The devices contains null value");

        for (ModelDevice device : devices) {
            this.devicesById.put(device.getID(), device);
        }

    }

    /**
     * Ad dll.
 */

    public void addAll(ModelDevices devices) {
        this.devicesById.putAll(devices.devicesById);
    }

    public void remove(ModelDevice device) {
        this.devicesById.remove(device.getID());
    }

    /**
     * Remov ell.
 */

    public void removeAll(Collection<ModelDevice> devices) {
        for (ModelDevice device : devices) {
            this.devicesById.remove(device.getID());
        }

    }

    /**
     * Remov ell.
 */

    public void removeAll(ModelDevices devices) {
        for (ModelDevice device : devices) {
            this.devicesById.remove(device.getID());
        }

    }

    /**
     * Clears all cached data.
 */

    public void clear() {
        this.devicesById.clear();
    }

    @Override
/**
 * Returns whether this has hode.
 */

    public int hashCode() {
        HashCodeBuilder hashCodeBuilder;
        hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(this.getClass()).append(this.devicesById);
        return hashCodeBuilder.toHashCode();
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
        } else if (!(object instanceof ModelDevices)) {
            return false;
        } else {
            ModelDevices other = (ModelDevices) object;
            return (new EqualsBuilder()).append(this.devicesById, other.devicesById).isEquals();
        }
    }
}
