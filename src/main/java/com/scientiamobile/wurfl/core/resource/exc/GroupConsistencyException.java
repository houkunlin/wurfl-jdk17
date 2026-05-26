package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public abstract class GroupConsistencyException extends DeviceConsistencyException {
   private static final long serialVersionUID = 10L;
   private String groupId;

   public GroupConsistencyException(ModelDevice device, String groupId) {
      super(device, (new StringBuilder("Group: ")).append(groupId).append(" in device: ").append(device.getID()).append(" consistency exception").toString());
      this.groupId = groupId;
   }

   public GroupConsistencyException(ModelDevice device, String groupId, String message) {
      super(device, message);
      this.groupId = groupId;
   }

   public String getGroup() {
      return this.groupId;
   }
}
