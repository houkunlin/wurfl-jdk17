package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class i implements MarkupResolver, Serializable {
   private static final long serialVersionUID = 1L;
   private final Logger a = LoggerFactory.getLogger(i.class);

   public MarkUp getMarkupForDevice(InternalDevice var1) {
      String var2;
      try {
         var2 = var1.getCapability("xhtml_support_level");
         var4 = var1.getCapability("preferred_markup");
      } catch (CapabilityNotDefinedException var3) {
         this.a.error("It is not possible getting markUp from capabilities: " + var3.getLocalizedMessage());
         throw new RuntimeException(var3.getLocalizedMessage(), var3);
      }

      MarkUp var5;
      if (Integer.valueOf(var2) >= 3) {
         var5 = MarkUp.XHTML_ADVANCED;
      } else if (Integer.valueOf(var2) > 0) {
         var5 = MarkUp.XHTML_SIMPLE;
      } else if (var4.indexOf("imode") != -1) {
         var5 = MarkUp.CHTML;
      } else {
         var5 = MarkUp.WML;
      }

      return var5;
   }
}
