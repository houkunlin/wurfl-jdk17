package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.InternalDevice;
import com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.exc.VirtualCapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class VirtualCapabilityHandler {
   private static final Map<String, VirtualCapabilityEvaluator> EVALUATORS_BY_NAME;
   private WURFLRequest request;

   private VirtualCapabilityHandler() {
   }

   public VirtualCapabilityHandler(WURFLRequest request) {
      this();
      this.request = request;
   }

   public String getVirtualCapability(String virtualCapabilityName, Device device) {
      VirtualCapabilityEvaluator evaluator;
      evaluator = EVALUATORS_BY_NAME.get(virtualCapabilityName);
      if (evaluator == null) {
         throw new VirtualCapabilityNotDefinedException(virtualCapabilityName);
      } else {
         synchronized(this) {
            return applyControlCapOverride(virtualCapabilityName, evaluator.eval(device, this.request), device);
         }
      }
   }

   public int getVirtualCapabilityAsInt(String virtualCapabilityName, Device device) {
      return Integer.parseInt(this.getVirtualCapability(virtualCapabilityName, device));
   }

   public boolean getVirtualCapabilityAsBool(String virtualCapabilityName, Device device) {
      return Boolean.parseBoolean(this.getVirtualCapability(virtualCapabilityName, device));
   }

   static String applyControlCapOverride(String virtualCapabilityName, String computedValue, InternalDevice device) {
      String controlCapabilityName = "controlcap_" + virtualCapabilityName;

      try {
         String overrideValue;
         overrideValue = device.getCapability(controlCapabilityName);
         if (overrideValue != null && !"default".equals(overrideValue)) {
            if ("force_true".equals(overrideValue)) {
               return "true";
            }

            if ("force_false".equals(overrideValue)) {
               return "false";
            }

            return overrideValue;
         }
      } catch (CapabilityNotDefinedException e) {
         throw new VirtualCapabilityNotDefinedException(virtualCapabilityName);
      }

      return computedValue == null ? "" : computedValue;
   }

   public Map<String, String> getAllVirtualCapabilities(Device device) {
      HashMap<String, String> virtualCapabilities = new HashMap<>();
      HashSet<VirtualCapabilityEvaluator> evaluators = new HashSet<>(EVALUATORS_BY_NAME.values());

      for(VirtualCapabilityEvaluator evaluator : evaluators) {
         String virtualCapabilityName = evaluator.getHandledVirtualCapabilityName();
         virtualCapabilities.put(virtualCapabilityName, applyControlCapOverride(virtualCapabilityName, evaluator.eval(device, this.request), device));
      }

      return virtualCapabilities;
   }

   public static Set<String> getAllVirtualCapabilities() {
      new VirtualCapabilityHandler();
      return EVALUATORS_BY_NAME.keySet();
   }

   public static Set<String> getMandatoryCapabilities() {
      return new HashSet<>(Arrays.asList(VirtualCapabilityEvaluator.MANDATORY_CAPABILITIES));
   }

   static {
EVALUATORS_BY_NAME = new ConcurrentHashMap<>();
EVALUATORS_BY_NAME.put("is_android", new IsAndroidOs());
      EVALUATORS_BY_NAME.put("is_ios", new IsIOs());
      EVALUATORS_BY_NAME.put("is_windows_phone", new IsWindowsPhone());
      EVALUATORS_BY_NAME.put("is_full_desktop", new IsFullDesktop());
      EVALUATORS_BY_NAME.put("is_app", new IsApp());
      EVALUATORS_BY_NAME.put("advertised_device_os", new OsName());
      EVALUATORS_BY_NAME.put("advertised_device_os_version", new OsVersion());
      EVALUATORS_BY_NAME.put("advertised_browser", new BrowserName());
      EVALUATORS_BY_NAME.put("advertised_browser_version", new BrowserVersion());
      EVALUATORS_BY_NAME.put("is_wml_preferred", new IsWMLPreferred());
      EVALUATORS_BY_NAME.put("is_xhtmlmp_preferred", new IsXHTMLPreferred());
      EVALUATORS_BY_NAME.put("is_html_preferred", new IsHTMLPreferred());
      EVALUATORS_BY_NAME.put("form_factor", new FormFactor());
      EVALUATORS_BY_NAME.put("complete_device_name", new CompleteDeviceName());
      EVALUATORS_BY_NAME.put("is_phone", new IsPhone());
      EVALUATORS_BY_NAME.put("is_app_webview", new IsAppWebview());
      EVALUATORS_BY_NAME.put("device_name", new DeviceName());
      EVALUATORS_BY_NAME.put("is_largescreen", new IsLargescreen());
      EVALUATORS_BY_NAME.put("is_mobile", new IsMobile());
      EVALUATORS_BY_NAME.put("is_robot", new IsRobot());
      EVALUATORS_BY_NAME.put("is_smartphone", new IsSmartphone());
      EVALUATORS_BY_NAME.put("is_touchscreen", new IsTouchscreen());
      EVALUATORS_BY_NAME.put("advertised_app_name", new AppName());

      try {
         Class<?> evaluatorClass = Class.forName("com.scientiamobile.wurfl.core.vcap.OsManufacturer");
         if (evaluatorClass != null) {
            Object evaluatorInstance = evaluatorClass.getDeclaredConstructor().newInstance();
            if (evaluatorInstance != null) {
               EVALUATORS_BY_NAME.put("os_manufacturer", (VirtualCapabilityEvaluator)evaluatorInstance);
            }
         }
      } catch (RuntimeException ignore) {
      } catch (ReflectiveOperationException ignore) {
      }
   }
}
