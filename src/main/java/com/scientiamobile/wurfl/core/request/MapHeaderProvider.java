package com.scientiamobile.wurfl.core.request;

import java.util.Enumeration;
import java.util.Map;
import org.apache.commons.collections4.iterators.IteratorEnumeration;

public class MapHeaderProvider implements WURFLHeaderProvider {
   private final Map a;

   public MapHeaderProvider(Map var1) {
      this.a = var1;
   }

   public String getHeader(String var1) {
      return (String)this.a.get(var1);
   }

   public Enumeration getHeaderNames() {
      return new IteratorEnumeration(this.a.keySet().iterator());
   }
}
