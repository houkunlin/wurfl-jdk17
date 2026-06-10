package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * 判断设备是否偏好 HTML Web 的虚拟能力评估器。
 * <p>通过检查 WURFL 设备数据库中的 {@code preferred_markup} 能力值
 * 是否以 {@code "html_web"} 开头来判断。如果设备首选标记语言为 HTML Web，
 * 说明它具备完整的桌面级别 HTML 渲染能力。</p>
 */

public class IsHTMLPreferred implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = -4750338441403942375L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        return Boolean.toString(device.getCapability("preferred_markup").startsWith("html_web"));
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "is_html_preferred";
    }
}
