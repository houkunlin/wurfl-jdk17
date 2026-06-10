package com.scientiamobile.wurfl.core.web.introspector;

import java.util.Map;

/**
 * 设备检测请求的响应数据结构体。
 * <p>包含检测到的设备 ID、User-Agent、请求类型以及请求的能力映射，
 * 序列化为 JSON 返回给客户端。</p>
 */

final class IntrospectorRequestResponse {
    /**
     * 匹配到的设备 ID
     */
    String deviceId;
    /**
     * 请求中使用的 User-Agent
     */
    String userAgent;
    /** 请求类型：{@code request}（自动检测）或 {@code form}（表单提交） */
    String requestType;
    /** 请求的能力名称到值的映射 */
    Map<String, String> capabilities;
}

