package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * DoCoMo（NTT docomo）日本运营商品牌设备匹配器。
 * <p>通过检查 User-Agent 是否以 "DoCoMo" 开头来识别 docomo 品牌的移动设备。
 * 支持 DoCoMo/2 和 DoCoMo/1 两个协议版本的恢复匹配。</p>
 */

final class DoCoMoMatcher extends MatcherBase {
    private static final String DOCOMO_VER2 = "docomo_generic_jap_ver2";
    private static final String DOCOMO_VER1 = "docomo_generic_jap_ver1";

    public DoCoMoMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * 返回所需验证的设备 ID 集合.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(DOCOMO_VER1);
        requiredDeviceIds.add(DOCOMO_VER2);
        return requiredDeviceIds;
    }

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("DoCoMo");
    }

    @Override
/**
 * 执行 RIS 匹配.
 */

    protected String risMatch(String normalizedUserAgent) {
        int matchLength;
        matchLength = StringMatchUtils.secondSlash(normalizedUserAgent);
        if (matchLength == -1) {
            matchLength = StringMatchUtils.firstOpenParenthesis(normalizedUserAgent);
        }

        return matchLength != -1
                ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength)
                : StringMatchUtils.NULL_STRING;
    }

    @Override
/**
 * 执行恢复匹配.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        return request.getNormalizedDeviceUserAgent().startsWith("DoCoMo/2") ? DOCOMO_VER2 : DOCOMO_VER1;
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "DoCoMoMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "DoCoMo";
    }
}
