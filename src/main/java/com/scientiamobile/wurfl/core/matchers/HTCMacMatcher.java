package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * HTC Mac 伪装匹配器，用于识别伪装成 Macintosh 的 HTC Android 设备。
 * <p>某些 HTC Android 设备的 User-Agent 以 Macintosh 开头但包含 HTC 标识。该匹配器专门处理此类伪装设备。</p>
 */

final class HTCMacMatcher extends MatcherBase {
    private static final String GENERIC_HTC_ANDROID_DISGUISED_AS_MAC = "generic_android_htc_disguised_as_mac";

    public HTCMacMatcher(UserAgentNormalizer normalizer, WURFLModel wurflModel) {
        super(normalizer, wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GENERIC_HTC_ANDROID_DISGUISED_AS_MAC);
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return cleanedDeviceUserAgent.startsWith("Mozilla/5.0 (Macintosh") && cleanedDeviceUserAgent.contains("HTC");
    }

    /**
     * 执行 RIS 匹配：以 "---" 分隔符位置作为截断点。
     *
     * @param normalizedUserAgent 要匹配的 User-Agent 字符串
     * @return RIS 匹配结果
     */
    @Override
    protected String risMatch(String normalizedUserAgent) {
        int matchLength = StringMatchUtils.indexOfOrLength(normalizedUserAgent, "---");
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
    }

    /**
     * 恢复匹配策略：统一返回 HTC Android 伪装为 Mac 的通用设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        return GENERIC_HTC_ANDROID_DISGUISED_AS_MAC;
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "HTCMacMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "HTCMac";
    }
}
