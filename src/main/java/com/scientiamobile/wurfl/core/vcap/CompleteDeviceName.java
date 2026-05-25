package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class CompleteDeviceName implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = -65030764132400949L;

   public String eval(Device var1, WURFLRequest var2) {
      StringBuilder var4 = new StringBuilder(var1.getCapability("brand_name"));
      String var3;
      if ((var3 = var1.getCapability("model_name")).length() > 0) {
         var4.append(" ").append(var3);
      }

      if ((var3 = var1.getCapability("marketing_name")).length() > 0) {
         var4.append(" (").append(var3).append(")");
      }

      return var4.toString();
   }

   public String getHandledVirtualCapabilityName() {
      return "complete_device_name";
   }
}
