package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.InternalDevice;
import com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.exc.VirtualCapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 虚拟能力处理器，负责管理和分发所有虚拟能力的评估请求。
 * <p>维护了所有虚拟能力评估器的注册表（名称到评估器的映射），
 * 提供统一的能力查询入口。支持控制能力覆盖（Control Cap Override）
 * 机制，允许通过设备数据库中的特殊能力值覆盖虚拟能力的计算结果。</p>
 */

public class VirtualCapabilityHandler {

    /**
     * 虚拟能力名称到评估器实例的映射表
     */
    private static final Map<String, VirtualCapabilityEvaluator> EVALUATORS_BY_NAME;

    static {
        EVALUATORS_BY_NAME = new ConcurrentHashMap<>();
        EVALUATORS_BY_NAME.put("is_android", new IsAndroidOs());
        EVALUATORS_BY_NAME.put("is_ios", new IsIOs());
        EVALUATORS_BY_NAME.put("is_windows_phone", new IsWindowsPhone());
        EVALUATORS_BY_NAME.put("is_full_desktop", new IsFullDesktop());
        EVALUATORS_BY_NAME.put("is_app", new IsApp());
        EVALUATORS_BY_NAME.put("advertised_device_os", new OsName());
        EVALUATORS_BY_NAME.put("advertised_device_os_version", new OsVersion());
        EVALUATORS_BY_NAME.put("advertised_browser", new BrowserName());
        EVALUATORS_BY_NAME.put("advertised_browser_version", new BrowserVersion());
        EVALUATORS_BY_NAME.put("is_wml_preferred", new IsWMLPreferred());
        EVALUATORS_BY_NAME.put("is_xhtmlmp_preferred", new IsXHTMLPreferred());
        EVALUATORS_BY_NAME.put("is_html_preferred", new IsHTMLPreferred());
        EVALUATORS_BY_NAME.put("form_factor", new FormFactor());
        EVALUATORS_BY_NAME.put("complete_device_name", new CompleteDeviceName());
        EVALUATORS_BY_NAME.put("is_phone", new IsPhone());
        EVALUATORS_BY_NAME.put("is_app_webview", new IsAppWebview());
        EVALUATORS_BY_NAME.put("device_name", new DeviceName());
        EVALUATORS_BY_NAME.put("is_largescreen", new IsLargescreen());
        EVALUATORS_BY_NAME.put("is_mobile", new IsMobile());
        EVALUATORS_BY_NAME.put("is_robot", new IsRobot());
        EVALUATORS_BY_NAME.put("is_smartphone", new IsSmartphone());
        EVALUATORS_BY_NAME.put("is_touchscreen", new IsTouchscreen());
        EVALUATORS_BY_NAME.put("advertised_app_name", new AppName());

        try {
            Class<?> evaluatorClass = Class.forName("com.scientiamobile.wurfl.core.vcap.OsManufacturer");
            if (evaluatorClass != null) {
                Object evaluatorInstance = evaluatorClass.getDeclaredConstructor().newInstance();
                if (evaluatorInstance != null) {
                    EVALUATORS_BY_NAME.put("os_manufacturer", (VirtualCapabilityEvaluator) evaluatorInstance);
                }
            }
        } catch (RuntimeException | ReflectiveOperationException ignore) {
        }
    }

    /**
     * 当前处理的 HTTP 请求对象
     */
    private WURFLRequest request;

    private VirtualCapabilityHandler() {
    }

    /**
     * 构造一个与特定请求关联的虚拟能力处理器。
     *
     * @param request 当前 HTTP 请求信息
     */
    public VirtualCapabilityHandler(WURFLRequest request) {
        this();
        this.request = request;
    }

