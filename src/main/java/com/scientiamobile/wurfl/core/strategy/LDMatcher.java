package com.scientiamobile.wurfl.core.strategy;

import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * LD（Levenshtein Distance）匹配器实现，用于通过编辑距离识别设备和浏览器。
 *
 * <p>该匹配器基于 Levenshtein 编辑距离算法，在候选列表中查找与目标 User-Agent 字符串
 * 编辑距离最小的设备标识。支持设置最大编辑距离和公共前缀长度来优化匹配效率和精度。</p>
 *
 * <p>该实现为单例模式，通过 {@link #INSTANCE} 访问。</p>
 */

public final class LDMatcher {
    /**
     * 单例实例
     */
    public static final LDMatcher INSTANCE = new LDMatcher();

    /**
     * 私有构造方法，防止外部实例化
     */
    private LDMatcher() {
        LoggerFactory.getLogger(LDMatcher.class);
    }

    /**
     * 计算两个字符串之间的 Levenshtein 编辑距离。
     *
     * <p>采用动态规划算法实现，包含以下优化策略：</p>
     * <ul>
     *   <li>自动将较短的字符串作为第一个参数以减少内存分配</li>
     *   <li>基于字符直方图的快速预检，在大距离时提前退出</li>
     *   <li>支持指定公共前缀长度，跳过前缀部分的计算</li>
     * </ul>
     *
     * @param firstValue  第一个待比较字符串（建议为较短的字符串）
     * @param secondValue 第二个待比较字符串
     * @param firstLength 第一个字符串的长度
     * @param secondLength 第二个字符串的长度
     * @param maxDistance 允许的最大编辑距离，超过此距离则提前返回 {@link Integer#MAX_VALUE}
     * @param prefixLength 公共前缀长度，前 {@code prefixLength} 个字符不计入距离计算
     * @return 两个字符串之间的编辑距离；如果距离超过 {@code maxDistance} 则返回 {@link Integer#MAX_VALUE}
     * @throws IllegalArgumentException 如果任一参数为 {@code null}
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
     * 获取该匹配器的名称标识。
     *
     * @return 匹配器名称 "LD"
     */
    public final String getName() {
        return "LD";
    }

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
    @SuppressWarnings({"rawtypes", "unchecked"})
    public final String match(Collection candidates, String value, int maxDistance) {
        return this.match(candidates, value, maxDistance, 0);
    }

    /**
     * 在候选列表中查找与目标值编辑距离最小的设备标识（支持指定公共前缀长度）。
     *
     * <p>遍历候选列表，对每个候选字符串计算 Levenshtein 编辑距离，
     * 记录距离最小且不超过 {@code maxDistance} 的候选作为最佳匹配。</p>
     *
     * @param candidates         候选设备标识列表
     * @param value              待匹配的目标 User-Agent 字符串
     * @param maxDistance        允许的最大编辑距离
     * @param commonPrefixLength 公共前缀长度，前 {@code commonPrefixLength} 个字符不参与距离计算
     * @return 最匹配的候选字符串，如果无匹配项则返回 {@code null}
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
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

    /**
     * 返回该匹配器的字符串表示形式，即匹配器名称。
     *
     * @return 匹配器名称 "LD"
     */
    @Override
    public String toString() {
        return this.getName();
    }
}
