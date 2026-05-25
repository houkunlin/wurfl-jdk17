package com.scientiamobile.wurfl.core.utils;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

final class c implements Predicate {
   // $FF: synthetic field
   private String a;

   c(String var1) {
      this.a = var1;
      super();
   }

   public final boolean evaluate(Object var1) {
      return StringUtils.containsIgnoreCase(this.a, (String)var1);
   }
}
