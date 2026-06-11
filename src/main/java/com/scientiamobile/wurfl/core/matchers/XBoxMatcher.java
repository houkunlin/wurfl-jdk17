package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

import java.util.HashSet;
import java.util.Set;

/**
 * Microsoft Xbox 游戏设备匹配器。
 * <p>通过检查 User-Agent 是否包含 "Xbox" 来识别微软的 Xbox 游戏主机，
 * 包括 Xbox 360 和 Xbox One。根据 User-Agent 中的 MSIE 版本和 Xbox 型号
 * 区分不同的设备版本。</p>
 */

final class XBoxMatcher extends AbstractMatcher {
    private static final String MICROSOFT_XBOXONE_VER1 = "microsoft_xboxone_ver1";
    private static final String MICROSOFT_XBOX360_VER1_SUBIE10 = "microsoft_xbox360_ver1_subie10";
    private static final String MICROSOFT_XBOX360_VER1 = "microsoft_xbox360_ver1";

    public XBoxMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(MICROSOFT_XBOXONE_VER1);
        requiredDeviceIds.add(MICROSOFT_XBOX360_VER1_SUBIE10);
        requiredDeviceIds.add(MICROSOFT_XBOX360_VER1);
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        return request.getCleanedDeviceUserAgent().contains("Xbox");
    }

    /**
     * 执行确定匹配.
     */
    @Override
    protected String applyConclusiveMatch(WURFLRequest request) {
        return null;
    }
    /**
     * 恢复匹配策略：根据 User-Agent 是否包含 "MSIE 10.0" 和 "Xbox One" 返回对应的通用设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        if (normalizedUserAgent.contains("MSIE 10.0")) {
            return normalizedUserAgent.contains("Xbox One") ? MICROSOFT_XBOXONE_VER1 : MICROSOFT_XBOX360_VER1_SUBIE10;
        } else {
            return MICROSOFT_XBOX360_VER1;
        }
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "XBoxMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "Xbox";
    }
}
