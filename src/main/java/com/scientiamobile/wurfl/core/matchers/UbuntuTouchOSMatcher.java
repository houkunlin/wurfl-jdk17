package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Ubuntu Touch 操作系统匹配器（Canonical 开发的移动操作系统）。
 * <p>通过检查 User-Agent 包含 "Ubuntu" 且同时包含 "Mobile" 或 "Tablet" 来识别。
 * 支持手机和平板两种形态的恢复匹配。</p>
 */

public class UbuntuTouchOSMatcher extends MatcherBase {
    private static final String GENERIC_UBUNTU_TOUCH_OS = "generic_ubuntu_touch_os";
    private static final String GENERIC_UBUNTU_TOUCH_OS_TABLET = "generic_ubuntu_touch_os_tablet";

    public UbuntuTouchOSMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GENERIC_UBUNTU_TOUCH_OS);
        requiredDeviceIds.add(GENERIC_UBUNTU_TOUCH_OS_TABLET);
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return cleanedDeviceUserAgent.contains("Ubuntu") && StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Mobile", "Tablet");
    }

    /**
     * 执行 RIS 匹配.
     */
    @Override
    protected String risMatch(String userAgent) {
        int matchLength = userAgent.indexOf("like Android");
        if (matchLength >= 0) {
            matchLength += 12;
            matchLength = userAgent.indexOf("WebKit/");
        }

        return matchLength >= 0 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength) : null;
    }

    /**
     * 执行恢复匹配.
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        return request.getNormalizedDeviceUserAgent().contains("Tablet") ? GENERIC_UBUNTU_TOUCH_OS_TABLET : GENERIC_UBUNTU_TOUCH_OS;
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "UbuntuTouchOSMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "UbuntuTouchOS";
    }
}
