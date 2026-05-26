package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public class InexistentGroupException extends GroupConsistencyException {
   private static final long serialVersionUID = 10L;

   public InexistentGroupException(ModelDevice device, String groupId) {
      super(device, groupId, (new StringBuilder("Device: ")).append(device.getID()).append(" define unknow group: ").append(groupId).toString());
   }
}
