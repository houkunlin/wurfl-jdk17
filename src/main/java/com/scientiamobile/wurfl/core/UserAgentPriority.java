package com.scientiamobile.wurfl.core;

/**
 * User-Agent 优先级策略枚举。
 * <p>定义了在设备检测时如何处理浏览器侧载的 User-Agent：</p>
 * <ul>
 *   <li>{@code OverrideSideloadedBrowserUserAgent} - 使用浏览器侧载的 User-Agent 覆盖设备 UA（默认）</li>
 *   <li>{@code UsePlainUserAgent} - 直接使用原始 User-Agent，不使用侧载 UA</li>
 * </ul>
 */

public enum UserAgentPriority {
    OverrideSideloadedBrowserUserAgent,
    UsePlainUserAgent;
}
