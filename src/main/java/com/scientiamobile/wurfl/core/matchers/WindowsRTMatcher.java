package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Windows RT 操作系统匹配器。
 * <p>Windows RT 是微软针对 ARM 架构设备推出的操作系统版本。
 * 该匹配器通过检查 User-Agent 同时包含 "Windows NT"、"ARM" 和 "Trident/" 来识别。
 * 区分包含 "like Gecko"（IE 11+）和不包含（IE 10）两种场景的恢复匹配。</p>
 */

final class WindowsRTMatcher extends AbstractMatcher {
    private static final String WINDOWS_8_RT_VER1_SUBOS81 = "windows_8_rt_ver1_subos81";
    private static final String GENERIC_WINDOWS_8_RT = "generic_windows_8_rt";

    public WindowsRTMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * 返回所需验证的设备 ID 集合.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GENERIC_WINDOWS_8_RT);
        requiredDeviceIds.add(WINDOWS_8_RT_VER1_SUBOS81);
        return requiredDeviceIds;
    }

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        return StringMatchUtils.containsAllOf(request.getCleanedDeviceUserAgent(), "Windows NT ", " ARM;", "Trident/");
    }

    @Override
/**
 * 执行 RIS 匹配.
 */

    protected String risMatch(String userAgent) {
        if (userAgent.contains("like Gecko")) {
            int geckoIndex;
            geckoIndex = userAgent.indexOf(" Gecko");
            if (geckoIndex >= 0) {
                return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, geckoIndex + 6);
            }
        } else {
            int armIndex;
            armIndex = userAgent.indexOf(" ARM;");
            if (armIndex >= 0) {
                return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, armIndex + 5);
            }
        }

        return null;
    }

    @Override
    /**
     * 恢复匹配策略：根据是否包含 "like Gecko"（IE 引擎版本）选择对应的 Windows RT 通用设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */

    protected String applyRecoveryMatch(WURFLRequest request) {
        return request.getNormalizedDeviceUserAgent().contains("like Gecko") ? WINDOWS_8_RT_VER1_SUBOS81 : GENERIC_WINDOWS_8_RT;
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "WindowsRTMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "WindowsRT";
    }
}
