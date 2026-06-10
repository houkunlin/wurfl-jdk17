package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * 判断设备是否偏好 XHTML MP（XHTML Mobile Profile）的虚拟能力评估器。
 * <p>通过检查 WURFL 设备数据库中的 {@code xhtml_support_level} 和
 * {@code preferred_markup} 能力值：要求 XHTML 支持级别 > 0，
 * 且首选标记语言不是 {@code html_web}。</p>
 */

public class IsXHTMLPreferred implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = -8161545030691618770L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        try {
            return Boolean.toString(device.getCapabilityAsInt("xhtml_support_level") > 0 && !device.getCapability("preferred_markup").startsWith("html_web"));
        } catch (RuntimeException e) {
            return "false";
        }
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "is_xhtmlmp_preferred";
    }
}
