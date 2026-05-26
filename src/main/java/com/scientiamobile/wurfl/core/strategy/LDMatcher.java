package com.scientiamobile.wurfl.core.strategy;

import java.util.Collection;
import java.util.Iterator;
import org.slf4j.LoggerFactory;

public final class LDMatcher {
   public static final LDMatcher INSTANCE = new LDMatcher();

   private LDMatcher() {
      LoggerFactory.getLogger(LDMatcher.class);
   }

   public final String getName() {
      return "LD";
   }

   public final String match(Collection candidates, String value, int maxDistance) {
      return this.match(candidates, value, maxDistance, 0);
   }

   public final String match(Collection candidates, String value, int maxDistance, int commonPrefixLength) {
      String bestMatch = null;
      int bestDistance = maxDistance + 1;
      int currentDistance = value.length();
      int valueLength = value.length();
      Iterator iterator = candidates.iterator();

      while(iterator.hasNext() && currentDistance > 0) {
         String candidate = (String)iterator.next();
         if (Math.abs(candidate.length() - valueLength) <= maxDistance && ((currentDistance = getLevenshteinDistance(candidate, value, candidate.length(), valueLength, maxDistance, commonPrefixLength)) < bestDistance || currentDistance == 0)) {
            bestDistance = currentDistance;
            bestMatch = candidate;
         }
      }

      return bestMatch;
   }

   public static int getLevenshteinDistance(String firstValue, String secondValue, int firstLength, int secondLength, int maxDistance, int prefixLength) {
      while(true) {
         if (firstValue != null && secondValue != null) {
            if (maxDistance == 0) {
               if (firstValue.equals(secondValue)) {
                  return 0;
               }

               return Integer.MAX_VALUE;
            }

            if (firstLength > secondLength) {
               String tempValue = secondValue;
               secondValue = firstValue;
               firstValue = tempValue;
               int tempLength = secondLength;
               secondLength = firstLength;
               firstLength = tempLength;
               continue;
            }

            if (secondLength < prefixLength) {
               return Integer.MAX_VALUE;
            }

            if (firstLength == 0) {
               return secondLength;
            }

            int[] charHistogram = new int[256];

            for(int i = prefixLength; i < secondLength; ++i) {
               ++charHistogram[(char)(secondValue.charAt(i) & 255)];
            }

            for(int i = prefixLength; i < firstLength; ++i) {
               --charHistogram[(char)(firstValue.charAt(i) & 255)];
            }

            int histogramDistance = 0;
            maxDistance <<= 1;

            for(char c = ' '; c < 'z'; ++c) {
               if ((histogramDistance += Math.abs(charHistogram[c])) > maxDistance) {
                  return Integer.MAX_VALUE;
               }
            }

            firstValue = firstValue.substring(prefixLength);
            firstLength -= prefixLength;
            secondValue = secondValue.substring(prefixLength);
            secondLength -= prefixLength;
            int[] previousRow = new int[firstLength + 1];
            int[] currentRow = new int[firstLength + 1];

            for(int i = 0; i <= firstLength; previousRow[i] = i++) {
            }

            for(int rowIndex = 1; rowIndex <= secondLength; ++rowIndex) {
               histogramDistance = secondValue.charAt(rowIndex - 1);
               currentRow[0] = rowIndex;

               for(int columnIndex = 1; columnIndex <= firstLength; ++columnIndex) {
                  int substitutionCost = firstValue.charAt(columnIndex - 1) == histogramDistance ? 0 : 1;
                  currentRow[columnIndex] = Math.min(Math.min(currentRow[columnIndex - 1] + 1, previousRow[columnIndex] + 1), previousRow[columnIndex - 1] + substitutionCost);
               }

               int[] previousRowTemp = previousRow;
               previousRow = currentRow;
               currentRow = previousRowTemp;
            }

            return previousRow[firstLength];
         }

         throw new IllegalArgumentException("Strings must not be null");
      }
   }

   public final String toString() {
      return this.getName();
   }
}
