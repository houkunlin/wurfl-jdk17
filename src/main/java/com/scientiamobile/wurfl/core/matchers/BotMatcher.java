package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/*/**
 * 网络爬虫（Bot/Crawler）匹配器。
 * <p>通过调用 WURFLRequest#_internalIsBot() 判断请求是否来自已知网络爬虫。支持 Google 图片代理的特殊处理。</p>
 */

final class BotMatcher extends AbstractMatcher {
    private static final String GOOGLE_IMAGE_PROXY = "google_image_proxy";

    public BotMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * 返回所需验证的设备 ID 集合。
 * <p>包括 Google 图片代理和通用网络爬虫设备 ID。</p>
 *
 * @return 必需的设备 ID 集合
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GOOGLE_IMAGE_PROXY);
        requiredDeviceIds.add("generic_web_crawler");
        return requiredDeviceIds;
    }

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        return request._internalIsBot();
    }

    @Override
    /**
     * 爬虫匹配器的 RIS 匹配策略取决于 User-Agent 的开头：
     * <ul>
     *   <li>以 "Mozilla" 开头 → 取第一个右括号位置截断</li>
     *   <li>其他 → 取第一个斜杠位置截断</li>
     * </ul>
     *
     * @param normalizedUserAgent 规范化后的 User-Agent
     * @return RIS 匹配结果
     */

    protected String risMatch(String normalizedUserAgent) {
        int matchLength = normalizedUserAgent.startsWith("Mozilla")
                ? StringMatchUtils.firstCloseParenthesis(normalizedUserAgent)
                : StringMatchUtils.firstSlash(normalizedUserAgent);
        return matchLength != -1
                ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength)
                : StringMatchUtils.NULL_STRING;
    }

    @Override
    /**
     * 确定匹配策略：如果 User-Agent 包含 "GoogleImageProxy"，则直接返回对应的设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 匹配到的设备 ID
     */

    protected String applyConclusiveMatch(WURFLRequest request) {
        return request.getCleanedDeviceUserAgent().contains("GoogleImageProxy") ? GOOGLE_IMAGE_PROXY : super.applyConclusiveMatch(request);
    }

    @Override
    /**
     * 恢复匹配策略：统一返回通用网络爬虫设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 固定返回 {@code "generic_web_crawler"}
     */

    protected String applyRecoveryMatch(WURFLRequest request) {
        return "generic_web_crawler";
    }

    @Override
    /**
     * 获取匹配器名称。
     *
     * @return 固定返回 {@code "BotMatcher"}
     */

    public String getMatcherName() {
        return "BotMatcher";
    }

    @Override
    /**
     * 获取桶匹配器名称。
     *
     * @return 固定返回 {@code "Bot"}
     */

    public String getBucketMatcherName() {
        return "Bot";
    }
}
