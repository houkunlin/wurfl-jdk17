package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.resource.exc.WURFLConsistencyException;

import java.io.Serial;

/**
 * 当 WURFL 模型中缺少某匹配器所需的设备 ID 时抛出的异常。
 * <p>此异常通常在匹配器构造时由 {@link AbstractMatcher#validateRequiredDeviceIds} 抛出，
 * 提示用户需要更新 wurfl.xml 文件到更新的版本。</p>
 */

final class MissingDeviceIdConsistencyException extends WURFLConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 构造缺少设备 ID 的一致性异常。
     *
     * @param message 异常描述信息，说明缺少哪个设备 ID
     */

    MissingDeviceIdConsistencyException(String message) {
        super(message);
    }
}

