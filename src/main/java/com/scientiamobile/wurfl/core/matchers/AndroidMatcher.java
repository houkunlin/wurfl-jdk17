package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Android 操作系统设备匹配器。
 * <p>通过检查 User-Agent 是否包含 "Android" 或 "android"（排除 "like Android" 和 "Symbian"）
 * 来识别 Android 设备。支持手机和平板两种形态的恢复匹配，根据 Android 版本号返回对应的通用设备 ID。</p>
 * <p>RIS 匹配策略包括：</p>
 * <ul>
 *   <li>查找 "---" 分隔符</li>
 *   <li>查找 Android 设备型号（Model）</li>
 *   <li>在 " Build/" 或 " AppleWebKit" 位置截断</li>
 * </ul>
 */

final class AndroidMatcher extends AbstractMatcher {
    private static final String GENERIC_ANDROID = "generic_android";
    private static final String GENERIC_ANDROID_VER2_2 = "generic_android_ver2_2";
    private static final String GENERIC_ANDROID_VER1_5_TABLET = "generic_android_ver1_5_tablet";
    private static final Set<String> SUPPORTED_MOBILE_DEVICE_IDS = new HashSet<>();
    private static final Set<String> SUPPORTED_TABLET_DEVICE_IDS = new HashSet<>();

    static {
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver1_5");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver1_6");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver2");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver2_1");
        SUPPORTED_MOBILE_DEVICE_IDS.add(GENERIC_ANDROID_VER2_2);
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver2_3");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver3_0");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver3_1");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver3_2");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver3_3");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver4");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver4_1");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver4_2");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver4_3");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver4_4");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver4_5");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver5_0");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver5_1");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver5_2");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver5_3");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver6_0");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver6_1");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver7_0");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver7_1");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver7_2");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver8_0");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver8_1");
        SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver9_0");
        SUPPORTED_TABLET_DEVICE_IDS.add(GENERIC_ANDROID_VER1_5_TABLET);
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver1_6_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver2_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver2_1_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver2_2_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver2_3_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver4_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver4_1_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver4_2_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver4_3_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver4_4_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver4_5_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver5_0_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver5_1_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver5_2_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver5_3_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver6_0_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver6_1_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver7_0_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver7_1_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver7_2_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver8_0_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver8_1_tablet");
        SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver9_0_tablet");
    }

    public AndroidMatcher(UserAgentNormalizer normalizer, WURFLModel wurflModel) {
        super(normalizer, wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合。
     * <p>包括所有支持的 Android 手机和平板通用设备 ID。</p>
     *
     * @return 必需的设备 ID 集合
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.addAll(SUPPORTED_MOBILE_DEVICE_IDS);
        requiredDeviceIds.addAll(SUPPORTED_TABLET_DEVICE_IDS);
        requiredDeviceIds.add(GENERIC_ANDROID);
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "like Android", "Symbian")
                && StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Android", "android");
    }

    /**
     * 执行 RIS 匹配.
     */
    @Override
    protected String risMatch(String normalizedUserAgent) {
        int matchLength;
        matchLength = normalizedUserAgent.indexOf("---");
        if (matchLength >= 0) {
            matchLength += 3;
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
        } else if (!StringMatchUtils.startsWithAnyOf(normalizedUserAgent, "Mozilla", "Dalvik")) {
            return null;
        } else {
            String androidModel;
            androidModel = UserAgentUtils.getAndroidModel(normalizedUserAgent);
            if (androidModel != null && !androidModel.isEmpty()) {
                matchLength = Math.min(
                        StringMatchUtils.indexOfOrLength(normalizedUserAgent, " Build/"),
                        StringMatchUtils.indexOfOrLength(normalizedUserAgent, " AppleWebKit")
                );
                return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
            } else {
                matchLength = normalizedUserAgent.length();
                return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
            }
        }
    }

    /**
     * 恢复匹配策略：根据 User-Agent 中的 Android 版本号构造通用设备 ID。
     * <p>如果 User-Agent 包含 "Safari" 但不含 "Mobile" 且不是 Android 3.x，则识别为平板，
     * 返回对应的平板通用设备 ID，否则返回手机通用设备 ID。</p>
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedDeviceUserAgent = request.getNormalizedDeviceUserAgent();
        String androidVersion = UserAgentUtils.getAndroidVersion(normalizedDeviceUserAgent, true).replaceAll("\\.", "_");
        String candidateDeviceId = "generic_android_ver" + androidVersion;
        if (candidateDeviceId.endsWith("2_0") || candidateDeviceId.endsWith("4_0")) {
            candidateDeviceId = candidateDeviceId.substring(0, candidateDeviceId.length() - 2);
        }

        if (!candidateDeviceId.startsWith("generic_android_ver3_") && !normalizedDeviceUserAgent.contains("Mobile") && normalizedDeviceUserAgent.contains("Safari")) {
            candidateDeviceId = candidateDeviceId.concat("_tablet");
            return SUPPORTED_TABLET_DEVICE_IDS.contains(candidateDeviceId) ? candidateDeviceId : GENERIC_ANDROID_VER1_5_TABLET;
        } else {
            return SUPPORTED_MOBILE_DEVICE_IDS.contains(candidateDeviceId) ? candidateDeviceId : GENERIC_ANDROID;
        }
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "AndroidMatcher";
    }

    /**
     * 获取桶匹配器名称。
     *
     * @return 固定返回 {@code "Android"}
     */
    @Override
    public String getBucketMatcherName() {
        return "Android";
    }
}