    /**
     * 应用控制能力覆盖机制。
     * <p>如果设备数据库中定义了名为 {@code controlcap_ + virtualCapabilityName} 的能力值，
     * 且其值不为 "default"，则使用该覆盖值替换原始计算结果。
     * 支持 {@code "force_true"} 和 {@code "force_false"} 特殊值。</p>
     *
     * @param virtualCapabilityName 虚拟能力名称
     * @param computedValue         评估器计算出的原始值
     * @param device                设备对象
     * @return 应用覆盖后的最终值
     * @throws VirtualCapabilityNotDefinedException 如果相关的 controlcap 能力未定义
     */
    static String applyControlCapOverride(String virtualCapabilityName, String computedValue, InternalDevice device) {
        String controlCapabilityName = "controlcap_" + virtualCapabilityName;

        try {
            String overrideValue;
            overrideValue = device.getCapability(controlCapabilityName);
            if (overrideValue != null && !"default".equals(overrideValue)) {
                if ("force_true".equals(overrideValue)) {
                    return "true";
                }

                if ("force_false".equals(overrideValue)) {
                    return "false";
                }

                return overrideValue;
            }
        } catch (CapabilityNotDefinedException e) {
            throw new VirtualCapabilityNotDefinedException(virtualCapabilityName);
        }

        return computedValue == null ? "" : computedValue;
    }

    /**
     * 获取所有已注册的虚拟能力名称集合。
     *
     * @return 虚拟能力名称的不可变集合
     */
    public static Set<String> getAllVirtualCapabilities() {
        new VirtualCapabilityHandler();
        return EVALUATORS_BY_NAME.keySet();
    }

    /**
     * 获取所有虚拟能力评估所必需的基础能力名称集合。
     *
     * @return 必要基础能力名称集合
     */
    public static Set<String> getMandatoryCapabilities() {
        return new HashSet<>(Arrays.asList(VirtualCapabilityEvaluator.MANDATORY_CAPABILITIES));
    }

    /**
     * 获取指定虚拟能力在给定设备上的评估值。
     * <p>查找对应名称的评估器执行计算，然后应用控制能力覆盖机制。
     * 使用 {@code synchronized} 确保线程安全。</p>
     *
     * @param virtualCapabilityName 虚拟能力名称
     * @param device                设备对象
     * @return 虚拟能力的评估值
     * @throws VirtualCapabilityNotDefinedException 如果虚拟能力名称未注册
     */
    public String getVirtualCapability(String virtualCapabilityName, Device device) {
        VirtualCapabilityEvaluator evaluator;
        evaluator = EVALUATORS_BY_NAME.get(virtualCapabilityName);
        if (evaluator == null) {
            throw new VirtualCapabilityNotDefinedException(virtualCapabilityName);
        } else {
            synchronized (this) {
                return applyControlCapOverride(virtualCapabilityName, evaluator.eval(device, this.request), device);
            }
        }
    }

    /**
     * 获取指定虚拟能力的整数值。
     *
     * @param virtualCapabilityName 虚拟能力名称
     * @param device                设备对象
     * @return 整型值
     */
    public int getVirtualCapabilityAsInt(String virtualCapabilityName, Device device) {
        return Integer.parseInt(this.getVirtualCapability(virtualCapabilityName, device));
    }

    /**
     * 获取指定虚拟能力的布尔值。
     *
     * @param virtualCapabilityName 虚拟能力名称
     * @param device                设备对象
     * @return 布尔值
     */
    public boolean getVirtualCapabilityAsBool(String virtualCapabilityName, Device device) {
        return Boolean.parseBoolean(this.getVirtualCapability(virtualCapabilityName, device));
    }

    /**
     * 获取所有虚拟能力在给定设备上的评估结果。
     *
     * @param device 设备对象
     * @return 虚拟能力名称到值的映射
     */
    public Map<String, String> getAllVirtualCapabilities(Device device) {
        HashMap<String, String> virtualCapabilities = new HashMap<>();
        HashSet<VirtualCapabilityEvaluator> evaluators = new HashSet<>(EVALUATORS_BY_NAME.values());

        for (VirtualCapabilityEvaluator evaluator : evaluators) {
            String virtualCapabilityName = evaluator.getHandledVirtualCapabilityName();
            virtualCapabilities.put(virtualCapabilityName, applyControlCapOverride(virtualCapabilityName, evaluator.eval(device, this.request), device));
        }

        return virtualCapabilities;
    }
}
