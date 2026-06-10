package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;

/**
 * 判断请求是否来自网络爬虫/机器人程序的虚拟能力评估器。
 * <p>委托父类 {@link AbstractVirtualCapabilityEvaluator#isRobot(WURFLRequest)}
 * 方法进行判断，基于 User-Agent、请求头特征以及排除关键词进行综合检测。</p>
 */

public class IsRobot extends AbstractVirtualCapabilityEvaluator {
    @Serial
    private static final long serialVersionUID = 290928780375573277L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        return Boolean.toString(isRobot(request));
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "is_robot";
    }
}
