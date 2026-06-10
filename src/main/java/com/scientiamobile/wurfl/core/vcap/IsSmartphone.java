package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;

/**
 * 判断设备是否为智能手机的虚拟能力评估器。
 * <p>委托父类 {@link AbstractVirtualCapabilityEvaluator#isSmartphone(Device)}
 * 方法进行判断，基于分辨率、操作系统、版本号、触屏能力和无线设备状态
 * 等多个维度综合评估。</p>
 */

public class IsSmartphone extends AbstractVirtualCapabilityEvaluator {
    @Serial
    private static final long serialVersionUID = 1131972797981270952L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        return Boolean.toString(isSmartphone(device));
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "is_smartphone";
    }
}
