package com.scientiamobile.wurfl.core.updater.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;

import java.io.Serial;

/**
 * 当 WURFL 文件的扩展名不符合要求时抛出的异常。
 * <p>WURFL 文件仅支持 {@code .zip} 和 {@code .gz} 两种扩展名格式。
 * 如果本地文件路径或远程 URL 使用了其他扩展名，将抛出此异常以阻止更新流程启动。</p>
 */

public class BadWurflExtensionException extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = -6562761498439936698L;

    public BadWurflExtensionException(String message) {
        super(message);
    }
}
