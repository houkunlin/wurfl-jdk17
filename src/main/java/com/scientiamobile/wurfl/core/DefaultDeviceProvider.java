package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.exc.MandatoryCapabilityMissing;
import com.scientiamobile.wurfl.core.matchers.MatchType;
import com.scientiamobile.wurfl.core.request.DefaultWURFLRequest;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.ModelDeviceWithAncestorId;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.vcap.VirtualCapabilityHandler;
import org.apache.commons.lang3.Validate;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * 默认的设备提供者实现，负责创建内部设备和构建最终设备实例。
 * <p>通过 WURFL 数据模型获取设备的能力数据，使用 {@link CapabilitiesHolderFactory} 创建能力持有器，
 * 并在构建最终设备时注入虚拟能力处理器和标记语言解析器。
 * 构造时会校验模型中是否包含所有必备能力，如果缺少则抛出异常。</p>
 */

class DefaultDeviceProvider implements DeviceProvider {
    /**
     * 断言是否禁用（用于内部校验开关）
     */
    private static boolean assertionsDisabled = !DefaultDeviceProvider.class.desiredAssertionStatus();
    /**
     * 标记语言解析器
     */
    private final MarkupResolver markupResolver;
    /** 能力持有器工厂 */
    private final CapabilitiesHolderFactory capabilitiesHolderFactory;
    /** WURFL 数据模型 */
    private final WURFLModel wurflModel;

    public DefaultDeviceProvider(WURFLModel wurflModel, CapabilitiesHolderFactory capabilitiesHolderFactory, MarkupResolver markupResolver) {
        LoggerFactory.getLogger(DefaultDeviceProvider.class);
        if (!assertionsDisabled && wurflModel == null) {
            throw new AssertionError();
        } else {
            this.wurflModel = wurflModel;
            Validate.notNull(capabilitiesHolderFactory, "capabilitiesHolderFactory must be not null.");
            Validate.notNull(markupResolver, "markupResolver must be not null.");
            this.markupResolver = markupResolver;
            this.capabilitiesHolderFactory = capabilitiesHolderFactory;
            Set<String> modelCapabilities = this.capabilitiesHolderFactory.getModelCapabilities();
            Set<String> mandatoryCapabilities = VirtualCapabilityHandler.getMandatoryCapabilities();
            StringBuilder missingCapabilities = new StringBuilder();

            for (String capability : mandatoryCapabilities) {
                if (!modelCapabilities.contains(capability)) {
                    missingCapabilities.append(capability).append(", ");
                }
            }

            if (missingCapabilities.length() > 0) {
                throw new MandatoryCapabilityMissing(missingCapabilities.substring(0, missingCapabilities.length() - 2));
            }
        }
    }

    public DefaultDeviceProvider(WURFLModel wurflModel, CapabilitiesHolderFactory capabilitiesHolderFactory) {
        this(wurflModel, capabilitiesHolderFactory, new MarkupResolverImpl());
    }

    @Override
/**
 * 根据设备 ID 获取内部设备实例。
 * <p>从模型中获取设备及其祖先信息，创建能力持有器并构建 {@link InternalDeviceImpl}。</p>
 *
 * @param deviceId 设备 ID
 * @return 内部设备实例
 */

    public InternalDevice getInternalDevice(String deviceId) {
        Validate.notNull(deviceId, "The deviceId must be not null");
        ModelDeviceWithAncestorId deviceWithAncestorId = this.getModelDeviceWithAncestorId(deviceId);
        if (!assertionsDisabled && deviceWithAncestorId.getModelDevice() == null) {
            throw new AssertionError("modelDevice is null");
        } else {
            ModelDevice modelDevice = deviceWithAncestorId.getModelDevice();
            CapabilitiesHolder capabilitiesHolder = this.capabilitiesHolderFactory.create(modelDevice);
            return new InternalDeviceImpl(deviceWithAncestorId.getModelDevice(), deviceWithAncestorId.getAncestorId(), capabilitiesHolder);
        }
    }

    @Override
/**
 * 使用 User-Agent 字符串构建最终设备实例。
 * <p>便捷方法，内部创建默认的 {@link DefaultWURFLRequest} 并委托给重载方法。</p>
 *
 * @param internalDevice    内部设备实例
 * @param userAgent         User-Agent 字符串
 * @param matchType         匹配类型
 * @param matcherName       匹配器名称
 * @param bucketMatcherName 桶匹配器名称
 * @return 最终设备实例
 */

    public Device buildDevice(InternalDevice internalDevice, String userAgent, MatchType matchType, String matcherName, String bucketMatcherName) {
        return this.buildDevice(internalDevice, (WURFLRequest) (new DefaultWURFLRequest(userAgent, (UserAgentNormalizer) null, UserAgentPriority.OverrideSideloadedBrowserUserAgent, EngineTarget.fastDesktopBrowserMatch)), matchType, matcherName, bucketMatcherName);
    }

    @Override
/**
 * 使用 WURFL 请求对象构建最终设备实例。
 * <p>根据内部设备获取其祖先模型信息，创建带标记语言解析器和虚拟能力处理器的 {@link DefaultDevice}。</p>
 *
 * @param internalDevice    内部设备实例
 * @param request           WURFL 请求对象（包含归一化后的 UA 和优先级等信息）
 * @param matchType         匹配类型
 * @param matcherName       匹配器名称
 * @param bucketMatcherName 桶匹配器名称
 * @return 最终设备实例
 */

    public Device buildDevice(InternalDevice internalDevice, WURFLRequest request, MatchType matchType, String matcherName, String bucketMatcherName) {
        Validate.notNull(internalDevice, "The internal device must be not null");
        String deviceId;
        deviceId = internalDevice.getId();
        Validate.notEmpty(deviceId, "The id must be not null String");
        ModelDeviceWithAncestorId deviceWithAncestorId = this.getModelDeviceWithAncestorId(deviceId);
        if (!assertionsDisabled && deviceWithAncestorId.getModelDevice() == null) {
            throw new AssertionError("modelDevice is null");
        } else {
            return new DefaultDevice(internalDevice, this.markupResolver, matchType, matcherName, bucketMatcherName, request.getNormalizedDeviceUserAgent(), new VirtualCapabilityHandler(request));
        }
    }

    /**
     * 从模型中获取指定设备及其祖先信息的包装对象。
     *
     * @param deviceId 设备 ID
     * @return 设备及其祖先 ID 的包装对象
     */

    private ModelDeviceWithAncestorId getModelDeviceWithAncestorId(String deviceId) {
        ModelDevice modelDevice = this.wurflModel.getDeviceById(deviceId);
        String ancestorId = this.wurflModel.getDeviceAncestor(modelDevice).getID();
        return new ModelDeviceWithAncestorId(modelDevice, ancestorId);
    }
}
