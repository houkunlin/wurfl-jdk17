package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.Validate;

public class WURFLUtils {
  private final WURFLModel a;
  
  private final DeviceProvider b;
  
  private final m c;
  
  WURFLUtils(WURFLModel paramWURFLModel, DeviceProvider paramDeviceProvider, m paramm) {
    this.a = paramWURFLModel;
    this.b = paramDeviceProvider;
    this.c = paramm;
  }
  
  public String getVersion() {
    return this.a.getVersion();
  }
  
  public boolean isDeviceDefined(String paramString) {
    Validate.notEmpty(paramString, "deviceId must be not null");
    return this.a.isDeviceDefined(paramString);
  }
  
  public ModelDevice getModelDeviceById(String paramString) {
    Validate.notEmpty(paramString, "The id must be not null Set");
    return this.a.getDeviceById(paramString);
  }
  
  public Set getModelDevices(Set paramSet) {
    Validate.notNull(paramSet, "The ids must be not null Set");
    Validate.noNullElements(paramSet, "The ids must not containing null elements");
    Validate.allElementsOfType(paramSet, String.class, "The ids must containing right devices id");
    return this.a.getDevices(paramSet);
  }
  
  public Set getAllDevicesId() {
    return this.a.getAllDevicesId();
  }
  
  public Set getAllModelDevices() {
    return this.a.getAllDevices();
  }
  
  public List getModelDeviceHierarchy(ModelDevice paramModelDevice) {
    Validate.notNull(paramModelDevice, "The root ModelDevice must be not null");
    return this.a.getDeviceHierarchy(paramModelDevice);
  }
  
  public ModelDevice getModelDeviceFallback(ModelDevice paramModelDevice) {
    Validate.notNull(paramModelDevice, "The target ModelDevice must be not null");
    return this.a.getDeviceFallback(paramModelDevice);
  }
  
  public ModelDevice getModelDeviceAncestor(ModelDevice paramModelDevice) {
    Validate.notNull(paramModelDevice, "The root ModelDevice must be not null");
    return this.a.getDeviceAncestor(paramModelDevice);
  }
  
  public boolean isCapabilityDefined(String paramString) {
    Validate.notEmpty(paramString, "The capabilityName must be not null");
    return this.a.isCapabilityDefined(paramString);
  }
  
  public Set getAllCapabilities() {
    return this.a.getAllCapabilities();
  }
  
  public String getGroupByCapability(String paramString) {
    Validate.notEmpty(paramString, "The capabilityName must be not null");
    return this.a.getGroupByCapability(paramString);
  }
  
  public ModelDevice getModelDeviceWhereCapabilityIsDefined(ModelDevice paramModelDevice, String paramString) {
    Validate.notNull(paramModelDevice, "The rootDevice must be not null Set");
    Validate.notEmpty(paramString, "The capabilityName must be not null");
    return this.a.getDeviceWhereCapabilityIsDefined(paramModelDevice, paramString);
  }
  
  public boolean isGroupDefined(String paramString) {
    Validate.notEmpty(paramString, "The groupName must be not null");
    return this.a.isGroupDefined(paramString);
  }
  
  public Set getAllGroups() {
    return this.a.getAllGroups();
  }
  
  public Set getCapabilitiesForGroup(String paramString) {
    Validate.notEmpty(paramString, "The groupName must be not null");
    return this.a.getCapabilitiesForGroup(paramString);
  }
  
  public InternalDevice getInternalDeviceById(String paramString) {
    return this.b.getInternalDevice(paramString);
  }
  
  public Device getDeviceById(String paramString) {
    return this.c.b(paramString);
  }
  
  public Device getDeviceById(String paramString, WURFLRequest paramWURFLRequest) {
    return this.c.a(paramString, paramWURFLRequest);
  }
  
  public Device getDeviceById(String paramString, HttpServletRequest paramHttpServletRequest) {
    return this.c.a(paramString, paramHttpServletRequest);
  }
  
  public Set getAllDevices() {
    Set set = getAllDevicesId();
    HashSet<Device> hashSet = new HashSet(set.size());
    for (String str : set) {
      Device device = getDeviceById(str);
      hashSet.add(device);
    } 
    return hashSet;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\WURFLUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */