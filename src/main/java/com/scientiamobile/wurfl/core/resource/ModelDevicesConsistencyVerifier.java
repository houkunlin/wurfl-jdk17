package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.resource.exc.BadCapabilityGroupException;
import com.scientiamobile.wurfl.core.resource.exc.CircularHierarchyException;
import com.scientiamobile.wurfl.core.resource.exc.GenericNotDefinedException;
import com.scientiamobile.wurfl.core.resource.exc.InexistentCapabilityException;
import com.scientiamobile.wurfl.core.resource.exc.InexistentGroupException;
import com.scientiamobile.wurfl.core.resource.exc.OrphanHierarchyException;
import com.scientiamobile.wurfl.core.resource.exc.RedefinedDeviceException;
import com.scientiamobile.wurfl.core.resource.exc.UserAgentNotUniqueException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

final class ModelDevicesConsistencyVerifier {
   private static boolean ASSERTIONS_DISABLED = !ModelDevicesConsistencyVerifier.class.desiredAssertionStatus();

   private ModelDevicesConsistencyVerifier() {
   }

   public static void verifyModelDevices(ModelDevices devices) {
      HashMap<String, ModelDevice> var1 = new HashMap<>();
      HashSet<String> var2 = new HashSet<>();
      if (!ASSERTIONS_DISABLED && devices == null) {
         throw new AssertionError("devices is null");
      } else if (!devices.containsId("generic")) {
         throw new GenericNotDefinedException();
      } else {
         Iterator var3 = devices.iterator();

         while(var3.hasNext()) {
            ModelDevice var4;
            ModelDevice var5 = var4 = (ModelDevice)var3.next();
            if (!ASSERTIONS_DISABLED && var5 == null) {
               throw new AssertionError("device is null");
            }

            if (var1.containsKey(var5.getUserAgent())) {
               ModelDevice var7 = var1.get(var5.getUserAgent());
               throw new UserAgentNotUniqueException(var5, var5.getUserAgent(), var7);
            }

            var1.put(var4.getUserAgent(), var4);
            verifyHierarchy(var4, devices, var2);
            var2.add(var4.getID());
            verifyGroups(var4, devices);
            verifyCapabilities(var4, devices);
         }

      }
   }

   private static void verifyHierarchy(ModelDevice device, ModelDevices devices, Set<String> visited) {
      if (!ASSERTIONS_DISABLED && device == null) {
         throw new AssertionError("device is null");
      } else if (!ASSERTIONS_DISABLED && devices == null) {
         throw new AssertionError("devices is null");
      } else {
         ArrayList<ModelDevice> var3 = new ArrayList<>(10);
         String var5 = device.getID();
         if (!ASSERTIONS_DISABLED && StringUtils.isEmpty(var5)) {
            throw new AssertionError();
         } else {
            var3.add(devices.getById(var5));

            while(!"generic".equals(var5)) {
               var5 = devices.getById(var5).getFallBack();
               if (!ASSERTIONS_DISABLED && StringUtils.isEmpty(var5)) {
                  throw new AssertionError();
               }

               if (visited.contains(var5)) {
                  return;
               }

               if (!devices.containsId(var5)) {
                  throw new OrphanHierarchyException(var3);
               }

               int var4;
               if ((var4 = var3.indexOf(devices.getById(var5))) != -1) {
                  LinkedList<ModelDevice> var6 = new LinkedList<>(var3.subList(var4, var3.size()));
                  throw new CircularHierarchyException(var6);
               }

               var3.add(devices.getById(var5));
            }

         }
      }
   }

   private static void verifyGroups(ModelDevice device, ModelDevices devices) {
      if (!ASSERTIONS_DISABLED && device == null) {
         throw new AssertionError("device is null");
      } else if (!ASSERTIONS_DISABLED && devices == null) {
         throw new AssertionError("devices is null");
      } else {
         ModelDevice var4 = devices.getById("generic");
         Set<String> var2 = device.getGroups();
         Set<String> var5 = var4.getGroups();

         for(Object groupObj : var2) {
            String group = (String)groupObj;
            if (!var5.contains(group)) {
               throw new InexistentGroupException(device, group);
            }
         }

      }
   }

   private static void verifyCapabilities(ModelDevice device, ModelDevices devices) {
      if (!ASSERTIONS_DISABLED && device == null) {
         throw new AssertionError("device is null");
      } else if (!ASSERTIONS_DISABLED && devices == null) {
         throw new AssertionError("devices is null");
      } else if (!ASSERTIONS_DISABLED && !devices.containsId("generic")) {
         throw new AssertionError("device do not containing generic");
      } else {
         ModelDevice var7;
         Map<String, String> var2 = (var7 = devices.getById("generic")).getCapabilities();

         for(Object capabilityNameObj : device.getCapabilities().keySet()) {
            String capabilityName = (String)capabilityNameObj;
            if (!var2.containsKey(capabilityName)) {
               throw new InexistentCapabilityException(device, capabilityName);
            }

            String var5 = device.getGroupForCapability(capabilityName);
            String var6 = var7.getGroupForCapability(capabilityName);
            if (!var5.equals(var6)) {
               throw new BadCapabilityGroupException(device, capabilityName, var5, var6);
            }
         }

      }
   }

   public static void verifyNoRedefinedDevices(ModelDevices patchDevices, ModelDevices baseDevices) {
      Iterator var7 = patchDevices.getDevices().iterator();

      while(var7.hasNext()) {
         ModelDevice var2;
         String var3 = (var2 = (ModelDevice)var7.next()).getUserAgent();
         String var4 = var2.getID();
         String var5 = var2.getFallBack();
         ModelDevice var6;
         if ((var6 = baseDevices.getById(var4)) == null) {
            return;
         }

         if (StringUtils.isEmpty(var5)) {
            throw new RedefinedDeviceException("Patched device with id " + var4 + "does not provide a value for fall_back");
         }

         if (!var3.equals(var6.getUserAgent())) {
            throw new RedefinedDeviceException(var2, var6, "user agent");
         }

         if (!var5.equals(var6.getFallBack())) {
            throw new RedefinedDeviceException(var2, var6, "fall_back");
         }
      }

   }

   static {
      LoggerFactory.getLogger(ModelDevicesConsistencyVerifier.class);
   }
}
