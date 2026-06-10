package com.scientiamobile.wurfl.core.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 异常信息提取工具类。
 * <p>提供沿异常链向上追溯、获取第一个非空异常消息的能力。
 * 在需要记录或展示异常根因摘要时，避免因多层包装异常导致信息丢失。</p>
 */

public class ExceptionUtils {

    /**
     * 沿异常链向上追溯，获取第一个可用的非空异常消息。
     * <p>默认最多追溯 10 层，避免因异常链过长导致性能问题。</p>
     *
     * @param throwable 异常起点
     * @return 第一个非空的异常消息；如果所有层级的消息均为空，则返回空字符串
     */
    public static String getFirstAvailableMessage(Throwable throwable) {
        return getFirstAvailableMessage(throwable, 10);
    }

    /**
     * 沿异常链向上追溯（指定最大深度），获取第一个可用的非空异常消息。
     * <p>遍历异常链的原因链（{@link Throwable#getCause()}），
     * 直到找到一条非空、非空白字符串的消息为止。
     * 这在处理嵌套异常（如 InvocationTargetException）时尤其有用，
     * 因为真实原因往往被外层包装异常掩盖。</p>
     *
     * @param throwable 异常起点
     * @param maxDepth  最大追溯深度，防止异常链形成环导致无限循环
     * @return 第一个非空的异常消息；如果所有层级的消息均为空，则返回空字符串
     */
    private static String getFirstAvailableMessage(Throwable throwable, int maxDepth) {
        while (throwable != null && maxDepth > 0) {
            String message = throwable.getMessage();
            if (StringUtils.isNotEmpty(message)) {
                return message;
            }
            throwable = throwable.getCause();
            maxDepth--;
        }
        return "";
    }
}
