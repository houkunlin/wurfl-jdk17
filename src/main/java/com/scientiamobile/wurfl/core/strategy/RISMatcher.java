package com.scientiamobile.wurfl.core.strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;
import org.slf4j.LoggerFactory;

public final class RISMatcher {
   public static final RISMatcher INSTANCE = new RISMatcher();

   private RISMatcher() {
      LoggerFactory.getLogger(RISMatcher.class);
   }

   public final String getName() {
      return "RIS";
   }

   @SuppressWarnings("rawtypes")
   public final String match(Collection candidates, String value, int threshold) {
      String matchedValue = null;
      int valueLength = value.length();
      ArrayList candidatesList = (ArrayList)candidates;
      int bestIndex = -1;
      int bestMatchLength = -1;
      int low = 0;
      int high = candidatesList.size() - 1;

      while(low <= high && bestMatchLength < valueLength) {
         int middle = (low + high) / 2;
         String middleValue = (String)candidatesList.get(middle);
         int matchLength;
         if ((matchLength = commonPrefixLength(value, middleValue)) > bestMatchLength) {
            bestIndex = middle;
            bestMatchLength = matchLength;
         }

         int compareResult;
         if ((compareResult = middleValue.compareTo(value)) < 0) {
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

         while(iterator.hasPrevious() && currentMatchLength == bestMatchLength) {
            String previousCandidate = (String)iterator.previous();
            if ((currentMatchLength = commonPrefixLength(value, previousCandidate)) == bestMatchLength) {
               --leftMostIndex;
            }
         }

         matchedValue = (String)candidatesList.get(leftMostIndex);
      }

      return matchedValue;
   }

   private static int commonPrefixLength(String firstValue, String secondValue) {
      int minLength = Math.min(firstValue.length(), secondValue.length());

      int index;
      for(index = 0; index < minLength && firstValue.charAt(index) == secondValue.charAt(index); ++index) {
      }

      return index;
   }

   @Override
   public final String toString() {
      return this.getName();
   }
}
