package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Q1 extends AbstractA {
  private static final Pattern b = Pattern.compile("^Mozilla/[45]\\.0 \\(compatible; MSIE (\\d+)\\.(\\d+)(?:[\\da-z]+)?;");

  private static final Pattern c = Pattern.compile("^Mozilla/5\\.0 \\(.+?Trident.+?; rv:(\\d\\d)\\.(\\d+)\\)");

  private static final Pattern d = Pattern.compile("^Mozilla/5\\.0 \\(Windows NT.+? Edge/(\\d+)\\.(\\d+)");

  private static final Pattern e = Pattern.compile("( \\.NET CLR [\\d\\.]+;?| Media Center PC [\\d\\.]+;?| OfficeLive[a-zA-Z0-9\\.\\d]+;?| InfoPath[\\.\\d]+;?)");

  private static final Map f;

  public Q1(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }

  protected final Set a() {
    HashSet<?> hashSet;
    (hashSet = new HashSet()).addAll(f.values());
    hashSet.add("generic");
    hashSet.add("generic_web_browser");
    hashSet.add("msie_5_5");
    return hashSet;
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    String str = paramWURFLRequest.getCleanedDeviceUserAgent();
    return (paramWURFLRequest._internalIsMobileBrowser() || !str.startsWith("Mozilla") || StringMatchUtils.containsAnyOf(str, new String[] { "Opera", "armv", "MOTO", "BREW" })) ? false : ((StringMatchUtils.containsAllOf(str, new String[] { "Trident", "rv:" }) || StringMatchUtils.containsAnyOf(str, new String[] { "MSIE", " Edge/" })));
  }

  protected final String a(WURFLRequest paramWURFLRequest) {
    String str = e.matcher(paramWURFLRequest.getNormalizedDeviceUserAgent()).replaceFirst("");
    Matcher[] arrayOfMatcher = { d.matcher(str), c.matcher(str), b.matcher(str) };
    boolean bool = false;
    Matcher matcher = null;
    for (byte b = 0; b < 3; b++) {
      Matcher matcher1;
      if (bool = (matcher1 = arrayOfMatcher[b]).find()) {
        matcher = matcher1;
        break;
      }
    }
    if (bool) {
      String str1 = matcher.group(1);
      String str2 = matcher.group(2);
      Integer integer = Integer.valueOf(-1);
      try {
        integer = Integer.valueOf(Integer.parseInt(str2));
      } catch (NumberFormatException numberFormatException) {}
      if ("5".equals(str1) && (new Integer(5)).equals(integer))
        return "msie_5_5";
      String str3;
      if ((str3 = (String)f.get(str1)) != null)
        return str3;
    }
    return super.a(paramWURFLRequest);
  }

  protected final String a(String paramString) {
    int i = StringMatchUtils.indexOfOrLength(paramString = e.matcher(paramString).replaceFirst(""), "Trident");
    return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    return StringMatchUtils.containsAnyOf(e.matcher(paramWURFLRequest.getNormalizedDeviceUserAgent()).replaceFirst(""), new String[] { "SLCC1", "Media Center PC", ".NET CLR", "OfficeLiveConnector" }) ? "generic_web_browser" : "generic";
  }

  public final String getMatcherName() {
    return "MSIEMatcher";
  }

  public final String getBucketMatcherName() {
    return "MSIE";
  }

  static {
    (f = new HashMap<Object, Object>()).put("0", "msie");
    f.put("4", "msie_4");
    f.put("5", "msie_5");
    f.put("6", "msie_6");
    f.put("7", "msie_7");
    f.put("8", "msie_8");
    f.put("9", "msie_9");
    f.put("10", "msie_10");
    f.put("11", "msie_11");
    f.put("12", "msie_12");
    f.put("13", "edge_13");
    f.put("14", "edge_14");
    f.put("15", "edge_15");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\Q.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
