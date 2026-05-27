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

public final class UserAgentUtils {
   private static final SortedSet<String> SUPPORTED_ANDROID_VERSIONS;
   private static final String JAVA_VERSION = System.getProperty("java.version");
   private static final String OS_NAME = System.getProperty("os.name");
   private static final String OS_VERSION = System.getProperty("os.version");
   public static final Pattern STRIP_QUOTE_PATTERN;
   public static final Pattern NAMESPACE_NUMBER_PATTERN;
   private static final Pattern ANDROID_VERSION_SLASH_PATTERN;
   private static final Pattern ANDROID_VERSION_SPACE_PATTERN;
   private static final Pattern AMAZON_ANDROID_VERSION_PATTERN;
   private static final Pattern OPERA_ON_ANDROID_MAJOR_VERSION_PATTERN;
   private static final Pattern ANDROID_MODEL_LINUX_ANDROID_RELEASE_PATTERN;
   private static final Pattern ANDROID_MODEL_ANDROID_LINUX_PATTERN;
   private static final Pattern ANDROID_MODEL_BUILD_PATTERN;
   private static final Pattern ANDROID_MODEL_AMAZON_APP_PATTERN;
   private static final Pattern GIONEE_MODEL_PATTERN;
   private static final Pattern SEMICOLON_WITHOUT_SPACE_PATTERN;
   private static final Pattern AMAZON_SHOPPING_MODEL_PATTERN;
   private static final Pattern XX_XX_LOCALE_PATTERN;
   private static final Pattern CMCC_SUFFIX_PATTERN;
   private static final Pattern HUAWEI_PREFIX_PATTERN;
   private static final Pattern COOLPAD_PREFIX_PATTERN;
   private static final Pattern HTC_PREFIX_PATTERN;
   private static final Pattern VERSION_SUFFIX_PATTERN;
   private static final Pattern UC_BROWSER_MAJOR_VERSION_PATTERN;
   private static final Pattern ADR_ANDROID_VERSION_PATTERN;
   private static final Pattern UC_ANDROID_MODEL_PATTERN;
   private static final Pattern VERSION_TRAILING_PATTERN;
   private static final Pattern SLASH_TO_END_PATTERN;
   private static final Pattern SAMSUNG_MODEL_PATTERN;
   private static final Pattern ORANGE_SUFFIX_PATTERN;
   private static final Pattern LG_MODEL_PATTERN;
   private static final Pattern LG_MODEL_OPTIONAL_HYPHEN_PATTERN;
   private static final Pattern TIMESTAMP_IN_BRACKETS_PATTERN;
   private static final Pattern BRAND_PREFIX_PATTERN;
   private static final Pattern WINDOWS_PHONE_MODEL_PATTERN;
   private static final Pattern WINDOWS_PHONE_EDGE_MODEL_PATTERN;
   private static final Pattern WINDOWS_PHONE_VERSION_PATTERN;
   private static final Pattern WINDOWS_NT_VERSION_PATTERN;
   private static final Pattern WINDOWS_PHONE_DESKTOP_MODEL_PATTERN;
   private static final Pattern WINDOWS_PHONE_ARM_EDGE_MODEL_PATTERN;
   private static final Pattern NOKIA_RM_MODEL_PATTERN;
   private static final Pattern MICROSOFT_RM_MODEL_PATTERN;
   private static final List<String> MOBILE_KEYWORDS;
   private static final List<String> DESKTOP_KEYWORDS;
   private static final List<String> SMART_TV_KEYWORDS;
   private static final List<String> BOT_KEYWORDS;
   private static final AhoCorasickKeywordMatcher MOBILE_KEYWORDS_MATCHER;
   private static final AhoCorasickKeywordMatcher DESKTOP_BROWSER_MATCHER;
   private static final AhoCorasickKeywordMatcher SMART_TV_BROWSER_MATCHER;
   private static final AhoCorasickKeywordMatcher BOT_MATCHER;
   private static final Pattern DESKTOP_SAFARI_PATTERN;
   private static final Pattern IE11_TRIDENT_PATTERN;
   private static final Pattern MSIE_9_10_PATTERN;
   private static final Pattern MSIE_LEGACY_PATTERN;
   private static final Pattern SCREEN_SIZE_PATTERN;

   private UserAgentUtils() {
   }

   public static String getUserAgent(HttpServletRequest request) {
      Validate.notNull(request, "The HttpServletRequest is null");
      String userAgent;
      userAgent = request.getHeader("Device-Stock-UA");
      if (userAgent == null || "".equals(userAgent)) {
         userAgent = request.getHeader("X-OperaMini-Phone-UA");
      }

      if (userAgent == null || "".equals(userAgent)) {
         userAgent = request.getHeader("User-Agent");
      }

      if (userAgent == null) {
         userAgent = "";
      }

      return userAgent;
   }

