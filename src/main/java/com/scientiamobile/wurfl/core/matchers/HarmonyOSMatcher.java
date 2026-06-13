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
 * 匹配策略采用双层设计：
 * <ol>
 *   <li>优先使用内置预设映射表（从 WURFL XML 中提取的已知设备型号 → device ID 映射）进行精确匹配</li>
 *   <li>若预设映射未命中，再退回到动态模糊查找策略</li>
 * </ol>
 * </p>
 */
final class HarmonyOSMatcher extends MatcherBase {

    private static final String GENERIC_ANDROID = "generic_android";

    /**
     * 匹配 "HUAWEI <型号>" 或 "HUAWEI-<型号>"
     */
    private static final Pattern HUAWEI_MODEL_PATTERN = Pattern.compile("HUAWEI[- ]([A-Za-z0-9-]+)");

    /**
     * 内置预设映射表：设备型号（小写，横线替换为下划线）→ WURFL device ID。
     * 数据来源于 WURFL XML 中 device_os 为 HarmonyOS 的实际设备条目，
     * 确保对已知华为设备的匹配准确性。
     */
    private static final Map<String, String> MODEL_TO_DEVICE_ID = buildModelMap();

    private static Map<String, String> buildModelMap() {
        Map<String, String> map = new LinkedHashMap<>();

        // ========== Mate 系列 ==========
        map.put("aln_al00", "huawei_aln_al00_ver1");        // Mate 60 Pro
        map.put("aln_al10", "huawei_aln_al10_ver1");        // Mate 60 Pro+
        map.put("alt_al00", "huawei_alt_al00_ver1");        // Mate X3
        map.put("alt_al10", "huawei_alt_al10_ver1");        // Mate X5
        map.put("bra_al00", "huawei_bra_al00_ver1");        // Mate 60
        map.put("cet_al00", "huawei_cet_al00_ver1");        // Mate 50
        map.put("cet_al60", "huawei_cet_al60_ver1");        // Mate 50E
        map.put("dco_al00", "huawei_dco_al00_ver1");        // Mate 50 RS Porsche Design
        map.put("dco_lx9", "huawei_dco_lx9_ver1");          // Mate 50 Pro
        map.put("tet_al00", "huawei_tet_al00_ver1");        // Mate X2 4G
        map.put("tet_an00", "huawei_tet_an00_ver1");        // Mate X2
        map.put("pal_lx9", "huawei_pal_lx9_ver1");          // Mate Xs 2
        map.put("noh_an50", "huawei_noh_an50_ver1");        // Mate 40 E Pro 5G
        map.put("lna_al00", "huawei_lna_al00_ver1");        // P60
        map.put("mna_lx9", "huawei_mna_lx9_ver1");          // P60 Pro
        map.put("grl_lx9", "huawei_grl_lx9_ver1");          // Mate XT Ultimate

        // ========== P / Pura 系列 ==========
        map.put("abr_al00", "huawei_abr_al00_ver1");        // P50
        map.put("abr_al60", "huawei_abr_al60_ver1");        // P50E
        map.put("abr_lx9", "huawei_abr_lx9_ver1");          // P50
        map.put("ady_al00", "huawei_ady_al00_ver1");        // Pura 70
        map.put("bal_al00", "huawei_bal_al00_ver1");        // P50 Pocket
        map.put("bal_al60", "huawei_bal_al60_ver1");        // Pocket S
        map.put("lem_al00", "huawei_lem_al00_ver1");        // Pocket 2
        map.put("hbp_al00", "huawei_hbp_al00_ver1");        // Pura 70 Ultra
        map.put("hbn_lx9", "huawei_hbn_lx9_ver1");          // Pura 70 Pro
        map.put("hbn_al80", "huawei_hbn_al80_ver1");        // Pura 70 Pro+
        map.put("hed_lx9", "huawei_hed_lx9_ver1");          // Pura 80
        map.put("jad_lx9", "huawei_jad_lx9_ver1");          // P50 Pro

        // ========== nova 系列 ==========
        map.put("ada_al00", "huawei_ada_al00_ver1");        // nova 12 Pro
        map.put("ada_al00u", "huawei_ada_al00u_ver1");      // nova 12 Ultra
        map.put("blk_al00", "huawei_blk_al00_ver1");        // nova 12
        map.put("blk_lx9", "huawei_blk_lx9_ver1");          // nova 13
        map.put("bne_lx1", "huawei_bne_lx1_ver1");          // nova 10 SE
        map.put("ctr_lx1", "huawei_ctr_lx1_ver1");          // nova Y90
        map.put("ctr_l91", "huawei_ctr_l91_ver1");          // nova 13i
        map.put("eve_lx9", "huawei_eve_lx9_ver1");          // nova Y61
        map.put("fin_al60", "huawei_fin_al60_ver1");        // Nova 12 Lite
        map.put("fio_bd00", "hi_fio_bd00_ver1");            // nova 9 SE 5G
        map.put("foa_lx9", "huawei_foa_lx9_ver1");          // nova 11
        map.put("gfy_lx1", "huawei_gfy_lx1_ver1");          // Nova Y72S
        map.put("gla_lx1", "huawei_gla_lx1_ver1");          // nova 10 Pro
        map.put("goa_al80", "huawei_goa_al80_ver1");        // nova 11 Pro
        map.put("jln_lx1", "huawei_jln_lx1_ver1");          // nova 9 SE
        map.put("mao_lx9", "huawei_mao_lx9_ver1");          // nova 11i
        map.put("mga_lx9n", "huawei_mga_lx9n_ver1");        // nova Y70 Plus
        map.put("mis_lx9", "huawei_mis_lx9_ver1");          // nova 13 Pro
        map.put("nam_al00", "huawei_nam_al00_ver1");        // nova 9 4G
        map.put("nam_lx9", "huawei_nam_lx9_ver1");          // nova 9
        map.put("nco_lx3", "huawei_nco_lx3_ver1");          // nova 10
        map.put("psd_al00", "huawei_psd_al00_ver1");        // nova Flip
        map.put("rte_al00", "huawei_rte_al00_ver1");        // nova 9 Pro
        map.put("icl_lc9", "huawei_icl_lx9_ver1");          // Mate X6

        // ========== 畅享（Enjoy）系列 ==========
        map.put("bre_al80", "huawei_bre_al80_ver1");        // Enjoy 70X
        map.put("ctr_al00", "huawei_ctr_al00_ver1");        // Enjoy 50 Pro
        map.put("ctr_al20", "huawei_ctr_al20_ver1");        // Enjoy 70 Pro
        map.put("fgd_al00", "huawei_fgd_al00_ver1");        // Enjoy 70
        map.put("gfy_al00", "huawei_gfy_al00_ver1");        // Enjoy 70S
        map.put("gar_an60", "wiko_gar_an60_ver1");          // Hi Enjoy 60s
        map.put("mao_al00", "huawei_mao_al00_ver1");        // Enjoy 60 Pro
        map.put("stg_al00", "huawei_stg_al00_ver1");        // Enjoy 60X
        map.put("stg_lx1", "huawei_stg_lx1_ver1");          // Nova Y91
        map.put("tyh622m", "huawei_tyh622m_ver1");          // Enjoy 50

        // ========== 平板系列 ==========
        map.put("ags5_l09", "huawei_ags5_l09_ver1");        // MatePad 10.4 SE
        map.put("ags5_w09", "huawei_ags5_w09_ver1");        // MATE PAD 10.4 SE WIFI
        map.put("ags6_w09", "huawei_ags6_w09_ver1");        // MatePad SE 11
        map.put("bah_w09", "huawei_bah4_w09_ver1");         // MatePad 10.4 (2022)
        map.put("btk_w09", "huawei_btk_w09_ver1");          // MatePad 11.5
        map.put("dby_w09", "huawei_dby_w09_ver1");          // MatePad 11 (2021)
        map.put("dby2_w00", "huawei_dby2_w00_ver1");        // MatePad Air
        map.put("got_w29", "huawei_got_w29_ver1");          // MatePad Pro 11
        map.put("mrr_w29", "huawei_mrr_w29_ver1");          // MatePad Pro 10.8 (2021)
        map.put("mrx_w09", "huawei_mrx_w09_ver1");          // MatePad Pro

        // ========== 历史 Mate/P 设备（旧版预设） ==========
        map.put("lio_al00", "huawei_lio_al00_ver1");        // Mate 30 Pro
        map.put("lio_an00", "huawei_lio_al00_ver1");        // Mate 30 Pro（LIO-AN00 映射到 AL00）
        map.put("tas_al00", "huawei_tas_al00_ver1");        // Mate 30
        map.put("oce_an10", "huawei_oce_an10_ver1");        // Mate 40
        map.put("ana_nx9", "huawei_ana_nx9_ver1");          // P40
        map.put("els_nx9", "huawei_els_nx9_ver1");          // P40 Pro

        return Collections.unmodifiableMap(map);
    }

    /**
     * WURFL 模型全量设备 ID 集合，用于动态模糊查找兜底
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

        // 1. 优先使用内置预设映射表进行精确匹配
        String normalizedModel = rawModel.toLowerCase(Locale.ENGLISH).replace("-", "_");
        String presetId = MODEL_TO_DEVICE_ID.get(normalizedModel);
        if (presetId != null) {
            return presetId;
        }

        // 1.5 尝试前缀匹配（例如 "LIO-AN00" → 匹配 "lio_" 开头的预设键）
        String prefixKey = normalizedModel.split("_")[0] + "_";
        for (Map.Entry<String, String> entry : MODEL_TO_DEVICE_ID.entrySet()) {
            if (entry.getKey().startsWith(prefixKey)) {
                return entry.getValue();
            }
        }

        // 2. 若预设映射未命中，退回到动态模糊查找策略
        String dynamicId = findDeviceByModel(rawModel);
        return dynamicId != null ? dynamicId : GENERIC_ANDROID;
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
