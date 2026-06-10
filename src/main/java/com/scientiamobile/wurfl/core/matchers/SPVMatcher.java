package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

/**
 * SPV（Orange 旗下品牌）设备匹配器。
 * <p>通过检查 User-Agent 是否包含 SPV 来识别 SPV 品牌的移动设备。</p>
 */

final class SPVMatcher extends MatcherBase {
    public SPVMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().contains("SPV");
    }

    @Override
    /**
     * 执行 RIS 匹配：找到 "SPV" 关键字后的分号位置作为截断点。
     *
     * @param userAgent 要匹配的 User-Agent 字符串
     * @return RIS 匹配结果
     */

    protected String risMatch(String userAgent) {
        int matchLength = StringMatchUtils.indexOfOrLength(userAgent, ";", StringMatchUtils.indexOfOrLength(userAgent, "SPV"));
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "SPVMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "SPV";
    }
}
