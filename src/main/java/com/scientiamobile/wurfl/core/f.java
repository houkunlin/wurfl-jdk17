package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.resource.exc.DeviceNotInModelException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class f implements b {
   private WURFLModel a;
   private ModelDevice b;
   private final Logger c = LoggerFactory.getLogger(f.class);

   public f(ModelDevice var1, WURFLModel var2) {
      this.a = var2;
      this.b = var1;
   }

   public final Map a() {
      HashMap var1 = new HashMap(this.a.getAllCapabilities().size());

      try {
         for(ModelDevice var3 : this.a.getDeviceHierarchy(this.b)) {
            var1.putAll(var3.getCapabilities());
         }
      } catch (DeviceNotInModelException var4) {
         if (this.c.isErrorEnabled()) {
            StrBuilder var2;
            (var2 = new StrBuilder()).append("Device: ").append(this.b.getID()).append(" is not in model. ").append("Capabilities will not loaded.");
            this.c.error(var2.toString());
         }
      }

      return var1;
   }

   public final String a(Map var1, String var2) {
      String var3;
      if ((var3 = (String)var1.get(var2)) != null) {
         return var3;
      } else {
         for(var4 = this.b; var4 != null && !var4.defineCapability(var2); var4 = var4.getAncestor()) {
         }

         String var10000 = var4 != null ? var4.getCapability(var2) : null;
         var3 = var10000;
         if (var10000 != null) {
            var1.put(var2, var3);
         }

         return var3;
      }
   }
}
