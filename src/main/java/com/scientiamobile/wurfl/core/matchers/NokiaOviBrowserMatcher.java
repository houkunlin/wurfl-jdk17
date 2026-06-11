package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Nokia Ovi 浏览器匹配器。
 * <p>Ovi 浏览器运行在诺基亚 Series40 和 Series30+ 功能手机平台上。通过检查 User-Agent 是否包含 S40OviBrowser 来识别。</p>
 */

final class NokiaOviBrowserMatcher extends MatcherBase {
    private static final String NOKIA_GENERIC_SERIES30PLUS = "nokia_generic_series30plus";
    private static final String NOKIA_GENERIC_SERIES40_OVIBROSR = "nokia_generic_series40_ovibrosr";

    public NokiaOviBrowserMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(NOKIA_GENERIC_SERIES30PLUS);
        requiredDeviceIds.add(NOKIA_GENERIC_SERIES40_OVIBROSR);
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().contains("S40OviBrowser");
    }
    /**
     * 执行 RIS 匹配：找到 "Nokia" 关键字后的斜杠或空格位置作为截断点。
     *
     * @param userAgent 要匹配的 User-Agent 字符串
     * @return RIS 匹配结果
     */
    @Override
    protected String risMatch(String userAgent) {
        int matchLength = StringMatchUtils.indexOfAnyOrLength(userAgent, new String[]{"/", " "}, userAgent.indexOf("Nokia"));
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
    }

    /**
     * 执行恢复匹配.
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        return request.getNormalizedDeviceUserAgent().contains("Series30Plus") ? NOKIA_GENERIC_SERIES30PLUS : NOKIA_GENERIC_SERIES40_OVIBROSR;
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "NokiaOviBrowserMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "NokiaOviBrowser";
    }
}
