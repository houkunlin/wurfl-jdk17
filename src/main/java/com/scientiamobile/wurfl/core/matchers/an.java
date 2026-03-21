package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

final class an extends a {
  private static String b = "opera";
  
  private static final Pattern c = Pattern.compile("Opera[ /]?(\\d+\\.\\d+)");
  
  private static final Map d;
  
  public an(UserAgentNormalizer paramUserAgentNormalizer, WURFLModel paramWURFLModel) {
    super(paramUserAgentNormalizer, paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<?> hashSet;
    (hashSet = new HashSet()).addAll(d.values());
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsMobileBrowser() && StringMatchUtils.containsAnyOf(paramWURFLRequest.getCleanedDeviceUserAgent(), new String[] { "Opera", "OPR/" }));
  }
  
  protected final String a(String paramString) {
    int i = StringMatchUtils.indexOf(paramString, "Opera");
    i = StringMatchUtils.indexOfOrLength(paramString, ".", i);
    return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
  }
  
  protected final String b(WURFLRequest paramWURFLRequest) {
    String[] arrayOfString;
    Matcher matcher;
    String str2;
    String str1;
    return ((matcher = c.matcher(paramWURFLRequest.getNormalizedDeviceUserAgent())).find() && StringUtils.isNotEmpty(str2 = matcher.group(1)) && ArrayUtils.isNotEmpty((Object[])(arrayOfString = str2.split("\\."))) && StringUtils.isNotEmpty(str1 = (String)d.get(arrayOfString[0]))) ? str1 : b;
  }
  
  public final String getMatcherName() {
    return "OperaMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "Opera";
  }
  
  static {
    (d = new HashMap<Object, Object>()).put("", b);
    d.put("7", "opera_7");
    d.put("8", "opera_8");
    d.put("9", "opera_9");
    d.put("10", "opera_10");
    d.put("11", "opera_11");
    d.put("12", "opera_12");
    d.put("15", "opera_15");
    d.put("16", "opera_16");
    d.put("17", "opera_17");
    d.put("18", "opera_18");
    d.put("19", "opera_19");
    d.put("20", "opera_20");
    d.put("21", "opera_21");
    d.put("22", "opera_22");
    d.put("23", "opera_23");
    d.put("24", "opera_24");
    d.put("25", "opera_25");
    d.put("26", "opera_26");
    d.put("27", "opera_27");
    d.put("28", "opera_28");
    d.put("29", "opera_29");
    d.put("30", "opera_30");
    d.put("31", "opera_31");
    d.put("32", "opera_32");
    d.put("33", "opera_33");
    d.put("34", "opera_34");
    d.put("35", "opera_35");
    d.put("36", "opera_36");
    d.put("37", "opera_37");
    d.put("38", "opera_38");
    d.put("39", "opera_39");
    d.put("40", "opera_40");
    d.put("41", "opera_41");
    d.put("42", "opera_42");
    d.put("43", "opera_43");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\an.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
