package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

import java.util.HashSet;
import java.util.Set;

/**
 * 孤儿设备 ID 匹配器，用于验证 WURFL 模型中是否包含所有必需的通用设备 ID。
 * <p>该匹配器的 {@link #canHandle} 始终返回 {@code false}，因此不会实际参与匹配流程。
 * 它的存在价值在于：通过构造函数中传入 {@link WURFLModel}，触发
 * {@link AbstractMatcher#validateRequiredDeviceIds} 校验，
 * 确保模型中包含 {@link AbstractMatcher} 的 {@code CATCH_ALL_FALLBACKS} 列表
 * 和 {@link #getRequiredDeviceIds()} 中定义的所有回退设备 ID。</p>
 * <p>如果缺少这些设备 ID，将在引擎启动时立即抛出异常，实现快速失败。</p>
 */

public class OrphanDeviceIdMatcher extends MatcherBase {
    public OrphanDeviceIdMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        return false;
    }

    @Override
/**
 * 返回所需验证的设备 ID 集合.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add("opwv_v6_generic");
        requiredDeviceIds.add("opwv_v7_generic");
        requiredDeviceIds.add("opwv_v72_generic");
        requiredDeviceIds.add("upgui_generic");
        requiredDeviceIds.add("uptext_generic");
        requiredDeviceIds.add("generic_netfront_ver3");
        requiredDeviceIds.add("generic_netfront_ver3_1");
        requiredDeviceIds.add("generic_netfront_ver3_2");
        requiredDeviceIds.add("generic_netfront_ver3_3");
        requiredDeviceIds.add("generic_netfront_ver3_4");
        requiredDeviceIds.add("generic_netfront_ver3_5");
        requiredDeviceIds.add("generic_netfront_ver4_0");
        return requiredDeviceIds;
    }
}
