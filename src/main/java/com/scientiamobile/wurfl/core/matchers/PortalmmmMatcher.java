package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * Portalmmm 品牌设备匹配器。
 * <p>通过检查 User-Agent 是否以 "portalmmm" 开头来识别 Portalmmm 品牌的移动设备。</p>
 * <p>注意：该匹配器的 {@link #risMatch} 方法返回 {@code null}，即不参与 RIS 确定匹配流程。</p>
 */

final class PortalmmmMatcher extends MatcherBase {
    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("portalmmm");
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "PortalmmmMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "Portalmmm";
    }

    /**
     * 执行 RIS 匹配.
     */
    @Override
    protected String risMatch(String normalizedUserAgent) {
        return null;
    }
}
