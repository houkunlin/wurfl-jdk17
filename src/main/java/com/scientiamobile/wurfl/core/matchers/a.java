package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class a implements A {
   protected static final Logger a = LoggerFactory.getLogger(a.class);
   private static final List b;
   private F c;
   private final UserAgentNormalizer d;
   // $FF: synthetic field
   private static boolean e = !a.class.desiredAssertionStatus();

   public String toString() {
      return this.getClass().getSimpleName();
   }

   protected Set a() {
      return new HashSet();
   }

   private void a(WURFLModel var1) {
      if (var1 != null) {
         Set var4 = var1.getAllDevicesId();

         for(String var3 : this.a()) {
            if (!var4.contains(var3)) {
               throw new s("wurfl.xml load error - Missing device id " + var3 + " you may need to update the wurfl.xml file to a more recent version");
            }
         }
      }

   }

   public a() {
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
      this.d = null;
   }

   public a(WURFLModel var1) {
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
      this.d = null;
      this.a(var1);
   }

   public a(UserAgentNormalizer var1) {
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
      this.d = var1;
   }

   public a(UserAgentNormalizer var1, WURFLModel var2) {
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
      this.d = var1;
      this.a(var2);
   }

   public final void setFilter(F var1) {
      this.c = var1;
   }

   public final F getFilter() {
      if (this.c == null) {
         this.c = new C(this);
      }

      return this.c;
   }

   public final DeviceInfo match(WURFLRequest var1) {
      String var2 = "generic";
      var1.normalizeUserAgent(this.d);
      String var3 = var1.getNormalizedDeviceUserAgent();
      MatchType var4 = MatchType.none;
      String var5 = this.getMatcherName();
      String var6 = this.getBucketMatcherName();
      if (!StringUtils.isBlank(var3)) {
         if (b(var2 = this.getFilter().a().a(var3))) {
            if (b(var2 = this.a(var1))) {
               if (b(var2 = this.b(var1))) {
                  this.getFilter().a();
                  String var10000;
                  if (var1._internalIsDesktopBrowserHeavyDutyAnalysis()) {
                     var10000 = "generic_web_browser";
                  } else {
                     String var9 = var1.getCleanedDeviceUserAgent();
                     Iterator var7 = b.iterator();

                     while(true) {
                        if (!var7.hasNext()) {
                           if (var9.indexOf("Mozilla/") <= 0 && !StringMatchUtils.containsAnyOf(var9, "Obigo", "AU-MIC/2", "AU-MIC-", "AU-OBIGO/", "Teleca Q03B1")) {
                              var10000 = StringMatchUtils.startsWithAnyOf(var9, "DoCoMo", "KDDI") ? "docomo_generic_jap_ver1" : (var1._internalIsMobileBrowser() ? "generic_mobile" : "generic");
                              break;
                           }

                           var10000 = "generic_xhtml";
                           break;
                        }

                        t var8 = (t)var7.next();
                        if (var9.contains(var8.a)) {
                           var10000 = var8.b;
                           break;
                        }
                     }
                  }

                  var2 = var10000;
                  var4 = MatchType.catchAll;
               } else {
                  var4 = MatchType.recovery;
               }
            } else {
               var4 = MatchType.conclusive;
            }
         } else {
            var4 = MatchType.exact;
         }
      }

      if (!e && var2 == null) {
         throw new AssertionError();
      } else {
         return new DeviceInfo(var2, var4, var5, var6, var1.getOriginalUserAgent(), var3);
      }
   }

   protected String a(WURFLRequest var1) {
      String var3 = var1.getNormalizedDeviceUserAgent();
      var3 = this.a(var3);
      String var2 = "generic";
      if (var3 != null) {
         var2 = this.getFilter().a().a(var3);
      }

      if (var2 == null) {
         var2 = "generic";
      }

      return var2;
   }

   protected String a(String var1) {
      int var2;
      return (var2 = StringMatchUtils.firstSlash(var1)) == -1 ? StringMatchUtils.NULL_STRING : StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2);
   }

   protected String b(WURFLRequest var1) {
      return "generic";
   }

   private static boolean b(String var0) {
      return StringUtils.isBlank(var0) || "generic".equals(var0);
   }

   public final String normalize(String var1) {
      return this.d == null ? var1 : this.d.normalize(var1);
   }

   public String getBucketMatcherName() {
      return "Abstract";
   }

   public String getMatcherName() {
      return this.getClass().getSimpleName();
   }

   static {
      (b = new ArrayList()).add(new t("CoreMedia", "apple_iphone_coremedia_ver1"));
      b.add(new t("Windows CE", "generic_ms_mobile"));
      b.add(new t("UP.Browser/7.2", "opwv_v72_generic"));
      b.add(new t("UP.Browser/7", "opwv_v7_generic"));
      b.add(new t("UP.Browser/6.2", "opwv_v62_generic"));
      b.add(new t("UP.Browser/6", "opwv_v6_generic"));
      b.add(new t("UP.Browser/5", "upgui_generic"));
      b.add(new t("UP.Browser/4", "uptext_generic"));
      b.add(new t("UP.Browser/3", "uptext_generic"));
      b.add(new t("Series60", "nokia_generic_series60"));
      b.add(new t("NetFront/3.0", "generic_netfront_ver3"));
      b.add(new t("ACS-NF/3.0", "generic_netfront_ver3"));
      b.add(new t("NetFront/3.1", "generic_netfront_ver3_1"));
      b.add(new t("ACS-NF/3.1", "generic_netfront_ver3_1"));
      b.add(new t("NetFront/3.2", "generic_netfront_ver3_2"));
      b.add(new t("ACS-NF/3.2", "generic_netfront_ver3_2"));
      b.add(new t("NetFront/3.3", "generic_netfront_ver3_3"));
      b.add(new t("ACS-NF/3.3", "generic_netfront_ver3_3"));
      b.add(new t("NetFront/3.4", "generic_netfront_ver3_4"));
      b.add(new t("NetFront/3.5", "generic_netfront_ver3_5"));
      b.add(new t("NetFront/4.0", "generic_netfront_ver4_0"));
   }
}