   public static String getUaProfile(HttpServletRequest request) {
      return getUaProfile((WURFLHeaderProvider)(new HttpServletRequestHeaderProvider(request)));
   }

   public static String getUaProfile(WURFLHeaderProvider headerProvider) {
      Validate.notNull(headerProvider, "The WURFLHeaderProvider is null");
      String headerName = null;
      String uaProfile = null;
      if (headerProvider.getHeader("Profile") != null) {
         headerName = "Profile";
      }

      if (headerName != null && headerName.trim().length() > 0) {
         uaProfile = headerProvider.getHeader(headerName);
      }

      if (uaProfile != null && uaProfile.trim().length() > 0) {
         uaProfile = STRIP_QUOTE_PATTERN.matcher(uaProfile).replaceAll("");
      }

      return uaProfile;
   }

   public static boolean isXhtmlRequester(HttpServletRequest request) {
      Validate.notNull(request, "HttpRequest is null");
      String accept;
      return (accept = request.getHeader("accept")) != null && (accept.indexOf("application/vnd.wap.xhtml+xml") != -1 || accept.indexOf("application/xhtml+xml") != -1 || accept.indexOf("application/text+html") != -1);
   }

   public static Predicate<String> isContainedIn(String value) {
      return new ContainsIgnoreCasePredicate(value);
   }

   public static Pattern createLocalePattern() {
      StringBuilder patternBuilder;
      (patternBuilder = new StringBuilder()).append("; (");
      String[] languages = Locale.getISOLanguages();

      for(int i = 0; i < languages.length; ++i) {
         if (i > 0) {
            patternBuilder.append("|");
         }

         patternBuilder.append(languages[i]);
      }

      patternBuilder.append(")");
      patternBuilder.append("(-(");
      String[] countries = Locale.getISOCountries();

      for(int i = 0; i < countries.length; ++i) {
         if (i > 0) {
            patternBuilder.append("|");
         }

         patternBuilder.append(countries[i]);
      }

      patternBuilder.append("))?");
      return Pattern.compile(patternBuilder.toString(), Pattern.CASE_INSENSITIVE);
   }

   public static Map<String, String> getHeaders(HttpServletRequest request) {
      HashMap<String, String> headers = new HashMap<>();
      Enumeration<String> headerNames = request.getHeaderNames();

      while(headerNames.hasMoreElements()) {
         String headerName = headerNames.nextElement();
         headers.put(headerName, request.getHeader(headerName));
      }

      return headers;
   }

   public static String getAndroidVersion(String userAgent, boolean returnDefaultIfMissing) {
      Matcher matcher;
      boolean found;
      if (!(found = (matcher = ANDROID_VERSION_SPACE_PATTERN.matcher(userAgent)).find())) {
         found = (matcher = ANDROID_VERSION_SLASH_PATTERN.matcher(userAgent)).find();
      }

      if (!found) {
         found = (matcher = AMAZON_ANDROID_VERSION_PATTERN.matcher(userAgent)).find();
      }

      if (found) {
         userAgent = matcher.group(1);
         if (SUPPORTED_ANDROID_VERSIONS.contains(userAgent)) {
            return userAgent;
         }
      }

      return returnDefaultIfMissing ? "4.0" : null;
   }

   public static String getOperaOnAndroidVersion(String userAgent, boolean returnDefaultIfMissing) {
      Matcher matcher;
      if (!(matcher = OPERA_ON_ANDROID_MAJOR_VERSION_PATTERN.matcher(userAgent)).find() || !matcher.group(1).equals("11") && !matcher.group(1).equals("12")) {
         return returnDefaultIfMissing ? "10" : null;
      } else {
         return matcher.group(1);
      }
   }

