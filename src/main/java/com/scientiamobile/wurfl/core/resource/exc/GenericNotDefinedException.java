package com.scientiamobile.wurfl.core.resource.exc;

import java.io.Serial;

/**
 * Generic 设备未定义异常。
 * <p>WURFL 模型中必须存在一个名为 "generic" 的基础设备，
 * 所有其他设备都直接或间接地派生自该设备。如果在加载设备集合时
 * 没有找到 "generic" 设备，则抛出此异常。</p>
 */

public class GenericNotDefinedException extends WURFLConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;

    public GenericNotDefinedException() {
        super("Device: generic is not defined");
    }
}
