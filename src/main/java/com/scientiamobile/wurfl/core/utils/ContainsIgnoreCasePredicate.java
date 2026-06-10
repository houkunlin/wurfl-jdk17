package com.scientiamobile.wurfl.core.utils;

import org.apache.commons.collections4.Predicate;

import java.util.Locale;

/**
 * Implementation of Contains Ignore Case Predicate.
 */

final class ContainsIgnoreCasePredicate implements Predicate<String> {
    private final String input;

    ContainsIgnoreCasePredicate(String input) {
        this.input = input;
    }

    /**
     * Evaluate.
     */

    public final boolean evaluate(String keyword) {
        return this.input != null && keyword != null && this.input.toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT));
    }
}
