package com.scientiamobile.wurfl.core.exc;

import java.io.Serial;

/**
 * 当 WURFL 配置中缺少必要的能力（Mandatory Capability）时抛出的异常。
 * <p>在 WURFL 框架初始化或验证配置时，如果发现必选的能力定义缺失，
 * 则会抛出此异常以提示用户修正配置，确保运行时能获取到所有必要的数据。</p>
 */

public class MandatoryCapabilityMissing extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = 233366160908694904L;
    /**
     * 缺失的必要能力列表描述
     */
    private final String missingMandatoryCapabilities;

    /**
     * 使用缺失的能力描述创建异常实例，消息前缀为默认值。
     *
     * @param missingMandatoryCapabilities 缺失的必要能力描述
     */
    public MandatoryCapabilityMissing(String missingMandatoryCapabilities) {
        this("Mandatory capabilities missing from configuration: ", missingMandatoryCapabilities);
    }

    /**
     * 使用自定义的消息前缀和缺失的能力描述创建异常实例。
     *
     * @param messagePrefix                错误消息前缀
     * @param missingMandatoryCapabilities 缺失的必要能力描述
     */
    public MandatoryCapabilityMissing(String messagePrefix, String missingMandatoryCapabilities) {
        super(messagePrefix + missingMandatoryCapabilities);
        this.missingMandatoryCapabilities = missingMandatoryCapabilities;
    }

    /**
     * 获取缺失的必要能力描述信息。
     *
     * @return 缺失的必要能力描述
     */
    public String getMissingMandatoryCapabilities() {
        return this.missingMandatoryCapabilities;
    }
}
