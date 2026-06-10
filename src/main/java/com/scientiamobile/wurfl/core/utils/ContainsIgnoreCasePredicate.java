package com.scientiamobile.wurfl.core.utils;

import org.apache.commons.collections4.Predicate;

import java.util.Locale;

/**
 * 忽略大小写的字符串包含判断谓词。
 * <p>实现了 Commons Collections 的 {@link Predicate} 接口，
 * 用于在匹配流程中判断目标字符串是否包含指定的关键词（不区分大小写）。
 * 常用于 User-Agent 关键词匹配场景。</p>
 */

final class ContainsIgnoreCasePredicate implements Predicate<String> {
    private final String input;

    /**
     * 构造一个忽略大小写的包含判断谓词。
     *
     * @param input 被搜索的基准字符串（将在此字符串中查找关键词）
     */
    ContainsIgnoreCasePredicate(String input) {
        this.input = input;
    }

    /**
     * 判断给定的关键词是否包含在基准字符串中（不区分大小写）。
     *
     * @param keyword 待检查的关键词
     * @return 如果基准字符串包含该关键词（忽略大小写），则返回 {@code true}
     */
    public final boolean evaluate(String keyword) {
        return this.input != null && keyword != null && this.input.toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT));
    }
}
