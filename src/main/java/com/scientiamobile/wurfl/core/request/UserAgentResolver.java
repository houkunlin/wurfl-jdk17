package com.scientiamobile.wurfl.core.request;

import jakarta.servlet.http.HttpServletRequest;

public interface UserAgentResolver {
   String resolve(HttpServletRequest var1);
}
