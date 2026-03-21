package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.Validate;

final class a implements UserAgentResolver {
  public final String resolve(HttpServletRequest paramHttpServletRequest) {
    Validate.notNull(paramHttpServletRequest, "The HttpServletRequest is null");
    return UserAgentUtils.getUserAgent(paramHttpServletRequest);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\a.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
