package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public abstract class GroupConsistencyException extends DeviceConsistencyException {
   private static final long serialVersionUID = 10L;
   private String groupId;

   protected GroupConsistencyException(ModelDevice device, String groupId) {
      super(device, "Group: " + groupId + " in device: " + device.getID() + " consistency exception");
      this.groupId = groupId;
   }

   protected GroupConsistencyException(ModelDevice device, String groupId, String message) {
      super(device, message);
      this.groupId = groupId;
   }

   public String getGroup() {
      return this.groupId;
   }
}
