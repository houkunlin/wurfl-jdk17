package com.scientiamobile.wurfl.core.matchers;

/**
 * 匹配类型枚举，描述了设备匹配结果的精度级别。
 * <p>匹配类型按精度从高到低排列：</p>
 * <ul>
 *   <li>{@link #exact} — 精确匹配，User-Agent 完全命中索引</li>
 *   <li>{@link #conclusive} — 确定匹配，通过 RIS 算法缩小范围后匹配</li>
 *   <li>{@link #recovery} — 恢复匹配，基于版本号或关键字推断</li>
 *   <li>{@link #catchAll} — 兜底匹配，使用全局回退规则</li>
 *   <li>{@link #highperformance} — 高性能模式匹配</li>
 *   <li>{@link #none} — 未匹配到任何设备</li>
 *   <li>{@link #cached} — 从缓存中获取的匹配结果</li>
 *   <li>{@link #fastDesktopBrowser} — 快速桌面浏览器匹配</li>
 * </ul>
 */

public enum MatchType {
    exact,
    conclusive,
    recovery,
    catchAll,
    highperformance,
    none,
    cached,
    fastDesktopBrowser;
}
