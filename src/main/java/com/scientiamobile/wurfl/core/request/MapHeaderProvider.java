package com.scientiamobile.wurfl.core.request;

import java.util.Enumeration;
import java.util.Map;
import org.apache.commons.collections4.iterators.IteratorEnumeration;

public class MapHeaderProvider implements WURFLHeaderProvider {
   private final Map<String, String> headers;

   public MapHeaderProvider(Map<String, String> headers) {
      this.headers = headers;
   }

   @Override
   public String getHeader(String headerName) {
      return this.headers.get(headerName);
   }

   @Override
   public Enumeration<String> getHeaderNames() {
      return new IteratorEnumeration<>(this.headers.keySet().iterator());
   }
}
