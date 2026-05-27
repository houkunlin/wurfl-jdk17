package com.scientiamobile.wurfl.core.exc;

public class MandatoryCapabilityMissing extends WURFLRuntimeException {
   private static final long serialVersionUID = 233366160908694904L;
   private String missingMandatoryCapabilities;

   public MandatoryCapabilityMissing(String missingMandatoryCapabilities) {
      this("Mandatory capabilities missing from configuration: ", missingMandatoryCapabilities);
   }

   public MandatoryCapabilityMissing(String messagePrefix, String missingMandatoryCapabilities) {
      super(messagePrefix + missingMandatoryCapabilities);
      this.missingMandatoryCapabilities = missingMandatoryCapabilities;
   }

   public String getMissingMandatoryCapabilities() {
      return this.missingMandatoryCapabilities;
   }
}
