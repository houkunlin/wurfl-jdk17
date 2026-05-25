package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.exc.MandatoryCapabilityMissing;
import com.scientiamobile.wurfl.core.matchers.MatchType;
import com.scientiamobile.wurfl.core.request.DefaultWURFLRequest;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.ModelDeviceWithAncestorId;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.vcap.VirtualCapabilityHandler;
import java.util.Set;
import org.apache.commons.lang.Validate;
import org.slf4j.LoggerFactory;

class g implements DeviceProvider {
   private final MarkupResolver a;
   private final CapabilitiesHolderFactory b;
   private final WURFLModel c;
   // $FF: synthetic field
   private static boolean d = !g.class.desiredAssertionStatus();

   public g(WURFLModel var1, CapabilitiesHolderFactory var2, MarkupResolver var3) {
      LoggerFactory.getLogger(g.class);
      if (!d && var1 == null) {
         throw new AssertionError();
      } else {
         this.c = var1;
         Validate.notNull(var2, "capabilitiesHolderFactory must be not null.");
         Validate.notNull(var3, "markupResolver must be not null.");
         this.a = var3;
         this.b = var2;
         Set var5 = this.b.getModelCapabilities();
         Set var6 = VirtualCapabilityHandler.getMandatoryCapabilities();
         StringBuilder var8 = new StringBuilder();

         for(String var4 : var6) {
            if (!var5.contains(var4)) {
               var8.append(var4).append(", ");
            }
         }

         if (var8.length() > 0) {
            throw new MandatoryCapabilityMissing(var8.substring(0, var8.length() - 2));
         }
      }
   }

   public g(WURFLModel var1, CapabilitiesHolderFactory var2) {
      this(var1, var2, new i());
   }

   public InternalDevice getInternalDevice(String var1) {
      Validate.notNull(var1, "The deviceId must be not null");
      ModelDeviceWithAncestorId var3 = this.a(var1);
      if (!d && var3.getModelDevice() == null) {
         throw new AssertionError("modelDevice is null");
      } else {
         ModelDevice var2 = var3.getModelDevice();
         a var4 = this.b.create(var2);
         return new h(var3.getModelDevice(), var3.getAncestorId(), var4);
      }
   }

   public Device buildDevice(InternalDevice var1, String var2, MatchType var3, String var4, String var5) {
      return this.buildDevice(var1, (WURFLRequest)(new DefaultWURFLRequest(var2, (UserAgentNormalizer)null, UserAgentPriority.OverrideSideloadedBrowserUserAgent, EngineTarget.fastDesktopBrowserMatch)), var3, var4, var5);
   }

   public Device buildDevice(InternalDevice var1, WURFLRequest var2, MatchType var3, String var4, String var5) {
      Validate.notNull(var1, "The internal device must be not null");
      String var6;
      Validate.notEmpty(var6 = var1.getId(), "The id must be not null String");
      ModelDeviceWithAncestorId var7 = this.a(var6);
      if (!d && var7.getModelDevice() == null) {
         throw new AssertionError("modelDevice is null");
      } else {
         return new DefaultDevice(var1, this.a, var3, var4, var5, var2.getNormalizedDeviceUserAgent(), new VirtualCapabilityHandler(var2));
      }
   }

   private final ModelDeviceWithAncestorId a(String var1) {
      ModelDevice var3 = this.c.getDeviceById(var1);
      String var2 = this.c.getDeviceAncestor(var3).getID();
      return new ModelDeviceWithAncestorId(var3, var2);
   }
}
