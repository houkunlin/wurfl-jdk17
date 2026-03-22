package com.scientiamobile.wurfl.core.matchers;

import org.apache.commons.lang3.Validate;
import org.slf4j.LoggerFactory;

import java.util.*;

final class G1 {
  private SortedMap a;

  private List b;

  private final F1 c;

  public G1(F1 paramF1) {
    LoggerFactory.getLogger(getClass());
    this.a = new TreeMap<Object, Object>();
    this.b = new ArrayList<>();
    this.c = paramF1;
  }

  public final Collection a() {
    return this.b;
  }

  public final void b() {
    Collections.sort(this.b);
  }

  public final String a(String paramString) {
    Validate.notNull(paramString, "The userAgent is empty");
    return (String)this.a.get(paramString);
  }

  public final void a(String paramString1, String paramString2) {
    Validate.notNull(paramString1, "user-agent cannot be null");
    Validate.notEmpty(paramString2, "The deviceId is empty");
    this.a.put(paramString1, paramString2);
    this.b.add(paramString1);
  }

  public final String toString() {
    return this.c.getMatcherName() + this.a.values();
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\G.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
