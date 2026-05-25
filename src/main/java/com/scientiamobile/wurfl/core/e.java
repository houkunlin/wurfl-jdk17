package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.Set;
import org.apache.commons.lang.Validate;

class e implements CapabilitiesHolderFactory {
   private WURFLModel a;
   // $FF: synthetic field
   private static boolean b = !e.class.desiredAssertionStatus();

   public e(WURFLModel var1) {
      if (!b && var1 == null) {
         throw new AssertionError();
      } else {
         this.a = var1;
      }
   }

   public a create(ModelDevice var1) {
      Validate.notNull(var1, "modelDevice is null");
      return new d(new f(var1, this.a), this.a.getCapabilityCount());
   }

   public Set getModelCapabilities() {
      return this.a.getAllCapabilities();
   }
}
