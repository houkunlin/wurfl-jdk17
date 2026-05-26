package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.resource.exc.UserAgentOverrideException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ModelDevicesPatchMerger {
   private static final Logger LOG = LoggerFactory.getLogger(ModelDevicesPatchMerger.class);
   private static boolean ASSERTIONS_DISABLED = !ModelDevicesPatchMerger.class.desiredAssertionStatus();

   private ModelDevicesPatchMerger() {
   }

   public static ModelDevices merge(ModelDevices baseDevices, ModelDevices patchDevices) {
      ModelDevices var2 = new ModelDevices(baseDevices);

      for(Object deviceObj : patchDevices) {
         ModelDevice var3 = (ModelDevice)deviceObj;
         if (baseDevices.containsId(var3.getID())) {
            ModelDevice var4;
            ModelDevice var10000 = var4 = baseDevices.getById(var3.getID());
            ModelDevice var5 = var3;
            var3 = var10000;
            if (var5 != null && var5.getUserAgent() != null) {
               HashMap<String, String> var6;
               (var6 = new HashMap<>(var3.getCapabilities())).putAll(var5.getCapabilities());
               Map<String, String> var7 = var3.getGroupsByCapability();
               Map<String, String> var8 = var5.getGroupsByCapability();
               HashMap<String, String> var9;
               (var9 = new HashMap<>()).putAll(var7);
               var9.putAll(var8);
               if (!var5.getUserAgent().equals(var3.getUserAgent())) {
                  throw new UserAgentOverrideException(var3, var5.getUserAgent(), var3.getUserAgent());
               }

               var10000 = (new ModelDeviceBuilder(var3.getID(), var3.getUserAgent(), var5.getFallBack())).setActualDeviceRoot(var5.isActualDeviceRoot()).setCapabilitiesByGroup(var9).setCapabilities(var6).build();
            } else {
               LOG.error("invalid patching device, not patched");
               var10000 = var3;
            }

            var3 = var10000;
            if (var10000 != null) {
               var2.remove(var4);
            }
         }

         if (!ASSERTIONS_DISABLED && var3 == null) {
            throw new AssertionError("patchedDevice is null");
         }

         var2.add(var3);
      }

      return var2;
   }
}
