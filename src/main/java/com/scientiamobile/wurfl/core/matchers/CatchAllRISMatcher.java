package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.List;

/**
 * Catch-All RIS 匹配器，作为所有非 Mozilla 系 User-Agent 的最终兜底。
 * <p>该匹配器可以处理任何请求（{@link #canHandle} 始终返回 {@code true}）。
 * 根据 User-Agent 的开头不同选择不同的截断策略：</p>
 * <ul>
 *   <li>以 "CFNetwork" 开头 → 以第一个空格位置截断</li>
 *   <li>其他 → 以第一个斜杠位置截断</li>
 * </ul>
 */

final class CatchAllRISMatcher extends AbstractMatcher {
    @Override
    public boolean canHandle(WURFLRequest request) {
        return true;
    }

    /**
     * 执行 RIS 匹配.
     */
    @Override
    protected String risMatch(String normalizedUserAgent) {
        List<String> userAgents = this.getFilter().getIndex().getUserAgents();
        if (normalizedUserAgent != null && normalizedUserAgent.startsWith("CFNetwork")) {
            int matchLength = StringMatchUtils.firstSpace(normalizedUserAgent);
            if (matchLength != -1) {
                return StringMatchUtils.risMatch(userAgents, normalizedUserAgent, matchLength);
            }
        } else {
            int matchLength = StringMatchUtils.firstSlash(normalizedUserAgent);
            if (matchLength != -1) {
                return StringMatchUtils.risMatch(userAgents, normalizedUserAgent, matchLength);
            }
        }

        return StringMatchUtils.NULL_STRING;
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "CatchAllRISMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "CatchAllRis";
    }
}
