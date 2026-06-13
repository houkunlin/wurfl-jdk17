package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * 判断设备是否偏好 WML（Wireless Markup Language）的虚拟能力评估器。
 * <p>通过检查 WURFL 设备数据库中的 {@code xhtml_support_level} 能力值：
 * 如果 XHTML 支持级别 &lt;= 0，则表明设备更倾向于使用 WML。</p>
 */

public class IsWMLPreferred implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 4429460118740181952L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        try {
            return Boolean.toString(device.getCapabilityAsInt("xhtml_support_level") <= 0);
        } catch (RuntimeException e) {
            return "false";
        }
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "is_wml_preferred";
    }
}
