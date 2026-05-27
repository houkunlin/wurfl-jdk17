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
      HashMap<String, ModelDevice> deviceByUserAgent = new HashMap<>();
      HashSet<String> visitedDeviceIds = new HashSet<>();
      if (!ASSERTIONS_DISABLED && devices == null) {
         throw new AssertionError("devices is null");
      } else if (!devices.containsId("generic")) {
         throw new GenericNotDefinedException();
      } else {
         Iterator<ModelDevice> iterator = devices.iterator();

         while(iterator.hasNext()) {
            ModelDevice device = iterator.next();
            if (!ASSERTIONS_DISABLED && device == null) {
               throw new AssertionError("device is null");
            }

            if (deviceByUserAgent.containsKey(device.getUserAgent())) {
               ModelDevice existing = deviceByUserAgent.get(device.getUserAgent());
               throw new UserAgentNotUniqueException(device, device.getUserAgent(), existing);
            }

            deviceByUserAgent.put(device.getUserAgent(), device);
            verifyHierarchy(device, devices, visitedDeviceIds);
            visitedDeviceIds.add(device.getID());
            verifyGroups(device, devices);
            verifyCapabilities(device, devices);
         }

      }
   }

   private static void verifyHierarchy(ModelDevice device, ModelDevices devices, Set<String> visited) {
      if (!ASSERTIONS_DISABLED && device == null) {
         throw new AssertionError("device is null");
      } else if (!ASSERTIONS_DISABLED && devices == null) {
         throw new AssertionError("devices is null");
      } else {
         ArrayList<ModelDevice> hierarchy = new ArrayList<>(10);
         String currentDeviceId = device.getID();
         if (!ASSERTIONS_DISABLED && StringUtils.isEmpty(currentDeviceId)) {
            throw new AssertionError();
         } else {
            hierarchy.add(devices.getById(currentDeviceId));

            while(!"generic".equals(currentDeviceId)) {
               currentDeviceId = devices.getById(currentDeviceId).getFallBack();
               if (!ASSERTIONS_DISABLED && StringUtils.isEmpty(currentDeviceId)) {
                  throw new AssertionError();
               }

               if (visited.contains(currentDeviceId)) {
                  return;
               }

               if (!devices.containsId(currentDeviceId)) {
                  throw new OrphanHierarchyException(hierarchy);
               }

               int circularIndex;
               circularIndex = hierarchy.indexOf(devices.getById(currentDeviceId));
               if (circularIndex != -1) {
                  LinkedList<ModelDevice> circularHierarchy = new LinkedList<>(hierarchy.subList(circularIndex, hierarchy.size()));
                  throw new CircularHierarchyException(circularHierarchy);
               }

               hierarchy.add(devices.getById(currentDeviceId));
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
         ModelDevice genericDevice = devices.getById("generic");
         Set<String> deviceGroups = device.getGroups();
         Set<String> genericGroups = genericDevice.getGroups();

         for(Object groupObj : deviceGroups) {
            String group = (String)groupObj;
            if (!genericGroups.contains(group)) {
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
         ModelDevice genericDevice = devices.getById("generic");
         Map<String, String> genericCapabilities = genericDevice.getCapabilities();

         for(Object capabilityNameObj : device.getCapabilities().keySet()) {
            String capabilityName = (String)capabilityNameObj;
            if (!genericCapabilities.containsKey(capabilityName)) {
               throw new InexistentCapabilityException(device, capabilityName);
            }

            String deviceGroup = device.getGroupForCapability(capabilityName);
            String genericGroup = genericDevice.getGroupForCapability(capabilityName);
            if (!deviceGroup.equals(genericGroup)) {
               throw new BadCapabilityGroupException(device, capabilityName, deviceGroup, genericGroup);
            }
         }

      }
   }

   public static void verifyNoRedefinedDevices(ModelDevices patchDevices, ModelDevices baseDevices) {
      Iterator<ModelDevice> iterator = patchDevices.getDevices().iterator();

      while(iterator.hasNext()) {
         ModelDevice patchDevice = iterator.next();
         String patchUserAgent = patchDevice.getUserAgent();
         String patchDeviceId = patchDevice.getID();
         String patchFallBack = patchDevice.getFallBack();
         ModelDevice baseDevice = baseDevices.getById(patchDeviceId);
         if (baseDevice == null) {
            return;
         }

         if (StringUtils.isEmpty(patchFallBack)) {
            throw new RedefinedDeviceException("Patched device with id " + patchDeviceId + "does not provide a value for fall_back");
         }

         if (!patchUserAgent.equals(baseDevice.getUserAgent())) {
            throw new RedefinedDeviceException(patchDevice, baseDevice, "user agent");
         }

         if (!patchFallBack.equals(baseDevice.getFallBack())) {
            throw new RedefinedDeviceException(patchDevice, baseDevice, "fall_back");
         }
      }

   }

   static {
      LoggerFactory.getLogger(ModelDevicesConsistencyVerifier.class);
   }
}
