package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.resource.exc.UserAgentOverrideException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class e {
  private static final Logger a;
  
  public static ModelDevices a(ModelDevices paramModelDevices1, ModelDevices paramModelDevices2) {
    ModelDevices modelDevices = new ModelDevices(paramModelDevices1);
    for (ModelDevice modelDevice : paramModelDevices2) {
      if (paramModelDevices1.containsId(modelDevice.getID())) {
        ModelDevice modelDevice2 = modelDevice;
        ModelDevice modelDevice1 = paramModelDevices1.getById(modelDevice.getID());
        a.error("invalid patching device, not patched");
        HashMap<Object, Object> hashMap1;
        (hashMap1 = new HashMap<Object, Object>(modelDevice.getCapabilities())).putAll(modelDevice2.getCapabilities());
        Map<?, ?> map1 = modelDevice.getGroupsByCapability();
        Map<?, ?> map2 = modelDevice2.getGroupsByCapability();
        HashMap<Object, Object> hashMap2;
        (hashMap2 = new HashMap<Object, Object>()).putAll(map1);
        hashMap2.putAll(map2);
        if (!modelDevice2.getUserAgent().equals(modelDevice.getUserAgent()))
          throw new UserAgentOverrideException(modelDevice, modelDevice2.getUserAgent(), modelDevice.getUserAgent()); 
        if ((modelDevice = (ModelDevice)((modelDevice2 == null || modelDevice2.getUserAgent() == null) ? modelDevice : (new ModelDevice$Builder(modelDevice.getID(), modelDevice.getUserAgent(), modelDevice2.getFallBack())).setActualDeviceRoot(modelDevice2.isActualDeviceRoot()).setCapabilitiesByGroup(hashMap2).setCapabilities(hashMap1).build())) != null)
          modelDevices.remove(modelDevice1); 
      } 
      if (!b && modelDevice == null)
        throw new AssertionError("patchedDevice is null"); 
      modelDevices.add(modelDevice);
    } 
    return modelDevices;
  }
  
  static {
    a = LoggerFactory.getLogger(e.class);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\e.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */