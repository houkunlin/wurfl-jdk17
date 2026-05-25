package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

public class FormFactor extends a {
   private static final long serialVersionUID = -3936563826288495198L;

   public String eval(Device var1, WURFLRequest var2) {
      if (var1.getVirtualCapabilityAsBool("is_robot")) {
         return "Robot";
      } else if (var1.getCapabilityAsBool("ux_full_desktop")) {
         return "Desktop";
      } else if (var1.getCapabilityAsBool("is_smarttv")) {
         return "Smart-TV";
      } else if (!var1.getCapabilityAsBool("is_wireless_device")) {
         return "Other Non-Mobile";
      } else if (var1.getCapabilityAsBool("is_tablet")) {
         return "Tablet";
      } else if (var1.getVirtualCapabilityAsBool("is_smartphone")) {
         return "Smartphone";
      } else {
         return var1.getCapabilityAsBool("can_assign_phone_number") ? "Feature Phone" : "Other Mobile";
      }
   }

   public String getHandledVirtualCapabilityName() {
      return "form_factor";
   }
}
