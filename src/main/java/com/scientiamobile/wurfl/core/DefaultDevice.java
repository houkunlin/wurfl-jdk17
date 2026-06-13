package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.exc.VirtualCapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.matchers.MatchType;
import com.scientiamobile.wurfl.core.vcap.VirtualCapabilityHandler;
import org.apache.commons.lang3.Validate;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 默认设备实现，封装了设备检测后返回给用户的完整设备信息。
 * <p>实现了 {@link EnrichedDevice} 接口，整合了内部设备（{@link InternalDevice}）的能力数据、
 * 虚拟能力处理器（{@link VirtualCapabilityHandler}）的虚拟能力以及标记语言解析器（{@link MarkupResolver}）
 * 的标记语言信息。同时记录了匹配类型、匹配器名称等匹配元数据。</p>
 * <p>获取能力时采用两阶段策略：先尝试从内部设备获取物理能力，如果未定义则回退到虚拟能力。</p>
 */

public class DefaultDevice implements EnrichedDevice, Serializable {
    @Serial
    private static final long serialVersionUID = 11L;

    static {
        LoggerFactory.getLogger(DefaultDevice.class);
    }

    /**
     * 匹配类型（如精确匹配、缓存匹配等）
     */
    private final MatchType matchType;
    /**
     * 桶匹配器的名称
     */
    private final String bucketMatcherName;
    /**
     * 最终匹配器的名称
     */
    private final String matcherName;
    /**
     * 归一化后的 User-Agent
     */
    private final String normalizedUserAgent;
    /**
     * 标记语言解析器（暂态，不参与序列化）
     */
    private transient MarkupResolver markupResolver;
    /**
     * 内部设备实例，提供物理能力数据
     */
    private transient InternalDevice internalDevice;
    /**
     * 计算出的标记语言类型
     */
    private transient MarkUp markUp;
    /**
     * 虚拟能力处理器（暂态，不参与序列化）
     */
    private transient VirtualCapabilityHandler virtualCapabilityHandler;

    /**
     * 构造函数，创建默认设备实例。
     *
     * @param internalDevice           内部设备实例
     * @param virtualCapabilityHandler 虚拟能力处理器
     * @param markupResolver           标记语言解析器
     * @param matchType                匹配类型
     * @param matcherName              匹配器名称
     * @param bucketMatcherName        桶匹配器名称
     * @param normalizedUserAgent      归一化后的 User-Agent
     */
    public DefaultDevice(InternalDevice internalDevice, VirtualCapabilityHandler virtualCapabilityHandler, MarkupResolver markupResolver, MatchType matchType, String matcherName, String bucketMatcherName, String normalizedUserAgent) {
        Validate.notNull(virtualCapabilityHandler, "The capabilitiesHandler must be not null");
        Validate.notNull(markupResolver, "The markupResolver must be not null");
        this.internalDevice = internalDevice;
        this.markupResolver = markupResolver;
        this.matchType = matchType;
        this.matcherName = matcherName;
        this.bucketMatcherName = bucketMatcherName;
        this.normalizedUserAgent = normalizedUserAgent;
        this.virtualCapabilityHandler = virtualCapabilityHandler;
    }

    /**
     * 构造函数，创建默认设备实例（参数顺序不同的重载版本）。
     *
     * @param internalDevice           内部设备实例
     * @param markupResolver           标记语言解析器
     * @param matchType                匹配类型
     * @param matcherName              匹配器名称
     * @param bucketMatcherName        桶匹配器名称
     * @param normalizedUserAgent      归一化后的 User-Agent
     * @param virtualCapabilityHandler 虚拟能力处理器
     */
    public DefaultDevice(InternalDevice internalDevice, MarkupResolver markupResolver, MatchType matchType, String matcherName, String bucketMatcherName, String normalizedUserAgent, VirtualCapabilityHandler virtualCapabilityHandler) {
        Validate.notNull(virtualCapabilityHandler, "The capabilitiesHandler must be not null");
        Validate.notNull(markupResolver, "The markupResolver must be not null");
        this.internalDevice = internalDevice;
        this.markupResolver = markupResolver;
        this.matchType = matchType;
        this.matcherName = matcherName;
        this.bucketMatcherName = bucketMatcherName;
        this.normalizedUserAgent = normalizedUserAgent;
        this.virtualCapabilityHandler = virtualCapabilityHandler;
    }

