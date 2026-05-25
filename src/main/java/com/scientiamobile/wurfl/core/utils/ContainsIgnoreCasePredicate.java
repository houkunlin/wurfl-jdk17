package com.scientiamobile.wurfl.core.utils;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.StringUtils;

final class ContainsIgnoreCasePredicate implements Predicate {
   private String input;

   ContainsIgnoreCasePredicate(String input) {
      this.input = input;
   }

   public final boolean evaluate(Object keyword) {
      return StringUtils.containsIgnoreCase(this.input, (String)keyword);
   }
}

