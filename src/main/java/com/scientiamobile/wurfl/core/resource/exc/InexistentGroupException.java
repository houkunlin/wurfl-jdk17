package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import org.apache.commons.lang.text.StrBuilder;

public class InexistentGroupException extends GroupConsistencyException {
   private static final long serialVersionUID = 10L;

   public InexistentGroupException(ModelDevice var1, String var2) {
      super(var1, var2, (new StrBuilder("Device: ")).append(var1.getID()).append(" define unknow group: ").append(var2).toString());
   }
}
