package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.UcwebU3Normalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

final class m extends a {
  private static String b = "generic_ms_phone_os8_subuaucweb";
  
  private static String c = "generic_ucweb_android_ver1";
  
  private static String d = "apple_iphone_ver1_subuaucweb";
  
  private static String e = "apple_ipad_ver1_subuaucweb";
  
  private static final Pattern f = Pattern.compile("iPhone OS (\\d+)(?:_\\d+)?.+ like");
  
  private static final Pattern g = Pattern.compile("CPU OS (\\d+)(?:_\\d+)?.+like Mac");
  
  private static List h;
  
  public m(UserAgentNormalizer paramUserAgentNormalizer, WURFLModel paramWURFLModel) {
    super(paramUserAgentNormalizer, paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<?> hashSet;
    (hashSet = new HashSet()).addAll(h);
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    String str = paramWURFLRequest.getCleanedDeviceUserAgent();
    return (!paramWURFLRequest._internalIsDesktopBrowser() && str.startsWith("Mozilla") && str.contains("UCBrowser"));
  }
  
  protected final String a(String paramString) {
    if (UserAgentUtils.getUcBrowserVersion(paramString, false) == null)
      return null; 
    if (paramString.contains("Windows Phone")) {
      String str = UserAgentUtils.getWindowsPhoneVersion(paramString);
      if (UserAgentUtils.getWindowsPhoneModel(paramString) != null && str != null)
        return StringMatchUtils.risMatch(getFilter().a().a(), paramString, paramString.indexOf("---") + 3); 
    } else if (paramString.contains("Android")) {
      String str1 = UserAgentUtils.getAndroidModel(paramString);
      String str2 = UserAgentUtils.getAndroidVersion(paramString, false);
      if (str1 != null && str2 != null)
        return StringMatchUtils.risMatch(getFilter().a().a(), paramString, paramString.indexOf("---") + 3); 
    } else if (paramString.contains("iPhone;")) {
      if (UcwebU3Normalizer.IPHONE.matcher(paramString).find())
        return StringMatchUtils.risMatch(getFilter().a().a(), paramString, paramString.indexOf("---") + 3); 
    } else if (paramString.contains("iPad") && UcwebU3Normalizer.IPAD.matcher(paramString).find()) {
      return StringMatchUtils.risMatch(getFilter().a().a(), paramString, paramString.indexOf("---") + 3);
    } 
    return null;
  }
  
  protected final String b(WURFLRequest paramWURFLRequest) {
    String str4 = UserAgentUtils.getWindowsPhoneVersion(str3);
    String str5 = null;
    if (StringUtils.isNotEmpty(str4)) {
      String[] arrayOfString1;
      if ((arrayOfString1 = str4.split("\\.")).length >= 2) {
        str4 = arrayOfString1[0];
        if (StringUtils.isEmpty(str3 = arrayOfString1[1])) {
          str5 = "generic_ms_phone_os" + str4 + "_subuaucweb";
        } else {
          str5 = "generic_ms_phone_os" + str4 + "_" + str3 + "_subuaucweb";
        } 
      } 
    } else {
      a.debug("user agent " + str3 + "has no version information");
    } 
    String str1;
    String str3;
    if (StringUtils.isNotEmpty(str3 = !(str3 = str1 = paramWURFLRequest.getNormalizedDeviceUserAgent()).contains("Windows Phone") ? null : (h.contains(str5) ? str5 : b)))
      return str3; 
    str4 = UserAgentUtils.getAndroidVersion(str3, false);
    str5 = null;
    String[] arrayOfString;
    if (StringUtils.isNotEmpty(str4) && (arrayOfString = str4.split("\\.")).length > 0)
      str5 = "generic_ucweb_android_ver" + arrayOfString[0]; 
    String str2;
    if (StringUtils.isNotEmpty(str2 = !(str3 = str1).contains("Android") ? null : (h.contains(str5) ? str5 : c)))
      return str2; 
    Matcher matcher = f.matcher(str2);
    str5 = null;
    if (matcher.find() && matcher.groupCount() > 0) {
      str2 = matcher.group(1);
      str5 = "apple_iphone_ver" + str2 + "_subuaucweb";
    } 
    if (StringUtils.isNotEmpty(str2 = !(str2 = str1).contains("iPhone") ? null : (h.contains(str5) ? str5 : d)))
      return str2; 
    matcher = g.matcher(str2);
    str5 = null;
    if (matcher.find() && matcher.groupCount() > 0) {
      str2 = matcher.group(1);
      str5 = "apple_ipad_ver1_sub" + str2 + "_subuaucweb";
    } 
    return StringUtils.isNotEmpty(str2 = !(str2 = str1).contains("iPad") ? null : (h.contains(str5) ? str5 : e)) ? str2 : "generic_ucweb";
  }
  
  public final String getMatcherName() {
    return "UcwebU3Matcher";
  }
  
  public final String getBucketMatcherName() {
    return "UcwebU3";
  }
  
  static {
    (h = new ArrayList<String>()).add("generic_ucweb");
    h.add(c);
    h.add("generic_ucweb_android_ver2");
    h.add("generic_ucweb_android_ver3");
    h.add("generic_ucweb_android_ver4");
    h.add("generic_ucweb_android_ver5");
    h.add("generic_ucweb_android_ver6");
    h.add("generic_ucweb_android_ver7");
    h.add(d);
    h.add("apple_iphone_ver2_subuaucweb");
    h.add("apple_iphone_ver3_subuaucweb");
    h.add("apple_iphone_ver4_subuaucweb");
    h.add("apple_iphone_ver5_subuaucweb");
    h.add("apple_iphone_ver6_subuaucweb");
    h.add("apple_iphone_ver7_subuaucweb");
    h.add("apple_iphone_ver8_subuaucweb");
    h.add("apple_iphone_ver9_subuaucweb");
    h.add("apple_iphone_ver10_subuaucweb");
    h.add(e);
    h.add("apple_ipad_ver1_sub4_subuaucweb");
    h.add("apple_ipad_ver1_sub5_subuaucweb");
    h.add("apple_ipad_ver1_sub6_subuaucweb");
    h.add("apple_ipad_ver1_sub7_subuaucweb");
    h.add("apple_ipad_ver1_sub8_subuaucweb");
    h.add("apple_ipad_ver1_sub9_subuaucweb");
    h.add("apple_ipad_ver1_sub10_subuaucweb");
    h.add(b);
    h.add("generic_ms_phone_os8_1_subuaucweb");
    h.add("generic_ms_phone_os10_subuaucweb");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\m.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */