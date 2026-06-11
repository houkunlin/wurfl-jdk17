package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

import java.util.HashSet;
import java.util.Set;

/**
 * Java MIDlet（移动信息设备小程序）匹配器。
 * <p>通过检查 User-Agent 是否包含 "UNTRUSTED/1.0" 来识别基于 Java ME 平台的 MIDlet 应用。
 * 该匹配器的确定匹配直接返回通用 MIDP MIDlet 设备 ID。</p>
 */

final class JavaMidletMatcher extends MatcherBase {
    private static final String GENERIC_MIDP_MIDLET = "generic_midp_midlet";

    public JavaMidletMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GENERIC_MIDP_MIDLET);
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        return request.getCleanedDeviceUserAgent().contains("UNTRUSTED/1.0");
    }

    /**
     * Java MIDlet 匹配器直接返回固定的通用设备 ID，不执行实际的 User-Agent 匹配。
     *
     * @param request WURFL 请求对象
     * @return 固定的 {@code "generic_midp_midlet"}
     */
    @Override
    protected String applyConclusiveMatch(WURFLRequest request) {
        return GENERIC_MIDP_MIDLET;
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "JavaMidletMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "JavaMidlet";
    }
}
