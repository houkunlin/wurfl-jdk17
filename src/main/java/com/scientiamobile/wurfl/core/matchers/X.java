package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class x extends AbstractA {
  private static String b = "google_image_proxy";

  public x(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }

  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add(b);
    hashSet.add("generic_web_crawler");
    return hashSet;
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return paramWURFLRequest._internalIsBot();
  }

  protected final String a(String paramString) {
    int i;
    return ((i = paramString.startsWith("Mozilla") ? StringMatchUtils.firstCloseParenthesis(paramString) : StringMatchUtils.firstSlash(paramString)) != -1) ? StringMatchUtils.risMatch(getFilter().a().a(), paramString, i) : StringMatchUtils.NULL_STRING;
  }

  protected final String a(WURFLRequest paramWURFLRequest) {
    return paramWURFLRequest.getCleanedDeviceUserAgent().contains("GoogleImageProxy") ? b : super.a(paramWURFLRequest);
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    return "generic_web_crawler";
  }

  public final String getMatcherName() {
    return "BotMatcher";
  }

  public final String getBucketMatcherName() {
    return "Bot";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\x.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
