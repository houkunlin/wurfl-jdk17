package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Nintendo（任天堂）游戏设备匹配器。
 * <p>通过检查 User-Agent 是否包含 Nintendo 来识别任天堂品牌的游戏设备，包括 Wii U、Wii、DSi、DS、3DS、New 3DS 和 Switch 等型号。</p>
 */

final class NintendoMatcher extends MatcherBase {
    public NintendoMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add("nintendo_wii_u_ver1");
        requiredDeviceIds.add("nintendo_wii_ver1");
        requiredDeviceIds.add("nintendo_dsi_ver1");
        requiredDeviceIds.add("nintendo_ds_ver1");
        requiredDeviceIds.add("nintendo_3ds_ver1");
        requiredDeviceIds.add("nintendo_new3ds_ver1");
        requiredDeviceIds.add("nintendo_switch_ver1");
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        if (request._internalIsDesktopBrowser()) {
            return false;
        } else if (cleanedDeviceUserAgent.contains("Nintendo")) {
            return true;
        } else {
            return cleanedDeviceUserAgent.startsWith("Mozilla/") && StringMatchUtils.containsAllOf(cleanedDeviceUserAgent, "Nitro", "Opera");
        }
    }
    /**
     * 确定匹配策略：根据 User-Agent 中包含的 Nintendo 设备型号关键字直接返回对应的设备 ID。
     * <p>依次检查 "New Nintendo 3DS"、"Nintendo 3DS"、"Nintendo WiiU"、"Nintendo Wii"、
     * "Nintendo DSi"、"Nintendo Switch"，最后检查 Nitro + Opera 组合（DS 浏览器）。</p>
     *
     * @param request WURFL 请求对象
     * @return 匹配到的设备 ID，无法确定时返回 "nintendo_wii_ver1"
     */
    @Override
    protected String applyConclusiveMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        if (normalizedUserAgent.contains("New Nintendo 3DS")) {
            return "nintendo_new3ds_ver1";
        } else if (normalizedUserAgent.contains("Nintendo 3DS")) {
            return "nintendo_3ds_ver1";
        } else if (normalizedUserAgent.contains("Nintendo WiiU")) {
            return "nintendo_wii_u_ver1";
        } else if (normalizedUserAgent.contains("Nintendo Wii")) {
            return "nintendo_wii_ver1";
        } else if (normalizedUserAgent.contains("Nintendo DSi")) {
            return "nintendo_dsi_ver1";
        } else if (normalizedUserAgent.contains("Nintendo Switch")) {
            return "nintendo_switch_ver1";
        } else {
            return normalizedUserAgent.startsWith("Mozilla/") && StringMatchUtils.containsAllOf(normalizedUserAgent, "Nitro", "Opera") ? "nintendo_ds_ver1" : "nintendo_wii_ver1";
        }
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "NintendoMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "Nintendo";
    }
}
