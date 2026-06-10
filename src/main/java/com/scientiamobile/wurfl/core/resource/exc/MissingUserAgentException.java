package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

/**
 * 设备缺少 User-Agent 异常。
 * <p>在加载 WURFL 设备时，如果设备既不是 "generic" 也没有提供 User-Agent，
 * 则抛出此异常。除 generic 外，所有设备都必须有 User-Agent 作为设备识别的依据。</p>
 */

public class MissingUserAgentException extends UserAgentConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 构造缺少 User-Agent 异常。
     *
     * @param device    缺少 User-Agent 的设备
     * @param userAgent 当前为空或无效的 User-Agent
     * @param message   异常描述信息
     */
    public MissingUserAgentException(ModelDevice device, String userAgent, String message) {
        super(device, userAgent, message);
    }
}
