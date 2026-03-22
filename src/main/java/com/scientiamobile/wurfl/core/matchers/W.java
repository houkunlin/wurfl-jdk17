package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

final class w extends AbstractA {
  private static String b = "blackberry_generic_ver10";

  private static String c = "blackberry_generic_ver10_tablet";

  private static String d = "rim_playbook_ver1";

  private static final Map<String, String> e = new LinkedHashMap<>();

  private static final Pattern f = Pattern.compile("BlackBerry[^/\\s]+/(\\d\\.\\d)");

  public w(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }

  protected final Set<String >a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<>()).addAll(e.values());
    hashSet.add("generic_mobile");
    hashSet.add(d);
    return hashSet;
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    String str = paramWURFLRequest.getCleanedDeviceUserAgent();
    return (!paramWURFLRequest._internalIsDesktopBrowser() && (StringUtils.containsIgnoreCase(str, "blackberry") || StringMatchUtils.containsAnyOf(str, new String[] { "(BB10;", "(PlayBook" })));
  }

  protected final String a(String paramString) {
    int i;
    if (paramString.contains("BB10")) {
      i = StringMatchUtils.indexOfOrLength(paramString, ")");
    } else if (paramString.startsWith("Mozilla/4")) {
      i = StringMatchUtils.secondSlash(paramString);
    } else if (paramString.startsWith("Mozilla/5")) {
      i = StringMatchUtils.ordinalIndexOfOrNotFound(paramString, ";", 3);
    } else if (paramString.contains("PlayBook")) {
      i = StringMatchUtils.firstCloseParenthesis(paramString);
    } else {
      i = StringMatchUtils.firstSlash(paramString);
    }
    return (i != -1) ? StringMatchUtils.risMatch(getFilter().a().a(), paramString, i) : StringMatchUtils.NULL_STRING;
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    String str1 = paramWURFLRequest.getNormalizedDeviceUserAgent();
    String str3 = str1;
    Matcher matcher;
    String str2 = (matcher = f.matcher(str3)).find() ? matcher.group(1) : null;
    if (str1.contains("BB10"))
      return str1.contains("Mobile") ? b : c;
    if (str1.contains("PlayBook"))
      return d;
    if (str2 != null)
      for (Map.Entry<String, String> entry : e.entrySet()) {
        if (str2.contains(entry.getKey()))
          return entry.getValue();
      }
    return "generic";
  }

  public final String getMatcherName() {
    return "BlackBerryMatcher";
  }

  public final String getBucketMatcherName() {
    return "BlackBerry";
  }

  static {
    (e = new LinkedHashMap<Object, Object>()).put("2.", "blackberry_generic_ver2");
    e.put("3.2", "blackberry_generic_ver3_sub2");
    e.put("3.3", "blackberry_generic_ver3_sub30");
    e.put("3.5", "blackberry_generic_ver3_sub50");
    e.put("3.6", "blackberry_generic_ver3_sub60");
    e.put("3.7", "blackberry_generic_ver3_sub70");
    e.put("4.1", "blackberry_generic_ver4_sub10");
    e.put("4.2", "blackberry_generic_ver4_sub20");
    e.put("4.3", "blackberry_generic_ver4_sub30");
    e.put("4.5", "blackberry_generic_ver4_sub50");
    e.put("4.6", "blackberry_generic_ver4_sub60");
    e.put("4.7", "blackberry_generic_ver4_sub70");
    e.put("4.", "blackberry_generic_ver4");
    e.put("5.", "blackberry_generic_ver5");
    e.put("6.", "blackberry_generic_ver6");
    e.put("10", b);
    e.put("10t", c);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\w.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
