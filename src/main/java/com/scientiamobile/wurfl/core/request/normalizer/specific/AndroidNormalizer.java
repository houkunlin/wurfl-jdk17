package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Android 设备的 User-Agent 特定规范化器。
 * <p>该规范化器从 UA 中提取 Android 版本号和设备型号信息，组合成前缀附加到原始 UA 之前。
 * 这种前置处理使得 WURFL 引擎在进行设备匹配时，能够优先以版本号和型号信息作为匹配依据，
 * 提高 Android 设备识别的准确性。</p>
 */
public class AndroidNormalizer implements UserAgentNormalizer {
    @Override
    public String normalize(String userAgent) {
        String androidVersion = UserAgentUtils.getAndroidVersion(userAgent, false);
        String androidModel = UserAgentUtils.getAndroidModel(userAgent);
        return !StringUtils.isEmpty(androidVersion) && !StringUtils.isEmpty(androidModel)
                ? androidVersion + " " + androidModel + "---" + userAgent
                : userAgent;
    }
}
