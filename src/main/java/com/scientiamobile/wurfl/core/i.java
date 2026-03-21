package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class i implements MarkupResolver, Serializable {
  private static final long serialVersionUID = 1L;
  
  private final Logger a = LoggerFactory.getLogger(i.class);
  
  public MarkUp getMarkupForDevice(InternalDevice paramInternalDevice) {
    MarkUp markUp;
    String str;
    try {
      str = paramInternalDevice.getCapability("xhtml_support_level");
      String str1 = paramInternalDevice.getCapability("preferred_markup");
    } catch (CapabilityNotDefinedException capabilityNotDefinedException) {
      this.a.error("It is not possible getting markUp from capabilities: " + capabilityNotDefinedException.getLocalizedMessage());
      throw new RuntimeException(capabilityNotDefinedException.getLocalizedMessage(), capabilityNotDefinedException);
    } 
    if (Integer.valueOf(str).intValue() >= 3) {
      markUp = MarkUp.XHTML_ADVANCED;
    } else if (Integer.valueOf(str).intValue() > 0) {
      markUp = MarkUp.XHTML_SIMPLE;
    } else if (markUp.indexOf("imode") != -1) {
      markUp = MarkUp.CHTML;
    } else {
      markUp = MarkUp.WML;
    } 
    return markUp;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\i.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */