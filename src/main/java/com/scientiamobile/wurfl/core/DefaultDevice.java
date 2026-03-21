package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.exc.VirtualCapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.matchers.MatchType;
import com.scientiamobile.wurfl.core.vcap.VirtualCapabilityHandler;
import java.io.Serializable;
import java.util.Map;
import org.apache.commons.lang.Validate;
import org.slf4j.LoggerFactory;

public class DefaultDevice implements EnrichedDevice, Serializable {
  private static final long serialVersionUID = 11L;
  
  private InternalDevice a;
  
  private final MatchType b;
  
  private final String c;
  
  private final String d;
  
  private final String e;
  
  private final MarkupResolver f;
  
  private transient MarkUp g;
  
  private VirtualCapabilityHandler h;
  
  public DefaultDevice(InternalDevice paramInternalDevice, VirtualCapabilityHandler paramVirtualCapabilityHandler, MarkupResolver paramMarkupResolver, MatchType paramMatchType, String paramString1, String paramString2, String paramString3) {
    Validate.notNull(this.h, "The capabilitiesHandler must be not null");
    Validate.notNull(paramMarkupResolver, "The markupResolver must be not null");
    this.a = paramInternalDevice;
    this.f = paramMarkupResolver;
    this.b = paramMatchType;
    this.d = paramString1;
    this.c = paramString2;
    this.e = paramString3;
    this.h = paramVirtualCapabilityHandler;
  }
  
  public DefaultDevice(InternalDevice paramInternalDevice, MarkupResolver paramMarkupResolver, MatchType paramMatchType, String paramString1, String paramString2, String paramString3, VirtualCapabilityHandler paramVirtualCapabilityHandler) {
    Validate.notNull(paramVirtualCapabilityHandler, "The capabilitiesHandler must be not null");
    Validate.notNull(paramMarkupResolver, "The markupResolver must be not null");
    this.a = paramInternalDevice;
    this.f = paramMarkupResolver;
    this.b = paramMatchType;
    this.d = paramString1;
    this.c = paramString2;
    this.e = paramString3;
    this.h = paramVirtualCapabilityHandler;
  }
  
  public Map getVirtualCapabilities() {
    return this.h.getAllVirtualCapabilities(this);
  }
  
  public String getVirtualCapability(String paramString) {
    return this.h.getVirtualCapability(paramString, this);
  }
  
  public int getVirtualCapabilityAsInt(String paramString) {
    return this.h.getVirtualCapabilityAsInt(paramString, this);
  }
  
  public boolean getVirtualCapabilityAsBool(String paramString) {
    return this.h.getVirtualCapabilityAsBool(paramString, this);
  }
  
  public MatchType getMatchType() {
    return this.b;
  }
  
  public String getBucketMatcherName() {
    return this.c;
  }
  
  public String getMatcherName() {
    return this.d;
  }
  
  public MarkUp getMarkUp() {
    if (this.g == null)
      this.g = this.f.getMarkupForDevice(this); 
    return this.g;
  }
  
  public String toString() {
    return "[" + getId() + ", match=" + getMatchType() + ']';
  }
  
  public String getCapability(String paramString) {
    try {
      return this.a.getCapability(paramString);
    } catch (CapabilityNotDefinedException capabilityNotDefinedException) {
      try {
        return getVirtualCapability(paramString);
      } catch (VirtualCapabilityNotDefinedException virtualCapabilityNotDefinedException) {
        throw capabilityNotDefinedException;
      } 
    } 
  }
  
  public String getId() {
    return this.a.getId();
  }
  
  public String getWURFLUserAgent() {
    return this.a.getWURFLUserAgent();
  }
  
  public int getCapabilityAsInt(String paramString) {
    try {
      return this.a.getCapabilityAsInt(paramString);
    } catch (CapabilityNotDefinedException capabilityNotDefinedException) {
      try {
        return getVirtualCapabilityAsInt(paramString);
      } catch (VirtualCapabilityNotDefinedException virtualCapabilityNotDefinedException) {
        throw capabilityNotDefinedException;
      } 
    } 
  }
  
  public boolean getCapabilityAsBool(String paramString) {
    try {
      return this.a.getCapabilityAsBool(paramString);
    } catch (CapabilityNotDefinedException capabilityNotDefinedException) {
      try {
        return getVirtualCapabilityAsBool(paramString);
      } catch (VirtualCapabilityNotDefinedException virtualCapabilityNotDefinedException) {
        throw capabilityNotDefinedException;
      } 
    } 
  }
  
  public Map getCapabilities() {
    return this.a.getCapabilities();
  }
  
  public boolean isActualDeviceRoot() {
    return this.a.isActualDeviceRoot();
  }
  
  public String getDeviceRootId() {
    return this.a.getDeviceRootId();
  }
  
  public InternalDevice getInternalDevice() {
    return this.a;
  }
  
  public String getNormalizedUserAgent() {
    return this.e;
  }
  
  static {
    LoggerFactory.getLogger(h.class);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\DefaultDevice.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */