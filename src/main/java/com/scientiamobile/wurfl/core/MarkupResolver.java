package com.scientiamobile.wurfl.core;

/**
 * 标记语言解析器接口，定义根据设备信息计算其所支持标记语言类型的策略。
 * <p>通常根据设备的 {@code xhtml_support_level} 和 {@code preferred_markup} 能力值来判断。</p>
 */

public interface MarkupResolver {
    /**
     * 根据设备的能力信息计算该设备支持的标记语言类型。
     *
     * @param device 内部设备实例
     * @return 标记语言枚举
     */
    MarkUp getMarkupForDevice(InternalDevice device);
}
