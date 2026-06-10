package com.scientiamobile.wurfl.core.web.introspector;

/**
 * WURFL 引擎及服务器环境信息的数据结构体。
 * <p>包含 API 版本、WURFL 数据版本、引擎配置（目标模式、UA 优先级）、
 * Servlet 容器信息和 Java 运行时环境信息，序列化为 JSON 返回给客户端。</p>
 */

final class IntrospectorInfoResponse {
    /**
     * WURFL Java API 版本号
     */
    String apiVersion;
    /**
     * WURFL 数据文件版本号
     */
    String wurflVersion;
    /** 引擎目标匹配模式 */
    String engineTarget;
    /** User-Agent 优先级策略 */
    String userAgentPriority;
    /** Servlet 容器服务器信息 */
    String serverInfo;
    /** 操作系统名称 */
    String osName;
    /** 操作系统版本 */
    String osVersion;
    /** Java 运行环境供应商 */
    String javaVendor;
    /** Java 运行环境版本 */
    String javaVersion;
}

