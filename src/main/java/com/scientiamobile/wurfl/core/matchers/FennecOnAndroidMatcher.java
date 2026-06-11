package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Fennec（Firefox for Android）浏览器匹配器。
 * <p>Fennec 是 Mozilla 为 Android 平台开发的 Firefox 移动浏览器。
 * 通过检查 User-Agent 同时包含 "Android" 和 "Fennec" 或 "Firefox" 来识别。
 * 支持 Android 2.x 到 9.x 的手机、平板和桌面三种形态的版本映射。
 * RIS 匹配使用 Gecko 渲染引擎版本（rv:）后的点号位置截断。</p>
 */

final class FennecOnAndroidMatcher extends MatcherBase {
    private static final Pattern VERSION_PREFIX = Pattern.compile("^.+?\\(.+?rv:\\d+(\\.)");
    private static final String GENERIC_ANDROID_FENNEC_2 = "generic_android_ver2_0_fennec";
    private static final String GENERIC_ANDROID_FENNEC_2_TABLET = "generic_android_ver2_0_fennec_tablet";
    private static final String GENERIC_ANDROID_FENNEC_2_DESKTOP = "generic_android_ver2_0_fennec_desktop";

    public FennecOnAndroidMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    private Set<String> requiredDeviceIds = this.getRequiredDeviceIds();

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        if (this.requiredDeviceIds != null) {
            return this.requiredDeviceIds;
        } else {
            HashSet<String> requiredDeviceIds;
            requiredDeviceIds = new HashSet<>();
            requiredDeviceIds.add("generic");
            requiredDeviceIds.add(GENERIC_ANDROID_FENNEC_2);
            requiredDeviceIds.add(GENERIC_ANDROID_FENNEC_2_TABLET);
            requiredDeviceIds.add(GENERIC_ANDROID_FENNEC_2_DESKTOP);
            requiredDeviceIds.add("generic_android_ver4_fennec");
            requiredDeviceIds.add("generic_android_ver4_fennec_tablet");
            requiredDeviceIds.add("generic_android_ver4_fennec_desktop");
            requiredDeviceIds.add("generic_android_ver5_0_fennec");
            requiredDeviceIds.add("generic_android_ver5_0_fennec_tablet");
            requiredDeviceIds.add("generic_android_ver5_0_fennec_desktop");
            requiredDeviceIds.add("generic_android_ver6_0_fennec");
            requiredDeviceIds.add("generic_android_ver6_0_fennec_tablet");
            requiredDeviceIds.add("generic_android_ver6_0_fennec_desktop");
            requiredDeviceIds.add("generic_android_ver7_0_fennec");
            requiredDeviceIds.add("generic_android_ver7_0_fennec_tablet");
            requiredDeviceIds.add("generic_android_ver7_0_fennec_desktop");
            requiredDeviceIds.add("generic_android_ver8_0_fennec");
            requiredDeviceIds.add("generic_android_ver8_0_fennec_tablet");
            requiredDeviceIds.add("generic_android_ver8_0_fennec_desktop");
            requiredDeviceIds.add("generic_android_ver9_0_fennec");
            requiredDeviceIds.add("generic_android_ver9_0_fennec_tablet");
            requiredDeviceIds.add("generic_android_ver9_0_fennec_desktop");
            this.requiredDeviceIds = requiredDeviceIds;
            return requiredDeviceIds;
        }
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent.contains("Android") && StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Fennec", "Firefox");
    }

    /**
     * 执行 RIS 匹配.
     */
    @Override
    protected String risMatch(String normalizedUserAgent) {
        Matcher versionPrefixMatcher = VERSION_PREFIX.matcher(normalizedUserAgent);
        int matchLength;
        matchLength = versionPrefixMatcher.end();
        return versionPrefixMatcher.find() && matchLength < normalizedUserAgent.length()
                ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength)
                : null;
    }

    /**
     * 恢复匹配策略：根据 Android 版本号和浏览器类型（手机/平板/桌面）构造对应的 Fennec 通用设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        String deviceId = null;
        int androidMajorVersion = 0;
        String androidVersion;
        String normalizedUserAgent;
        normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        androidVersion = UserAgentUtils.getAndroidVersion(normalizedUserAgent, false);
        if (androidVersion != null) {
            String[] versionParts = androidVersion.split("\\.");
            androidMajorVersion = ArrayUtils.isNotEmpty(versionParts) ? Integer.parseInt(versionParts[0]) : 0;
            deviceId = "generic_android_ver" + androidMajorVersion + "_0_fennec";
        }

        if (androidMajorVersion < 5) {
            deviceId = "generic_android_ver4_fennec";
        }

        if (StringMatchUtils.containsAllOf(normalizedUserAgent, "Firefox", "Tablet")) {
            deviceId = deviceId + "_tablet";
        } else if (StringMatchUtils.containsAllOf(normalizedUserAgent, "Firefox", "Desktop")) {
            deviceId = deviceId + "_desktop";
        }

        return this.requiredDeviceIds.contains(deviceId) ? deviceId : "generic_android_ver4_fennec";
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "FennecOnAndroidMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "FennecOnAndroid";
    }


}
