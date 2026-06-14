package com.scientiamobile.wurfl.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 惰性编译的正则模式包装器。
 * <p>仅存储正则表达式字符串，在首次调用 {@link #matcher(CharSequence)} 或 {@link #pattern()}
 * 时编译为 {@link Pattern}。适用于类加载时批量声明但实际运行时只用部分模式的场景。</p>
 */
final class LazyPattern {
    private final String regex;
    private volatile Pattern pattern;

    LazyPattern(String regex) {
        this.regex = regex;
    }

    /**
     * 获取或惰性编译底层 Pattern。
     */
    Pattern pattern() {
        Pattern p = this.pattern;
        if (p == null) {
            synchronized (this) {
                p = this.pattern;
                if (p == null) {
                    p = this.pattern = Pattern.compile(this.regex);
                }
            }
        }
        return p;
    }

    /**
     * 委托给 {@link Pattern#matcher(CharSequence)}。
     */
    Matcher matcher(CharSequence input) {
        return pattern().matcher(input);
    }
}
