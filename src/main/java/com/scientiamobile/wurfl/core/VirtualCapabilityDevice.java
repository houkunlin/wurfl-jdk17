package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public class VirtualCapabilityDevice implements Serializable {
   private static final long serialVersionUID = -9083698933173727805L;
   private static final Pattern a = Pattern.compile("Windows NT ([0-9]+?\\.[0-9])");
   private static final Pattern b = Pattern.compile("Windows [0-9\\.]+");
   private static final Pattern c = Pattern.compile("PPC.+OS X ([0-9\\._]+)");
   private static final Pattern d = Pattern.compile("PPC.+OS X ([0-9\\._]+)");
   private static final Pattern e = Pattern.compile("Trident/([\\d\\.]+)");
   private static final Pattern f = Pattern.compile("Intel Mac OS X ([0-9\\._]+)");
   private static final Pattern g = Pattern.compile("MacOS X ([0-9\\._]+)");
   private static final Pattern h = Pattern.compile("\\.");
   private final l i;
   private final l j;
   private static Map k = new HashMap();
   private static Map l = new HashMap();
   private static Map m = new HashMap();
   private static Set n = new HashSet(16);
   private String o;
   private String p;
   private String q;

   public String getDeviceUserAgent() {
      return this.o;
   }

   public String getBrowserUserAgent() {
      return this.p;
   }

   public String getCleanedDeviceUserAgent() {
      return this.q;
   }

   public l getBrowserPair() {
      return this.i;
   }

   public String getOsPairName() {
      return this.j.a();
   }

   public String getBrowserPairName() {
      return this.i.a();
   }

   public String getBrowserPairVersion() {
      return this.i.b();
   }

   public String getOsPairVersion() {
      return this.j.b();
   }

   public l getOsPair() {
      return this.j;
   }

   public VirtualCapabilityDevice(WURFLRequest var1) {
      if (var1.isUrlEncoded()) {
         this.o = var1.getCleanedDeviceUserAgent();
         this.p = this.o;
         this.q = var1.getCleanedDeviceUserAgent();
      } else {
         this.o = var1.getDeviceUserAgent();
         this.p = var1.getBrowserUserAgent();
         this.q = var1.getCleanedDeviceUserAgent();
      }

      this.i = new l();
      this.j = new l();
   }

   public VirtualCapabilityDevice(String var1, String var2, String var3, String var4) {
      if (!UserAgentUtils.isRawUrlEncoded(var4) && !UserAgentUtils.hasIIsLoggingStyle(var4)) {
         this.o = var1;
         this.p = var2;
         this.q = var3;
      } else {
         this.o = var1;
         this.p = var2;
         this.q = var3;
      }

      this.i = new l();
      this.j = new l();
   }

   public void normalizeOS() {
      if (this.j.a() != null && StringMatchUtils.indexOf(this.o, "Windows") >= 0) {
         Matcher var1;
         if ((var1 = a.matcher(this.j.a())).find()) {
            this.j.a("Windows");
            this.j.b(k.containsKey(var1.group(1)) ? (String)k.get(var1.group(1)) : var1.group());
            return;
         }

         if (b.matcher(this.j.a()).find()) {
            return;
         }
      }

      if (StringMatchUtils.indexOf(this.j.a(), "Windows Phone") >= 0 && this.j.b() != null && m.containsKey(this.j.b())) {
         this.getOsPair().b((String)m.get(this.j.b()));
      }

      if (this.j.a(this.o, c, "Mac OS X", 1)) {
         this.j.b(this.j.b().replaceAll("_", "."));
      } else if (this.j.a(this.o, g, "Mac OS X", 1)) {
         this.j.b(this.j.b().replaceAll("_", "."));
      } else if (!this.j.a(this.o, d, "Mac OS X", (String)null)) {
         if (this.j.a(this.o, f, "Mac OS X", 1)) {
            if (this.j.b() != null) {
               this.j.b(this.j.b().replaceAll("_", "."));
               String[] var4;
               if (StringUtils.isNotEmpty(this.j.b()) && (var4 = h.split(this.j.b())) != null && var4.length > 1 && StringUtils.isNumeric(var4[0]) && StringUtils.isNumeric(var4[1]) && Integer.parseInt(var4[0]) >= 10 && Integer.parseInt(var4[1]) >= 12) {
                  this.j.a("macOS");
                  return;
               }
            }

         } else if (!this.j.a(this.o, "Mac_PowerPC", "Mac OS X")) {
            if (!this.j.a(this.o, "CrOS", "Chrome OS")) {
               if (this.j.a() != null && (this.j.a().contains("Linux") || this.j.a().contains("X11"))) {
                  this.j.a("Linux");
               }

               if (StringUtils.isNotEmpty(this.j.a())) {
                  for(String var2 : n) {
                     if (this.j.a().contains(var2)) {
                        return;
                     }
                  }

                  this.j.a("");
                  this.j.b("");
                  if (StringMatchUtils.indexOf(this.o, "Linux") >= 0 || StringMatchUtils.indexOf(this.o, "X11") >= 0) {
                     this.j.a("Linux");
                     return;
                  }
               }

            }
         }
      }
   }

   public void normalizeBrowser() {
      Matcher var1 = e.matcher(this.o);
      if ("IE".equals(this.i.a()) && var1.find()) {
         String var2 = var1.group(1);
         if (l.containsKey(var2) && !(var2 = (String)l.get(var2)).equals(this.i.b())) {
            this.i.b(var2 + "(Compatibility View)");
         }
      }

   }

   static {
      k.put("4.0", "NT 4.0");
      k.put("5.0", "2000");
      k.put("5.1", "XP");
      k.put("5.2", "XP");
      k.put("6.0", "Vista");
      k.put("6.1", "7");
      k.put("6.2", "8");
      k.put("6.3", "8.1");
      k.put("6.4", "10");
      k.put("10.0", "10");
      l.put("4.0", "8.0");
      l.put("5.0", "9.0");
      l.put("6.0", "10.0");
      l.put("7.0", "11.0");
      m.put("7.10", "7.5");
      m.put("8.10", "8.1");
      m.put("8.15", "10");
      n.add("Windows CE");
      n.add("Windows Mobile");
      n.add("Windows Phone");
      n.add("Nintendo");
      n.add("Android");
      n.add("iOS");
      n.add("Tizen");
      n.add("Nokia Series 40");
      n.add("Symbian");
      n.add("BlackBerry");
      n.add("RIM Tablet OS");
      n.add("Bada");
      n.add("webOS");
      n.add("Linux");
      n.add("X11");
      n.add("Ubuntu");
      n.add("Fedora");
      n.add("Mac OS X");
      n.add("Fire OS");
   }
}
