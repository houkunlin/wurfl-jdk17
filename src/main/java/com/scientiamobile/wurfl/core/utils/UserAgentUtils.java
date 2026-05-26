package com.scientiamobile.wurfl.core.utils;

import com.scientiamobile.wurfl.core.WURFLEngine;
import com.scientiamobile.wurfl.core.request.HttpServletRequestHeaderProvider;
import com.scientiamobile.wurfl.core.request.WURFLHeaderProvider;
import com.scientiamobile.wurfl.core.resource.ResourceUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.StrBuilder;

public final class UserAgentUtils {
   private static final SortedSet a;
   public static final Pattern STRIP_QUOTE_PATTERN;
   public static final Pattern NAMESPACE_NUMBER_PATTERN;
   private static final Pattern b;
   private static final Pattern c;
   private static final Pattern d;
   private static final Pattern e;
   private static final Pattern f;
   private static final Pattern g;
   private static final Pattern h;
   private static final Pattern i;
   private static final Pattern j;
   private static final Pattern k;
   private static final Pattern l;
   private static final Pattern m;
   private static final Pattern n;
   private static final Pattern o;
   private static final Pattern p;
   private static final Pattern q;
   private static final Pattern r;
   private static final Pattern s;
   private static final Pattern t;
   private static final Pattern u;
   private static final Pattern v;
   private static final Pattern w;
   private static final Pattern x;
   private static final Pattern y;
   private static final Pattern z;
   private static final Pattern A;
   private static final Pattern B;
   private static final Pattern C;
   private static final Pattern D;
   private static final Pattern E;
   private static final Pattern F;
   private static final Pattern G;
   private static final Pattern H;
   private static final Pattern I;
   private static final Pattern J;
   private static final Pattern K;
   private static final Pattern L;
   private static final Pattern M;
   private static final List N;
   private static final List O;
   private static final List P;
   private static final List Q;
   private static final AhoCorasickKeywordMatcher R;
   private static final AhoCorasickKeywordMatcher S;
   private static final AhoCorasickKeywordMatcher T;
   private static final AhoCorasickKeywordMatcher U;
   private static final Pattern V;
   private static final Pattern W;
   private static final Pattern X;
   private static final Pattern Y;
   private static final Pattern Z;

   private UserAgentUtils() {
   }

   public static String getUserAgent(HttpServletRequest var0) {
      Validate.notNull(var0, "The HttpServletRequest is null");
      String var1;
      if ((var1 = var0.getHeader("Device-Stock-UA")) == null || "".equals(var1)) {
         var1 = var0.getHeader("X-OperaMini-Phone-UA");
      }

      if (var1 == null || "".equals(var1)) {
         var1 = var0.getHeader("User-Agent");
      }

      if (var1 == null) {
         var1 = "";
      }

      return var1;
   }

   public static String getUaProfile(HttpServletRequest var0) {
      return getUaProfile((WURFLHeaderProvider)(new HttpServletRequestHeaderProvider(var0)));
   }

   public static String getUaProfile(WURFLHeaderProvider var0) {
      Validate.notNull(var0, "The WURFLHeaderProvider is null");
      String var1 = null;
      String var2 = null;
      if (var0.getHeader("Profile") != null) {
         var1 = "Profile";
      }

      if (var1 != null && var1.trim().length() > 0) {
         var2 = var0.getHeader(var1);
      }

      if (var2 != null && var2.trim().length() > 0) {
         var2 = STRIP_QUOTE_PATTERN.matcher(var2).replaceAll("");
      }

      return var2;
   }

   public static boolean isXhtmlRequester(HttpServletRequest var0) {
      Validate.notNull(var0, "HttpRequest is null");
      String var1;
      return (var1 = var0.getHeader("accept")) != null && (var1.indexOf("application/vnd.wap.xhtml+xml") != -1 || var1.indexOf("application/xhtml+xml") != -1 || var1.indexOf("application/text+html") != -1);
   }

   public static Predicate isContainedIn(String var0) {
      return new ContainsIgnoreCasePredicate(var0);
   }