   public static String getAndroidModel(String userAgent) {
      userAgent = SEMICOLON_WITHOUT_SPACE_PATTERN.matcher(userAgent).replaceAll("; ");
      Matcher primaryMatcher;
      primaryMatcher = GIONEE_MODEL_PATTERN.matcher(userAgent);
      if (primaryMatcher.find()) {
         userAgent = primaryMatcher.group(1);
      } else {
         primaryMatcher = ANDROID_MODEL_LINUX_ANDROID_RELEASE_PATTERN.matcher(userAgent);
         Matcher secondaryMatcher = ANDROID_MODEL_ANDROID_LINUX_PATTERN.matcher(userAgent);
         if (primaryMatcher.find()) {
            userAgent = StringMatchUtils.rtrim(primaryMatcher.group(1), ';', ' ');
         } else if (secondaryMatcher.find()) {
            userAgent = StringMatchUtils.rtrim(secondaryMatcher.group(1), ' ', ';');
         } else {
            primaryMatcher = ANDROID_MODEL_BUILD_PATTERN.matcher(userAgent);
            secondaryMatcher = ANDROID_MODEL_AMAZON_APP_PATTERN.matcher(userAgent);
            Matcher amazonShoppingMatcher = AMAZON_SHOPPING_MODEL_PATTERN.matcher(userAgent);
            if (primaryMatcher.find()) {
               userAgent = StringMatchUtils.rtrim(primaryMatcher.group(1), ';', ' ');
            } else if (secondaryMatcher.find()) {
               userAgent = secondaryMatcher.group(1);
            } else {
               if (!amazonShoppingMatcher.find()) {
                  return null;
               }

               userAgent = amazonShoppingMatcher.group(1);
            }
         }
      }

      if (userAgent != null && userAgent.startsWith("Build/")) {
         return null;
      } else {
         int slashIndex;
         if (StringMatchUtils.indexOf(userAgent = StringMatchUtils.replaceAll(StringMatchUtils.replaceAll(userAgent, XX_XX_LOCALE_PATTERN, ""), CMCC_SUFFIX_PATTERN, ""), "*") >= 0 && (slashIndex = StringMatchUtils.indexOf(userAgent, "/")) >= 0) {
            userAgent = userAgent.substring(0, slashIndex);
         }

         userAgent = StringMatchUtils.replaceAll(StringMatchUtils.replaceAll(userAgent, HUAWEI_PREFIX_PATTERN, "HUAWEI "), COOLPAD_PREFIX_PATTERN, "Coolpad ");
         if (userAgent.contains("HTC")) {
            slashIndex = StringMatchUtils.indexOf(userAgent = StringMatchUtils.replaceAll(userAgent, HTC_PREFIX_PATTERN, "HTC~"), "/");
            if (slashIndex >= 0) {
               userAgent = userAgent.substring(0, slashIndex);
            }

            userAgent = StringMatchUtils.replaceAll(userAgent, VERSION_SUFFIX_PATTERN, "");
         }

         userAgent = StringMatchUtils.replaceAll(StringMatchUtils.replaceAll(StringMatchUtils.replaceAll(StringMatchUtils.replaceAll(StringMatchUtils.replaceAll(userAgent, SAMSUNG_MODEL_PATTERN, "$1"), ORANGE_SUFFIX_PATTERN, "ORANGE"), LG_MODEL_OPTIONAL_HYPHEN_PATTERN, "$1"), TIMESTAMP_IN_BRACKETS_PATTERN, "").trim(), BRAND_PREFIX_PATTERN, "");
      if (userAgent.length() == 0) {
            userAgent = null;
         }

         return userAgent;
      }
   }

   public static String getUcBrowserVersion(String userAgent, boolean returnDefaultIfMissing) {
      Matcher matcher;
      return (matcher = UC_BROWSER_MAJOR_VERSION_PATTERN.matcher(userAgent)).find() ? matcher.group(1) : null;
   }

   public static String getUcAndroidVersion(String userAgent, boolean returnDefaultIfMissing) {
      Matcher matcher;
      matcher = ADR_ANDROID_VERSION_PATTERN.matcher(userAgent);
      if (matcher.find()) {
         String androidVersion = matcher.group(1);
         if (SUPPORTED_ANDROID_VERSIONS.contains(androidVersion)) {
            return androidVersion;
         }
      }

      return returnDefaultIfMissing ? "4.0" : null;
   }

   public static String getUcAndroidModel(String userAgent, boolean returnDefaultIfMissing) {
      Matcher matcher;
      if (!(matcher = UC_ANDROID_MODEL_PATTERN.matcher(userAgent)).find()) {
         return null;
      } else {
         String model = matcher.group(1);
         if (userAgent.contains("HTC")) {
            model = HTC_PREFIX_PATTERN.matcher(model).replaceFirst("HTC~");
            model = VERSION_TRAILING_PATTERN.matcher(model).replaceFirst("");
            model = SLASH_TO_END_PATTERN.matcher(model).replaceFirst("");
         }

         model = SAMSUNG_MODEL_PATTERN.matcher(model).replaceFirst("$1");
         model = ORANGE_SUFFIX_PATTERN.matcher(model).replaceFirst("ORANGE");
         model = LG_MODEL_PATTERN.matcher(model).replaceFirst("$1");
         String cleaned;
         return StringUtils.isEmpty(cleaned = TIMESTAMP_IN_BRACKETS_PATTERN.matcher(model).replaceFirst("").trim()) ? null : cleaned;
      }
   }

