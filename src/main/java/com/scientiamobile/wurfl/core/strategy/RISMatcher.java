package com.scientiamobile.wurfl.core.strategy;

import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;

public final class RISMatcher {
    public static final RISMatcher INSTANCE = new RISMatcher();

    private RISMatcher() {
        LoggerFactory.getLogger(RISMatcher.class);
    }

    private static int commonPrefixLength(String first, String second) {
        int shorter = Math.min(first.length(), second.length());
        for (int i = 0; i < shorter; i++) {
            if (first.charAt(i) != second.charAt(i)) {
                return i;
            }
        }
        return shorter;
    }

    public String getName() {
        return "RIS";
    }

    @SuppressWarnings({"unchecked"})
    public String match(Collection<?> candidates, String value, int threshold) {
        int valueLength = value.length();
        ArrayList<String> candidatesList = (ArrayList<String>) candidates;
        int bestIndex = -1;
        int bestMatchLength = -1;
        int low = 0;
        int high = candidatesList.size() - 1;

        while (low <= high && bestMatchLength < valueLength) {
            int middle = (low + high) / 2;
            String middleValue = candidatesList.get(middle);
            int matchLength = commonPrefixLength(value, middleValue);
            if (matchLength > bestMatchLength) {
                bestIndex = middle;
                bestMatchLength = matchLength;
            }

            int compareResult = middleValue.compareTo(value);
            if (compareResult < 0) {
                low = middle + 1;
            } else if (compareResult == 0) {
                break;
            } else {
                high = middle - 1;
            }
        }

        if (bestMatchLength < threshold) {
            return null;
        }

        int leftMostIndex = bestIndex;
        int currentMatchLength = bestMatchLength;
        ListIterator<String> iterator = candidatesList.listIterator(bestIndex);

        while (iterator.hasPrevious() && currentMatchLength == bestMatchLength) {
            String previousCandidate = iterator.previous();
            currentMatchLength = commonPrefixLength(value, previousCandidate);
            if (currentMatchLength == bestMatchLength) {
                leftMostIndex--;
            }
        }

        return candidatesList.get(leftMostIndex);
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
