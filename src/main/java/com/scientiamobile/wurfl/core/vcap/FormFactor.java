package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

public class FormFactor extends AbstractVirtualCapabilityEvaluator {
   private static final long serialVersionUID = -3936563826288495198L;

   @Override
   public String eval(Device device, WURFLRequest request) {
      if (device.getVirtualCapabilityAsBool("is_robot")) {
         return "Robot";
      } else if (device.getCapabilityAsBool("ux_full_desktop")) {
         return "Desktop";
      } else if (device.getCapabilityAsBool("is_smarttv")) {
         return "Smart-TV";
      } else if (!device.getCapabilityAsBool("is_wireless_device")) {
         return "Other Non-Mobile";
      } else if (device.getCapabilityAsBool("is_tablet")) {
         return "Tablet";
      } else if (device.getVirtualCapabilityAsBool("is_smartphone")) {
         return "Smartphone";
      } else {
         return device.getCapabilityAsBool("can_assign_phone_number") ? "Feature Phone" : "Other Mobile";
      }
   }

   @Override
   public String getHandledVirtualCapabilityName() {
      return "form_factor";
   }
}
