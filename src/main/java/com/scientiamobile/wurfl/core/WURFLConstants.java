package com.scientiamobile.wurfl.core;

/**
 * WURFL 运行时调试常量定义接口。
 * <p>控制 WURFL 引擎内部是否启用调试、跟踪和统计跟踪信息输出。
 * 在版本构建时默认全部禁用。</p>
 */

public interface WURFLConstants {
    /**
     * 是否启用调试日志输出
     */
    boolean IS_DEBUG_ENABLED = false;
    /**
     * 是否启用跟踪日志输出
     */
    boolean IS_TRACE_ENABLED = false;
    /**
     * 是否启用统计跟踪日志输出
     */
    boolean IS_TRACE_STATS_ENABLED = false;
}
