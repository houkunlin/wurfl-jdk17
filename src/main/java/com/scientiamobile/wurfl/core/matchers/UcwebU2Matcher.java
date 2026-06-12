package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.UcwebU2Normalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * UCWeb U2 内核浏览器匹配器。
 * <p>UCWeb U2 是 UC 浏览器的旧版内核，运行在多种移动平台上。
 * 通过检查 User-Agent 是否以 "UCWEB" 开头且包含 "UCBrowser" 来识别。
 * RIS 匹配根据不同平台（Android、iOS、Windows Phone、Symbian、Java）验证设备信息后匹配。</p>
 */

final class UcwebU2Matcher extends MatcherBase {
    public UcwebU2Matcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
        super(userAgentNormalizer, wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add("generic_ucweb");
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent.startsWith("UCWEB") && cleanedDeviceUserAgent.contains("UCBrowser");
    }

    /**
     * 执行 RIS 匹配：通过 "---" 分隔符定位，根据不同平台（Adr/Android、iPh OS/iOS、
     * wds/Windows Phone、Symbian、Java）验证设备信息完整性后进行匹配。
     *
     * @param userAgent 要匹配的 User-Agent 字符串
     * @return RIS 匹配结果
     */
    @Override
    protected String risMatch(String userAgent) {
        if (UserAgentUtils.getUcBrowserVersion(userAgent, true) == null) {
            return null;
        }

        int matchLength = userAgent.indexOf("---");
        if (matchLength <= 0) {
            return null;
        }
        matchLength += 3;
        String subUserAgent = userAgent.substring(matchLength);

        if (!isValidUcwebPlatform(userAgent, subUserAgent)) {
            return null;
        }

        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
    }

    /**
     * 验证 UCWeb 请求是否来自已知平台，且设备信息完整。
     *
     * @return 平台信息有效返回 {@code true}
     */
    private static boolean isValidUcwebPlatform(String userAgent, String subUserAgent) {
        if (userAgent.contains("Adr")) {
            return UserAgentUtils.getUcAndroidModel(userAgent, false) != null
                    && UserAgentUtils.getUcAndroidVersion(userAgent, false) != null;
        }
        if (userAgent.contains("iPh OS")) {
            return UcwebU2Normalizer.IPHONE.matcher(subUserAgent).find();
        }
        if (userAgent.contains("wds")) {
            return UcwebU2Normalizer.WINDOWS_PHONE.matcher(subUserAgent).find();
        }
        if (userAgent.contains("Symbian")) {
            return UcwebU2Normalizer.SYMBIAN.matcher(subUserAgent).find();
        }
        if (userAgent.contains("Java")) {
            return UcwebU2Normalizer.JAVA.matcher(subUserAgent).find();
        }
        return false;
    }

    /**
     * 恢复匹配策略：统一返回通用 UCWeb 设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 固定返回 {@code "generic_ucweb"}
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        return "generic_ucweb";
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "UcwebU2Matcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "UcwebU2";
    }
}
