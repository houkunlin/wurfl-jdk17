package com.scientiamobile.wurfl.core.utils;

import java.util.Locale;
import org.apache.commons.collections4.Predicate;

final class ContainsIgnoreCasePredicate implements Predicate<String> {
   private final String input;

   ContainsIgnoreCasePredicate(String input) {
      this.input = input;
   }

   public final boolean evaluate(String keyword) {
      return this.input != null && keyword != null && this.input.toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT));
   }
}
