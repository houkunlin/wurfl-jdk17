package com.scientiamobile.wurfl.core.exc;

import java.io.Serial;

/**
 * WURFL 运行时异常类，所有 WURFL 相关的非已检查异常（Unchecked Exception）的基类。
 * <p>当发生 WURFL 处理过程中的意外错误时抛出此异常或其子类，
 * 例如设备未定义、功能不存在等情况。与 {@link WURFLException} 不同，
 * 此异常不强制调用方捕获，适用于表示编程错误或不可恢复的运行时问题。</p>
 */

public class WURFLRuntimeException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 使用默认错误信息创建运行时异常实例。
     */
    public WURFLRuntimeException() {
        super("WURFL unexpected exception");
    }

    /**
     * 使用指定的错误信息创建运行时异常实例。
     *
     * @param message 异常描述信息
     */
    public WURFLRuntimeException(String message) {
        super(message);
    }

    /**
     * 使用指定的错误信息和根本原因创建运行时异常实例。
     *
     * @param message 异常描述信息
     * @param cause   导致此异常的根本原因
     */
    public WURFLRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 使用根本原因创建运行时异常实例，使用默认错误信息。
     *
     * @param cause 导致此异常的根本原因
     */
    public WURFLRuntimeException(Throwable cause) {
        super("WURFL unexpected exception", cause);
    }
}
