package com.scientiamobile.wurfl.core.updater.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;

import java.io.Serial;

/**
 * 当 WURFL 文件或目录权限不足时抛出的异常。
 * <p>在更新过程中，如果本地 WURFL 文件或其所在目录不可写，将抛出此异常。
 * 开发者需要确保应用程序对文件系统具有适当的写入权限。</p>
 */

public class WurflFilePermissionException extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public WurflFilePermissionException(String message) {
        super(message);
    }

    public WurflFilePermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
