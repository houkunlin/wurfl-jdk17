package com.scientiamobile.wurfl.core.exc;

public class GroupNotDefinedException extends WURFLRuntimeException {
   private static final long serialVersionUID = 1L;
   private String groupId;

   public GroupNotDefinedException(String groupId, String message) {
      super(message);
      this.groupId = groupId;
   }

   public GroupNotDefinedException(String groupId) {
      this(groupId, (new StringBuilder("Group: ")).append(groupId).append(" is not defined in WURFL").toString());
   }

   public String getGroupId() {
      return this.groupId;
   }
}
