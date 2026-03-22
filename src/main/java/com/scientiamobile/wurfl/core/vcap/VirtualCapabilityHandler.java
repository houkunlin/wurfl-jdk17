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
  private static final Map<String, VirtualCapabilityEvaluator> a = new ConcurrentHashMap<>();

  private WURFLRequest b;

  private VirtualCapabilityHandler() {}

  public VirtualCapabilityHandler(WURFLRequest paramWURFLRequest) {
    this();
    this.b = paramWURFLRequest;
  }

  public String getVirtualCapability(String paramString, Device paramDevice) {
    VirtualCapabilityEvaluator virtualCapabilityEvaluator;
    if ((virtualCapabilityEvaluator = (VirtualCapabilityEvaluator)a.get(paramString)) == null)
      throw new VirtualCapabilityNotDefinedException(paramString);
    synchronized (this) {
      return a(paramString, virtualCapabilityEvaluator.eval(paramDevice, this.b), (InternalDevice)paramDevice);
    }
  }

  public int getVirtualCapabilityAsInt(String paramString, Device paramDevice) {
    return Integer.parseInt(getVirtualCapability(paramString, paramDevice));
  }

  public boolean getVirtualCapabilityAsBool(String paramString, Device paramDevice) {
    return Boolean.parseBoolean(getVirtualCapability(paramString, paramDevice));
  }

  static String a(String paramString1, String paramString2, InternalDevice paramInternalDevice) {
    String str = "controlcap_" + paramString1;
    try {
      String str1;
      if ((str1 = paramInternalDevice.getCapability(str)) != null && !"default".equals(str1))
        return "force_true".equals(str1) ? "true" : ("force_false".equals(str1) ? "false" : str1);
    } catch (CapabilityNotDefinedException capabilityNotDefinedException) {
      throw new VirtualCapabilityNotDefinedException(paramString1);
    }
    return (paramString2 == null) ? "" : paramString2;
  }

  public Map getAllVirtualCapabilities(Device paramDevice) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    HashSet<?> hashSet;
    (hashSet = new HashSet<>()).addAll(a.values());
    Iterator<?> iterator = hashSet.iterator();
    while (iterator.hasNext()) {
      VirtualCapabilityEvaluator virtualCapabilityEvaluator;
      String str = (virtualCapabilityEvaluator = (VirtualCapabilityEvaluator)iterator.next()).getHandledVirtualCapabilityName();
      hashMap.put(str, a(str, virtualCapabilityEvaluator.eval(paramDevice, this.b), (InternalDevice)paramDevice));
    }
    return hashMap;
  }

  public static Set<String> getAllVirtualCapabilities() {
    return a.keySet();
  }

  public static Set<String> getMandatoryCapabilities() {
    return new HashSet<>(Arrays.asList(VirtualCapabilityEvaluator.MANDATORY_CAPABILITIES));
  }

  static {
    (a = new ConcurrentHashMap<>()).put("is_android", new IsAndroidOs());
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
      Class<?> clazz;
      if ((clazz = Class.forName("com.scientiamobile.wurfl.core.vcap.OsManufacturer")) != null && (clazz = (Class<?>)clazz.newInstance()) != null)
        a.put("os_manufacturer", (VirtualCapabilityEvaluator)clazz);
    } catch (Exception exception) {
    }
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\VirtualCapabilityHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
