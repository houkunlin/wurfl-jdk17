package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HarmonyOS 设备匹配器，处理纯 HarmonyOS 格式 User-Agent（不含 Android 关键字）。
 * <p>华为 HarmonyOS 设备有两种 UA 格式：
 * <ul>
 *   <li>混合格式：{@code Android 10; HarmonyOS; ...} （WURFL 数据库已有条目）</li>
 *   <li>纯格式：{@code HarmonyOS; ...} （不含 Android，本匹配器处理）</li>
 * </ul>
 * 通过提取 UA 中的华为设备型号，映射到 WURFL 中已有的对应设备条目。</p>
 */
final class HarmonyOSMatcher extends MatcherBase {

    private static final String GENERIC_ANDROID = "generic_android";

    /**
     * 匹配 "HUAWEI <型号>" 或 "HUAWEI-<型号>"
     */
    private static final Pattern HUAWEI_MODEL_PATTERN = Pattern.compile("HUAWEI[- ]([A-Za-z0-9-]+)");

    /**
     * 支持的设备 ID 集合
     */
    private static final Set<String> SUPPORTED_DEVICE_IDS = new HashSet<>();

    /**
     * 华为机型前缀到设备 ID 的映射
     */
    private static final Map<String, String> MODEL_PREFIX_MAP = new HashMap<>();

    static {
        // 基础兜底
        SUPPORTED_DEVICE_IDS.add(GENERIC_ANDROID);

        // 注册已知华为 HarmonyOS 设备
        addSupportedDevice("huawei_nam_al00_ver1");    // nova 9 4G
        addSupportedDevice("huawei_nam_al00_ver1_suban120"); // nova 9 4G A12
        addSupportedDevice("huawei_lio_n29_ver1");      // Mate 30 Pro 5G
        addSupportedDevice("huawei_lio_an00p_ver1");    // Mate 30 Pro (LIO-AN00P)
        addSupportedDevice("huawei_lio_an00m_ver1");    // Mate 30 Pro (LIO-AN00m)
        addSupportedDevice("huawei_lio_al00_ver1");     // Mate 30 Pro (LIO-AL00)
        addSupportedDevice("huawei_tas_al00_ver1");     // Mate 30 Pro 4G (TAS-AL00)
        addSupportedDevice("huawei_tas_an00_ver1");     // Mate 30 Pro (TAS-AN00)
        addSupportedDevice("huawei_wgr_w19_ver1");      // MatePad Pro 10.8
        addSupportedDevice("huawei_btk_w09_ver1");      // MatePad 11
        addSupportedDevice("huawei_abr_al00_ver1");     // P40 Pro
        addSupportedDevice("huawei_abr_al60_ver1");     // P40 Pro+
        addSupportedDevice("huawei_abr_lx9_ver1");      // P40 Pro
        addSupportedDevice("huawei_ana_nx9_ver1");      // P40
        addSupportedDevice("huawei_els_nx9_ver1");      // P40 Lite
        addSupportedDevice("huawei_jef_an00_ver1");     // Mate 40 Pro
        addSupportedDevice("huawei_jef_nx9_ver1");      // Mate 40 Pro
        addSupportedDevice("huawei_noh_an50_ver1");     // Mate 40 Pro+
        addSupportedDevice("huawei_noh_nx9_ver1");      // Mate 40 Pro+
        addSupportedDevice("huawei_oce_an10_ver1");     // P50 Pro
        addSupportedDevice("huawei_oce_an50_ver1");     // P50 Pro
        addSupportedDevice("huawei_brq_an00_ver1");     // P50
        addSupportedDevice("huawei_cdy_an00_ver1");     // Mate 40E
        addSupportedDevice("huawei_wlz_an00_ver1");     // Mate X2
        addSupportedDevice("huawei_wlz_al10_ver1");     // Mate X2
        addSupportedDevice("huawei_mrx_w09_ver1");      // MatePad Pro

        // 机型前缀 → 设备 ID 映射
        MODEL_PREFIX_MAP.put("LIO", "huawei_lio_n29_ver1");
        MODEL_PREFIX_MAP.put("TAS", "huawei_tas_al00_ver1");
        MODEL_PREFIX_MAP.put("NAM", "huawei_nam_al00_ver1");
        MODEL_PREFIX_MAP.put("WGR", "huawei_wgr_w19_ver1");
        MODEL_PREFIX_MAP.put("BTK", "huawei_btk_w09_ver1");
        MODEL_PREFIX_MAP.put("MRX", "huawei_mrx_w09_ver1");
        MODEL_PREFIX_MAP.put("ABR", "huawei_abr_al00_ver1");
        MODEL_PREFIX_MAP.put("ANA", "huawei_ana_nx9_ver1");
        MODEL_PREFIX_MAP.put("ELS", "huawei_els_nx9_ver1");
        MODEL_PREFIX_MAP.put("JEF", "huawei_jef_an00_ver1");
        MODEL_PREFIX_MAP.put("NOH", "huawei_noh_an50_ver1");
        MODEL_PREFIX_MAP.put("OCE", "huawei_oce_an10_ver1");
        MODEL_PREFIX_MAP.put("BRQ", "huawei_brq_an00_ver1");
        MODEL_PREFIX_MAP.put("CDY", "huawei_cdy_an00_ver1");
        MODEL_PREFIX_MAP.put("WLZ", "huawei_wlz_an00_ver1");
    }

    private static void addSupportedDevice(String deviceId) {
        SUPPORTED_DEVICE_IDS.add(deviceId);
    }

    public HarmonyOSMatcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
        super(userAgentNormalizer, wurflModel);
    }

    public HarmonyOSMatcher(WURFLModel wurflModel) {
        super(wurflModel);
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

        // 尝试通过型号匹配已知华为设备
        String deviceId = resolveByModel(ua);
        if (deviceId != null) {
            return deviceId;
        }

        return GENERIC_ANDROID;
    }

    /**
     * 从 UA 中提取华为设备型号，尝试匹配已知设备。
     */
    private static String resolveByModel(String ua) {
        Matcher matcher = HUAWEI_MODEL_PATTERN.matcher(ua);
        if (!matcher.find()) {
            return null;
        }
        String rawModel = matcher.group(1);
        if (StringUtils.isEmpty(rawModel)) {
            return null;
        }

        // 提取前缀（横线之前的部分，如 "LIO" 从 "LIO-AN00"）
        String prefix = rawModel.split("-")[0].trim();
        if (StringUtils.isEmpty(prefix)) {
            return null;
        }

        // 1. 优先通过前缀查找
        String mapped = MODEL_PREFIX_MAP.get(prefix.toUpperCase(Locale.ENGLISH));
        if (mapped != null) {
            return mapped;
        }

        // 2. 尝试直接构造 device ID: lower_case_model_ver1
        String normalizedModel = rawModel.replaceAll("[^A-Za-z0-9]", "_").toLowerCase(Locale.ENGLISH);
        String candidate = "huawei_" + normalizedModel + "_ver1";
        if (SUPPORTED_DEVICE_IDS.contains(candidate)) {
            return candidate;
        }

        // 3. 尝试去掉末尾字母和数字
        String baseCandidate = "huawei_" + normalizedModel.replaceAll("_[a-z0-9]+$", "") + "_ver1";
        if (SUPPORTED_DEVICE_IDS.contains(baseCandidate)) {
            return baseCandidate;
        }

        return null;
    }

    @Override
    protected Set<String> getRequiredDeviceIds() {
        return new HashSet<>(SUPPORTED_DEVICE_IDS);
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
