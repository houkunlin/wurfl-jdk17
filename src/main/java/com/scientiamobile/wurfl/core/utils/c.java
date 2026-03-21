package com.scientiamobile.wurfl.core.utils;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.StringUtils;

final class c implements Predicate {
  c(String paramString) {}
  
  public final boolean evaluate(Object paramObject) {
    return StringUtils.containsIgnoreCase(this.a, (String)paramObject);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\utils\c.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
