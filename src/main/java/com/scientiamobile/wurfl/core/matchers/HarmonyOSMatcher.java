package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HarmonyOS 设备匹配器，处理纯 HarmonyOS 格式 User-Agent（不含 Android 关键字）。
 * <p>华为 HarmonyOS 设备有两种 UA 格式：
 * <ul>
 *   <li>混合格式：{@code Android 10; HarmonyOS; ...} （WURFL 数据库已有条目）</li>
 *   <li>纯格式：{@code HarmonyOS; ...} （不含 Android，本匹配器处理）</li>
 * </ul>
 * recovery match 采用动态查找策略：从 UA 中提取华为设备型号（如 "MRX-W09"），
 * 按优先级生成候选 device ID 并在 WURFL 模型全量设备集合中验证存在性。
 * 新设备随 WURFL 数据更新自动适配，无需修改 Java 代码。</p>
 */
final class HarmonyOSMatcher extends MatcherBase {

    private static final String GENERIC_ANDROID = "generic_android";

    /**
     * 匹配 "HUAWEI <型号>" 或 "HUAWEI-<型号>"
     */
    private static final Pattern HUAWEI_MODEL_PATTERN = Pattern.compile("HUAWEI[- ]([A-Za-z0-9-]+)");

    /**
     * WURFL 模型全量设备 ID 集合，用于运行时动态查找
     */
    private final Set<String> allDeviceIds;

    public HarmonyOSMatcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
        super(userAgentNormalizer, wurflModel);
        this.allDeviceIds = wurflModel != null ? wurflModel.getAllDevicesId() : Collections.emptySet();
    }

    public HarmonyOSMatcher(WURFLModel wurflModel) {
        super(wurflModel);
        this.allDeviceIds = wurflModel != null ? wurflModel.getAllDevicesId() : Collections.emptySet();
    }

    @Override
    public boolean canHandle(WURFLRequest request) {
        String ua = request.getCleanedDeviceUserAgent();
        return ua != null && ua.contains("HarmonyOS");
    }

    @Override
    protected String risMatch(String userAgent) {
        int closeParenIndex = StringMatchUtils.firstCloseParenthesis(userAgent);
        return closeParenIndex != -1
                ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, closeParenIndex)
                : StringMatchUtils.NULL_STRING;
    }

    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        String ua = request.getNormalizedDeviceUserAgent();
        if (StringUtils.isEmpty(ua)) {
            return GENERIC_ANDROID;
        }

        // 提取 UA 中的华为设备型号（如 "MRX-W09" 从 "HUAWEI MRX-W09"）
        Matcher matcher = HUAWEI_MODEL_PATTERN.matcher(ua);
        if (!matcher.find()) {
            return GENERIC_ANDROID;
        }
        String rawModel = matcher.group(1);
        if (StringUtils.isEmpty(rawModel)) {
            return GENERIC_ANDROID;
        }

        // 动态查找匹配设备
        String deviceId = findDeviceByModel(rawModel);
        return deviceId != null ? deviceId : GENERIC_ANDROID;
    }

    /**
     * 从型号字符串动态查找 WURFL 中匹配的设备 ID。
     * <p>通过提取型号前缀 + 后缀，按优先级生成多种候选 device ID 模式，
     * 在 WURFL 模型全量设备集合中验证存在性。</p>
     *
     * @param rawModel 原始型号字符串，如 "MRX-W09"、"LIO-AN00"
     * @return 匹配的设备 ID，未找到返回 null
     */
    private String findDeviceByModel(String rawModel) {
        String[] parts = rawModel.split("-", 2);
        String prefix = parts[0].toLowerCase(Locale.ENGLISH);
        String suffix = parts.length > 1 ? parts[1].toLowerCase(Locale.ENGLISH) : "";

        // 1. 精确型号匹配：huawei_<前缀>_<后缀>_ver1（如 huawei_mrx_w09_ver1）
        if (!suffix.isEmpty()) {
            String candidate = "huawei_" + prefix + "_" + suffix + "_ver1";
            if (allDeviceIds.contains(candidate)) {
                return candidate;
            }
        }

        // 2. 纯前缀匹配：huawei_<前缀>_ver1（如 huawei_mrx_ver1）
        String prefixOnly = "huawei_" + prefix + "_ver1";
        if (allDeviceIds.contains(prefixOnly)) {
            return prefixOnly;
        }

        // 3. 国内常见后缀截断保留前4字符（如 an00、al00、tl00、w09、nx9）
        if (suffix.length() >= 4) {
            String suffixBase = suffix.substring(0, 4);
            String candidate = "huawei_" + prefix + "_" + suffixBase + "_ver1";
            if (allDeviceIds.contains(candidate)) {
                return candidate;
            }
        }

        // 4. 前2字符 + "00"（如 an00、al00、tl00、nx9 → nx00）
        if (suffix.length() >= 2) {
            String suffixShort = suffix.substring(0, 2) + "00";
            String candidate = "huawei_" + prefix + "_" + suffixShort + "_ver1";
            if (allDeviceIds.contains(candidate)) {
                return candidate;
            }
        }

        // 5. 逐字符缩短后缀尝试（处理 UA 型号与 WURFL 设备 ID 后缀不完全一致的情况，
        //    如 LIO-AN00 → WURFL 中只有 lio_an00p、lio_n29）
        for (int end = suffix.length() - 1; end >= 1; end--) {
            String shortened = suffix.substring(0, end);
            String candidate = "huawei_" + prefix + "_" + shortened + "_ver1";
            if (allDeviceIds.contains(candidate)) {
                return candidate;
            }
        }

        // 6. 扫描所有设备 ID 查找前缀匹配（兜底，仅对 huawei_<前缀>_ 前缀查找）
        //    适用于前述模式均不匹配但 WURFL 中存在相近设备的情况
        for (String deviceId : allDeviceIds) {
            if (deviceId.startsWith("huawei_" + prefix + "_")) {
                return deviceId;
            }
        }

        return null;
    }

    @Override
    protected Set<String> getRequiredDeviceIds() {
        return Set.of(GENERIC_ANDROID);
    }

    @Override
    public String getMatcherName() {
        return "HarmonyOSMatcher";
    }

    @Override
    public String getBucketMatcherName() {
        return "HarmonyOS";
    }
}
