package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Amazon Kindle 设备匹配器。
 * <p>通过检查 User-Agent 是否包含 "Kindle" 或 "Silk" 来识别 Amazon 的 Kindle 系列设备。
 * 对于包含 "Android" + "/Kindle" 或 "Silk" 的 User-Agent 不做处理（由 Android 匹配器处理）。
 * 支持 Kindle 1/2/3、Kindle Fire 和 Silk 浏览器的映射。</p>
 */

final class KindleMatcher extends MatcherBase {
    private static final String GENERIC_AMAZON_KINDLE = "generic_amazon_kindle";
    private static final Map<String, String> DEVICE_BY_TOKEN;

    static {
        DEVICE_BY_TOKEN = new LinkedHashMap<>();
        DEVICE_BY_TOKEN.put("Kindle/1", "amazon_kindle_ver1");
        DEVICE_BY_TOKEN.put("Kindle/2", "amazon_kindle2_ver1");
        DEVICE_BY_TOKEN.put("Kindle/3", "amazon_kindle3_ver1");
        DEVICE_BY_TOKEN.put("Kindle Fire", "amazon_kindle_fire_ver1");
        DEVICE_BY_TOKEN.put("Silk", "amazon_kindle_fire_ver1");
    }

    public KindleMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GENERIC_AMAZON_KINDLE);
        requiredDeviceIds.addAll(DEVICE_BY_TOKEN.values());
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return cleanedDeviceUserAgent.contains("Android") && StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "/Kindle", "Silk") ? false : StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Kindle", "Silk");
    }

    /**
     * 执行 RIS 匹配：按优先级尝试以下截断位置：
     * <ul>
     *   <li>"Build/" 关键字位置</li>
     *   <li>"Kindle/" + 版本号（仅限 v1-v3）位置</li>
     *   <li>"PlayStation Vita" 关键字位置</li>
     * </ul>
     *
     * @param userAgent 要匹配的 User-Agent 字符串
     * @return RIS 匹配结果
     */
    @Override
    protected String risMatch(String userAgent) {
        int matchLength;
        matchLength = userAgent.indexOf("Build/");
        if (matchLength != -1) {
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
        } else {
            matchLength = userAgent.indexOf("Kindle/");
            if (matchLength >= 0) {
                matchLength += 7;
                char firstVersionChar;
                firstVersionChar = userAgent.charAt(matchLength);
                if (firstVersionChar >= '1' && firstVersionChar <= '3') {
                    return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength + 1);
                }
            }

            matchLength = userAgent.indexOf("PlayStation Vita");
            return matchLength >= 0 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength + 16 + 1) : null;
        }
    }

    /**
     * 执行恢复匹配.
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();

        for (Map.Entry<String, String> entry : DEVICE_BY_TOKEN.entrySet()) {
            if (normalizedUserAgent.contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        return GENERIC_AMAZON_KINDLE;
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "KindleMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "Kindle";
    }
}
