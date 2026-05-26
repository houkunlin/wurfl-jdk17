package com.scientiamobile.wurfl.core.exc;

public class MandatoryCapabilityMissing extends WURFLRuntimeException {
   private static final long serialVersionUID = 233366160908694904L;
   private String a;

   public MandatoryCapabilityMissing(String var1) {
      this("Mandatory capabilities missing from configuration: ", var1);
   }

   public MandatoryCapabilityMissing(String var1, String var2) {
      super((new StringBuilder(var1)).append(var2).toString());
      this.a = var2;
   }

   public String getMissingMandatoryCapabilities() {
      return this.a;
   }
}
