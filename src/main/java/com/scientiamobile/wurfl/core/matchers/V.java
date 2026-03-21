package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
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

final class v extends a {
  private static String b = "apple_iphone_coremedia_ver1";
  
  private static final String c = "apple_iphone_ver".concat("1");
  
  private static final String[] d = new String[] { "iPhone", "iPod", "iPad" };
  
  private static final Pattern e = Pattern.compile(" (\\d+)_\\d+[ _]");
  
  private static final Pattern f = Pattern.compile("(?:iPhone|iPad|iPod) ?(\\d+,\\d+)");
  
  private static final List g = new ArrayList();
  
  private static final Map h = new HashMap<Object, Object>();
  
  private static final Map i = new HashMap<Object, Object>();
  
  private static final Map j = new HashMap<Object, Object>();
  
  private static final List k = new ArrayList();
  
  public v(UserAgentNormalizer paramUserAgentNormalizer, WURFLModel paramWURFLModel) {
    super(paramUserAgentNormalizer, paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<?> hashSet;
    (hashSet = new HashSet()).addAll(g);
    hashSet.addAll(k);
    hashSet.add(b);
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(paramWURFLRequest.getCleanedDeviceUserAgent(), d) && !StringMatchUtils.containsAnyOf(paramWURFLRequest.getCleanedDeviceUserAgent(), new String[] { "Symbian", "Nintendo" }));
  }
  
  protected final String a(WURFLRequest paramWURFLRequest) {
    String str1 = paramWURFLRequest.getNormalizedDeviceUserAgent();
    String str2 = null;
    Matcher matcher;
    if ((matcher = f.matcher(str1)).find()) {
      String str = matcher.group(1);
      if (str1.contains("iPod")) {
        str2 = (String)j.get(str);
      } else if (str1.contains("iPad")) {
        str2 = (String)i.get(str);
      } else if (str1.contains("iPhone")) {
        str2 = (String)h.get(str);
      } 
    } 
    int i;
    if ((i = StringMatchUtils.firstChar(str1, '_').intValue()) < 0) {
      if ((i = StringUtils.indexOf(str1, "like Mac OS X;")) >= 0) {
        i += 14;
      } else {
        i = str1.length();
      } 
    } else {
      i++;
    } 
    if ((str1 = StringMatchUtils.risMatch(getFilter().a().a(), str1, i)) != null) {
      str1 = getFilter().a().a(str1);
      if (str2 != null && str1 != null) {
        str2 = str1 + "_subhw" + str2;
        if (k.contains(str2))
          return str2; 
      } 
      return str1;
    } 
    return null;
  }
  
  protected final String b(WURFLRequest paramWURFLRequest) {
    String str1 = paramWURFLRequest.getNormalizedDeviceUserAgent();
    Matcher matcher = e.matcher(str1);
    String str2 = "-1";
    if (matcher.find())
      str2 = matcher.group(1); 
    if (str1.contains("CoreMedia"))
      return b; 
    if (StringUtils.contains(str1, "iPod")) {
      str1 = "apple_ipod_touch_ver".concat(str2);
      return g.contains(str1) ? str1 : "apple_ipod_touch_ver".concat("1");
    } 
    if (StringUtils.contains(str1, "iPad")) {
      if ("3".equals(str2))
        return "apple_ipad_ver1".concat("_subua32"); 
      if ("4".equals(str2))
        return "apple_ipad_ver1".concat("_sub42"); 
      str1 = "apple_ipad_ver1".concat("_sub").concat(str2);
      return g.contains(str1) ? str1 : "apple_ipad_ver1";
    } 
    str1 = "apple_iphone_ver".concat(str2);
    return g.contains(str1) ? str1 : c;
  }
  
  public final String getMatcherName() {
    return "AppleMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "Apple";
  }
  
