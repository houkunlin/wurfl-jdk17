package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class ap extends a {
  private static String b = "generic_opera_mini_android_version5";
  
  private static String[] c = new String[] { "Opera/9.80 (J2ME/MIDP; Opera Mini/5", "Opera/9.80 (Android; Opera Mini/5.0", "Opera/9.80 (Android; Opera Mini/5.1" };
  
  public ap(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add(b);
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && StringMatchUtils.containsAllOf(paramWURFLRequest.getCleanedDeviceUserAgent(), new String[] { "Android", "Opera Mini" }));
  }
  
  protected final String a(String paramString) {
    int i;
    if ((i = paramString.indexOf(" Build/")) < 0) {
      String[] arrayOfString;
      int j = (arrayOfString = c).length;
      for (byte b = 0; b < j; b++) {
        String str = arrayOfString[b];
        if (paramString.startsWith(str)) {
          i = str.length();
          break;
        } 
      } 
    } 
    return (i >= 0) ? StringMatchUtils.risMatch(getFilter().a().a(), paramString, i) : null;
  }
  
  protected final String b(WURFLRequest paramWURFLRequest) {
    return b;
  }
  
  public final String getMatcherName() {
    return "OperaMiniOnAndroidMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "OperaMiniOnAndroid";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\ap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */