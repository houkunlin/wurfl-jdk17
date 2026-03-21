package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public final class DesktopApplicationMatcher extends AbstractA {
  private static String b = "ms_office";

  private static final Set c = new HashSet();

  private static final Pattern d = Pattern.compile("MSOffice ([0-9]+)");

  private static final Pattern e = Pattern.compile("Microsoft Office/([0-9.]+)");

  public DesktopApplicationMatcher(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }

  protected final Set a() {
    HashSet<?> hashSet;
    (hashSet = new HashSet()).addAll(c);
    hashSet.add("generic_web_browser");
    return hashSet;
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsMobileBrowser() && StringMatchUtils.containsAnyOf(paramWURFLRequest.getCleanedDeviceUserAgent(), new String[] { "Microsoft Office", "MSOffice", "office", "DesktopApp " }));
  }

  protected final String a(String paramString) {
    Matcher matcher1 = d.matcher(paramString);
    Matcher matcher2 = e.matcher(paramString);
    if (matcher1.find()) {
      int i;
      if ((i = StringMatchUtils.firstCloseParenthesis(paramString = paramString.substring(paramString.indexOf("MSOffice")))) != -1)
        return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
    } else {
      int i;
      if (matcher2.find() && (i = (paramString = paramString.substring(paramString.indexOf("Microsoft Office"))).indexOf('.')) != -1)
        return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
    }
    return "generic";
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    return StringMatchUtils.containsAnyOf(paramWURFLRequest.getDeviceUserAgent(), new String[] { "Office", "office" }) ? b : (StringUtils.contains(paramWURFLRequest.getDeviceUserAgent(), "DesktopApp ") ? "generic_desktop_application" : "generic_web_browser");
  }

  public final String getMatcherName() {
    return "DesktopApplicationMatcher";
  }

  public final String getBucketMatcherName() {
    return "DesktopApplication";
  }

  static {
    c.add("generic_desktop_application");
    c.add(b);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\DesktopApplicationMatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