   public static Pattern createLocalePattern() {
      StrBuilder var0;
      (var0 = new StrBuilder()).append("; (");
      String[] var1 = Locale.getISOLanguages();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var2 > 0) {
            var0.append("|");
         }

         var0.append(var1[var2]);
      }

      var0.append(")");
      var0.append("(-(");
      String[] var4 = Locale.getISOCountries();

      for(int var3 = 0; var3 < var4.length; ++var3) {
         if (var3 > 0) {
            var0.append("|");
         }

         var0.append(var4[var3]);
      }

      var0.append("))?");
      return Pattern.compile(var0.toString(), 2);
   }

   public static Map<String, String> getHeaders(HttpServletRequest var0) {
      HashMap<String, String> var1 = new HashMap<>();
      Enumeration var2 = var0.getHeaderNames();

      while(var2.hasMoreElements()) {
         String var3 = (String)var2.nextElement();
         var1.put(var3, var0.getHeader(var3));
      }

      return var1;
   }

   public static String getAndroidVersion(String var0, boolean var1) {
      Matcher var2;
      boolean var3;
      if (!(var3 = (var2 = c.matcher(var0)).find())) {
         var3 = (var2 = b.matcher(var0)).find();
      }

      if (!var3) {
         var3 = (var2 = d.matcher(var0)).find();
      }

      if (var3) {
         var0 = var2.group(1);
         if (a.contains(var0)) {
            return var0;
         }
      }

      return var1 ? "4.0" : null;
   }

   public static String getOperaOnAndroidVersion(String var0, boolean var1) {
      Matcher var2;
      if (!(var2 = e.matcher(var0)).find() || !var2.group(1).equals("11") && !var2.group(1).equals("12")) {
         return var1 ? "10" : null;
      } else {
         return var2.group(1);
      }
   }

   public static String getAndroidModel(String var0) {
      var0 = k.matcher(var0).replaceAll("; ");
      Matcher var1;
      if ((var1 = j.matcher(var0)).find()) {
         var0 = var1.group(1);
      } else {
         var1 = f.matcher(var0);
         Matcher var2 = g.matcher(var0);
         if (var1.find()) {
            var0 = StringMatchUtils.rtrim(var1.group(1), ';', ' ');
         } else if (var2.find()) {
            var0 = StringMatchUtils.rtrim(var2.group(1), ' ', ';');
         } else {
            var1 = h.matcher(var0);
            var2 = i.matcher(var0);
            Matcher var5 = l.matcher(var0);
            if (var1.find()) {
               var0 = StringMatchUtils.rtrim(var1.group(1), ';', ' ');
            } else if (var2.find()) {
               var0 = var2.group(1);
            } else {
               if (!var5.find()) {
                  return null;
               }

               var0 = var5.group(1);
            }
         }
      }

      if (var0 != null && var0.startsWith("Build/")) {
         return null;
      } else {
         int var12;
         if (StringMatchUtils.indexOf(var0 = StringMatchUtils.replaceAll(StringMatchUtils.replaceAll(var0, m, ""), n, ""), "*") >= 0 && (var12 = StringMatchUtils.indexOf(var0, "/")) >= 0) {
            var0 = var0.substring(0, var12);
         }

         if ((var0 = StringMatchUtils.replaceAll(StringMatchUtils.replaceAll(var0, o, "HUAWEI "), p, "Coolpad ")).contains("HTC")) {
            if ((var12 = StringMatchUtils.indexOf(var0 = StringMatchUtils.replaceAll(var0, q, "HTC~"), "/")) >= 0) {
               var0 = var0.substring(0, var12);
            }

            var0 = StringMatchUtils.replaceAll(var0, r, "");
         }

         if ((var0 = StringMatchUtils.replaceAll(StringMatchUtils.replaceAll(StringMatchUtils.replaceAll(StringMatchUtils.replaceAll(StringMatchUtils.replaceAll(var0, y, "$1"), z, "ORANGE"), B, "$1"), C, "").trim(), D, "")).length() == 0) {
            var0 = null;
         }

         return var0;
      }
   }

   public static String getUcBrowserVersion(String var0, boolean var1) {
      Matcher var2;
      return (var2 = s.matcher(var0)).find() ? var2.group(1) : null;
   }

   public static String getUcAndroidVersion(String var0, boolean var1) {
      Matcher var2;
      if ((var2 = t.matcher(var0)).find()) {
         String var3 = var2.group(1);
         if (a.contains(var3)) {
            return var3;
         }
      }

      return var1 ? "4.0" : null;
   }

   public static String getUcAndroidModel(String var0, boolean var1) {
      Matcher var2;
      if (!(var2 = u.matcher(var0)).find()) {
         return null;
      } else {
         String var3 = var2.group(1);
         if (var0.contains("HTC")) {
            var3 = v.matcher(var3).replaceFirst("HTC~");
            var3 = w.matcher(var3).replaceFirst("");
            var3 = x.matcher(var3).replaceFirst("");
         }

         var3 = y.matcher(var3).replaceFirst("$1");
         var3 = z.matcher(var3).replaceFirst("ORANGE");
         var3 = A.matcher(var3).replaceFirst("$1");
         String var9;
         return StringUtils.isEmpty(var9 = C.matcher(var3).replaceFirst("").trim()) ? null : var9;
      }
   }

   public static final String getWindowsPhoneVersion(String var0) {
      Matcher var1;
      if ((var1 = G.matcher(var0)).find()) {
         String var2;
         if ((var2 = var1.group(1)).startsWith("10.0")) {
            return "10.0";
         } else if (!var2.startsWith("6.3") && !var2.startsWith("8.1")) {
            if (var2.startsWith("8.")) {
               return "8.0";
            } else if (var2.startsWith("7.8")) {
               return "7.8";
            } else if (!var2.startsWith("7.10") && !var2.startsWith("7.5")) {
               return var2.startsWith("6.5") ? "6.5" : "7.0";
            } else {
               return "7.5";
            }
         } else {
            return "8.1";
         }
      } else {
         return null;
      }
   }

   public static final String getWindowsPhoneModel(String var0) {
      return cleanAndReplaceWindowsPhoneModel(var0, E, F);
   }

   public static final String getWindowsPhoneDesktopVersion(String var0) {
      Matcher var1;
      if ((var1 = H.matcher(var0)).find()) {
         String var2;
         if ((var2 = var1.group(1)).indexOf("10.0") >= 0) {
            return "10.0";
         } else {
            return var2.indexOf("6.3") < 0 && var2.indexOf("8.1") < 0 ? "8.0" : "8.1";
         }
      } else {
         return null;
      }
   }

   public static final String getWindowsPhoneDesktopModel(String var0) {
      return cleanAndReplaceWindowsPhoneModel(var0, I, J);
   }

   public static final String cleanAndReplaceWindowsPhoneModel(String var0, Pattern... var1) {
      var0 = K.matcher(var0).replaceAll("; ");
      Matcher var2 = null;
      Pattern[] var6;
      int var3 = (var6 = var1).length;

      for(int var4 = 0; var4 < var3 && !(var2 = var6[var4].matcher(var0)).find(); ++var4) {
         var2 = null;
      }

      if (var2 != null) {
         String var7 = var2.group(1).replace("_blocked", "");
         var7 = L.matcher(var7).replaceFirst("$1");
         return M.matcher(var7).replaceFirst("$1");
      } else {
         return null;
      }
   }

   public static boolean isWindowsPhoneAdClient(String var0) {
      return StringMatchUtils.startsWithAnyOf(var0, "Windows Phone Ad Client", "WindowsPhoneAdClient");
   }

   public static boolean mobileKeywordsDetected(String var0) {
      return R.matchesAny(var0);
   }

   public static boolean screenSizeDetected(String var0) {
      return Z.matcher(var0).find();
   }

   public static boolean isDesktopBrowser(String var0) {
      return S.matchesAny(var0);
   }

   public static boolean isSmartTvBrowser(String var0) {
      return T.matchesAny(var0);
   }

   public static boolean isBot(String var0) {
      return U.matchesAny(var0);
   }

   public static boolean isDesktopPattern(String var0) {
      return V.matcher(var0).matches();
   }

   public static boolean isIEPattern(String var0) {
      return W.matcher(var0).find() || X.matcher(var0).find() || Y.matcher(var0).find();
   }

   public static String createApiUserAgent(WURFLEngine var0) {
      StringBuilder var10000 = (new StringBuilder(ResourceUtils.getBuildId())).append("/WURFL_JAVA_API/1.9.1.0 WURFL/");
      String var3;
      String var10001;
      if (StringUtils.isEmpty(var3 = var0.getWURFLUtils().getVersion())) {
         var10001 = var3;
      } else {
         String var1 = "scientiamobile.com - ";
         int var2;
         if ((var2 = var3.indexOf(var1)) == -1) {
            var10001 = var3;
         } else {
            var3 = var3.substring(var2 + var1.length());
            var10001 = "Snapshot_" + var3.substring(0, var3.lastIndexOf(32)).replace('-', '_');
         }
      }

      return var10000.append(var10001).append(" Java/").append(System.getProperty("java.version")).append(" ").append(System.getProperty("os.name")).append("/").append(System.getProperty("os.version")).toString();
   }

   public static List getMobileBrowsers() {
      return Collections.unmodifiableList(N);
   }

   public static boolean hasIIsLoggingStyle(String var0) {
      return StringUtils.countMatches(var0, " ") == 0 && StringUtils.countMatches(var0, "+") > 2;
   }

   public static boolean isRawUrlEncoded(String var0) {
      return StringUtils.countMatches(var0, "%") > 2;
   }

   public static UserAgentWithNeedleCount getAsciiPrintableStringWithNeedleCount(StringBuilder var0) {
      boolean var1 = false;
      char[] var2 = new char[]{'+', '%'};
      int[] var3 = new int[2];
      if (var0 != null && var0.length() != 0) {
         for(int var4 = var0.length() - 1; var4 >= 0; --var4) {
            char var5 = var0.charAt(var4);
            var1 = var1 || var5 == ' ';
            if (!CharUtils.isAsciiPrintable(var5)) {
               var0.deleteCharAt(var4);
            } else {
               for(int var6 = 0; var6 < 2; ++var6) {
                  if (var5 == var2[var6]) {
                     int var10002 = var3[var6]++;
                  }
               }
            }
         }

         return new UserAgentWithNeedleCount(var0.toString(), var3[0], var3[1], var1);
      } else {
         return new UserAgentWithNeedleCount("", 0, 0, false);
      }
   }

   public static String getAsciiPrintableString(String var0) {
      if (var0 == null) {
         return "";
      } else {
         StringBuilder var2 = new StringBuilder(var0);

         for(int var1 = var2.length() - 1; var1 >= 0; --var1) {
            if (!CharUtils.isAsciiPrintable(var2.charAt(var1))) {
               var2.deleteCharAt(var1);
            }
         }

         return var2.toString();
      }
   }

   static {
      (a = new TreeSet()).add("1.0");
      a.add("1.5");
      a.add("1.6");
      a.add("2.0");
      a.add("2.1");
      a.add("2.2");
      a.add("2.3");
      a.add("2.4");
      a.add("3.0");
      a.add("3.1");
      a.add("3.2");
      a.add("3.3");
      a.add("4.0");
      a.add("4.1");
      a.add("4.2");
      a.add("4.3");
      a.add("4.4");
      a.add("4.5");
      a.add("5.0");
      a.add("5.1");
      a.add("5.2");
      a.add("5.3");
      a.add("6.0");
      a.add("6.1");
      a.add("7.0");
      a.add("7.1");
      a.add("7.2");
      a.add("8.0");
      a.add("8.1");
      a.add("9.0");
      STRIP_QUOTE_PATTERN = Pattern.compile("\"");
      NAMESPACE_NUMBER_PATTERN = Pattern.compile("ns=(\\d*)");
      b = Pattern.compile("Android/(\\d\\.\\d)");
      c = Pattern.compile("Android (\\d\\.\\d)");
      d = Pattern.compile(":::Android_(\\d\\.\\d)");
      e = Pattern.compile("Version/(\\d+)\\.\\d+");
      f = Pattern.compile("(^[A-Za-z0-9_\\-\\+ ]+)[/ ]?(?:[A-Za-z0-9_\\-\\+\\.]+)? +Linux/[0-9\\.\\+]+ +Android[ /][0-9\\.]+ +Release/[0-9\\.]+");
      g = Pattern.compile("(^[A-Za-z0-9_\\-\\+ ]+)[/ ]?(?:[A-Za-z0-9_\\-\\+\\.]+)? Android/[0-9\\.]+ \\(Linux;");
      h = Pattern.compile("Android [^;]+;(?>(?: xx-xx[ ;]+)?)(.+?)(?:Build/|MIUI/|\\))");
      i = Pattern.compile("^(?:AmazonWebView|Appstore|Amazon\\.com)/.+Android[/ ][\\d\\.]+/(?:[\\d]+/)?([A-Za-z0-9_\\- ]+)\\b");
      j = Pattern.compile("Mobile Safari/[\\d\\.]+ (GiONEE-[A-Za-z0-9]+)/");
      k = Pattern.compile(";(?! )");
      l = Pattern.compile("^mShop:::Amazon_Android_[\\d\\.]+:::(.+?):::Android_[\\d\\.]+");
      m = Pattern.compile("xx-xx");
      n = Pattern.compile("(?:_CMCC_TD|_CMCC|_TD)\\b");
      o = Pattern.compile("HW-HUAWEI_");
      p = Pattern.compile("YL-Coolpad[ _]");
      q = Pattern.compile("HTC[ _\\-/]");
      r = Pattern.compile("( V| )\\d+?\\.[\\d\\.]+$");
      Pattern.compile("( V| )\\d+?\\.[\\d\\.]+$");
      s = Pattern.compile("UCBrowser\\/(\\d+)\\.\\d");
      t = Pattern.compile("; Adr (\\d+\\.\\d+)\\.?");
      u = Pattern.compile("Adr [\\d\\.]+; [a-zA-Z]+-[a-zA-Z]+; (.*)\\) U2");
      v = Pattern.compile("HTC[ _\\-/]");
      w = Pattern.compile("(/| +V?\\d)[\\.\\d]+$");
      x = Pattern.compile("/.*$");
      y = Pattern.compile("(SAMSUNG[^/]+)/.*$");
      z = Pattern.compile("ORANGE/.*$");
      A = Pattern.compile("(LG-[A-Za-z0-9\\-]+).*$");
      B = Pattern.compile("(LG-?[A-Za-z0-9\\-]+).*$");
      C = Pattern.compile("\\[[\\d]{10}\\]");
      D = Pattern.compile("^(?:SAMSUNG|SonyEricsson|Sony|HUAWEI)[ \\-]?");
      E = Pattern.compile("IEMobile/\\d+\\.\\d+;(?: ARM;)?(?: Touch;)? ?([^;\\)]+(; ?[^;\\)]+)?)");
      F = Pattern.compile("Android [\\d\\.]+?; ([^;\\)]+(; ?[^;\\)]+)?).+?Edge/\\d");
      G = Pattern.compile("Windows ?Phone(?: ?OS)? ?(\\d+\\.\\d+)");
      H = Pattern.compile("Windows NT (\\d+\\.\\d+)");
      I = Pattern.compile("\\(Windows NT [\\d\\.]+?; ARM;.+?; WPDesktop; ([^;\\)]+(; ?[^;\\)]+)?)\\) like Gecko");
      J = Pattern.compile("\\(Windows NT [\\d\\.]+?; ARM; ([^;\\)]+(; ?[^;\\)]+)?).+?Edge/\\d");
      K = Pattern.compile(";(?! )");
      L = Pattern.compile("(NOKIA; RM-.+?)_.*");
      M = Pattern.compile("(Microsoft; RM-.+?)_.*");
      N = new ArrayList();
      O = new ArrayList();
      P = new ArrayList();
      Q = new ArrayList();
      N.add("midp");
      N.add("mobile");
      N.add("android");
      N.add("samsung");
      N.add("nokia");
      N.add("up.browser");
      N.add("phone");
      N.add("opera mini");
      N.add("opera mobi");
      N.add("brew");
      N.add("sonyericsson");
      N.add("blackberry");
      N.add("netfront");
      N.add("uc browser");
      N.add("symbian");
      N.add("j2me");
      N.add("wap2.");
      N.add("up.link");
      N.add(" arm;");
      N.add("windows ce");
      N.add("vodafone");
      N.add("ucweb");
      N.add("zte-");
      N.add("ipad;");
      N.add("docomo");
      N.add("armv");
      N.add("maemo");
      N.add("palm");
      N.add("bolt");
      N.add("fennec");
      N.add("wireless");
      N.add("adr-");
      N.add("htc");
      N.add("; xbox");
      N.add("nintendo");
      N.add("zunewp7");
      N.add("skyfire");
      N.add("silk");
      N.add("untrusted");
      N.add("lgtelecom");
      N.add(" gt-");
      N.add("ventana");
      N.add("tizen");
      O.add("wow64");
      O.add(".net clr");
      O.add("gtb7");
      O.add("macintosh");
      O.add("slcc1");
      O.add("gtb6");
      O.add("funwebproducts");
      O.add("aol 9.");
      O.add("gtb8");
      O.add("iceweasel");
      O.add("epiphany");
      P.add("googletv");
      P.add("boxee");
      P.add("sonydtv");
      P.add("appletv");
      P.add("apple tv");
      P.add("smarttv");
      P.add("smart-tv");
      P.add("dlna");
      P.add("ce-html");
      P.add("inettvbrowser");
      P.add("opera tv");
      P.add("viera");
      P.add("konfabulator");
      P.add("sony bravia");
      P.add("crkey");
      P.add("sonycebrowser");
      P.add("hbbtv");
      P.add("large screen");
      P.add("netcast");
      P.add("philipstv");
      P.add("digital-tv");
      P.add(" mb90/");
      P.add(" mb91/");
      P.add(" mb95/");
      P.add("vizio-dtv");
      P.add("bravia");
      P.add("roku");
      Q.add("+http");
      Q.add("bot");
      Q.add("crawler");
      Q.add("spider");
      Q.add("novarra");
      Q.add("transcoder");
      Q.add("yahoo! searchmonkey");
      Q.add("yahoo! slurp");
      Q.add("feedfetcher-google");
      Q.add("mowser");
      Q.add("trove");
      Q.add("google web preview");
      Q.add("googleimageproxy");
      Q.add("gigablastopensource");
      Q.add("http-client");
      Q.add("exactsearch");
      Q.add("gecko/20100721");
      Q.add("sureseeker.com");
      Q.add("ltx71");
      Q.add("searchsecure");
      Q.add("iopus-i-m");
      R = new AhoCorasickKeywordMatcher(N);
      S = new AhoCorasickKeywordMatcher(O);
      T = new AhoCorasickKeywordMatcher(P);
      U = new AhoCorasickKeywordMatcher(Q);
      V = Pattern.compile("^Mozilla/5\\.0 \\((?:Macintosh|Windows)[^\\)]+\\) AppleWebKit/[\\d\\.]+ \\(KHTML, like Gecko\\) Version/[\\d\\.]+ Safari/[\\d\\.]+$");
      W = Pattern.compile("^Mozilla\\/5\\.0 \\(Windows NT.+?Trident.+?; rv:\\d\\d\\.\\d+\\)");
      X = Pattern.compile("^Mozilla\\/5\\.0 \\(compatible; MSIE (9|10)\\.0; Windows NT \\d\\.\\d");
      Y = Pattern.compile("^Mozilla\\/4\\.0 \\(compatible; MSIE \\d\\.\\d; Windows NT \\d\\.\\d");
      Z = Pattern.compile("[^\\d]\\d{3}x\\d{3}");
   }
}
