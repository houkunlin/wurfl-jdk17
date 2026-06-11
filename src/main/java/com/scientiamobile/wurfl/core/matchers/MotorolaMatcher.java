package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Motorola（摩托罗拉）品牌设备匹配器。
 * <p>通过检查 User-Agent 是否以 "Mot-"、"MOT-"、"MOTO"、"moto" 开头
 * 或包含 "Motorola" 来识别摩托罗拉品牌的移动设备。
 * 支持 MIB（Motorola Internet Browser）浏览器的恢复匹配。</p>
 */

final class MotorolaMatcher extends MatcherBase {
    private static final String MOT_MIB22_GENERIC = "mot_mib22_generic";

    public MotorolaMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add("generic");
        requiredDeviceIds.add(MOT_MIB22_GENERIC);
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && StringMatchUtils.startsWithAnyOf(cleanedDeviceUserAgent, "Mot-", "MOT-", "MOTO", "moto")
                || cleanedDeviceUserAgent.contains("Motorola");
    }

    /**
     * 执行恢复匹配.
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        return StringMatchUtils.containsAnyOf(request.getNormalizedDeviceUserAgent(), "MIB/2.2", "MIB/BER2.2") ? MOT_MIB22_GENERIC : "generic";
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "MotorolaMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "Motorola";
    }
}
