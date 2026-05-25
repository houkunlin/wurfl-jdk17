package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import org.apache.commons.lang.text.StrBuilder;

public class UserAgentNotUniqueException extends UserAgentConsistencyException {
   private static final long serialVersionUID = 10L;
   private ModelDevice a;

   public UserAgentNotUniqueException(ModelDevice var1, String var2, ModelDevice var3) {
      super(var1, var2, (new StrBuilder("Device: ")).append(var1).append(" define duplicate user-agent: ").append(var2).append(" defined by device: ").append(var3).toString());
      this.a = var3;
   }

   public ModelDevice getDefiningDevice() {
      return this.a;
   }
}
