package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import org.apache.commons.lang3.text.StrBuilder;

public abstract class GroupConsistencyException extends DeviceConsistencyException {
   private static final long serialVersionUID = 10L;
   private String a;

   public GroupConsistencyException(ModelDevice var1, String var2) {
      super(var1, (new StrBuilder("Group: ")).append(var2).append(" in device: ").append(var1.getID()).append(" consistency exception").toString());
      this.a = var2;
   }

   public GroupConsistencyException(ModelDevice var1, String var2, String var3) {
      super(var1, var3);
      this.a = var2;
   }

   public String getGroup() {
      return this.a;
   }
}
