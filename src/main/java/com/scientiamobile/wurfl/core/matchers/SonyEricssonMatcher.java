package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

/**
 * Sony Ericsson（索尼爱立信）品牌设备匹配器。
 * <p>通过检查 User-Agent 是否包含 "Sony" 来识别索尼爱立信品牌的移动设备。
 * RIS 匹配根据 User-Agent 是否以 "SonyEricsson" 开头采用不同的截断策略。</p>
 */

final class SonyEricssonMatcher extends MatcherBase {
    public SonyEricssonMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().contains("Sony");
    }

    @Override
/**
 * 执行 RIS 匹配.
 */

    protected String risMatch(String normalizedUserAgent) {
        if (normalizedUserAgent.startsWith("SonyEricsson")) {
            int matchLength = StringMatchUtils.firstSlash(normalizedUserAgent);
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength - 2);
        } else {
            int matchLength = StringMatchUtils.secondSlash(normalizedUserAgent);
            return matchLength != -1
                    ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength)
                    : StringMatchUtils.NULL_STRING;
        }
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "SonyEricssonMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "SonyEricsson";
    }
}
