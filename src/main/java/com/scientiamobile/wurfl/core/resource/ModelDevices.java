package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.utils.CollectionFactory;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ModelDevices implements Serializable {
  private static final long serialVersionUID = 10L;

  private Map<String, ModelDevice> a = CollectionFactory.createConcurrentHashMap();

  private LinkedList<String> b = new LinkedList<>();

  public ModelDevices() {}

  public ModelDevices(ModelDevices paramModelDevices) {
    this.a.putAll(paramModelDevices.a);
    this.b = (LinkedList)paramModelDevices.getDeviceIdsByInsertionOrder();
  }

  public ModelDevices(Map<String, ModelDevice> paramMap) {
    Validate.notNull(paramMap);
    Validate.noNullElements(paramMap.values());
    this.a.putAll(paramMap);
  }

  public ModelDevices(Collection<ModelDevice> paramCollection) {
    Validate.notNull(paramCollection);
    Validate.noNullElements(paramCollection);
    for (ModelDevice modelDevice : paramCollection)
      this.a.put(modelDevice.getID(), modelDevice);
  }

  public ModelDevices(ModelDevice[] paramArrayOfModelDevice) {
    this(Arrays.asList(paramArrayOfModelDevice));
  }

  public int size() {
    return this.a.size();
  }

  public boolean contains(ModelDevice paramModelDevice) {
    return this.a.containsValue(paramModelDevice);
  }

  public boolean containsId(String paramString) {
    return this.a.containsKey(paramString);
  }

  public Set<ModelDevice> getDevices() {
    return new HashSet<>(this.a.values());
  }

  public Map<String, ModelDevice> getDevicesById() {
    return Collections.unmodifiableMap(this.a);
  }

  public Iterator<ModelDevice> iterator() {
    return getDevicesById().values().iterator();
  }

  public ModelDevice getById(String paramString) {
    return (ModelDevice)this.a.get(paramString);
  }

  public void add(ModelDevice paramModelDevice) {
    this.a.put(paramModelDevice.getID(), paramModelDevice);
    if (this.a.size() > this.b.size())
      this.b.add(paramModelDevice.getID());
  }

  public List<String> getDeviceIdsByInsertionOrder() {
    return this.b;
  }

  public void addAll(Collection<ModelDevice> paramCollection) {
    Validate.notNull(paramCollection);
    Validate.noNullElements(paramCollection);
    for (ModelDevice modelDevice : paramCollection)
      this.a.put(modelDevice.getID(), modelDevice);
  }

  public void addAll(ModelDevices paramModelDevices) {
    this.a.putAll(paramModelDevices.a);
  }

  public void remove(ModelDevice paramModelDevice) {
    this.a.remove(paramModelDevice.getID());
  }

  public void removeAll(Collection<ModelDevice> paramCollection) {
    for (ModelDevice modelDevice : paramCollection)
      this.a.remove(modelDevice.getID());
  }

  public void removeAll(ModelDevices paramModelDevices) {
    for (ModelDevice modelDevice : paramModelDevices)
      this.a.remove(modelDevice.getID());
  }

  public void clear() {
    this.a.clear();
  }

  public int hashCode() {
    HashCodeBuilder hashCodeBuilder;
    (hashCodeBuilder = new HashCodeBuilder()).append(getClass()).append(this.a);
    return hashCodeBuilder.toHashCode();
  }

  public boolean equals(Object paramObject) {
    EqualsBuilder equalsBuilder;
    (equalsBuilder = new EqualsBuilder()).appendSuper(getClass().isInstance(paramObject));
    if (equalsBuilder.isEquals()) {
      paramObject = paramObject;
      equalsBuilder.append(this.a, ((ModelDevices)paramObject).a);
    }
    return equalsBuilder.isEquals();
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\ModelDevices.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
