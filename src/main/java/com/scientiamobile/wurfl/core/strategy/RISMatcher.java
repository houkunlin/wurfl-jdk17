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

    private static int commonPrefixLength(String firstValue, String secondValue) {
        int minLength = Math.min(firstValue.length(), secondValue.length());

        int index = 0;
        for (; index < minLength && firstValue.charAt(index) == secondValue.charAt(index); ++index) {
        }

        return index;
    }

    public String getName() {
        return "RIS";
    }

    @SuppressWarnings("rawtypes")
    public String match(Collection candidates, String value, int threshold) {
        String matchedValue = null;
        int valueLength = value.length();
        ArrayList candidatesList = (ArrayList) candidates;
        int bestIndex = -1;
        int bestMatchLength = -1;
        int low = 0;
        int high = candidatesList.size() - 1;

        while (low <= high && bestMatchLength < valueLength) {
            int middle = (low + high) / 2;
            String middleValue = (String) candidatesList.get(middle);
            int matchLength;
            matchLength = commonPrefixLength(value, middleValue);
            if (matchLength > bestMatchLength) {
                bestIndex = middle;
                bestMatchLength = matchLength;
            }

            int compareResult;
            compareResult = middleValue.compareTo(value);
            if (compareResult < 0) {
                low = middle + 1;
            } else {
                if (compareResult <= 0) {
                    break;
                }

                high = middle - 1;
            }
        }

        if (bestMatchLength >= threshold) {
            int leftMostIndex = bestIndex;
            int currentMatchLength = bestMatchLength;
            ListIterator iterator = candidatesList.listIterator(bestIndex);

            while (iterator.hasPrevious() && currentMatchLength == bestMatchLength) {
                String previousCandidate = (String) iterator.previous();
                currentMatchLength = commonPrefixLength(value, previousCandidate);
                if (currentMatchLength == bestMatchLength) {
                    --leftMostIndex;
                }
            }

            matchedValue = (String) candidatesList.get(leftMostIndex);
        }

        return matchedValue;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
