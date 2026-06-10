package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;

/**
 * 设备形态因子（Form Factor）虚拟能力评估器。
 * <p>根据设备的多项能力值综合判断设备的形态类别，返回以下之一：
 * {@code Robot}、{@code Desktop}、{@code Smart-TV}、
 * {@code Other Non-Mobile}、{@code Tablet}、{@code Smartphone}、
 * {@code Feature Phone}、{@code Other Mobile}。
 * 判断顺序按照从特殊到常规的优先级执行。</p>
 */

public class FormFactor extends AbstractVirtualCapabilityEvaluator {
    @Serial
    private static final long serialVersionUID = -3936563826288495198L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        if (device.getVirtualCapabilityAsBool("is_robot")) {
            return "Robot";
        } else if (device.getCapabilityAsBool("ux_full_desktop")) {
            return "Desktop";
        } else if (device.getCapabilityAsBool("is_smarttv")) {
            return "Smart-TV";
        } else if (!device.getCapabilityAsBool("is_wireless_device")) {
            return "Other Non-Mobile";
        } else if (device.getCapabilityAsBool("is_tablet")) {
            return "Tablet";
        } else if (device.getVirtualCapabilityAsBool("is_smartphone")) {
            return "Smartphone";
        } else {
            return device.getCapabilityAsBool("can_assign_phone_number") ? "Feature Phone" : "Other Mobile";
        }
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "form_factor";
    }
}
