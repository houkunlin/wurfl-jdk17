package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import org.apache.commons.lang.text.StrBuilder;

public class UserAgentOverrideException extends UserAgentConsistencyException {
   private static final long serialVersionUID = 10L;
   private String a;

   public UserAgentOverrideException(ModelDevice var1, String var2, String var3) {
      super(var1, var2, (new StrBuilder("Device: ")).append(var1).append(" override defined user-agent: ").append(var3).append(" with overriding user-agent:").append(var2).toString());
      this.a = var3;
   }

   public String getExistUserAgent() {
      return this.a;
   }
}
