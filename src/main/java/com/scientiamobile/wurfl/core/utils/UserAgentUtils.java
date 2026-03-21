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
  
  public static final Pattern STRIP_QUOTE_PATTERN = Pattern.compile("\"");
  
  public static final Pattern NAMESPACE_NUMBER_PATTERN = Pattern.compile("ns=(\\d*)");
  
  private static final Pattern b = Pattern.compile("Android/(\\d\\.\\d)");
  
  private static final Pattern c = Pattern.compile("Android (\\d\\.\\d)");
  
  private static final Pattern d = Pattern.compile(":::Android_(\\d\\.\\d)");
  
  private static final Pattern e = Pattern.compile("Version/(\\d+)\\.\\d+");
  
  private static final Pattern f = Pattern.compile("(^[A-Za-z0-9_\\-\\+ ]+)[/ ]?(?:[A-Za-z0-9_\\-\\+\\.]+)? +Linux/[0-9\\.\\+]+ +Android[ /][0-9\\.]+ +Release/[0-9\\.]+");
  
  private static final Pattern g = Pattern.compile("(^[A-Za-z0-9_\\-\\+ ]+)[/ ]?(?:[A-Za-z0-9_\\-\\+\\.]+)? Android/[0-9\\.]+ \\(Linux;");
  
  private static final Pattern h = Pattern.compile("Android [^;]+;(?>(?: xx-xx[ ;]+)?)(.+?)(?:Build/|MIUI/|\\))");
  
  private static final Pattern i = Pattern.compile("^(?:AmazonWebView|Appstore|Amazon\\.com)/.+Android[/ ][\\d\\.]+/(?:[\\d]+/)?([A-Za-z0-9_\\- ]+)\\b");
  
  private static final Pattern j = Pattern.compile("Mobile Safari/[\\d\\.]+ (GiONEE-[A-Za-z0-9]+)/");
  
  private static final Pattern k = Pattern.compile(";(?! )");
  
  private static final Pattern l = Pattern.compile("^mShop:::Amazon_Android_[\\d\\.]+:::(.+?):::Android_[\\d\\.]+");
  
  private static final Pattern m = Pattern.compile("UCBrowser\\/(\\d+)\\.\\d");
  
  private static final Pattern n = Pattern.compile("; Adr (\\d+\\.\\d+)\\.?");
  
  private static final Pattern o = Pattern.compile("Adr [\\d\\.]+; [a-zA-Z]+-[a-zA-Z]+; (.*)\\) U2");
  
  private static final Pattern p = Pattern.compile("HTC[ _\\-/]");
  
  private static final Pattern q = Pattern.compile("(/| +V?\\d)[\\.\\d]+$");
  
  private static final Pattern r = Pattern.compile("/.*$");
  
  private static final Pattern s = Pattern.compile("(SAMSUNG[^/]+)/.*$");
  
  private static final Pattern t = Pattern.compile("ORANGE/.*$");
  
  private static final Pattern u = Pattern.compile("(LG-[A-Za-z0-9\\-]+).*$");
  
  private static final Pattern v = Pattern.compile("\\[[\\d]{10}\\]");
  
  private static final Pattern w = Pattern.compile("IEMobile/\\d+\\.\\d+;(?: ARM;)?(?: Touch;)? ?([^;\\)]+(; ?[^;\\)]+)?)");
  
  private static final Pattern x = Pattern.compile("Android [\\d\\.]+?; ([^;\\)]+(; ?[^;\\)]+)?).+?Edge/\\d");
  
  private static final Pattern y = Pattern.compile("Windows ?Phone(?: ?OS)? ?(\\d+\\.\\d+)");
  
  private static final Pattern z = Pattern.compile("Windows NT (\\d+\\.\\d+)");
  
  private static final Pattern A = Pattern.compile("\\(Windows NT [\\d\\.]+?; ARM;.+?; WPDesktop; ([^;\\)]+(; ?[^;\\)]+)?)\\) like Gecko");
  
  private static final Pattern B = Pattern.compile("\\(Windows NT [\\d\\.]+?; ARM; ([^;\\)]+(; ?[^;\\)]+)?).+?Edge/\\d");
  
  private static final Pattern C = Pattern.compile(";(?! )");
  
  private static final Pattern D = Pattern.compile("(NOKIA; RM-.+?)_.*");
  
  private static final Pattern E = Pattern.compile("(Microsoft; RM-.+?)_.*");
  
  private static final List F = new ArrayList();
  
  private static final List G = new ArrayList();
  
  private static final List H = new ArrayList();
  
  private static final List I = new ArrayList();
  
  private static final a J = new a(F);
  
  private static final a K = new a(G);
  
  private static final a L = new a(H);
  
  private static final a M = new a(I);
  
  private static final Pattern N = Pattern.compile("^Mozilla/5\\.0 \\((?:Macintosh|Windows)[^\\)]+\\) AppleWebKit/[\\d\\.]+ \\(KHTML, like Gecko\\) Version/[\\d\\.]+ Safari/[\\d\\.]+$");
  
  private static final Pattern O = Pattern.compile("^Mozilla\\/5\\.0 \\(Windows NT.+?Trident.+?; rv:\\d\\d\\.\\d+\\)");
  
  private static final Pattern P = Pattern.compile("^Mozilla\\/5\\.0 \\(compatible; MSIE (9|10)\\.0; Windows NT \\d\\.\\d");
  
  private static final Pattern Q = Pattern.compile("^Mozilla\\/4\\.0 \\(compatible; MSIE \\d\\.\\d; Windows NT \\d\\.\\d");
  
  private static final Pattern R = Pattern.compile("[^\\d]\\d{3}x\\d{3}");
  
  public static String getUserAgent(HttpServletRequest paramHttpServletRequest) {
    Validate.notNull(paramHttpServletRequest, "The HttpServletRequest is null");
    String str;
    if ((str = paramHttpServletRequest.getHeader("Device-Stock-UA")) == null || "".equals(str))
      str = paramHttpServletRequest.getHeader("X-OperaMini-Phone-UA"); 
    if (str == null || "".equals(str))
      str = paramHttpServletRequest.getHeader("User-Agent"); 
    if (str == null)
      str = ""; 
    return str;
  }
  
  public static String getUaProfile(HttpServletRequest paramHttpServletRequest) {
    return getUaProfile((WURFLHeaderProvider)new HttpServletRequestHeaderProvider(paramHttpServletRequest));
  }
  
  public static String getUaProfile(WURFLHeaderProvider paramWURFLHeaderProvider) {
    Validate.notNull(paramWURFLHeaderProvider, "The WURFLHeaderProvider is null");
    String str1 = null;
    String str2 = null;
    if (paramWURFLHeaderProvider.getHeader("Profile") != null)
      str1 = "Profile"; 
    if (str1 != null && str1.trim().length() > 0)
      str2 = paramWURFLHeaderProvider.getHeader(str1); 
    if (str2 != null && str2.trim().length() > 0)
      str2 = STRIP_QUOTE_PATTERN.matcher(str2).replaceAll(""); 
    return str2;
  }
  
  public static boolean isXhtmlRequester(HttpServletRequest paramHttpServletRequest) {
    Validate.notNull(paramHttpServletRequest, "HttpRequest is null");
    String str;
    return ((str = paramHttpServletRequest.getHeader("accept")) != null && (str.indexOf("application/vnd.wap.xhtml+xml") != -1 || str.indexOf("application/xhtml+xml") != -1 || str.indexOf("application/text+html") != -1));
  }
  
  public static Predicate isContainedIn(String paramString) {
    return new c(paramString);
  }
  
  public static Pattern createLocalePattern() {
    StrBuilder strBuilder;
    (strBuilder = new StrBuilder()).append("; (");
    String[] arrayOfString1 = Locale.getISOLanguages();
    for (byte b2 = 0; b2 < arrayOfString1.length; b2++) {
      if (b2 > 0)
        strBuilder.append("|"); 
      strBuilder.append(arrayOfString1[b2]);
    } 
    strBuilder.append(")");
    strBuilder.append("(-(");
    String[] arrayOfString2 = Locale.getISOCountries();
    for (byte b1 = 0; b1 < arrayOfString2.length; b1++) {
      if (b1 > 0)
        strBuilder.append("|"); 
      strBuilder.append(arrayOfString2[b1]);
    } 
    strBuilder.append("))?");
    return Pattern.compile(strBuilder.toString(), 2);
  }
  
  public static Map getHeaders(HttpServletRequest paramHttpServletRequest) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    Enumeration<String> enumeration = paramHttpServletRequest.getHeaderNames();
    while (enumeration.hasMoreElements()) {
      String str = enumeration.nextElement();
      hashMap.put(str, paramHttpServletRequest.getHeader(str));
    } 
    return hashMap;
  }
  
  public static String getAndroidVersion(String paramString, boolean paramBoolean) {
    Matcher matcher;
    boolean bool;
    if (!(bool = (matcher = c.matcher(paramString)).find()))
      bool = (matcher = b.matcher(paramString)).find(); 
    if (!bool)
      bool = (matcher = d.matcher(paramString)).find(); 
    if (bool) {
      paramString = matcher.group(1);
      if (a.contains(paramString))
        return paramString; 
    } 
    return paramBoolean ? "2.0" : null;
  }
  
  public static String getOperaOnAndroidVersion(String paramString, boolean paramBoolean) {
    Matcher matcher;
    return ((matcher = e.matcher(paramString)).find() && (matcher.group(1).equals("11") || matcher.group(1).equals("12"))) ? matcher.group(1) : (paramBoolean ? "10" : null);
  }
  
  public static String getAndroidModel(String paramString) {
    paramString = k.matcher(paramString).replaceAll("; ");
    Matcher matcher;
    if ((matcher = j.matcher(paramString)).find()) {
      paramString = matcher.group(1);
    } else {
      matcher = f.matcher(paramString);
      Matcher matcher1 = g.matcher(paramString);
      if (matcher.find()) {
        paramString = StringMatchUtils.rtrim(matcher.group(1), new char[] { ';', ' ' });
      } else if (matcher1.find()) {
        paramString = StringMatchUtils.rtrim(matcher1.group(1), new char[] { ' ', ';' });
      } else {
        matcher = h.matcher(paramString);
        matcher1 = i.matcher(paramString);
        Matcher matcher2 = l.matcher(paramString);
        if (matcher.find()) {
          str = StringMatchUtils.rtrim(matcher.group(1), new char[] { ';', ' ' });
        } else if (matcher1.find()) {
          str = matcher1.group(1);
        } else if (str.find()) {
          str = str.group(1);
        } else {
          return null;
        } 
      } 
    } 
    if (str != null && str.startsWith("Build/"))
      return null; 
    String str;
    int i;
    if (StringMatchUtils.indexOf(str = str.replaceAll("xx-xx", "").replaceAll("(?:_CMCC_TD|_CMCC|_TD)\\b", ""), "*") >= 0 && (i = StringMatchUtils.indexOf(str, "/")) >= 0)
      str = str.substring(0, i); 
    if (StringMatchUtils.indexOf(str = str.replaceAll("HW-HUAWEI_", "HUAWEI "), "YL-Coolpad") >= 0)
      str = str.replaceAll("YL-Coolpad[ _]", "Coolpad "); 
    if (str.contains("HTC")) {
      if ((i = StringMatchUtils.indexOf(str = str.replaceAll("HTC[ _\\-/]", "HTC~"), "/")) >= 0)
        str = str.substring(0, i); 
      str = str.replaceAll("( V| )\\d+?\\.[\\d\\.]+$", "");
    } 
    if ((str = str.replaceAll("(SAMSUNG[^/]+)/.*$", "$1").replaceAll("ORANGE/.*$", "ORANGE").replaceAll("(LG-?[A-Za-z0-9\\-]+).*$", "$1").replaceAll("\\[[\\d]{10}\\]", "").trim().replaceAll("^(?:SAMSUNG|SonyEricsson|Sony)[ \\-]?", "")).length() == 0)
      str = null; 
    return str;
  }
  
  public static String getUcBrowserVersion(String paramString, boolean paramBoolean) {
    Matcher matcher;
    return (matcher = m.matcher(paramString)).find() ? matcher.group(1) : null;
  }
  
  public static String getUcAndroidVersion(String paramString, boolean paramBoolean) {
    Matcher matcher;
    if ((matcher = n.matcher(paramString)).find()) {
      String str = matcher.group(1);
      if (a.contains(str))
        return str; 
    } 
    return paramBoolean ? "2.0" : null;
  }
  
  public static String getUcAndroidModel(String paramString, boolean paramBoolean) {
    Matcher matcher;
    if (!(matcher = o.matcher(paramString)).find())
      return null; 
    String str = matcher.group(1);
    if (paramString.contains("HTC")) {
      str = p.matcher(str).replaceFirst("HTC~");
      str = q.matcher(str).replaceFirst("");
      str = r.matcher(str).replaceFirst("");
    } 
    str = s.matcher(str).replaceFirst("$1");
    str = t.matcher(str).replaceFirst("ORANGE");
    str = u.matcher(str).replaceFirst("$1");
    return StringUtils.isEmpty(str = v.matcher(str).replaceFirst("").trim()) ? null : str;
  }
  
  public static final String getWindowsPhoneVersion(String paramString) {
    Matcher matcher;
    return (matcher = y.matcher(paramString)).find() ? ((str = matcher.group(1)).startsWith("10.0") ? "10.0" : ((str.startsWith("6.3") || str.startsWith("8.1")) ? "8.1" : (str.startsWith("8.") ? "8.0" : (str.startsWith("7.8") ? "7.8" : ((str.startsWith("7.10") || str.startsWith("7.5")) ? "7.5" : (str.startsWith("6.5") ? "6.5" : "7.0")))))) : null;
  }
  
  public static final String getWindowsPhoneModel(String paramString) {
    return cleanAndReplaceWindowsPhoneModel(paramString, new Pattern[] { w, x });
  }
  
  public static final String getWindowsPhoneDesktopVersion(String paramString) {
    Matcher matcher;
    return (matcher = z.matcher(paramString)).find() ? (((str = matcher.group(1)).indexOf("10.0") >= 0) ? "10.0" : ((str.indexOf("6.3") >= 0 || str.indexOf("8.1") >= 0) ? "8.1" : "8.0")) : null;
  }
  
  public static final String getWindowsPhoneDesktopModel(String paramString) {
    return cleanAndReplaceWindowsPhoneModel(paramString, new Pattern[] { A, B });
  }
  
  public static final String cleanAndReplaceWindowsPhoneModel(String paramString, Pattern... paramVarArgs) {
    paramString = C.matcher(paramString).replaceAll("; ");
    Matcher matcher = null;
    int i = (paramVarArgs = paramVarArgs).length;
    for (byte b = 0; b < i && !(matcher = paramVarArgs[b].matcher(paramString)).find(); b++)
      matcher = null; 
    if (matcher != null) {
      String str = matcher.group(1).replace("_blocked", "");
      str = D.matcher(str).replaceFirst("$1");
      return E.matcher(str).replaceFirst("$1");
    } 
    return null;
  }
  
  public static boolean isWindowsPhoneAdClient(String paramString) {
    return StringMatchUtils.startsWithAnyOf(paramString, new String[] { "Windows Phone Ad Client", "WindowsPhoneAdClient" });
  }
  
  public static boolean mobileKeywordsDetected(String paramString) {
    return J.a(paramString);
  }
  
  public static boolean screenSizeDetected(String paramString) {
    return R.matcher(paramString).find();
  }
  
  public static boolean isDesktopBrowser(String paramString) {
    return K.a(paramString);
  }
  
  public static boolean isSmartTvBrowser(String paramString) {
    return L.a(paramString);
  }
  
  public static boolean isBot(String paramString) {
    return M.a(paramString);
  }
  
  public static boolean isDesktopPattern(String paramString) {
    return N.matcher(paramString).matches();
  }
  
  public static boolean isIEPattern(String paramString) {
    return (O.matcher(paramString).find() || P.matcher(paramString).find() || Q.matcher(paramString).find());
  }
  
  public static String createApiUserAgent(WURFLEngine paramWURFLEngine) {
    String str2 = "scientiamobile.com - ";
    String str1 = str1.substring(i + str2.length());
    int i;
    return ResourceUtils.getBuildId() + "/WURFL_JAVA_API/1.9.0.0 WURFL/" + (StringUtils.isEmpty(str1 = paramWURFLEngine.getWURFLUtils().getVersion()) ? str1 : (((i = str1.indexOf(str2)) == -1) ? str1 : ("Snapshot_" + str1.substring(0, str1.lastIndexOf(' ')).replace('-', '_')))) + " Java/" + System.getProperty("java.version") + " " + System.getProperty("os.name") + "/" + System.getProperty("os.version");
  }
  
  public static List getMobileBrowsers() {
    return Collections.unmodifiableList(F);
  }
  
  public static boolean hasIIsLoggingStyle(String paramString) {
    return (StringUtils.countMatches(paramString, " ") == 0 && StringUtils.countMatches(paramString, "+") > 2);
  }
  
  public static boolean isRawUrlEncoded(String paramString) {
    return (StringUtils.countMatches(paramString, "%") > 2);
  }
  
  public static UserAgentWithNeedleCount getAsciiPrintableStringWithNeedleCount(StringBuilder paramStringBuilder) {
    boolean bool = false;
    char[] arrayOfChar = { '+', '%' };
    int[] arrayOfInt = new int[2];
    if (paramStringBuilder == null || paramStringBuilder.length() == 0)
      return new UserAgentWithNeedleCount("", 0, 0, false); 
    for (int i = paramStringBuilder.length() - 1; i >= 0; i--) {
      char c = paramStringBuilder.charAt(i);
      bool = (bool || c == ' ') ? true : false;
      if (!CharUtils.isAsciiPrintable(c)) {
        paramStringBuilder.deleteCharAt(i);
      } else {
        for (byte b = 0; b < 2; b++) {
          if (c == arrayOfChar[b])
            arrayOfInt[b] = arrayOfInt[b] + 1; 
        } 
      } 
    } 
    return new UserAgentWithNeedleCount(paramStringBuilder.toString(), arrayOfInt[0], arrayOfInt[1], bool);
  }
  
  public static String getAsciiPrintableString(String paramString) {
    if (paramString == null)
      return ""; 
    StringBuilder stringBuilder;
    for (int i = (stringBuilder = new StringBuilder(paramString)).length() - 1; i >= 0; i--) {
      if (!CharUtils.isAsciiPrintable(stringBuilder.charAt(i)))
        stringBuilder.deleteCharAt(i); 
    } 
    return stringBuilder.toString();
  }
  
  static {
    (a = new TreeSet<String>()).add("1.0");
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
  }
  
  static {
    F.add("midp");
    F.add("mobile");
    F.add("android");
    F.add("samsung");
    F.add("nokia");
    F.add("up.browser");
    F.add("phone");
    F.add("opera mini");
    F.add("opera mobi");
    F.add("brew");
    F.add("sonyericsson");
    F.add("blackberry");
    F.add("netfront");
    F.add("uc browser");
    F.add("symbian");
    F.add("j2me");
    F.add("wap2.");
    F.add("up.link");
    F.add(" arm;");
    F.add("windows ce");
    F.add("vodafone");
    F.add("ucweb");
    F.add("zte-");
    F.add("ipad;");
    F.add("docomo");
    F.add("armv");
    F.add("maemo");
    F.add("palm");
    F.add("bolt");
    F.add("fennec");
    F.add("wireless");
    F.add("adr-");
    F.add("htc");
    F.add("; xbox");
    F.add("nintendo");
    F.add("zunewp7");
    F.add("skyfire");
    F.add("silk");
    F.add("untrusted");
    F.add("lgtelecom");
    F.add(" gt-");
    F.add("ventana");
    F.add("tizen");
    G.add("wow64");
    G.add(".net clr");
    G.add("gtb7");
    G.add("macintosh");
    G.add("slcc1");
    G.add("gtb6");
    G.add("funwebproducts");
    G.add("aol 9.");
    G.add("gtb8");
    G.add("iceweasel");
    G.add("epiphany");
    H.add("googletv");
    H.add("boxee");
    H.add("sonydtv");
    H.add("appletv");
    H.add("apple tv");
    H.add("smarttv");
    H.add("smart-tv");
    H.add("dlna");
    H.add("ce-html");
    H.add("inettvbrowser");
    H.add("opera tv");
    H.add("viera");
    H.add("konfabulator");
    H.add("sony bravia");
    H.add("crkey");
    H.add("sonycebrowser");
    H.add("hbbtv");
    H.add("large screen");
    H.add("netcast");
    H.add("philipstv");
    H.add("digital-tv");
    H.add(" mb90/");
    H.add(" mb91/");
    H.add(" mb95/");
    H.add("vizio-dtv");
    H.add("bravia");
    H.add("roku");
    I.add("+http");
    I.add("bot");
    I.add("crawler");
    I.add("spider");
    I.add("novarra");
    I.add("transcoder");
    I.add("yahoo! searchmonkey");
    I.add("yahoo! slurp");
    I.add("feedfetcher-google");
    I.add("mowser");
    I.add("trove");
    I.add("google web preview");
    I.add("googleimageproxy");
    I.add("gigablastopensource");
    I.add("http-client");
    I.add("exactsearch");
    I.add("gecko/20100721");
    I.add("sureseeker.com");
    I.add("ltx71");
    I.add("searchsecure");
    I.add("iopus-i-m");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\utils\UserAgentUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
