package com.scientiamobile.wurfl.core.request;

import javax.servlet.http.HttpServletRequest;

public interface UserAgentResolver {
   String resolve(HttpServletRequest var1);
}
