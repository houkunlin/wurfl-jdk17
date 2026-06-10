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

    @Override
/**
 * 返回所需验证的设备 ID 集合.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add("generic_ucweb");
        return requiredDeviceIds;
    }

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent.startsWith("UCWEB") && cleanedDeviceUserAgent.contains("UCBrowser");
    }

    @Override
    /**
     * 执行 RIS 匹配：通过 "---" 分隔符定位，根据不同平台（Adr/Android、iPh OS/iOS、
     * wds/Windows Phone、Symbian、Java）验证设备信息完整性后进行匹配。
     *
     * @param userAgent 要匹配的 User-Agent 字符串
     * @return RIS 匹配结果
     */

    protected String risMatch(String userAgent) {
        if (UserAgentUtils.getUcBrowserVersion(userAgent, true) == null) {
            return null;
        } else {
            int matchLength;
            matchLength = userAgent.indexOf("---");
            if (matchLength > 0) {
                matchLength += 3;
                String subUserAgent = userAgent.substring(matchLength);
                if (userAgent.contains("Adr")) {
                    String androidModel = UserAgentUtils.getUcAndroidModel(userAgent, false);
                    String androidVersion = UserAgentUtils.getUcAndroidVersion(userAgent, false);
                    if (androidModel != null && androidVersion != null) {
                        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
                    }
                } else if (userAgent.contains("iPh OS")) {
                    if (UcwebU2Normalizer.IPHONE.matcher(subUserAgent).find()) {
                        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
                    }
                } else if (userAgent.contains("wds")) {
                    if (UcwebU2Normalizer.WINDOWS_PHONE.matcher(subUserAgent).find()) {
                        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
                    }
                } else if (userAgent.contains("Symbian")) {
                    if (UcwebU2Normalizer.SYMBIAN.matcher(subUserAgent).find()) {
                        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
                    }
                } else if (userAgent.contains("Java") && UcwebU2Normalizer.JAVA.matcher(subUserAgent).find()) {
                    return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
                }
            }

            return null;
        }
    }

    @Override
    /**
     * 恢复匹配策略：统一返回通用 UCWeb 设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 固定返回 {@code "generic_ucweb"}
     */

    protected String applyRecoveryMatch(WURFLRequest request) {
        return "generic_ucweb";
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "UcwebU2Matcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "UcwebU2";
    }
}
