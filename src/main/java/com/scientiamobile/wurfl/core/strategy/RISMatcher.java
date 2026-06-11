package com.scientiamobile.wurfl.core.strategy;

import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;

/**
 * RIS（Reduced Initial Substring）匹配器实现，用于识别 WURFL 设备。
 *
 * <p>该匹配器基于二分搜索算法，在有序的 User-Agent 候选列表中查找与目标值具有最长公共前缀的条目。
 * 这是 WURFL 设备检测中最常用的匹配策略之一，适用于大多数现代设备浏览器的 User-Agent 识别。</p>
 *
 * <p>该实现为单例模式，通过 {@link #INSTANCE} 访问。</p>
 */
public final class RISMatcher {
    public static final RISMatcher INSTANCE = new RISMatcher();

    private RISMatcher() {
        LoggerFactory.getLogger(RISMatcher.class);
    }

    /**
     * 计算两个字符串的最长公共前缀长度。
     *
     * <p>从字符串起始位置逐字符比较，返回第一个不相同字符的索引位置。
     * 如果其中一个字符串是另一个的前缀，则返回较短字符串的长度。</p>
     *
     * @param first  第一个待比较字符串
     * @param second 第二个待比较字符串
     * @return 两个字符串的最长公共前缀长度
     */
    private static int commonPrefixLength(String first, String second) {
        int shorter = Math.min(first.length(), second.length());
        for (int i = 0; i < shorter; i++) {
            if (first.charAt(i) != second.charAt(i)) {
                return i;
            }
        }
        return shorter;
    }

    /**
     * 获取该匹配器的名称标识。
     *
     * @return 匹配器名称 "RIS"
     */
    public String getName() {
        return "RIS";
    }

    /**
     * 在有序的候选列表中查找与目标值最匹配的设备标识。
     *
     * <p>采用二分搜索策略，在每一步查找中同时计算公共前缀长度，
     * 记录具有最长公共前缀的候选条目。如果最佳匹配长度低于阈值，则返回 {@code null}。</p>
     *
     * <p>在找到最佳匹配位置后，会向左遍历以定位具有相同匹配长度的最左索引，
     * 确保匹配结果的确定性。</p>
     *
     * @param candidates 候选设备标识列表（必须已排序）
     * @param value      待匹配的目标 User-Agent 字符串
     * @param threshold  最小匹配长度阈值，低于此长度的匹配将被拒绝
     * @return 最匹配的候选字符串，如果无匹配项则返回 {@code null}
     */
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
