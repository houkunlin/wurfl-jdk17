package com.scientiamobile.wurfl.core.exc;

import java.io.Serial;

/**
 * WURFL 基础异常类，所有 WURFL 相关的已检查异常（Checked Exception）的基类。
 * <p>当发生 WURFL 数据处理过程中可预料的错误时抛出此异常或其子类，
 * 例如设备数据解析失败、资源加载异常等情况，要求调用方显式处理。</p>
 * <p>对应运行时异常请参见 {@link WURFLRuntimeException}。</p>
 */

public abstract class WURFLException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 使用默认错误信息创建异常实例。
     */
    protected WURFLException() {
        super("Generic Exception in WURFL.");
    }

    /**
     * 使用指定的错误信息创建异常实例。
     *
     * @param message 异常描述信息
     */
    protected WURFLException(String message) {
        super(message);
    }

    /**
     * 使用根本原因创建异常实例。
     *
     * @param cause 导致此异常的根本原因
     */
    protected WURFLException(Throwable cause) {
        super(cause);
    }

    /**
     * 使用指定的错误信息和根本原因创建异常实例。
     *
     * @param message 异常描述信息
     * @param cause   导致此异常的根本原因
     */
    protected WURFLException(String message, Throwable cause) {
        super(message, cause);
    }
}
