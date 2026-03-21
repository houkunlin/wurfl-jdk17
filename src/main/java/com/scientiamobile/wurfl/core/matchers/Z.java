package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.Collection;
import org.apache.commons.lang.StringUtils;

final class z extends a {
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return true;
  }
  
  protected final String a(String paramString) {
    Collection collection = getFilter().a().a();
    if (StringUtils.startsWith(paramString, "CFNetwork")) {
      int i;
      if ((i = StringMatchUtils.firstSpace(paramString)) != -1)
        return StringMatchUtils.risMatch(collection, paramString, i); 
    } else {
      int i;
      if ((i = StringMatchUtils.firstSlash(paramString)) != -1)
        return StringMatchUtils.risMatch(collection, paramString, i); 
    } 
    return StringMatchUtils.NULL_STRING;
  }
  
  public final String getMatcherName() {
    return "CatchAllRISMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "CatchAllRis";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\z.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */