package com.scientiamobile.wurfl.core.utils;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

final class ContainsIgnoreCasePredicate implements Predicate {
   private String input;

   ContainsIgnoreCasePredicate(String input) {
      this.input = input;
   }

   public final boolean evaluate(Object keyword) {
      return StringUtils.containsIgnoreCase(this.input, (String)keyword);
   }
}

