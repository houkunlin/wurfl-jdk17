package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.resource.WURFLResource;

import java.io.Serial;

/**
 * WURFL 资源操作异常。
 * <p>当对 WURFL 资源（如 XML 文件、输入流等）进行操作时发生错误时抛出，
 * 例如解析失败、资源不可用等情况。该异常会携带触发异常的 {@link WURFLResource} 实例，
 * 便于调用方定位具体是哪个资源出了问题。</p>
 */

public class WURFLResourceException extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 触发异常的 WURFL 资源对象
     */
    private final transient WURFLResource resource;

    /**
     * 使用指定资源构造异常，异常消息会自动包含资源信息。
     *
     * @param resource 触发异常的 WURFL 资源
     */
    public WURFLResourceException(WURFLResource resource) {
        super("WURFL resource exception in: " + resource.getInfo());
        this.resource = resource;
    }

    /**
     * 使用指定资源和根原因构造异常。
     *
     * @param resource 触发异常的 WURFL 资源
     * @param cause    导致异常的根原因
     */
    public WURFLResourceException(WURFLResource resource, Throwable cause) {
        super(cause);
        this.resource = resource;
    }

    /**
     * 使用指定资源和自定义消息构造异常。
     *
     * @param resource 触发异常的 WURFL 资源
     * @param message  自定义异常描述信息
     */
    public WURFLResourceException(WURFLResource resource, String message) {
        super(message);
        this.resource = resource;
    }

    /**
     * 使用指定资源、自定义消息和根原因构造异常。
     *
     * @param resource 触发异常的 WURFL 资源
     * @param message  自定义异常描述信息
     * @param cause    导致异常的根原因
     */
    public WURFLResourceException(WURFLResource resource, String message, Throwable cause) {
        super(message, cause);
        this.resource = resource;
    }

    /**
     * 获取触发异常的 WURFL 资源对象。
     *
     * @return 与当前异常关联的 WURFL 资源
     */

    public WURFLResource getResource() {
        return this.resource;
    }
}
