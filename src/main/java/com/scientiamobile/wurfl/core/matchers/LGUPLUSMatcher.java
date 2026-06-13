package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * LG U+（韩国运营商）品牌设备匹配器。
 * <p>通过检查 User-Agent 是否包含 "lgtelecom" 或 "LGUPLUS" 来识别 LG U+ 品牌设备。
 * 恢复匹配根据 User-Agent 中包含的操作系统和浏览器特征选择对应的通用设备 ID，
 * 支持 RexOS、Windows Mobile 和 Android 平台。</p>
 *
 * @deprecated 当前 WURFL 数据文件中已无 LGUPLUS 品牌设备（对应设备数为 0），
 * 该匹配器已不再参与实际设备匹配，保留仅用于兼容旧版 WURFL 数据。
 * 计划在后续主要版本中移除。
 */
@Deprecated
final class LGUPLUSMatcher extends MatcherBase {
    private static final String GENERIC_LGUPLUS = "generic_lguplus";
    private static final Map<String, String[]> DEVICE_BY_TOKENS;

    static {
        DEVICE_BY_TOKENS = new LinkedHashMap<>();
        DEVICE_BY_TOKENS.put("generic_lguplus_rexos_facebook_browser", new String[]{"Windows NT 5", "POLARIS"});
        DEVICE_BY_TOKENS.put("generic_lguplus_rexos_webviewer_browser", new String[]{"Windows NT 5"});
        DEVICE_BY_TOKENS.put("generic_lguplus_winmo_facebook_browser", new String[]{"Windows CE", "POLARIS"});
        DEVICE_BY_TOKENS.put("generic_lguplus_android_webkit_browser", new String[]{"Android", "AppleWebKit"});
    }

    public LGUPLUSMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.addAll(DEVICE_BY_TOKENS.keySet());
        requiredDeviceIds.add(GENERIC_LGUPLUS);
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(request.getCleanedDeviceUserAgent(), "lgtelecom", "LGUPLUS");
    }

    /**
     * 执行确定匹配.
     */
    @Override
    protected String applyConclusiveMatch(WURFLRequest request) {
        return null;
    }

    /**
     * 恢复匹配策略：遍历预定义的设备规则，根据 User-Agent 中是否包含特定的操作系统和浏览器特征
     * 返回对应的 LG U+ 通用设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedDeviceUserAgent = request.getNormalizedDeviceUserAgent();
        for (Map.Entry<String, String[]> entry : DEVICE_BY_TOKENS.entrySet()) {
            if (StringMatchUtils.containsAllOf(normalizedDeviceUserAgent, entry.getValue())) {
                return entry.getKey();
            }
        }

        return GENERIC_LGUPLUS;
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "LGUPLUSMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "LGUPLUS";
    }
}
