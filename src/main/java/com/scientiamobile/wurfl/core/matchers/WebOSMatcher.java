package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * webOS（HP/Palm）操作系统匹配器。
 * <p>webOS 是惠普（原 Palm）开发的移动操作系统。通过检查 User-Agent 是否包含 webOS 或 hpwOS 来识别。恢复匹配区分 webOS 3.x（平板，如 HP TouchPad）和早期版本。</p>
 */

final class WebOSMatcher extends AbstractMatcher {
    private static final String HP_TABLET_WEBOS_GENERIC = "hp_tablet_webos_generic";
    private static final String HP_WEBOS_GENERIC = "hp_webos_generic";

    public WebOSMatcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
        super(userAgentNormalizer, wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(HP_TABLET_WEBOS_GENERIC);
        requiredDeviceIds.add(HP_WEBOS_GENERIC);
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(request.getCleanedDeviceUserAgent(), "webOS", "hpwOS");
    }

    /**
     * 执行 RIS 匹配.
     */
    @Override
    protected String risMatch(String userAgent) {
        int matchLength = StringMatchUtils.indexOfOrLength(userAgent, "---");
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
    }

    /**
     * 恢复匹配策略：根据是否包含 "hpwOS/3"（WebOS 3.x，平板版本）返回对应的通用设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        return request.getNormalizedDeviceUserAgent().contains("hpwOS/3") ? HP_TABLET_WEBOS_GENERIC : HP_WEBOS_GENERIC;
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "WebOSMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "WebOS";
    }
}
