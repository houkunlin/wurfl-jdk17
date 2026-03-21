package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.resource.exc.DeviceNotInModelException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class f implements b {
  private WURFLModel a;
  
  private ModelDevice b;
  
  private final Logger c = LoggerFactory.getLogger(f.class);
  
  public f(ModelDevice paramModelDevice, WURFLModel paramWURFLModel) {
    this.a = paramWURFLModel;
    this.b = paramModelDevice;
  }
  
  public final Map a() {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>(this.a.getAllCapabilities().size());
    try {
      for (ModelDevice modelDevice : this.a.getDeviceHierarchy(this.b))
        hashMap.putAll(modelDevice.getCapabilities()); 
    } catch (DeviceNotInModelException deviceNotInModelException) {
      if (this.c.isErrorEnabled()) {
        StrBuilder strBuilder;
        (strBuilder = new StrBuilder()).append("Device: ").append(this.b.getID()).append(" is not in model. ").append("Capabilities will not loaded.");
        this.c.error(strBuilder.toString());
      } 
    } 
    return hashMap;
  }
  
  public final String a(Map<String, String> paramMap, String paramString) {
    String str2;
    if ((str2 = (String)paramMap.get(paramString)) != null)
      return str2; 
    ModelDevice modelDevice;
    for (modelDevice = this.b; modelDevice != null && !modelDevice.defineCapability(paramString); modelDevice = modelDevice.getAncestor());
    String str1;
    if ((str1 = (String)((modelDevice != null) ? modelDevice.getCapability(paramString) : null)) != null)
      paramMap.put(paramString, str1); 
    return str1;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
