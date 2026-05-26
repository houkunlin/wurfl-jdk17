package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.VirtualCapabilityDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityUserAgentTool;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

abstract class AbstractVirtualCapabilityEvaluator implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = 8192401578396133213L;
   protected static final Pattern c = Pattern.compile("Mozilla/5.0 \\(Linux;( U;)? Android.*AppleWebKit.*\\(KHTML, like Gecko\\)");
   protected static final Pattern d;
   protected static final Pattern e;
   protected static final Pattern f;
   static final Set<String> g;
   private static Set<String> a;
   static final Set<String> h;
   protected static final List<String> i;
   private static List<String> b;
   private static final Pattern j;

   protected static boolean a(String var0, String var1) {
      return "iOS".equals(var0) && !var1.contains("Safari");
   }

   protected static boolean a(Device var0, String var1, WURFLRequest var2) {
      VirtualCapabilityDevice var3 = VirtualCapabilityUserAgentTool.getInstance().assignProperties(var2, var0);
      return "Mac OS X".equals(var3.getOsPairName()) && !var1.contains("Safari");
   }

   protected static boolean a(String var0, String var1, WURFLRequest var2) {
      return a(var0, var1, var2.getHeader("X-Requested-With"));
   }

   protected static boolean a(String var0, String var1, String var2) {
      return var0.equals(var1) && StringUtils.isNotEmpty(var2) && a.contains(var2);
   }

   protected static boolean a(WURFLRequest var0) {
      Map var1 = var0.getHeaders();
      String var2 = var0.isUrlEncoded() ? var0.getCleanedDeviceUserAgent() : var0.getOriginalUserAgent();
      String var4;
      if (var1.containsKey("Accept-Encoding") && var2.contains("Trident/") && (var4 = (String)var1.get("Accept-Encoding")) != null && !var4.contains("deflate")) {
         return true;
      } else {
         for(String keyword : b) {
            if (var2.contains(keyword)) {
               return false;
            }
         }

         return var0._internalIsBot();
      }
   }

   protected static boolean a(Device var0) {
      int var1;
      try {
         var1 = Integer.parseInt(var0.getCapability("resolution_width"));
      } catch (NumberFormatException var5) {
         return false;
      }

      if ("false".equals(var0.getCapability("is_wireless_device"))) {
         return false;
      } else if ("true".equals(var0.getCapability("is_tablet"))) {
         return false;
      } else if ("false".equals(var0.getCapability("can_assign_phone_number"))) {
         return false;
      } else if (!"touchscreen".equals(var0.getCapability("pointing_method"))) {
         return false;
      } else if (var1 < 320) {
         return false;
      } else {
         String var7 = var0.getCapability("device_os_version");
         Matcher var8 = j.matcher(var7);
         float var2 = 0.0F;
         boolean var3;
         if (var3 = var8.matches()) {
            try {
               var2 = Float.parseFloat(var8.group(1));
            } catch (NumberFormatException var4) {
               var3 = false;
            }
         }

         String var6 = var0.getCapability("device_os");
         if ("iOS".equals(var6)) {
            return var3 && var2 >= 3.0F;
         } else if ("Android".equals(var6)) {
            return var3 && var2 >= 2.2F;
         } else if ("Windows Phone OS".equals(var6)) {
            return true;
         } else if ("RIM OS".equals(var6)) {
            return var3 && (double)var2 >= (double)7.0F;
         } else if ("webOS".equals(var6)) {
            return true;
         } else if ("MeeGo".equals(var6)) {
            return true;
         } else if ("Bada OS".equals(var6)) {
            return var3 && (double)var2 >= (double)2.0F;
         } else {
            return false;
         }
      }
   }

   static {
      Pattern.compile("^Mozilla/5.0 \\(Linux; Android [45]\\.[\\d\\.]+; .+ Build/.+\\) AppleWebKit/[\\d\\.+]+ \\(KHTML, like Gecko\\) Version/[\\d\\.]+ Chrome/([\\d]+)\\.[\\d\\.]+? (?:Mobile )?Safari/[\\d\\.+]+$");
      d = Pattern.compile("^Mozilla/5.0 \\(Linux;( U;)? Android [1234]\\.[\\d\\.]+(-update1)?; [a-zA-Z]+-[a-zA-Z]+; .+ Build/.+\\) AppleWebKit/[\\d\\.+]+ \\(KHTML, like Gecko\\) Version/[\\d\\.]+ (Mobile )?Safari/[\\d\\.+]+$");
      e = Pattern.compile("Chrome/(\\d+)\\.");
      f = Pattern.compile("Android [1234]\\.[123]");
      g = new HashSet<>();
      a = new HashSet<>();
      h = new HashSet<>();
      i = new ArrayList<>();
      b = new ArrayList<>(3);
      j = Pattern.compile("^(\\d+(?:\\.\\d+)?).*");
      b.add("CUBOT");
      b.add("Cubot");
      b.add("Botswana");
      i.add("^Dalvik");
      i.add("Darwin/");
      i.add("CFNetwork");
      i.add("^Windows Phone Ad Client");
      i.add("^NativeHost");
      i.add("^AndroidDownloadManager");
      i.add("-HttpClient");
      i.add("^AppCake");
      i.add("AppEngine-Google");
      i.add("AppleCoreMedia");
      i.add("^AppTrailers");
      i.add("^ChoiceFM");
      i.add("^ClassicFM");
      i.add("^Clipfish");
      i.add("^FaceFighter");
      i.add("^Flixster");
      i.add("^Gold/");
      i.add("^GoogleAnalytics/");
      i.add("^Heart/");
      i.add("^iBrowser/");
      i.add("iTunes-");
      i.add("^Java/");
      i.add("^LBC/3.");
      i.add("Twitter");
      i.add("Pinterest");
      i.add("^Instagram");
      i.add("FBAN");
      i.add("#iP(hone|od|ad)[\\d],[\\d]");
      i.add("#com(?:\\.[a-z]+){2,}");
      i.add("#net(?:\\.[a-z]+){2,}");
      i.add("WebView");
      i.add("FB_IAB");
      i.add("FB4A");
      i.add("MobileApp");
      i.add("DesktopApp");
      i.add("^mShop:::");
      i.add(" GSA/");
      g.add("com.android.browser");
      g.add("com.htc.sense.browser");
      g.add("com.asus.browser");
      g.add("com.google.android.browser");
      g.add("com.lenovo.browser");
      g.add("com.huawei.android.browser");
      a.add("com.facebook.katana");
      a.add("com.ksmobile.cb");
      a.add("com.nhn.android.search");
      a.add("app.staples");
      a.add("flipboard.app");
      a.add("com.google.android.apps.magazines");
      a.add("com.pandora.android");
      a.add("com.stumbleupon.android.app");
      h.add("UCBrowser");
      h.add("Opera");
      h.add(" OPR/");
      h.add("YaBrowser");
      h.add("MiuiBrowser");
      h.add("MQQBrowser");
      h.add("CriOS");
      h.add("Firefox");
   }
}
