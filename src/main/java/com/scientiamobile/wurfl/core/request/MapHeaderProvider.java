package com.scientiamobile.wurfl.core.request;

import java.util.Enumeration;
import java.util.Map;
import org.apache.commons.collections4.iterators.IteratorEnumeration;

public class MapHeaderProvider implements WURFLHeaderProvider {
  private final Map a;
  
  public MapHeaderProvider(Map paramMap) {
    this.a = paramMap;
  }
  
  public String getHeader(String paramString) {
    return (String)this.a.get(paramString);
  }
  
  public Enumeration getHeaderNames() {
    return (Enumeration)new IteratorEnumeration(this.a.keySet().iterator());
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\MapHeaderProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
