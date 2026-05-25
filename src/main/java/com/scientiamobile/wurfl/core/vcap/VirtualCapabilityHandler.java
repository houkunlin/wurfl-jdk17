package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.InternalDevice;
import com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.exc.VirtualCapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class VirtualCapabilityHandler {
   private static final Map a;
   private WURFLRequest b;

   private VirtualCapabilityHandler() {
   }

   public VirtualCapabilityHandler(WURFLRequest var1) {
      this();
      this.b = var1;
   }

   public String getVirtualCapability(String var1, Device var2) {
      VirtualCapabilityEvaluator var3;
      if ((var3 = (VirtualCapabilityEvaluator)a.get(var1)) == null) {
         throw new VirtualCapabilityNotDefinedException(var1);
      } else {
         synchronized(this) {
            return a(var1, var3.eval(var2, this.b), var2);
         }
      }
   }

   public int getVirtualCapabilityAsInt(String var1, Device var2) {
      return Integer.parseInt(this.getVirtualCapability(var1, var2));
   }

   public boolean getVirtualCapabilityAsBool(String var1, Device var2) {
      return Boolean.parseBoolean(this.getVirtualCapability(var1, var2));
   }

   static String a(String var0, String var1, InternalDevice var2) {
      String var3 = "controlcap_" + var0;

      try {
         String var5;
         if ((var5 = var2.getCapability(var3)) != null && !"default".equals(var5)) {
            if ("force_true".equals(var5)) {
               return "true";
            }

            if ("force_false".equals(var5)) {
               return "false";
            }

            return var5;
         }
      } catch (CapabilityNotDefinedException var4) {
         throw new VirtualCapabilityNotDefinedException(var0);
      }

      return var1 == null ? "" : var1;
   }

   public Map getAllVirtualCapabilities(Device var1) {
      HashMap var2 = new HashMap();
      HashSet var3;
      (var3 = new HashSet()).addAll(a.values());
      Iterator var6 = var3.iterator();

      while(var6.hasNext()) {
         VirtualCapabilityEvaluator var4;
         String var5 = (var4 = (VirtualCapabilityEvaluator)var6.next()).getHandledVirtualCapabilityName();
         var2.put(var5, a(var5, var4.eval(var1, this.b), var1));
      }

      return var2;
   }

   public static Set getAllVirtualCapabilities() {
      new VirtualCapabilityHandler();
      return a.keySet();
   }

   public static Set getMandatoryCapabilities() {
      return new HashSet(Arrays.asList(VirtualCapabilityEvaluator.MANDATORY_CAPABILITIES));
   }

   static {
      (a = new ConcurrentHashMap()).put("is_android", new IsAndroidOs());
      a.put("is_ios", new IsIOs());
      a.put("is_windows_phone", new IsWindowsPhone());
      a.put("is_full_desktop", new IsFullDesktop());
      a.put("is_app", new IsApp());
      a.put("advertised_device_os", new OsName());
      a.put("advertised_device_os_version", new OsVersion());
      a.put("advertised_browser", new BrowserName());
      a.put("advertised_browser_version", new BrowserVersion());
      a.put("is_wml_preferred", new IsWMLPreferred());
      a.put("is_xhtmlmp_preferred", new IsXHTMLPreferred());
      a.put("is_html_preferred", new IsHTMLPreferred());
      a.put("form_factor", new FormFactor());
      a.put("complete_device_name", new CompleteDeviceName());
      a.put("is_phone", new IsPhone());
      a.put("is_app_webview", new IsAppWebview());
      a.put("device_name", new DeviceName());
      a.put("is_largescreen", new IsLargescreen());
      a.put("is_mobile", new IsMobile());
      a.put("is_robot", new IsRobot());
      a.put("is_smartphone", new IsSmartphone());
      a.put("is_touchscreen", new IsTouchscreen());
      a.put("advertised_app_name", new AppName());

      try {
         Class var0;
         Object var2;
         if ((var0 = Class.forName("com.scientiamobile.wurfl.core.vcap.OsManufacturer")) != null && (var2 = var0.newInstance()) != null) {
            a.put("os_manufacturer", (VirtualCapabilityEvaluator)var2);
         }

      } catch (Exception var1) {
      }
   }
}
