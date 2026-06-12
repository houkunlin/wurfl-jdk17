package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.io.Serializable;

/**
 * 默认的标记语言解析器实现。
 * <p>根据设备的 {@code xhtml_support_level} 和 {@code preferred_markup} 能力值
 * 来判断设备所支持的标记语言类型。规则如下：</p>
 * <ul>
 *   <li>XHTML 支持级别 >= 3 → 高级 XHTML</li>
 *   <li>XHTML 支持级别 > 0 → 简单 XHTML</li>
 *   <li>首选标记包含 "imode" → CHTML</li>
 *   <li>其他 → WML</li>
 * </ul>
 */

class MarkupResolverImpl implements MarkupResolver, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(MarkupResolverImpl.class);

    /**
     * 根据设备的能力信息计算其支持的标记语言类型。
     *
     * @param device 内部设备实例
     * @return 标记语言枚举
     * @throws WURFLRuntimeException 如果无法从设备获取所需能力值
     */
    @Override
    public MarkUp getMarkupForDevice(InternalDevice device) {
        String xhtmlSupportLevel;
        String preferredMarkup;

        try {
            xhtmlSupportLevel = device.getCapability("xhtml_support_level");
            preferredMarkup = device.getCapability("preferred_markup");
        } catch (CapabilityNotDefinedException e) {
            log.error("It is not possible getting markUp from capabilities: {}", e.getLocalizedMessage());
            throw new WURFLRuntimeException("It is not possible getting markUp", e);
        }

        MarkUp markup;
        if (Integer.parseInt(xhtmlSupportLevel) >= 3) {
            markup = MarkUp.XHTML_ADVANCED;
        } else if (Integer.parseInt(xhtmlSupportLevel) > 0) {
            markup = MarkUp.XHTML_SIMPLE;
        } else if (preferredMarkup.contains("imode")) {
            markup = MarkUp.CHTML;
        } else {
            markup = MarkUp.WML;
        }

        return markup;
    }
}