  static {
    g.add("apple_ipod_touch_ver1");
    g.add("apple_ipod_touch_ver2");
    g.add("apple_ipod_touch_ver3");
    g.add("apple_ipod_touch_ver4");
    g.add("apple_ipod_touch_ver5");
    g.add("apple_ipod_touch_ver6");
    g.add("apple_ipod_touch_ver7");
    g.add("apple_ipod_touch_ver8");
    g.add("apple_ipod_touch_ver9");
    g.add("apple_ipod_touch_ver10");
    g.add("apple_ipod_touch_ver11");
    g.add("apple_ipad_ver1");
    g.add("apple_ipad_ver1_subua32");
    g.add("apple_ipad_ver1_sub42");
    g.add("apple_ipad_ver1_sub5");
    g.add("apple_ipad_ver1_sub6");
    g.add("apple_ipad_ver1_sub7");
    g.add("apple_ipad_ver1_sub8");
    g.add("apple_ipad_ver1_sub9");
    g.add("apple_ipad_ver1_sub10");
    g.add("apple_ipad_ver1_sub11");
    g.add(c);
    g.add("apple_iphone_ver2");
    g.add("apple_iphone_ver3");
    g.add("apple_iphone_ver4");
    g.add("apple_iphone_ver5");
    g.add("apple_iphone_ver6");
    g.add("apple_iphone_ver7");
    g.add("apple_iphone_ver8");
    g.add("apple_iphone_ver9");
    g.add("apple_iphone_ver10");
    g.add("apple_iphone_ver11");
    h.put("1,1", "2g");
    h.put("1,2", "3g");
    h.put("2,1", "3gs");
    h.put("3,1", "4");
    h.put("3,2", "4");
    h.put("3,3", "4");
    h.put("4,1", "4s");
    h.put("5,1", "5");
    h.put("5,2", "5");
    h.put("5,3", "5c");
    h.put("5,4", "5c");
    h.put("6,1", "5s");
    h.put("6,2", "5s");
    h.put("7,1", "6plus");
    h.put("7,2", "6");
    h.put("8,2", "6splus");
    h.put("8,1", "6s");
    h.put("8,4", "se");
    h.put("9,1", "7");
    h.put("9,2", "7plus");
    h.put("9,3", "7");
    h.put("9,4", "7plus");
    i.put("1,1", "1");
    i.put("2,1", "2");
    i.put("2,2", "2");
    i.put("2,3", "2");
    i.put("2,4", "2");
    i.put("2,5", "mini1");
    i.put("2,6", "mini1");
    i.put("2,7", "mini1");
    i.put("3,1", "3");
    i.put("3,2", "3");
    i.put("3,3", "3");
    i.put("3,4", "4");
    i.put("3,5", "4");
    i.put("3,6", "4");
    i.put("4,1", "air");
    i.put("4,2", "air");
    i.put("4,3", "air");
    i.put("4,4", "mini2");
    i.put("4,5", "mini2");
    i.put("4,6", "mini2");
    i.put("4,7", "mini3");
    i.put("4,8", "mini3");
    i.put("4,9", "mini3");
    i.put("5,3", "air2");
    i.put("5,4", "air2");
    i.put("5,1", "mini4");
    i.put("5,2", "mini4");
    i.put("6,7", "pro");
    i.put("6,8", "pro");
    i.put("6,3", "pro97");
    i.put("6,4", "pro97");
    i.put("6,11", "5");
    i.put("6,12", "5");
    i.put("7,1", "pro2");
    i.put("7,2", "pro2");
    i.put("7,3", "pro2105");
    i.put("7,4", "pro2105");
    j.put("1,1", "1");
    j.put("2,1", "2");
    j.put("3,1", "3");
    j.put("4,1", "4");
    j.put("5,1", "5");
    j.put("7,1", "6");
    k.add("apple_ipad_ver1_subhw1");
    k.add("apple_ipad_ver1_sub42_subhw1");
    k.add("apple_ipad_ver1_sub43_subhw1");
    k.add("apple_ipad_ver1_sub43_subhw2");
    k.add("apple_ipad_ver1_sub5_subhw1");
    k.add("apple_ipad_ver1_sub5_subhw2");
    k.add("apple_ipad_ver1_sub51_subhw1");
    k.add("apple_ipad_ver1_sub51_subhw2");
    k.add("apple_ipad_ver1_sub51_subhw3");
    k.add("apple_ipad_ver1_sub6_subhw2");
    k.add("apple_ipad_ver1_sub6_subhw3");
    k.add("apple_ipad_ver1_sub6_subhw4");
    k.add("apple_ipad_ver1_sub6_subhwmini1");
    k.add("apple_ipad_ver1_sub61_subhw2");
    k.add("apple_ipad_ver1_sub61_subhw3");
    k.add("apple_ipad_ver1_sub61_subhw4");
    k.add("apple_ipad_ver1_sub61_subhwmini1");
    k.add("apple_ipad_ver1_sub7_subhw2");
    k.add("apple_ipad_ver1_sub7_subhw3");
    k.add("apple_ipad_ver1_sub7_subhw4");
    k.add("apple_ipad_ver1_sub7_subhwmini1");
    k.add("apple_ipad_ver1_sub7_subhwmini2");
    k.add("apple_ipad_ver1_sub7_subhwair");
    k.add("apple_ipad_ver1_sub71_subhw2");
    k.add("apple_ipad_ver1_sub71_subhw3");
    k.add("apple_ipad_ver1_sub71_subhw4");
    k.add("apple_ipad_ver1_sub71_subhwmini1");
    k.add("apple_ipad_ver1_sub71_subhwmini2");
    k.add("apple_ipad_ver1_sub71_subhwair");
    k.add("apple_ipad_ver1_sub8_subhw2");
    k.add("apple_ipad_ver1_sub8_subhw3");
    k.add("apple_ipad_ver1_sub8_subhw4");
    k.add("apple_ipad_ver1_sub8_subhwmini1");
    k.add("apple_ipad_ver1_sub8_subhwmini2");
    k.add("apple_ipad_ver1_sub8_subhwair");
    k.add("apple_ipad_ver1_sub8_1_subhw2");
    k.add("apple_ipad_ver1_sub8_1_subhw3");
    k.add("apple_ipad_ver1_sub8_1_subhw4");
    k.add("apple_ipad_ver1_sub8_1_subhwair");
    k.add("apple_ipad_ver1_sub8_1_subhwair2");
    k.add("apple_ipad_ver1_sub8_1_subhwmini1");
    k.add("apple_ipad_ver1_sub8_1_subhwmini2");
    k.add("apple_ipad_ver1_sub8_1_subhwmini3");
    k.add("apple_ipad_ver1_sub8_2_subhw2");
    k.add("apple_ipad_ver1_sub8_2_subhw3");
    k.add("apple_ipad_ver1_sub8_2_subhw4");
    k.add("apple_ipad_ver1_sub8_2_subhwair");
    k.add("apple_ipad_ver1_sub8_2_subhwair2");
    k.add("apple_ipad_ver1_sub8_2_subhwmini1");
    k.add("apple_ipad_ver1_sub8_2_subhwmini2");
    k.add("apple_ipad_ver1_sub8_2_subhwmini3");
    k.add("apple_ipad_ver1_sub8_3_subhw2");
    k.add("apple_ipad_ver1_sub8_3_subhw3");
    k.add("apple_ipad_ver1_sub8_3_subhw4");
    k.add("apple_ipad_ver1_sub8_3_subhwair");
    k.add("apple_ipad_ver1_sub8_3_subhwair2");
    k.add("apple_ipad_ver1_sub8_3_subhwmini1");
    k.add("apple_ipad_ver1_sub8_3_subhwmini2");
    k.add("apple_ipad_ver1_sub8_3_subhwmini3");
    k.add("apple_ipad_ver1_sub8_4_subhw2");
    k.add("apple_ipad_ver1_sub8_4_subhw3");
    k.add("apple_ipad_ver1_sub8_4_subhw4");
    k.add("apple_ipad_ver1_sub8_4_subhwair");
    k.add("apple_ipad_ver1_sub8_4_subhwair2");
    k.add("apple_ipad_ver1_sub8_4_subhwmini1");
    k.add("apple_ipad_ver1_sub8_4_subhwmini2");
    k.add("apple_ipad_ver1_sub8_4_subhwmini3");
    k.add("apple_ipad_ver1_sub9_subhw2");
    k.add("apple_ipad_ver1_sub9_subhw3");
    k.add("apple_ipad_ver1_sub9_subhw4");
    k.add("apple_ipad_ver1_sub9_subhwair");
    k.add("apple_ipad_ver1_sub9_subhwair2");
    k.add("apple_ipad_ver1_sub9_subhwmini1");
    k.add("apple_ipad_ver1_sub9_subhwmini2");
    k.add("apple_ipad_ver1_sub9_subhwmini3");
    k.add("apple_ipad_ver1_sub9_subhwmini4");
    k.add("apple_ipad_ver1_sub9_1_subhw2");
    k.add("apple_ipad_ver1_sub9_1_subhw3");
    k.add("apple_ipad_ver1_sub9_1_subhw4");
    k.add("apple_ipad_ver1_sub9_1_subhwair");
    k.add("apple_ipad_ver1_sub9_1_subhwair2");
    k.add("apple_ipad_ver1_sub9_1_subhwmini1");
    k.add("apple_ipad_ver1_sub9_1_subhwmini2");
    k.add("apple_ipad_ver1_sub9_1_subhwmini3");
    k.add("apple_ipad_ver1_sub9_1_subhwmini4");
    k.add("apple_ipad_ver1_sub9_1_subhwpro");
    k.add("apple_ipad_ver1_sub9_2_subhw2");
    k.add("apple_ipad_ver1_sub9_2_subhw3");
    k.add("apple_ipad_ver1_sub9_2_subhw4");
    k.add("apple_ipad_ver1_sub9_2_subhwair");
    k.add("apple_ipad_ver1_sub9_2_subhwair2");
    k.add("apple_ipad_ver1_sub9_2_subhwmini1");
    k.add("apple_ipad_ver1_sub9_2_subhwmini2");
    k.add("apple_ipad_ver1_sub9_2_subhwmini3");
    k.add("apple_ipad_ver1_sub9_2_subhwmini4");
    k.add("apple_ipad_ver1_sub9_2_subhwpro");
    k.add("apple_ipad_ver1_sub9_3_subhw2");
    k.add("apple_ipad_ver1_sub9_3_subhw3");
    k.add("apple_ipad_ver1_sub9_3_subhw4");
    k.add("apple_ipad_ver1_sub9_3_subhwair");
    k.add("apple_ipad_ver1_sub9_3_subhwair2");
    k.add("apple_ipad_ver1_sub9_3_subhwmini1");
    k.add("apple_ipad_ver1_sub9_3_subhwmini2");
    k.add("apple_ipad_ver1_sub9_3_subhwmini3");
    k.add("apple_ipad_ver1_sub9_3_subhwmini4");
    k.add("apple_ipad_ver1_sub9_3_subhwpro");
    k.add("apple_ipad_ver1_sub9_3_subhwpro97");
    k.add("apple_ipad_ver1_sub10_subhw4");
    k.add("apple_ipad_ver1_sub10_subhwair");
    k.add("apple_ipad_ver1_sub10_subhwair2");
    k.add("apple_ipad_ver1_sub10_subhwmini2");
    k.add("apple_ipad_ver1_sub10_subhwmini3");
    k.add("apple_ipad_ver1_sub10_subhwmini4");
    k.add("apple_ipad_ver1_sub10_subhwpro");
    k.add("apple_ipad_ver1_sub10_subhwpro97");
    k.add("apple_iphone_ver1_subhw2g");
    k.add("apple_iphone_ver2_subhw2g");
    k.add("apple_iphone_ver2_subhw3g");
    k.add("apple_iphone_ver2_1_subhw2g");
    k.add("apple_iphone_ver2_1_subhw3g");
    k.add("apple_iphone_ver2_2_subhw2g");
    k.add("apple_iphone_ver2_2_subhw3g");
    k.add("apple_iphone_ver3_subhw2g");
    k.add("apple_iphone_ver3_subhw3g");
    k.add("apple_iphone_ver3_subhw3gs");
    k.add("apple_iphone_ver3_1_subhw3g");
    k.add("apple_iphone_ver3_1_subhw3gs");
    k.add("apple_iphone_ver4_subhw3g");
    k.add("apple_iphone_ver4_subhw3gs");
    k.add("apple_iphone_ver4_subhw4");
    k.add("apple_iphone_ver4_1_subhw3g");
    k.add("apple_iphone_ver4_1_subhw3gs");
    k.add("apple_iphone_ver4_1_subhw4");
    k.add("apple_iphone_ver4_2_subhw3g");
    k.add("apple_iphone_ver4_2_subhw3gs");
    k.add("apple_iphone_ver4_2_subhw4");
    k.add("apple_iphone_ver4_3_subhw3gs");
    k.add("apple_iphone_ver4_3_subhw4");
    k.add("apple_iphone_ver5_subhw3gs");
    k.add("apple_iphone_ver5_subhw4");
    k.add("apple_iphone_ver5_subhw4s");
    k.add("apple_iphone_ver5_1_subhw3gs");
    k.add("apple_iphone_ver5_1_subhw4");
    k.add("apple_iphone_ver5_1_subhw4s");
    k.add("apple_iphone_ver6_subhw3gs");
    k.add("apple_iphone_ver6_subhw4");
    k.add("apple_iphone_ver6_subhw4s");
    k.add("apple_iphone_ver6_subhw5");
    k.add("apple_iphone_ver6_1_subhw3gs");
    k.add("apple_iphone_ver6_1_subhw4");
    k.add("apple_iphone_ver6_1_subhw4s");
    k.add("apple_iphone_ver6_1_subhw5");
    k.add("apple_iphone_ver7_subhw4");
    k.add("apple_iphone_ver7_subhw4s");
    k.add("apple_iphone_ver7_subhw5");
    k.add("apple_iphone_ver7_subhw5c");
    k.add("apple_iphone_ver7_subhw5s");
    k.add("apple_iphone_ver7_1_subhw4");
    k.add("apple_iphone_ver7_1_subhw4s");
    k.add("apple_iphone_ver7_1_subhw5");
    k.add("apple_iphone_ver7_1_subhw5c");
    k.add("apple_iphone_ver7_1_subhw5s");
    k.add("apple_iphone_ver8_subhw4s");
    k.add("apple_iphone_ver8_subhw5");
    k.add("apple_iphone_ver8_subhw5c");
    k.add("apple_iphone_ver8_subhw5s");
    k.add("apple_iphone_ver8_subhw6");
    k.add("apple_iphone_ver8_subhw6plus");
    k.add("apple_iphone_ver8_subua802_subhw4s");
    k.add("apple_iphone_ver8_subua802_subhw5");
    k.add("apple_iphone_ver8_subua802_subhw5c");
    k.add("apple_iphone_ver8_subua802_subhw5s");
    k.add("apple_iphone_ver8_subua802_subhw6");
    k.add("apple_iphone_ver8_subua802_subhw6plus");
    k.add("apple_iphone_ver8_1_subhw4s");
    k.add("apple_iphone_ver8_1_subhw5");
    k.add("apple_iphone_ver8_1_subhw5c");
    k.add("apple_iphone_ver8_1_subhw5s");
    k.add("apple_iphone_ver8_1_subhw6");
    k.add("apple_iphone_ver8_1_subhw6plus");
    k.add("apple_iphone_ver8_2_subhw4s");
    k.add("apple_iphone_ver8_2_subhw5");
    k.add("apple_iphone_ver8_2_subhw5c");
    k.add("apple_iphone_ver8_2_subhw5s");
    k.add("apple_iphone_ver8_2_subhw6");
    k.add("apple_iphone_ver8_2_subhw6plus");
    k.add("apple_iphone_ver8_3_subhw4s");
    k.add("apple_iphone_ver8_3_subhw5");
    k.add("apple_iphone_ver8_3_subhw5c");
    k.add("apple_iphone_ver8_3_subhw5s");
    k.add("apple_iphone_ver8_3_subhw6");
    k.add("apple_iphone_ver8_3_subhw6plus");
    k.add("apple_iphone_ver8_4_subhw4s");
    k.add("apple_iphone_ver8_4_subhw5");
    k.add("apple_iphone_ver8_4_subhw5c");
    k.add("apple_iphone_ver8_4_subhw5s");
    k.add("apple_iphone_ver8_4_subhw6");
    k.add("apple_iphone_ver8_4_subhw6plus");
    k.add("apple_iphone_ver9_subhw4s");
    k.add("apple_iphone_ver9_subhw5");
    k.add("apple_iphone_ver9_subhw5c");
    k.add("apple_iphone_ver9_subhw5s");
    k.add("apple_iphone_ver9_subhw6");
    k.add("apple_iphone_ver9_subhw6plus");
    k.add("apple_iphone_ver9_subhw6s");
    k.add("apple_iphone_ver9_subhw6splus");
    k.add("apple_iphone_ver9_1_subhw4s");
    k.add("apple_iphone_ver9_1_subhw5");
    k.add("apple_iphone_ver9_1_subhw5c");
    k.add("apple_iphone_ver9_1_subhw5s");
    k.add("apple_iphone_ver9_1_subhw6");
    k.add("apple_iphone_ver9_1_subhw6plus");
    k.add("apple_iphone_ver9_1_subhw6s");
    k.add("apple_iphone_ver9_1_subhw6splus");
    k.add("apple_iphone_ver9_2_subhw4s");
    k.add("apple_iphone_ver9_2_subhw5");
    k.add("apple_iphone_ver9_2_subhw5c");
    k.add("apple_iphone_ver9_2_subhw5s");
    k.add("apple_iphone_ver9_2_subhw6");
    k.add("apple_iphone_ver9_2_subhw6plus");
    k.add("apple_iphone_ver9_2_subhw6s");
    k.add("apple_iphone_ver9_2_subhw6splus");
    k.add("apple_iphone_ver9_3_subhw4s");
    k.add("apple_iphone_ver9_3_subhw5");
    k.add("apple_iphone_ver9_3_subhw5s");
    k.add("apple_iphone_ver9_3_subhw5c");
    k.add("apple_iphone_ver9_3_subhw6");
    k.add("apple_iphone_ver9_3_subhw6plus");
    k.add("apple_iphone_ver9_3_subhw6s");
    k.add("apple_iphone_ver9_3_subhw6splus");
    k.add("apple_iphone_ver9_3_subhwse");
    k.add("apple_iphone_ver10_subhw5");
    k.add("apple_iphone_ver10_subhw5c");
    k.add("apple_iphone_ver10_subhw5s");
    k.add("apple_iphone_ver10_subhw6");
    k.add("apple_iphone_ver10_subhw6plus");
    k.add("apple_iphone_ver10_subhw6s");
    k.add("apple_iphone_ver10_subhw6splus");
    k.add("apple_iphone_ver10_subhwse");
    k.add("apple_iphone_ver10_subhw7");
    k.add("apple_iphone_ver10_subhw7plus");
    k.add("apple_iphone_ver10_1_subhw5");
    k.add("apple_iphone_ver10_1_subhw5c");
    k.add("apple_iphone_ver10_1_subhw5s");
    k.add("apple_iphone_ver10_1_subhw6");
    k.add("apple_iphone_ver10_1_subhw6plus");
    k.add("apple_iphone_ver10_1_subhw6s");
    k.add("apple_iphone_ver10_1_subhw6splus");
    k.add("apple_iphone_ver10_1_subhwse");
    k.add("apple_iphone_ver10_1_subhw7");
    k.add("apple_iphone_ver10_1_subhw7plus");
    k.add("apple_iphone_ver10_2_subhw5");
    k.add("apple_iphone_ver10_2_subhw5c");
    k.add("apple_iphone_ver10_2_subhw5s");
    k.add("apple_iphone_ver10_2_subhw6");
    k.add("apple_iphone_ver10_2_subhw6plus");
    k.add("apple_iphone_ver10_2_subhw6s");
    k.add("apple_iphone_ver10_2_subhw6splus");
    k.add("apple_iphone_ver10_2_subhwse");
    k.add("apple_iphone_ver10_2_subhw7");
    k.add("apple_iphone_ver10_2_subhw7plus");
    k.add("apple_iphone_ver10_3_subhw5");
    k.add("apple_iphone_ver10_3_subhw5c");
    k.add("apple_iphone_ver10_3_subhw5s");
    k.add("apple_iphone_ver10_3_subhwse");
    k.add("apple_iphone_ver10_3_subhw6");
    k.add("apple_iphone_ver10_3_subhw6plus");
    k.add("apple_iphone_ver10_3_subhw6s");
    k.add("apple_iphone_ver10_3_subhw6splus");
    k.add("apple_iphone_ver10_3_subhw7");
    k.add("apple_iphone_ver10_3_subhw7plus");
    k.add("apple_iphone_ver11_subhw5s");
    k.add("apple_iphone_ver11_subhwse");
    k.add("apple_iphone_ver11_subhw6");
    k.add("apple_iphone_ver11_subhw6plus");
    k.add("apple_iphone_ver11_subhw6s");
    k.add("apple_iphone_ver11_subhw6splus");
    k.add("apple_iphone_ver11_subhw7");
    k.add("apple_iphone_ver11_subhw7plus");
    k.add("apple_ipad_ver1_sub10_1_subhw4");
    k.add("apple_ipad_ver1_sub10_1_subhwair");
    k.add("apple_ipad_ver1_sub10_1_subhwair2");
    k.add("apple_ipad_ver1_sub10_1_subhwmini2");
    k.add("apple_ipad_ver1_sub10_1_subhwmini3");
    k.add("apple_ipad_ver1_sub10_1_subhwmini4");
    k.add("apple_ipad_ver1_sub10_1_subhwpro");
    k.add("apple_ipad_ver1_sub10_1_subhwpro97");
    k.add("apple_ipad_ver1_sub10_2_subhw4");
    k.add("apple_ipad_ver1_sub10_2_subhwair");
    k.add("apple_ipad_ver1_sub10_2_subhwair2");
    k.add("apple_ipad_ver1_sub10_2_subhwmini2");
    k.add("apple_ipad_ver1_sub10_2_subhwmini3");
    k.add("apple_ipad_ver1_sub10_2_subhwmini4");
    k.add("apple_ipad_ver1_sub10_2_subhwpro");
    k.add("apple_ipad_ver1_sub10_2_subhwpro97");
    k.add("apple_ipad_ver1_sub10_3_subhwmini2");
    k.add("apple_ipad_ver1_sub10_3_subhwmini3");
    k.add("apple_ipad_ver1_sub10_3_subhwmini4");
    k.add("apple_ipad_ver1_sub10_3_subhw4");
    k.add("apple_ipad_ver1_sub10_3_subhwair");
    k.add("apple_ipad_ver1_sub10_3_subhwair2");
    k.add("apple_ipad_ver1_sub10_3_subhwpro97");
    k.add("apple_ipad_ver1_sub10_3_subhwpro");
    k.add("apple_ipad_ver1_sub10_3_subhw5");
    k.add("apple_ipad_ver1_sub10_3_subhwpro2");
    k.add("apple_ipad_ver1_sub10_3_subhwpro2105");
    k.add("apple_ipad_ver1_sub11_subhwmini2");
    k.add("apple_ipad_ver1_sub11_subhwmini3");
    k.add("apple_ipad_ver1_sub11_subhwmini4");
    k.add("apple_ipad_ver1_sub11_subhw5");
    k.add("apple_ipad_ver1_sub11_subhwair");
    k.add("apple_ipad_ver1_sub11_subhwair2");
    k.add("apple_ipad_ver1_sub11_subhwpro");
    k.add("apple_ipad_ver1_sub11_subhwpro97");
    k.add("apple_ipad_ver1_sub11_subhwpro2");
    k.add("apple_ipad_ver1_sub11_subhwpro2105");
    k.add("apple_ipod_touch_ver1_subhw1");
    k.add("apple_ipod_touch_ver2_subhw1");
    k.add("apple_ipod_touch_ver2_1_subhw1");
    k.add("apple_ipod_touch_ver2_1_subhw2");
    k.add("apple_ipod_touch_ver2_2_subhw1");
    k.add("apple_ipod_touch_ver2_2_subhw2");
    k.add("apple_ipod_touch_ver3_subhw1");
    k.add("apple_ipod_touch_ver3_subhw2");
    k.add("apple_ipod_touch_ver3_1_subhw1");
    k.add("apple_ipod_touch_ver3_1_subhw2");
    k.add("apple_ipod_touch_ver3_1_subhw3");
    k.add("apple_ipod_touch_ver4_subhw2");
    k.add("apple_ipod_touch_ver4_subhw3");
    k.add("apple_ipod_touch_ver4_1_subhw2");
    k.add("apple_ipod_touch_ver4_1_subhw3");
    k.add("apple_ipod_touch_ver4_1_subhw4");
    k.add("apple_ipod_touch_ver4_2_subhw2");
    k.add("apple_ipod_touch_ver4_2_subhw3");
    k.add("apple_ipod_touch_ver4_2_subhw4");
    k.add("apple_ipod_touch_ver4_3_subhw3");
    k.add("apple_ipod_touch_ver4_3_subhw4");
    k.add("apple_ipod_touch_ver5_subhw3");
    k.add("apple_ipod_touch_ver5_subhw4");
    k.add("apple_ipod_touch_ver5_1_subhw3");
    k.add("apple_ipod_touch_ver5_1_subhw4");
    k.add("apple_ipod_touch_ver6_subhw3");
    k.add("apple_ipod_touch_ver6_subhw4");
    k.add("apple_ipod_touch_ver6_subhw5");
    k.add("apple_ipod_touch_ver6_1_subhw4");
    k.add("apple_ipod_touch_ver6_1_subhw5");
    k.add("apple_ipod_touch_ver7_subhw5");
    k.add("apple_ipod_touch_ver7_1_subhw5");
    k.add("apple_ipod_touch_ver8_subhw5");
    k.add("apple_ipod_touch_ver8_1_subhw5");
    k.add("apple_ipod_touch_ver8_2_subhw5");
    k.add("apple_ipod_touch_ver8_3_subhw5");
    k.add("apple_ipod_touch_ver8_4_subhw5");
    k.add("apple_ipod_touch_ver9_subhw5");
    k.add("apple_ipod_touch_ver9_subhw6");
    k.add("apple_ipod_touch_ver9_1_subhw5");
    k.add("apple_ipod_touch_ver9_1_subhw6");
    k.add("apple_ipod_touch_ver9_2_subhw5");
    k.add("apple_ipod_touch_ver9_2_subhw6");
    k.add("apple_ipod_touch_ver9_3_subhw5");
    k.add("apple_ipod_touch_ver9_3_subhw6");
    k.add("apple_ipod_touch_ver10_subhw6");
    k.add("apple_ipod_touch_ver10_1_subhw6");
    k.add("apple_ipod_touch_ver10_2_subhw6");
    k.add("apple_ipod_touch_ver10_3_subhw6");
    k.add("apple_ipod_touch_ver11_subhw6");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\v.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
