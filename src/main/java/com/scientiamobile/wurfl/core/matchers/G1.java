package com.scientiamobile.wurfl.core.matchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.commons.lang.Validate;
import org.slf4j.LoggerFactory;

final class G {
   private SortedMap a;
   private List b;
   private final F c;

   public G(F var1) {
      LoggerFactory.getLogger(this.getClass());
      this.a = new TreeMap();
      this.b = new ArrayList();
      this.c = var1;
   }

   public final Collection a() {
      return this.b;
   }

   public final void b() {
      Collections.sort(this.b);
   }

   public final String a(String var1) {
      Validate.notNull(var1, "The userAgent is empty");
      return (String)this.a.get(var1);
   }

   public final void a(String var1, String var2) {
      Validate.notNull(var1, "user-agent cannot be null");
      Validate.notEmpty(var2, "The deviceId is empty");
      this.a.put(var1, var2);
      this.b.add(var1);
   }

   public final String toString() {
      return this.c.getMatcherName() + this.a.values();
   }
}
