package com.scientiamobile.wurfl.core.strategy;

import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Matcher implementation for identifying LD devices and browsers.
 */

public final class LDMatcher {
    public static final LDMatcher INSTANCE = new LDMatcher();

    private LDMatcher() {
        LoggerFactory.getLogger(LDMatcher.class);
    }

    /**
     * Returns the levenshtei nistance.
     */

    public static int getLevenshteinDistance(String firstValue, String secondValue, int firstLength, int secondLength, int maxDistance, int prefixLength) {
        if (firstValue == null || secondValue == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        if (maxDistance == 0) {
            return firstValue.equals(secondValue) ? 0 : Integer.MAX_VALUE;
        }
        // Ensure shorter string is first for optimal array allocation
        if (firstLength > secondLength) {
            return getLevenshteinDistance(secondValue, firstValue, secondLength, firstLength, maxDistance, prefixLength);
        }
        if (secondLength < prefixLength) {
            return Integer.MAX_VALUE;
        }
        if (firstLength == 0) {
            return secondLength;
        }
        // Histogram-based early exit
        int[] charHistogram = new int[256];
        for (int i = prefixLength; i < secondLength; i++) {
            charHistogram[secondValue.charAt(i) & 0xff]++;
        }
        for (int i = prefixLength; i < firstLength; i++) {
            charHistogram[firstValue.charAt(i) & 0xff]--;
        }
        int histogramDistance = 0;
        int maxDistanceDoubled = maxDistance << 1;
        for (char c = ' '; c < 'z'; c++) {
            histogramDistance += Math.abs(charHistogram[c]);
            if (histogramDistance > maxDistanceDoubled) {
                return Integer.MAX_VALUE;
            }
        }
        // Levenshtein DP calculation
        firstValue = firstValue.substring(prefixLength);
        firstLength -= prefixLength;
        secondValue = secondValue.substring(prefixLength);
        secondLength -= prefixLength;
        int[] previousRow = new int[firstLength + 1];
        for (int i = 0; i <= firstLength; i++) {
            previousRow[i] = i;
        }
        for (int rowIndex = 1; rowIndex <= secondLength; rowIndex++) {
            int[] currentRow = new int[firstLength + 1];
            currentRow[0] = rowIndex;
            char secondChar = secondValue.charAt(rowIndex - 1);
            for (int col = 1; col <= firstLength; col++) {
                int substitutionCost = firstValue.charAt(col - 1) == secondChar ? 0 : 1;
                currentRow[col] = Math.min(currentRow[col - 1] + 1,
                        Math.min(previousRow[col] + 1, previousRow[col - 1] + substitutionCost));
            }
            previousRow = currentRow;
        }
        return previousRow[firstLength];
    }

    /**
     * Returns the name.
 */

    public final String getName() {
        return "LD";
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    /**
     * 在候选列表中查找与目标值编辑距离最小的设备标识（不指定公共前缀长度）。
     * <p>该方法委托给 {@link #match(Collection, String, int, int)}，
     * 并将公共前缀长度设为 0，即从头开始计算编辑距离。</p>
     *
     * @param candidates  候选设备标识列表
     * @param value       待匹配的目标 User-Agent 字符串
     * @param maxDistance 允许的最大编辑距离
     * @return 最匹配的候选字符串，如果无匹配项则返回 {@code null}
     */
    public final String match(Collection candidates, String value, int maxDistance) {
        return this.match(candidates, value, maxDistance, 0);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
/**
 * Attempts to match the given request to a device.
 * @param request the WURFL request
 * @return device info for the matched device
 */

    public final String match(Collection candidates, String value, int maxDistance, int commonPrefixLength) {
        String bestMatch = null;
        int bestDistance = maxDistance + 1;
        int valueLength = value.length();
        int currentDistance = valueLength;

        for (String candidate : (Collection<String>) candidates) {
            if (currentDistance == 0) {
                break;
            }
            currentDistance = getLevenshteinDistance(candidate, value, candidate.length(), valueLength, maxDistance, commonPrefixLength);
            if (Math.abs(candidate.length() - valueLength) <= maxDistance
                    && currentDistance < bestDistance) {
                bestDistance = currentDistance;
                bestMatch = candidate;
            }
        }
        return bestMatch;
    }

    @Override
/**
 * Returns a string representation of this object.
 */

    public String toString() {
        return this.getName();
    }
}
