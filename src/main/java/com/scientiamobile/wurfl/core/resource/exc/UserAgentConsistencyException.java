package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import org.apache.commons.lang.text.StrBuilder;

public abstract class UserAgentConsistencyException extends DeviceConsistencyException {
   private static final long serialVersionUID = 10L;
   private String a;

   public UserAgentConsistencyException(ModelDevice var1, String var2, String var3) {
      super(var1, var3);
      this.a = var2;
   }

   public UserAgentConsistencyException(ModelDevice var1, String var2) {
      super(var1, (new StrBuilder("Device: ")).append(var1.getID()).append(" user-agent: ").append(var2).append(" consistency exception").toString());
      this.a = var2;
   }

   public String getUserAgent() {
      return this.a;
   }
}
