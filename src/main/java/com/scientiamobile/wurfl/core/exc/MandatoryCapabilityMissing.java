package com.scientiamobile.wurfl.core.exc;

import org.apache.commons.lang3.text.StrBuilder;

public class MandatoryCapabilityMissing extends WURFLRuntimeException {
  private static final long serialVersionUID = 233366160908694904L;
  
  private String a;
  
  public MandatoryCapabilityMissing(String paramString) {
    this("Mandatory capabilities missing from configuration: ", paramString);
  }
  
  public MandatoryCapabilityMissing(String paramString1, String paramString2) {
    super((new StrBuilder(paramString1)).append(paramString2).toString());
    this.a = paramString2;
  }
  
  public String getMissingMandatoryCapabilities() {
    return this.a;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\exc\MandatoryCapabilityMissing.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
