package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;

import java.io.Serial;

/**
 * WURFL XML 解析异常。
 * <p>在解析 WURFL XML 数据（包括主文件和补丁文件）时，
 * 如果遇到格式错误、缺少必需属性、数据重复等不符合预期格式的情况，则抛出此异常。
 * 它是 WURFL 模块中最常见的运行时异常之一。</p>
 */

public class WURFLParsingException extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public WURFLParsingException() {
    }

    public WURFLParsingException(String message) {
        super(message);
    }

    public WURFLParsingException(Throwable cause) {
        super(cause);
    }

    public WURFLParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
