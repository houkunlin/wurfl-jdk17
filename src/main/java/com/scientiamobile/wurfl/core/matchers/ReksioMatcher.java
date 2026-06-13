package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

import java.util.HashSet;
import java.util.Set;

/**
 * Reksio 品牌设备匹配器。
 * <p>通过检查 User-Agent 是否以 Reksio 开头来识别 Reksio 品牌的移动设备。确定匹配直接返回 generic_reksio。</p>
 *
 * @deprecated 当前 WURFL 数据文件中已无 Reksio 品牌设备（对应设备数为 0），
 * 该匹配器已不再参与实际设备匹配，保留仅用于兼容旧版 WURFL 数据。
 * 计划在后续主要版本中移除。
 */
@Deprecated
final class ReksioMatcher extends MatcherBase {
    private static final String REKSIO_DEVICE_ID = "generic_reksio";

    public ReksioMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(REKSIO_DEVICE_ID);
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("Reksio");
    }

    /**
     * Reksio 匹配器直接返回固定的通用设备 ID，不执行实际的 User-Agent 匹配。
     *
     * @param request WURFL 请求对象
     * @return 固定的 {@code "generic_reksio"}
     */
    @Override
    protected String applyConclusiveMatch(WURFLRequest request) {
        return REKSIO_DEVICE_ID;
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "ReksioMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "Reksio";
    }
}
