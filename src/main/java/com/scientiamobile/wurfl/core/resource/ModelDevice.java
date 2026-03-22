package com.scientiamobile.wurfl.core.resource;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.*;

public class ModelDevice implements Serializable {
  private static final long serialVersionUID = 10L;

  private String a;

  private String b;

  private String c;

  private boolean d;

  private Map<String, String> e;

  private Map<String, String> f;

  private ModelDevice g;

  protected ModelDevice() {}

  public ModelDevice(String paramString1, String paramString2, String paramString3, boolean paramBoolean, Map<String, String> paramMap1, Map<String, String> paramMap2) {
    Validate.notEmpty(paramString2, "The id must be not null");
    Validate.notEmpty(paramString3, "The fallBack must be not null");
    Validate.notEmpty(paramString1, "The userAgent must be not null");
    Validate.notNull(paramMap1, "The capabilities must be not null");
    Validate.notNull(paramMap2, "The groupsByCapability must be not null");
    Validate.noNullElements(paramMap1.values(), "The capabilities can not contain null value");
    Validate.noNullElements(paramMap2.values(), "The capabilities can not contain null value");
    Validate.isTrue(paramMap1.keySet().equals(paramMap2.keySet()), "The capabilities and groups must be same Set");
    this.a = paramString1;
    this.b = paramString2;
    this.c = paramString3;
    this.d = paramBoolean;
    this.e = Collections.unmodifiableMap(paramMap1);
    this.f = Collections.unmodifiableMap(paramMap2);
  }

  public String getUserAgent() {
    return this.a;
  }

  public String getID() {
    return this.b;
  }

  public String getFallBack() {
    return this.c;
  }

  public boolean isActualDeviceRoot() {
    return this.d;
  }

  public Map<String, String> getCapabilities() {
    return this.e;
  }

  public Map<String, String> getGroupsByCapability() {
    return this.f;
  }

  public boolean defineCapability(String paramString) {
    return this.e.containsKey(paramString);
  }

  public String getCapability(String paramString) {
    if (!defineCapability(paramString))
      throw new AssertionError(this.b + " do not define " + paramString);
    return this.e.get(paramString);
  }

  public boolean defineGroup(String paramString) {
    return this.f.containsValue(paramString);
  }

  public Set<String> getGroups() {
    return new HashSet<>(this.f.values());
  }

  public String getGroupForCapability(String paramString) {
    if (!defineCapability(paramString))
      throw new AssertionError();
    return this.f.get(paramString);
  }

  public Set<String> getCapabilitiesNamesForGroup(String paramString) {
    if (!defineGroup(paramString))
      throw new AssertionError();
    HashSet<String> hashSet = new HashSet<>();
      for (Map.Entry<String, String> stringStringEntry : this.f.entrySet()) {
          Map.Entry<String, String> entry;
          if (((entry = stringStringEntry).getValue()).equals(paramString))
              hashSet.add(entry.getKey());
      }
    return hashSet;
  }

  public Map<String, String> getCapabilitiesForGroup(String paramString) {
    HashMap<String, String> hashMap = new HashMap<>();
    for (String str : getCapabilitiesNamesForGroup(paramString))
      hashMap.put(str, this.e.get(str));
    return hashMap;
  }

  public ModelDevice getAncestor() {
    return this.g;
  }

  public void setAncestor(ModelDevice paramModelDevice) {
    this.g = paramModelDevice;
  }

  public int hashCode() {
    HashCodeBuilder hashCodeBuilder;
    (hashCodeBuilder = new HashCodeBuilder(11, 45)).append(getClass()).append(this.b);
    return hashCodeBuilder.toHashCode();
  }

  public boolean equals(Object paramObject) {
    EqualsBuilder equalsBuilder = new EqualsBuilder();
    if (paramObject instanceof ModelDevice) {
      paramObject = paramObject;
      equalsBuilder.append(this.b, ((ModelDevice)paramObject).b);
    } else {
      equalsBuilder.append(true, false);
    }
    return equalsBuilder.isEquals();
  }

  public String toString() {
    ToStringBuilder toStringBuilder;
    (toStringBuilder = new ToStringBuilder(this)).append(this.b);
    return toStringBuilder.toString();
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\ModelDevice.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