   public static final String getWindowsPhoneVersion(String userAgent) {
      Matcher matcher;
      matcher = WINDOWS_PHONE_VERSION_PATTERN.matcher(userAgent);
      if (matcher.find()) {
         String windowsPhoneVersion;
         windowsPhoneVersion = matcher.group(1);
      if (windowsPhoneVersion.startsWith("10.0")) {
            return "10.0";
         } else if (!windowsPhoneVersion.startsWith("6.3") && !windowsPhoneVersion.startsWith("8.1")) {
            if (windowsPhoneVersion.startsWith("8.")) {
               return "8.0";
            } else if (windowsPhoneVersion.startsWith("7.8")) {
               return "7.8";
            } else if (!windowsPhoneVersion.startsWith("7.10") && !windowsPhoneVersion.startsWith("7.5")) {
               return windowsPhoneVersion.startsWith("6.5") ? "6.5" : "7.0";
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

   public static final String getWindowsPhoneModel(String userAgent) {
      return cleanAndReplaceWindowsPhoneModel(userAgent, WINDOWS_PHONE_MODEL_PATTERN, WINDOWS_PHONE_EDGE_MODEL_PATTERN);
   }

   public static final String getWindowsPhoneDesktopVersion(String userAgent) {
      Matcher matcher;
      matcher = WINDOWS_NT_VERSION_PATTERN.matcher(userAgent);
      if (matcher.find()) {
         String windowsNtVersion;
         windowsNtVersion = matcher.group(1);
      if (windowsNtVersion.indexOf("10.0") >= 0) {
            return "10.0";
         } else {
            return windowsNtVersion.indexOf("6.3") < 0 && windowsNtVersion.indexOf("8.1") < 0 ? "8.0" : "8.1";
         }
      } else {
         return null;
      }
   }

   public static final String getWindowsPhoneDesktopModel(String userAgent) {
      return cleanAndReplaceWindowsPhoneModel(userAgent, WINDOWS_PHONE_DESKTOP_MODEL_PATTERN, WINDOWS_PHONE_ARM_EDGE_MODEL_PATTERN);
   }

   public static final String cleanAndReplaceWindowsPhoneModel(String userAgent, Pattern... patterns) {
      userAgent = SEMICOLON_WITHOUT_SPACE_PATTERN.matcher(userAgent).replaceAll("; ");
      Matcher matcher = null;
      Pattern[] patternArray;
      int patternCount = (patternArray = patterns).length;

      for(int i = 0; i < patternCount && !(matcher = patternArray[i].matcher(userAgent)).find(); ++i) {
         matcher = null;
      }

      if (matcher != null) {
         String model = matcher.group(1).replace("_blocked", "");
         model = NOKIA_RM_MODEL_PATTERN.matcher(model).replaceFirst("$1");
         return MICROSOFT_RM_MODEL_PATTERN.matcher(model).replaceFirst("$1");
      } else {
         return null;
      }
   }

   public static boolean isWindowsPhoneAdClient(String userAgent) {
      return StringMatchUtils.startsWithAnyOf(userAgent, "Windows Phone Ad Client", "WindowsPhoneAdClient");
   }

   public static boolean mobileKeywordsDetected(String userAgent) {
      return MOBILE_KEYWORDS_MATCHER.matchesAny(userAgent);
   }

   public static boolean screenSizeDetected(String userAgent) {
      return SCREEN_SIZE_PATTERN.matcher(userAgent).find();
   }

   public static boolean isDesktopBrowser(String userAgent) {
      return DESKTOP_BROWSER_MATCHER.matchesAny(userAgent);
   }

   public static boolean isSmartTvBrowser(String userAgent) {
      return SMART_TV_BROWSER_MATCHER.matchesAny(userAgent);
   }

   public static boolean isBot(String userAgent) {
      return BOT_MATCHER.matchesAny(userAgent);
   }

   public static boolean isDesktopPattern(String userAgent) {
      return DESKTOP_SAFARI_PATTERN.matcher(userAgent).matches();
   }

   public static boolean isIEPattern(String userAgent) {
      return IE11_TRIDENT_PATTERN.matcher(userAgent).find() || MSIE_9_10_PATTERN.matcher(userAgent).find() || MSIE_LEGACY_PATTERN.matcher(userAgent).find();
   }

   public static String createApiUserAgent(WURFLEngine wurflEngine) {
      String wurflVersion;
      String snapshotVersion;
      if (StringUtils.isEmpty(wurflVersion = wurflEngine.getWURFLUtils().getVersion())) {
         snapshotVersion = wurflVersion;
      } else {
         String versionPrefix = "scientiamobile.com - ";
         int prefixIndex;
         prefixIndex = wurflVersion.indexOf(versionPrefix);
         if (prefixIndex == -1) {
            snapshotVersion = wurflVersion;
         } else {
            wurflVersion = wurflVersion.substring(prefixIndex + versionPrefix.length());
            snapshotVersion = "Snapshot_" + wurflVersion.substring(0, wurflVersion.lastIndexOf(32)).replace('-', '_');
         }
      }

      return (new StringBuilder(ResourceUtils.getBuildId())).append("/WURFL_JAVA_API/1.9.1.0 WURFL/").append(snapshotVersion).append(" Java/").append(JAVA_VERSION).append(" ").append(OS_NAME).append("/").append(OS_VERSION).toString();
   }

   public static List<String> getMobileKeywords() {
      return Collections.unmodifiableList(MOBILE_KEYWORDS);
   }

   public static boolean hasIIsLoggingStyle(String userAgent) {
      return StringUtils.countMatches(userAgent, " ") == 0 && StringUtils.countMatches(userAgent, "+") > 2;
   }

   public static boolean isRawUrlEncoded(String userAgent) {
      return StringUtils.countMatches(userAgent, "%") > 2;
   }

   public static UserAgentWithNeedleCount getAsciiPrintableStringWithNeedleCount(StringBuilder userAgentBuilder) {
      boolean hasSpaceChars = false;
      char[] needles = new char[]{'+', '%'};
      int[] needleCounts = new int[2];
      if (userAgentBuilder != null && userAgentBuilder.length() != 0) {
         for(int i = userAgentBuilder.length() - 1; i >= 0; --i) {
            char ch = userAgentBuilder.charAt(i);
            hasSpaceChars = hasSpaceChars || ch == ' ';
            if (!CharUtils.isAsciiPrintable(ch)) {
               userAgentBuilder.deleteCharAt(i);
            } else {
               for(int needleIndex = 0; needleIndex < 2; ++needleIndex) {
                  if (ch == needles[needleIndex]) {
                     needleCounts[needleIndex]++;
                  }
               }
            }
         }

         return new UserAgentWithNeedleCount(userAgentBuilder.toString(), needleCounts[0], needleCounts[1], hasSpaceChars);
      } else {
         return new UserAgentWithNeedleCount("", 0, 0, false);
      }
   }

   public static String getAsciiPrintableString(String userAgent) {
      if (userAgent == null) {
         return "";
      } else {
         StringBuilder userAgentBuilder = new StringBuilder(userAgent);

         for(int i = userAgentBuilder.length() - 1; i >= 0; --i) {
            if (!CharUtils.isAsciiPrintable(userAgentBuilder.charAt(i))) {
               userAgentBuilder.deleteCharAt(i);
            }
         }

         return userAgentBuilder.toString();
      }
   }

   static {
      (SUPPORTED_ANDROID_VERSIONS = new TreeSet<>()).add("1.0");
      SUPPORTED_ANDROID_VERSIONS.add("1.5");
      SUPPORTED_ANDROID_VERSIONS.add("1.6");
      SUPPORTED_ANDROID_VERSIONS.add("2.0");
      SUPPORTED_ANDROID_VERSIONS.add("2.1");
      SUPPORTED_ANDROID_VERSIONS.add("2.2");
      SUPPORTED_ANDROID_VERSIONS.add("2.3");
      SUPPORTED_ANDROID_VERSIONS.add("2.4");
      SUPPORTED_ANDROID_VERSIONS.add("3.0");
      SUPPORTED_ANDROID_VERSIONS.add("3.1");
      SUPPORTED_ANDROID_VERSIONS.add("3.2");
      SUPPORTED_ANDROID_VERSIONS.add("3.3");
      SUPPORTED_ANDROID_VERSIONS.add("4.0");
      SUPPORTED_ANDROID_VERSIONS.add("4.1");
      SUPPORTED_ANDROID_VERSIONS.add("4.2");
      SUPPORTED_ANDROID_VERSIONS.add("4.3");
      SUPPORTED_ANDROID_VERSIONS.add("4.4");
      SUPPORTED_ANDROID_VERSIONS.add("4.5");
      SUPPORTED_ANDROID_VERSIONS.add("5.0");
      SUPPORTED_ANDROID_VERSIONS.add("5.1");
      SUPPORTED_ANDROID_VERSIONS.add("5.2");
      SUPPORTED_ANDROID_VERSIONS.add("5.3");
      SUPPORTED_ANDROID_VERSIONS.add("6.0");
      SUPPORTED_ANDROID_VERSIONS.add("6.1");
      SUPPORTED_ANDROID_VERSIONS.add("7.0");
      SUPPORTED_ANDROID_VERSIONS.add("7.1");
      SUPPORTED_ANDROID_VERSIONS.add("7.2");
      SUPPORTED_ANDROID_VERSIONS.add("8.0");
      SUPPORTED_ANDROID_VERSIONS.add("8.1");
      SUPPORTED_ANDROID_VERSIONS.add("9.0");
      STRIP_QUOTE_PATTERN = Pattern.compile("\"");
      NAMESPACE_NUMBER_PATTERN = Pattern.compile("ns=(\\d*)");
      ANDROID_VERSION_SLASH_PATTERN = Pattern.compile("Android/(\\d\\.\\d)");
      ANDROID_VERSION_SPACE_PATTERN = Pattern.compile("Android (\\d\\.\\d)");
      AMAZON_ANDROID_VERSION_PATTERN = Pattern.compile(":::Android_(\\d\\.\\d)");
      OPERA_ON_ANDROID_MAJOR_VERSION_PATTERN = Pattern.compile("Version/(\\d+)\\.\\d+");
      ANDROID_MODEL_LINUX_ANDROID_RELEASE_PATTERN = Pattern.compile("(^[A-Za-z0-9_\\-\\+ ]+)[/ ]?(?:[A-Za-z0-9_\\-\\+\\.]+)? +Linux/[0-9\\.\\+]+ +Android[ /][0-9\\.]+ +Release/[0-9\\.]+");
      ANDROID_MODEL_ANDROID_LINUX_PATTERN = Pattern.compile("(^[A-Za-z0-9_\\-\\+ ]+)[/ ]?(?:[A-Za-z0-9_\\-\\+\\.]+)? Android/[0-9\\.]+ \\(Linux;");
      ANDROID_MODEL_BUILD_PATTERN = Pattern.compile("Android [^;]+;(?>(?: xx-xx[ ;]+)?)(.+?)(?:Build/|MIUI/|\\))");
      ANDROID_MODEL_AMAZON_APP_PATTERN = Pattern.compile("^(?:AmazonWebView|Appstore|Amazon\\.com)/.+Android[/ ][\\d\\.]+/(?:[\\d]+/)?([A-Za-z0-9_\\- ]+)\\b");
      GIONEE_MODEL_PATTERN = Pattern.compile("Mobile Safari/[\\d\\.]+ (GiONEE-[A-Za-z0-9]+)/");
      SEMICOLON_WITHOUT_SPACE_PATTERN = Pattern.compile(";(?! )");
      AMAZON_SHOPPING_MODEL_PATTERN = Pattern.compile("^mShop:::Amazon_Android_[\\d\\.]+:::(.+?):::Android_[\\d\\.]+");
      XX_XX_LOCALE_PATTERN = Pattern.compile("xx-xx");
      CMCC_SUFFIX_PATTERN = Pattern.compile("(?:_CMCC_TD|_CMCC|_TD)\\b");
      HUAWEI_PREFIX_PATTERN = Pattern.compile("HW-HUAWEI_");
      COOLPAD_PREFIX_PATTERN = Pattern.compile("YL-Coolpad[ _]");
      HTC_PREFIX_PATTERN = Pattern.compile("HTC[ _\\-/]");
      VERSION_SUFFIX_PATTERN = Pattern.compile("( V| )\\d+?\\.[\\d\\.]+$");
      UC_BROWSER_MAJOR_VERSION_PATTERN = Pattern.compile("UCBrowser\\/(\\d+)\\.\\d");
      ADR_ANDROID_VERSION_PATTERN = Pattern.compile("; Adr (\\d+\\.\\d+)\\.?");
      UC_ANDROID_MODEL_PATTERN = Pattern.compile("Adr [\\d\\.]+; [a-zA-Z]+-[a-zA-Z]+; (.*)\\) U2");
      VERSION_TRAILING_PATTERN = Pattern.compile("(/| +V?\\d)[\\.\\d]+$");
      SLASH_TO_END_PATTERN = Pattern.compile("/.*$");
      SAMSUNG_MODEL_PATTERN = Pattern.compile("(SAMSUNG[^/]+)/.*$");
      ORANGE_SUFFIX_PATTERN = Pattern.compile("ORANGE/.*$");
      LG_MODEL_PATTERN = Pattern.compile("(LG-[A-Za-z0-9\\-]+).*$");
      LG_MODEL_OPTIONAL_HYPHEN_PATTERN = Pattern.compile("(LG-?[A-Za-z0-9\\-]+).*$");
      TIMESTAMP_IN_BRACKETS_PATTERN = Pattern.compile("\\[[\\d]{10}\\]");
      BRAND_PREFIX_PATTERN = Pattern.compile("^(?:SAMSUNG|SonyEricsson|Sony|HUAWEI)[ \\-]?");
      WINDOWS_PHONE_MODEL_PATTERN = Pattern.compile("IEMobile/\\d+\\.\\d+;(?: ARM;)?(?: Touch;)? ?([^;\\)]+(; ?[^;\\)]+)?)");
      WINDOWS_PHONE_EDGE_MODEL_PATTERN = Pattern.compile("Android [\\d\\.]+?; ([^;\\)]+(; ?[^;\\)]+)?).+?Edge/\\d");
      WINDOWS_PHONE_VERSION_PATTERN = Pattern.compile("Windows ?Phone(?: ?OS)? ?(\\d+\\.\\d+)");
      WINDOWS_NT_VERSION_PATTERN = Pattern.compile("Windows NT (\\d+\\.\\d+)");
      WINDOWS_PHONE_DESKTOP_MODEL_PATTERN = Pattern.compile("\\(Windows NT [\\d\\.]+?; ARM;.+?; WPDesktop; ([^;\\)]+(; ?[^;\\)]+)?)\\) like Gecko");
      WINDOWS_PHONE_ARM_EDGE_MODEL_PATTERN = Pattern.compile("\\(Windows NT [\\d\\.]+?; ARM; ([^;\\)]+(; ?[^;\\)]+)?).+?Edge/\\d");
      NOKIA_RM_MODEL_PATTERN = Pattern.compile("(NOKIA; RM-.+?)_.*");
      MICROSOFT_RM_MODEL_PATTERN = Pattern.compile("(Microsoft; RM-.+?)_.*");
      MOBILE_KEYWORDS = new ArrayList<>();
      DESKTOP_KEYWORDS = new ArrayList<>();
      SMART_TV_KEYWORDS = new ArrayList<>();
      BOT_KEYWORDS = new ArrayList<>();
      MOBILE_KEYWORDS.add("midp");
      MOBILE_KEYWORDS.add("mobile");
      MOBILE_KEYWORDS.add("android");
      MOBILE_KEYWORDS.add("samsung");
      MOBILE_KEYWORDS.add("nokia");
      MOBILE_KEYWORDS.add("up.browser");
      MOBILE_KEYWORDS.add("phone");
      MOBILE_KEYWORDS.add("opera mini");
      MOBILE_KEYWORDS.add("opera mobi");
      MOBILE_KEYWORDS.add("brew");
      MOBILE_KEYWORDS.add("sonyericsson");
      MOBILE_KEYWORDS.add("blackberry");
      MOBILE_KEYWORDS.add("netfront");
      MOBILE_KEYWORDS.add("uc browser");
      MOBILE_KEYWORDS.add("symbian");
      MOBILE_KEYWORDS.add("j2me");
      MOBILE_KEYWORDS.add("wap2.");
      MOBILE_KEYWORDS.add("up.link");
      MOBILE_KEYWORDS.add(" arm;");
      MOBILE_KEYWORDS.add("windows ce");
      MOBILE_KEYWORDS.add("vodafone");
      MOBILE_KEYWORDS.add("ucweb");
      MOBILE_KEYWORDS.add("zte-");
      MOBILE_KEYWORDS.add("ipad;");
      MOBILE_KEYWORDS.add("docomo");
      MOBILE_KEYWORDS.add("armv");
      MOBILE_KEYWORDS.add("maemo");
      MOBILE_KEYWORDS.add("palm");
      MOBILE_KEYWORDS.add("bolt");
      MOBILE_KEYWORDS.add("fennec");
      MOBILE_KEYWORDS.add("wireless");
      MOBILE_KEYWORDS.add("adr-");
      MOBILE_KEYWORDS.add("htc");
      MOBILE_KEYWORDS.add("; xbox");
      MOBILE_KEYWORDS.add("nintendo");
      MOBILE_KEYWORDS.add("zunewp7");
      MOBILE_KEYWORDS.add("skyfire");
      MOBILE_KEYWORDS.add("silk");
      MOBILE_KEYWORDS.add("untrusted");
      MOBILE_KEYWORDS.add("lgtelecom");
      MOBILE_KEYWORDS.add(" gt-");
      MOBILE_KEYWORDS.add("ventana");
      MOBILE_KEYWORDS.add("tizen");
      DESKTOP_KEYWORDS.add("wow64");
      DESKTOP_KEYWORDS.add(".net clr");
      DESKTOP_KEYWORDS.add("gtb7");
      DESKTOP_KEYWORDS.add("macintosh");
      DESKTOP_KEYWORDS.add("slcc1");
      DESKTOP_KEYWORDS.add("gtb6");
      DESKTOP_KEYWORDS.add("funwebproducts");
      DESKTOP_KEYWORDS.add("aol 9.");
      DESKTOP_KEYWORDS.add("gtb8");
      DESKTOP_KEYWORDS.add("iceweasel");
      DESKTOP_KEYWORDS.add("epiphany");
      SMART_TV_KEYWORDS.add("googletv");
      SMART_TV_KEYWORDS.add("boxee");
      SMART_TV_KEYWORDS.add("sonydtv");
      SMART_TV_KEYWORDS.add("appletv");
      SMART_TV_KEYWORDS.add("apple tv");
      SMART_TV_KEYWORDS.add("smarttv");
      SMART_TV_KEYWORDS.add("smart-tv");
      SMART_TV_KEYWORDS.add("dlna");
      SMART_TV_KEYWORDS.add("ce-html");
      SMART_TV_KEYWORDS.add("inettvbrowser");
      SMART_TV_KEYWORDS.add("opera tv");
      SMART_TV_KEYWORDS.add("viera");
      SMART_TV_KEYWORDS.add("konfabulator");
      SMART_TV_KEYWORDS.add("sony bravia");
      SMART_TV_KEYWORDS.add("crkey");
      SMART_TV_KEYWORDS.add("sonycebrowser");
      SMART_TV_KEYWORDS.add("hbbtv");
      SMART_TV_KEYWORDS.add("large screen");
      SMART_TV_KEYWORDS.add("netcast");
      SMART_TV_KEYWORDS.add("philipstv");
      SMART_TV_KEYWORDS.add("digital-tv");
      SMART_TV_KEYWORDS.add(" mb90/");
      SMART_TV_KEYWORDS.add(" mb91/");
      SMART_TV_KEYWORDS.add(" mb95/");
      SMART_TV_KEYWORDS.add("vizio-dtv");
      SMART_TV_KEYWORDS.add("bravia");
      SMART_TV_KEYWORDS.add("roku");
      BOT_KEYWORDS.add("+http");
      BOT_KEYWORDS.add("bot");
      BOT_KEYWORDS.add("crawler");
      BOT_KEYWORDS.add("spider");
      BOT_KEYWORDS.add("novarra");
      BOT_KEYWORDS.add("transcoder");
      BOT_KEYWORDS.add("yahoo! searchmonkey");
      BOT_KEYWORDS.add("yahoo! slurp");
      BOT_KEYWORDS.add("feedfetcher-google");
      BOT_KEYWORDS.add("mowser");
      BOT_KEYWORDS.add("trove");
      BOT_KEYWORDS.add("google web preview");
      BOT_KEYWORDS.add("googleimageproxy");
      BOT_KEYWORDS.add("gigablastopensource");
      BOT_KEYWORDS.add("http-client");
      BOT_KEYWORDS.add("exactsearch");
      BOT_KEYWORDS.add("gecko/20100721");
      BOT_KEYWORDS.add("sureseeker.com");
      BOT_KEYWORDS.add("ltx71");
      BOT_KEYWORDS.add("searchsecure");
      BOT_KEYWORDS.add("iopus-i-m");
      MOBILE_KEYWORDS_MATCHER = new AhoCorasickKeywordMatcher(MOBILE_KEYWORDS);
      DESKTOP_BROWSER_MATCHER = new AhoCorasickKeywordMatcher(DESKTOP_KEYWORDS);
      SMART_TV_BROWSER_MATCHER = new AhoCorasickKeywordMatcher(SMART_TV_KEYWORDS);
      BOT_MATCHER = new AhoCorasickKeywordMatcher(BOT_KEYWORDS);
      DESKTOP_SAFARI_PATTERN = Pattern.compile("^Mozilla/5\\.0 \\((?:Macintosh|Windows)[^\\)]+\\) AppleWebKit/[\\d\\.]+ \\(KHTML, like Gecko\\) Version/[\\d\\.]+ Safari/[\\d\\.]+$");
      IE11_TRIDENT_PATTERN = Pattern.compile("^Mozilla\\/5\\.0 \\(Windows NT.+?Trident.+?; rv:\\d\\d\\.\\d+\\)");
      MSIE_9_10_PATTERN = Pattern.compile("^Mozilla\\/5\\.0 \\(compatible; MSIE (9|10)\\.0; Windows NT \\d\\.\\d");
      MSIE_LEGACY_PATTERN = Pattern.compile("^Mozilla\\/4\\.0 \\(compatible; MSIE \\d\\.\\d; Windows NT \\d\\.\\d");
      SCREEN_SIZE_PATTERN = Pattern.compile("[^\\d]\\d{3}x\\d{3}");
   }
}
