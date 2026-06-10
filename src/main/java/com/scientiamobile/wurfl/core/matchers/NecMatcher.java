package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

/**
 * NEC（日本电气）品牌设备匹配器。
 * <p>通过检查 User-Agent 是否以 NEC- 或 KGT 开头来识别 NEC 品牌的移动设备。</p>
 */

final class NecMatcher extends MatcherBase {
    public NecMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
    /**
     * 判断 User-Agent 是否以 "NEC-" 或 "KGT" 开头且非桌面浏览器。
     *
     * @param request WURFL 请求对象
     * @return 如果是 NEC 设备则返回 {@code true}
     */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && StringMatchUtils.startsWithAnyOf(request.getCleanedDeviceUserAgent(), "NEC-", "KGT");
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "NecMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "Nec";
    }
}
