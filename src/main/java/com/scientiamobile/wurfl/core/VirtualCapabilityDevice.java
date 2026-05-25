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
   private final NameVersionPair i;
   private final NameVersionPair j;
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

   public NameVersionPair getBrowserPair() {
      return this.i;
   }

   public String getOsPairName() {
      return this.j.getName();
   }

   public String getBrowserPairName() {
      return this.i.getName();
   }

   public String getBrowserPairVersion() {
      return this.i.getVersion();
   }

   public String getOsPairVersion() {
      return this.j.getVersion();
   }

   public NameVersionPair getOsPair() {
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

      this.i = new NameVersionPair();
      this.j = new NameVersionPair();
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

      this.i = new NameVersionPair();
      this.j = new NameVersionPair();
   }

   public void normalizeOS() {
      if (this.j.getName() != null && StringMatchUtils.indexOf(this.o, "Windows") >= 0) {
         Matcher var1;
         if ((var1 = a.matcher(this.j.getName())).find()) {
            this.j.setName("Windows");
            this.j.setVersion(k.containsKey(var1.group(1)) ? (String)k.get(var1.group(1)) : var1.group());
            return;
         }

         if (b.matcher(this.j.getName()).find()) {
            return;
         }
      }

      if (StringMatchUtils.indexOf(this.j.getName(), "Windows Phone") >= 0 && this.j.getVersion() != null && m.containsKey(this.j.getVersion())) {
         this.getOsPair().setVersion((String)m.get(this.j.getVersion()));
      }

      if (this.j.matchAndSetGroup(c, this.o, "Mac OS X", 1)) {
         this.j.setVersion(this.j.getVersion().replaceAll("_", "."));
      } else if (this.j.matchAndSetGroup(g, this.o, "Mac OS X", 1)) {
         this.j.setVersion(this.j.getVersion().replaceAll("_", "."));
      } else if (!this.j.matchAndSet(d, this.o, "Mac OS X", (String)null)) {
         if (this.j.matchAndSetGroup(f, this.o, "Mac OS X", 1)) {
            if (this.j.getVersion() != null) {
               this.j.setVersion(this.j.getVersion().replaceAll("_", "."));
               String[] var4;
               if (StringUtils.isNotEmpty(this.j.getVersion()) && (var4 = h.split(this.j.getVersion())) != null && var4.length > 1 && StringUtils.isNumeric(var4[0]) && StringUtils.isNumeric(var4[1]) && Integer.parseInt(var4[0]) >= 10 && Integer.parseInt(var4[1]) >= 12) {
                  this.j.setName("macOS");
                  return;
               }
            }

         } else if (!this.j.containsAndSetName(this.o, "Mac_PowerPC", "Mac OS X")) {
            if (!this.j.containsAndSetName(this.o, "CrOS", "Chrome OS")) {
               if (this.j.getName() != null && (this.j.getName().contains("Linux") || this.j.getName().contains("X11"))) {
                  this.j.setName("Linux");
               }

               if (StringUtils.isNotEmpty(this.j.getName())) {
                  for(String var2 : n) {
                     if (this.j.getName().contains(var2)) {
                        return;
                     }
                  }

                  this.j.setName("");
                  this.j.setVersion("");
                  if (StringMatchUtils.indexOf(this.o, "Linux") >= 0 || StringMatchUtils.indexOf(this.o, "X11") >= 0) {
                     this.j.setName("Linux");
                     return;
                  }
               }

            }
         }
      }
   }

   public void normalizeBrowser() {
      Matcher var1 = e.matcher(this.o);
      if ("IE".equals(this.i.getName()) && var1.find()) {
         String var2 = var1.group(1);
         if (l.containsKey(var2) && !(var2 = (String)l.get(var2)).equals(this.i.getVersion())) {
            this.i.setVersion(var2 + "(Compatibility View)");
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