    /**
     * 反序列化后恢复对象，将 transient 字段置为 null。
     * 这些字段包含运行时状态，无法也不应通过序列化恢复。
     */
    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.internalDevice = null;
        this.markupResolver = null;
        this.markUp = null;
        this.virtualCapabilityHandler = null;
    }

    /**
     * 获取所有虚拟能力的名称到值映射。
     *
     * @return 虚拟能力映射
     */
    @Override
    public Map<String, String> getVirtualCapabilities() {
        return this.virtualCapabilityHandler.getAllVirtualCapabilities(this);
    }

    @Override
    public VirtualCapabilityInfo getVirtualCapabilityInfo() {
        return new VirtualCapabilityInfo(
                getVirtualCapabilityAsBool("is_app_webview"),
                getVirtualCapabilityAsBool("is_app"),
                getVirtualCapabilityAsBool("is_mobile"),
                getVirtualCapabilityAsBool("is_phone"),
                getVirtualCapabilityAsBool("is_full_desktop"),
                getVirtualCapabilityAsBool("is_smartphone"),
                getVirtualCapabilityAsBool("is_robot"),
                getVirtualCapabilityAsBool("is_largescreen"),
                getVirtualCapabilityAsBool("is_android"),
                getVirtualCapabilityAsBool("is_ios"),
                getVirtualCapabilityAsBool("is_windows_phone"),
                getVirtualCapabilityAsBool("is_touchscreen"),
                getVirtualCapabilityAsBool("is_wml_preferred"),
                getVirtualCapabilityAsBool("is_xhtmlmp_preferred"),
                getVirtualCapabilityAsBool("is_html_preferred"),
                getVirtualCapability("advertised_device_os"),
                getVirtualCapability("advertised_device_os_version"),
                getVirtualCapability("advertised_browser"),
                getVirtualCapability("advertised_browser_version"),
                getVirtualCapability("advertised_app_name"),
                getVirtualCapability("complete_device_name"),
                getVirtualCapability("device_name"),
                getVirtualCapability("form_factor")
        );
    }

    /**
     * 获取指定名称的虚拟能力值。
     *
     * @param virtualCapabilityName 虚拟能力名称
     * @return 虚拟能力值
     */
    @Override
    public String getVirtualCapability(String virtualCapabilityName) {
        return this.virtualCapabilityHandler.getVirtualCapability(virtualCapabilityName, this);
    }

    /**
     * 获取虚拟能力值并转换为整数。
     *
     * @param virtualCapabilityName 虚拟能力名称
     * @return 整型的能力值
     */
    @Override
    public int getVirtualCapabilityAsInt(String virtualCapabilityName) {
        return this.virtualCapabilityHandler.getVirtualCapabilityAsInt(virtualCapabilityName, this);
    }

    /**
     * 获取虚拟能力值并转换为布尔值。
     *
     * @param virtualCapabilityName 虚拟能力名称
     * @return 布尔型的能力值
     */
    @Override
    public boolean getVirtualCapabilityAsBool(String virtualCapabilityName) {
        return this.virtualCapabilityHandler.getVirtualCapabilityAsBool(virtualCapabilityName, this);
    }

    /**
     * 获取匹配类型。
     *
     * @return 匹配类型枚举
     */
    @Override
    public MatchType getMatchType() {
        return this.matchType;
    }

    /**
     * 获取桶匹配器的名称。
     *
     * @return 桶匹配器名称
     */
    @Override
    public String getBucketMatcherName() {
        return this.bucketMatcherName;
    }

    /**
     * 获取最终匹配器的名称。
     *
     * @return 匹配器名称
     */
    @Override
    public String getMatcherName() {
        return this.matcherName;
    }

    /**
     * 获取设备支持的标记语言类型。
     * <p>通过 {@link MarkupResolver} 根据设备能力计算，结果会被缓存。</p>
     *
     * @return 标记语言枚举
     */
    @Override
    public MarkUp getMarkUp() {
        if (this.markUp == null) {
            this.markUp = this.markupResolver.getMarkupForDevice(this);
        }

        return this.markUp;
    }

    /**
     * 返回设备的简短字符串表示，格式为 {@code [设备ID, match=匹配类型]}。
     *
     * @return 字符串表示
     */
    @Override
    public String toString() {
        return "[" + this.getId() + ", match=" + this.getMatchType() + ']';
    }

    /**
     * 获取指定名称的能力值。
     * <p>先尝试从内部设备获取物理能力，如果未定义（抛出 {@link CapabilityNotDefinedException}），
     * 则回退到从虚拟能力处理器获取。如果两者都未定义，则抛出原始的能力未定义异常。</p>
     *
     * @param capabilityName 能力名称
     * @return 能力值
     * @throws CapabilityNotDefinedException 如果物理能力和虚拟能力都未定义
     */
    @Override
    public String getCapability(String capabilityName) {
        try {
            return this.internalDevice.getCapability(capabilityName);
        } catch (CapabilityNotDefinedException capabilityNotDefinedException) {
            try {
                return this.getVirtualCapability(capabilityName);
            } catch (VirtualCapabilityNotDefinedException virtualCapabilityNotDefinedException) {
                throw capabilityNotDefinedException;
            }
        }
    }

    /**
     * 获取设备 ID。
     *
     * @return 设备 ID
     */
    @Override
    public String getId() {
        return this.internalDevice.getId();
    }

    /**
     * 获取 WURFL 数据中定义的设备 User-Agent 字符串。
     *
     * @return User-Agent 字符串
     */
    @Override
    public String getWURFLUserAgent() {
        return this.internalDevice.getWURFLUserAgent();
    }

    /**
     * 获取指定能力值并将其解析为整数。
     * <p>先尝试从物理能力获取，如果未定义则回退到虚拟能力，再无法获取则抛出原始异常。</p>
     *
     * @param capabilityName 能力名称
     * @return 整型的能力值
     */
    @Override
    public int getCapabilityAsInt(String capabilityName) {
        try {
            return this.internalDevice.getCapabilityAsInt(capabilityName);
        } catch (CapabilityNotDefinedException capabilityNotDefinedException) {
            try {
                return this.getVirtualCapabilityAsInt(capabilityName);
            } catch (VirtualCapabilityNotDefinedException virtualCapabilityNotDefinedException) {
                throw capabilityNotDefinedException;
            }
        }
    }

    /**
     * 获取指定能力值并转换为布尔值。
     * <p>先尝试从物理能力获取，如果未定义则回退到虚拟能力，再无法获取则抛出原始异常。</p>
     *
     * @param capabilityName 能力名称
     * @return 布尔型的能力值
     */
    @Override
    public boolean getCapabilityAsBool(String capabilityName) {
        try {
            return this.internalDevice.getCapabilityAsBool(capabilityName);
        } catch (CapabilityNotDefinedException capabilityNotDefinedException) {
            try {
                return this.getVirtualCapabilityAsBool(capabilityName);
            } catch (VirtualCapabilityNotDefinedException virtualCapabilityNotDefinedException) {
                throw capabilityNotDefinedException;
            }
        }
    }

    /**
     * 获取设备的所有能力映射。
     *
     * @return 能力名称到值的映射
     */
    @Override
    public Map<String, String> getCapabilities() {
        return this.internalDevice.getCapabilities();
    }

    /**
     * 判断该设备是否是某个设备树的实际根节点。
     *
     * @return 如果是实际设备根节点则返回 {@code true}
     */
    @Override
    public boolean isActualDeviceRoot() {
        return this.internalDevice.isActualDeviceRoot();
    }

    /**
     * 获取该设备所属设备树的根节点 ID。
     *
     * @return 设备树根节点 ID
     */
    @Override
    public String getDeviceRootId() {
        return this.internalDevice.getDeviceRootId();
    }

    /**
     * 获取内部设备实例。
     *
     * @return 内部设备实例
     */
    public InternalDevice getInternalDevice() {
        return this.internalDevice;
    }

    /**
     * 获取归一化后的 User-Agent 字符串。
     *
     * @return 归一化后的 User-Agent
     */
    @Override
    public String getNormalizedUserAgent() {
        return this.normalizedUserAgent;
    }
}
