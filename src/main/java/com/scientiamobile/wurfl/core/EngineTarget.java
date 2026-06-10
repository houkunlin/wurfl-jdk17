package com.scientiamobile.wurfl.core;

/**
 * WURFL 引擎的目标匹配模式枚举。
 * <p>定义了引擎在设备匹配时的策略偏好：</p>
 * <ul>
 *   <li>{@code defaultTarget} - 默认模式，自动选择</li>
 *   <li>{@code fastDesktopBrowserMatch} - 优先快速匹配桌面浏览器</li>
 *   <li>{@code performance} - 性能优先模式，牺牲部分精度换取速度</li>
 *   <li>{@code accuracy} - 精度优先模式，使用更严格的匹配策略</li>
 * </ul>
 */

public enum EngineTarget {
    defaultTarget,
    fastDesktopBrowserMatch,
    performance,
    accuracy;
}
