package com.scientiamobile.wurfl.core.request;

import java.util.Enumeration;

public interface WURFLHeaderProvider {
   String getHeader(String var1);

   Enumeration getHeaderNames();
}
