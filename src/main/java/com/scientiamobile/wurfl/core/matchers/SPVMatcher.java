package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

/**
 * SPV（Orange 旗下品牌）设备匹配器。
 * <p>通过检查 User-Agent 是否包含 SPV 来识别 SPV 品牌的移动设备。</p>
 *
 * @deprecated 当前 WURFL 数据文件中已无 SPV 品牌设备（对应设备数为 0），
 * 该匹配器已不再参与实际设备匹配，保留仅用于兼容旧版 WURFL 数据。
 * 计划在后续主要版本中移除。
 */
@Deprecated
final class SPVMatcher extends MatcherBase {
    public SPVMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().contains("SPV");
    }

    /**
     * 执行 RIS 匹配：找到 "SPV" 关键字后的分号位置作为截断点。
     *
     * @param userAgent 要匹配的 User-Agent 字符串
     * @return RIS 匹配结果
     */
    @Override
    protected String risMatch(String userAgent) {
        int matchLength = StringMatchUtils.indexOfOrLength(userAgent, ";", StringMatchUtils.indexOfOrLength(userAgent, "SPV"));
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "SPVMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "SPV";
    }
}
