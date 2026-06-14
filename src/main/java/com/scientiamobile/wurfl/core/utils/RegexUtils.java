package com.scientiamobile.wurfl.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式安全匹配工具类。
 * <p>提供带超时控制的正则匹配方法，防止恶意构造的 User-Agent 字符串导致
 * 灾难性回溯（ReDoS）。通过在每个字符访问时检查运行时间，超时则中断匹配。</p>
 *
 * <p>User-Agent 字符串已在 {@code DefaultWURFLRequest} 中被截断至 512 字符，
 * 本工具在此基础上提供额外的运行时防护。</p>
 */
public final class RegexUtils {

    private static final long DEFAULT_TIMEOUT_NANOS = 200_000_000L; // 200ms

    private RegexUtils() {
    }

    /**
     * 在指定时间内执行 {@link Matcher#find()}，超时返回 {@code false}。
     */
    public static boolean findWithTimeout(Pattern pattern, String input, long timeoutNanos) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        TimeLimitedCharSequence timed = new TimeLimitedCharSequence(input, timeoutNanos);
        try {
            return pattern.matcher(timed).find();
        } catch (RegexTimeoutException e) {
            return false;
        }
    }

    /**
     * 在默认超时（200ms）内执行 {@link Matcher#find()}。
     */
    public static boolean findWithTimeout(Pattern pattern, String input) {
        return findWithTimeout(pattern, input, DEFAULT_TIMEOUT_NANOS);
    }

    /**
     * 在指定时间内执行 {@link Matcher#matches()}，超时返回 {@code false}。
     */
    public static boolean matchesWithTimeout(Pattern pattern, String input, long timeoutNanos) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        TimeLimitedCharSequence timed = new TimeLimitedCharSequence(input, timeoutNanos);
        try {
            return pattern.matcher(timed).matches();
        } catch (RegexTimeoutException e) {
            return false;
        }
    }

    /**
     * 在默认超时（200ms）内执行 {@link Matcher#matches()}。
     */
    public static boolean matchesWithTimeout(Pattern pattern, String input) {
        return matchesWithTimeout(pattern, input, DEFAULT_TIMEOUT_NANOS);
    }

    /**
     * 在默认超时（200ms）内执行 {@link Matcher#find()} 并返回匹配器。
     * <p>超时或未匹配时返回 {@code null}，不抛异常。</p>
     */
    public static Matcher findMatcherWithTimeout(Pattern pattern, String input) {
        return findMatcherWithTimeout(pattern, input, DEFAULT_TIMEOUT_NANOS);
    }

    /**
     * 在指定时间内执行 {@link Matcher#find()} 并返回匹配器。
     * <p>每轮 {@code charAt()} / {@code length()} 检查运行时间，
     * 超时返回 {@code null} 而非抛异常。分组结果可在返回的 {@link Matcher}
     * 上正常提取。</p>
     */
    public static Matcher findMatcherWithTimeout(Pattern pattern, String input, long timeoutNanos) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        TimeLimitedCharSequence timed = new TimeLimitedCharSequence(input, timeoutNanos);
        try {
            Matcher matcher = pattern.matcher(timed);
            if (matcher.find()) {
                return matcher;
            }
            return null;
        } catch (RegexTimeoutException e) {
            return null;
        }
    }

    /**
     * 带超时控制的 CharSequence 包装器。
     * <p>Java 正则引擎在匹配过程中会频繁调用 {@link #charAt(int)} 和 {@link #length()}，
     * 在此处检查已消耗的时间，超过阈值则抛出 {@link RegexTimeoutException} 中断匹配。</p>
     */
    private static final class TimeLimitedCharSequence implements CharSequence {

        private final String source;
        private final long deadlineNanos;

        TimeLimitedCharSequence(String source, long timeoutNanos) {
            this.source = source;
            this.deadlineNanos = System.nanoTime() + timeoutNanos;
        }

        @Override
        public int length() {
            checkTimeout();
            return source.length();
        }

        @Override
        public char charAt(int index) {
            checkTimeout();
            return source.charAt(index);
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            checkTimeout();
            return source.subSequence(start, end);
        }

        @Override
        public String toString() {
            return source;
        }

        private void checkTimeout() {
            if (System.nanoTime() > deadlineNanos) {
                throw new RegexTimeoutException("Regex matching timed out");
            }
        }
    }

    /**
     * 正则匹配超时异常（非受检异常）。
     */
    public static final class RegexTimeoutException extends RuntimeException {
        public RegexTimeoutException(String message) {
            super(message);
        }
    }
}
