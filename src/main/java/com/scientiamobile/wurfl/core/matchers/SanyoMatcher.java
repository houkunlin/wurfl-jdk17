package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.Locale;

/**
 * Sanyo（三洋）品牌设备匹配器。
 * <p>通过检查 User-Agent 是否以 "sanyo"（不区分大小写）开头或包含 "MobilePhone"
 * 来识别 Sanyo 品牌的移动设备。</p>
 */

final class SanyoMatcher extends MatcherBase {
    public SanyoMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser()
                && (cleanedDeviceUserAgent.toLowerCase(Locale.US).startsWith("sanyo") || cleanedDeviceUserAgent.contains("MobilePhone"));
    }

    @Override
/**
 * 执行 RIS 匹配.
 */

    protected String risMatch(String normalizedUserAgent) {
        if (normalizedUserAgent.contains("MobilePhone")) {
            int matchLength = StringMatchUtils.indexOfOrLength(normalizedUserAgent, "/", StringMatchUtils.indexOf(normalizedUserAgent, "MobilePhone"));
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
        } else {
            return super.risMatch(normalizedUserAgent);
        }
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "SanyoMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "Sanyo";
    }
}
