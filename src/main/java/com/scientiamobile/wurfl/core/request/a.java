package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.Validate;

final class a implements UserAgentResolver {
   public a() {
   }

   public final String resolve(HttpServletRequest var1) {
      Validate.notNull(var1, "The HttpServletRequest is null");
      return UserAgentUtils.getUserAgent(var1);
   }
}
