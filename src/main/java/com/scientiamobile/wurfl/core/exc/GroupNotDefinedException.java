package com.scientiamobile.wurfl.core.exc;

public class GroupNotDefinedException extends WURFLRuntimeException {
   private static final long serialVersionUID = 1L;
   private String a;

   public GroupNotDefinedException(String var1, String var2) {
      super(var2);
      this.a = var1;
   }

   public GroupNotDefinedException(String var1) {
      this(var1, (new StringBuilder("Group: ")).append(var1).append(" is not defined in WURFL").toString());
   }

   public String getGroupId() {
      return this.a;
   }
}
