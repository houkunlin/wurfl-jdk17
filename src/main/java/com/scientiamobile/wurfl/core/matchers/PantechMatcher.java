package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

/**
 * Pantech（泛泰）品牌设备匹配器。
 * <p>通过检查 User-Agent 是否以 "Pantech"、"PT-"、"PANTECH" 或 "PG-" 开头
 * 来识别 Pantech 品牌的移动设备。</p>
 */

final class PantechMatcher extends MatcherBase {
    public PantechMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser()
                && StringMatchUtils.startsWithAnyOf(cleanedDeviceUserAgent, "Pantech", "PT-", "PANTECH", "PG-");
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "PantechMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "Pantech";
    }
}
