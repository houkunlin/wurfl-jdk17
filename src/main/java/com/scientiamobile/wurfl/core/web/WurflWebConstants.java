package com.scientiamobile.wurfl.core.web;

import com.scientiamobile.wurfl.core.WURFLEngine;

/**
 * WURFL Web 应用常量定义接口。
 * <p>定义了 WURFL 引擎在 Web 环境中使用的上下文参数名和属性键名常量，
 * 包括 WURFL 数据文件路径、补丁文件路径、引擎目标模式、User-Agent 优先级等配置键名。</p>
 */

public interface WurflWebConstants {
    /**
     * Servlet 初始化参数名：WURFL 引擎在 ServletContext 中的属性键名
     */
    String WURFL_ENGINE_KEY_PARAM = "wurflEngineKey";
    /**
     * WURFL 引擎实例在 ServletContext 中的默认属性键名
     */
    String WURFL_ENGINE_KEY = WURFLEngine.class.getName();
    /** Servlet 初始化参数名：WURFL 补丁文件路径 */
    String WURFL_PATCH = "wurflPatch";
    /** Servlet 初始化参数名：WURFL 主数据文件路径 */
    String WURFL = "wurfl";
    /** WURFL 数据文件在 Web 应用中的默认存放位置 */
    String WURFL_DEFAULT_LOCATION = "/WEB-INF/wurfl.zip";
    /** Servlet 初始化参数名：引擎目标匹配模式 */
    String WURFL_ENGINE_TARGET_KEY = "wurflEngineTarget";
    /** Servlet 初始化参数名：User-Agent 优先级策略 */
    String WURFL_USER_AGENT_PRIORITY_KEY = "wurflUserAgentPriority";
}
