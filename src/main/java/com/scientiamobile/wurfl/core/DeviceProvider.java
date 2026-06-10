package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.matchers.MatchType;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * 设备提供者接口，定义内部设备创建和最终设备实例构建的策略。
 * <p>实现类负责从 WURFL 数据模型中获取设备数据，创建内部设备表示，
 * 并将其包装为带有匹配元数据的最终设备实例。</p>
 */

public interface DeviceProvider {
    /**
     * 根据设备 ID 从模型中获取内部设备实例。
     *
     * @param deviceId 设备 ID
     * @return 内部设备实例
     */
    InternalDevice getInternalDevice(String deviceId);

    /**
     * 使用 User-Agent 字符串构建最终设备实例。
     *
     * @param device            内部设备实例
     * @param userAgent         User-Agent 字符串
     * @param matchType         匹配类型
     * @param matcherName       匹配器名称
     * @param bucketMatcherName 桶匹配器名称
     * @return 最终设备实例
     */
    Device buildDevice(InternalDevice device, String userAgent, MatchType matchType, String matcherName, String bucketMatcherName);

    /**
     * 使用 WURFL 请求对象构建最终设备实例。
     *
     * @param device            内部设备实例
     * @param request           WURFL 请求对象
     * @param matchType         匹配类型
     * @param matcherName       匹配器名称
     * @param bucketMatcherName 桶匹配器名称
     * @return 最终设备实例
     */
    Device buildDevice(InternalDevice device, WURFLRequest request, MatchType matchType, String matcherName, String bucketMatcherName);
}
