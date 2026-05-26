package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

final class FirefoxOSMatcher extends MatcherBase {
   private static String FALLBACK_TABLET = "firefox_os_ver1_3_tablet";
   private static String FALLBACK_GENERIC = "generic_firefox_os";
   private static final Pattern VERSION_RV_PREFIX = Pattern.compile("\\brv:\\d+\\.\\d+(.)");
   private static final Pattern VERSION_RV = Pattern.compile("\\brv:(\\d+\\.\\d+)");
   private static final Map RV_TO_FIREFOX_OS_VERSION = new HashMap();
   private static final List SUPPORTED_DEVICES = new ArrayList();

   public FirefoxOSMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set getRequiredDeviceIds() {
      HashSet var1;
      (var1 = new HashSet()).addAll(SUPPORTED_DEVICES);
      var1.add(FALLBACK_TABLET);
      var1.add(FALLBACK_GENERIC);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2;
      return StringUtils.contains(var2 = var1.getCleanedDeviceUserAgent(), "Firefox/") && StringMatchUtils.containsAnyOf(var2, "Mobile", "Tablet");
   }

   protected final String risMatch(String var1) {
      Matcher var2;
      return (var2 = VERSION_RV_PREFIX.matcher(var1)).find() ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2.end(1)) : null;
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      String normalizedUserAgent = var1.getNormalizedDeviceUserAgent();
      String var10000;
      label24: {
         String var2 = normalizedUserAgent;
         Matcher var5;
         if ((var5 = VERSION_RV.matcher(var2)).find()) {
            String var6 = var5.group(1);
            if (RV_TO_FIREFOX_OS_VERSION.containsKey(var6)) {
               var10000 = (String)RV_TO_FIREFOX_OS_VERSION.get(var6);
               break label24;
            }
         }

         var10000 = "1.0";
      }

      String var7 = var10000.replace(".", "_").replace("_0", "");
      var7 = "firefox_os_ver" + var7;
      if (normalizedUserAgent.contains("Tablet")) {
         String var4 = var7 + "_tablet";
         return SUPPORTED_DEVICES.contains(var4) ? var4 : FALLBACK_TABLET;
      } else {
         return SUPPORTED_DEVICES.contains(var7) ? var7 : FALLBACK_GENERIC;
      }
   }

   public final String getMatcherName() {
      return "FirefoxOSMatcher";
   }

   public final String getBucketMatcherName() {
      return "FirefoxOS";
   }

   static {
      RV_TO_FIREFOX_OS_VERSION.put("18.0", "1.0");
      RV_TO_FIREFOX_OS_VERSION.put("18.1", "1.1");
      RV_TO_FIREFOX_OS_VERSION.put("26.0", "1.2");
      RV_TO_FIREFOX_OS_VERSION.put("28.0", "1.3");
      RV_TO_FIREFOX_OS_VERSION.put("30.0", "1.4");
      RV_TO_FIREFOX_OS_VERSION.put("32.0", "2.0");
      RV_TO_FIREFOX_OS_VERSION.put("33.0", "2.1");
      RV_TO_FIREFOX_OS_VERSION.put("34.0", "2.1");
      RV_TO_FIREFOX_OS_VERSION.put("37.0", "2.2");
      RV_TO_FIREFOX_OS_VERSION.put("43.0", "2.5");
      SUPPORTED_DEVICES.add("firefox_os_ver1");
      SUPPORTED_DEVICES.add("firefox_os_ver1_1");
      SUPPORTED_DEVICES.add("firefox_os_ver1_2");
      SUPPORTED_DEVICES.add("firefox_os_ver1_3");
      SUPPORTED_DEVICES.add("firefox_os_ver1_4");
      SUPPORTED_DEVICES.add("firefox_os_ver1_4_tablet");
      SUPPORTED_DEVICES.add("firefox_os_ver2_0");
      SUPPORTED_DEVICES.add("firefox_os_ver2_0_tablet");
      SUPPORTED_DEVICES.add("firefox_os_ver2_1");
      SUPPORTED_DEVICES.add("firefox_os_ver2_1_tablet");
      SUPPORTED_DEVICES.add("firefox_os_ver2_2");
      SUPPORTED_DEVICES.add("firefox_os_ver2_2_tablet");
      SUPPORTED_DEVICES.add("firefox_os_ver2_5");
      SUPPORTED_DEVICES.add("firefox_os_ver2_5_tablet");
   }
}
