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
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

final class d {
   // $FF: synthetic field
   private static boolean a = !d.class.desiredAssertionStatus();

   private d() {
   }

   public static void a(ModelDevices var0) {
      HashMap var1 = new HashMap();
      HashSet var2 = new HashSet();
      if (!a && var0 == null) {
         throw new AssertionError("devices is null");
      } else if (!var0.containsId("generic")) {
         throw new GenericNotDefinedException();
      } else {
         Iterator var3 = var0.iterator();

         while(var3.hasNext()) {
            ModelDevice var4;
            ModelDevice var5 = var4 = (ModelDevice)var3.next();
            if (!a && var5 == null) {
               throw new AssertionError("device is null");
            }

            if (var1.containsKey(var5.getUserAgent())) {
               ModelDevice var7 = (ModelDevice)var1.get(var5.getUserAgent());
               throw new UserAgentNotUniqueException(var5, var5.getUserAgent(), var7);
            }

            var1.put(var4.getUserAgent(), var4);
            a(var4, var0, var2);
            var2.add(var4.getID());
            a(var4, var0);
            b(var4, var0);
         }

      }
   }

   private static void a(ModelDevice var0, ModelDevices var1, Set var2) {
      if (!a && var0 == null) {
         throw new AssertionError("device is null");
      } else if (!a && var1 == null) {
         throw new AssertionError("devices is null");
      } else {
         if (var0.getID().equals("my_dev_id")) {
            System.out.println("VERIFYING device with ID: " + var0.getID());
         }

         ArrayList var3 = new ArrayList(10);
         String var5 = var0.getID();
         if (!a && StringUtils.isEmpty(var5)) {
            throw new AssertionError();
         } else {
            var3.add(var1.getById(var5));

            while(!"generic".equals(var5)) {
               var5 = var1.getById(var5).getFallBack();
               if (!a && StringUtils.isEmpty(var5)) {
                  throw new AssertionError();
               }

               if (var2.contains(var5)) {
                  return;
               }

               if (!var1.containsId(var5)) {
                  throw new OrphanHierarchyException(var3);
               }

               int var4;
               if ((var4 = var3.indexOf(var1.getById(var5))) != -1) {
                  LinkedList var6 = new LinkedList(var3.subList(var4, var3.size()));
                  throw new CircularHierarchyException(var6);
               }

               var3.add(var1.getById(var5));
            }

         }
      }
   }

   private static void a(ModelDevice var0, ModelDevices var1) {
      if (!a && var0 == null) {
         throw new AssertionError("device is null");
      } else if (!a && var1 == null) {
         throw new AssertionError("devices is null");
      } else {
         ModelDevice var4 = var1.getById("generic");
         Set var2 = var0.getGroups();
         Set var5 = var4.getGroups();

         for(String var3 : var2) {
            if (!var5.contains(var3)) {
               throw new InexistentGroupException(var0, var3);
            }
         }

      }
   }

   private static void b(ModelDevice var0, ModelDevices var1) {
      if (!a && var0 == null) {
         throw new AssertionError("device is null");
      } else if (!a && var1 == null) {
         throw new AssertionError("devices is null");
      } else if (!a && !var1.containsId("generic")) {
         throw new AssertionError("device do not containing generic");
      } else {
         ModelDevice var7;
         Map var2 = (var7 = var1.getById("generic")).getCapabilities();

         for(String var4 : var0.getCapabilities().keySet()) {
            if (!var2.containsKey(var4)) {
               throw new InexistentCapabilityException(var0, var4);
            }

            String var5 = var0.getGroupForCapability(var4);
            String var6 = var7.getGroupForCapability(var4);
            if (!var5.equals(var6)) {
               throw new BadCapabilityGroupException(var0, var4, var5, var6);
            }
         }

      }
   }

   public static void a(ModelDevices var0, ModelDevices var1) {
      Iterator var7 = var0.getDevices().iterator();

      while(var7.hasNext()) {
         ModelDevice var2;
         String var3 = (var2 = (ModelDevice)var7.next()).getUserAgent();
         String var4 = var2.getID();
         String var5 = var2.getFallBack();
         ModelDevice var6;
         if ((var6 = var1.getById(var4)) == null) {
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
      LoggerFactory.getLogger(d.class);
   }
}
