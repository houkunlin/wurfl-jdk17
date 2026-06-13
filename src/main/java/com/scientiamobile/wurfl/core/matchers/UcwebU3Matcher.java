package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.Constants;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.UcwebU3Normalizer;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UCWeb U3 内核浏览器匹配器。
 * <p>UCWeb U3 是 UC 浏览器基于 WebKit 的新版内核，运行在 Android、iOS 和 Windows Phone 平台上。通过检查 User-Agent 是否以 Mozilla 开头且包含 UCBrowser 来识别。</p>
 */

final class UcwebU3Matcher extends MatcherBase {
    private static final String GENERIC_MS_PHONE_OS8_SUBUAWCWEB = "generic_ms_phone_os8_subuaucweb";
    private static final String GENERIC_UCWEB_ANDROID_VER1 = "generic_ucweb_android_ver1";
    private static final String APPLE_IPHONE_VER1_SUBUAWCWEB = "apple_iphone_ver1_subuaucweb";
    private static final String APPLE_IPAD_VER1_SUBUAWCWEB = "apple_ipad_ver1_subuaucweb";
    private static final Pattern IPHONE_IOS_VERSION = Pattern.compile("iPhone OS (\\d+)(?:_\\d+)?.+ like");
    private static final Pattern IPAD_IOS_VERSION = Pattern.compile("CPU OS (\\d+)(?:_\\d+)?.+like Mac");
    private static final Pattern ANDROID_VERSION_U3_PATTERN = Pattern.compile("\\bAndroid (\\d+)(?:\\.\\d+)?");
    private static final List<String> SUPPORTED_DEVICE_IDS;

    /**
     * 型号名称到设备 ID 的索引映射（实例级懒加载）。
     * <p>每个 UcwebU3Matcher 实例独立构建，首次调用 lookupAndroidDeviceByModel
     * 时懒加载。动态重载 WURFL 数据时 MatcherManager 创建新实例，自然重建索引，
     * 不会出现静态缓存导致的脏数据问题。</p>
     */
    private volatile Map<String, String> modelNameToDeviceId;

    /**
     * WURFL 设备模型引用（用于构建型号索引）。
     */
    private final WURFLModel wurflModel;

    static {
        SUPPORTED_DEVICE_IDS = new ArrayList<>();
        SUPPORTED_DEVICE_IDS.add("generic_ucweb");
        SUPPORTED_DEVICE_IDS.add(GENERIC_UCWEB_ANDROID_VER1);
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver2");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver3");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver4");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver5");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver6");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver7");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver8");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver9");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver10");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver11");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver12");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver13");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver14");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver15");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver16");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver17");
        SUPPORTED_DEVICE_IDS.add(APPLE_IPHONE_VER1_SUBUAWCWEB);
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver2_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver3_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver4_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver5_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver6_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver7_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver8_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver9_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver10_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver11_subuaucweb");
        SUPPORTED_DEVICE_IDS.add(APPLE_IPAD_VER1_SUBUAWCWEB);
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub4_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub5_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub6_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub7_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub8_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub9_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub10_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub11_subuaucweb");
        SUPPORTED_DEVICE_IDS.add(GENERIC_MS_PHONE_OS8_SUBUAWCWEB);
        SUPPORTED_DEVICE_IDS.add("generic_ms_phone_os8_1_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("generic_ms_phone_os10_subuaucweb");
    }

    public UcwebU3Matcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
        super(userAgentNormalizer, wurflModel);
        this.wurflModel = wurflModel;
    }

    /**
     * 获取型号名称→设备 ID 索引映射，首次调用时懒加载构建并实例级缓存。
     * <p>仅遍历 actual_device_root=true 的设备（约 4.2 万条），
     * 排除通用设备的 model_name（如"Android 16.0"），减少内存占用约 65%。
     * 实例级缓存确保动态重载时自动重建，无脏数据风险。</p>
     */
    private Map<String, String> getModelNameIndex() {
        Map<String, String> result = modelNameToDeviceId;
        if (result != null) {
            return result;
        }
        synchronized (this) {
            result = modelNameToDeviceId;
            if (result != null) {
                return result;
            }
            result = buildModelNameIndex(wurflModel);
            this.modelNameToDeviceId = result;
            return result;
        }
    }

    /**
     * 遍历 WURFL 模型中 actual_device_root=true 的有型号名称的设备，构建索引。
     * <p>仅索引定义了 model_name 的具体设备，排除不相关的通用/父设备。</p>
     */
    private static Map<String, String> buildModelNameIndex(WURFLModel model) {
        Map<String, String> index = new HashMap<>();
        if (model == null) {
            return index;
        }
        for (String deviceId : model.getAllDevicesId()) {
            try {
                ModelDevice device = model.getDeviceById(deviceId);
                // 仅索引实际设备根节点，排除通用设备
                if (!device.isActualDeviceRoot()) {
                    continue;
                }
                if (!device.defineCapability("model_name")) {
                    continue;
                }
                String modelName = device.getCapability("model_name");
                if (modelName != null && !modelName.isEmpty()) {
                    index.putIfAbsent(modelName.toLowerCase(Locale.ENGLISH), deviceId);
                }
            } catch (Exception ignored) {
                // 跳过异常的设备条目
            }
        }
        return index;
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        return new HashSet<>(SUPPORTED_DEVICE_IDS);
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent.startsWith("Mozilla") && cleanedDeviceUserAgent.contains("UCBrowser");
    }

    /**
     * 执行确定匹配，覆盖父类方法以使用原始 User-Agent 进行 RIS 匹配。
     * <p>先通过规范化 UA 验证 UC 浏览器版本和平台信息有效后，
     * 提取设备型号，以其在原始 UA 中的位置作为匹配长度阈值，
     * 在 UC 浏览器设备索引中查找最匹配的设备。</p>
     *
     * @param request WURFL 请求对象
     * @return 匹配到的设备 ID，未匹配返回 "generic"
     */
    @Override
    protected String applyConclusiveMatch(WURFLRequest request) {
        // 通过规范化 UA 验证 UC 浏览器版本和平台有效性
        String normalizedUa = request.getNormalizedDeviceUserAgent();
        if (UserAgentUtils.getUcBrowserVersion(normalizedUa, false) == null) {
            return Constants.GENERIC;
        }
        if (!isValidUcwebPlatform(normalizedUa)) {
            return Constants.GENERIC;
        }

        // 使用原始 UA 进行 RIS 匹配（索引中存储的是原始 UA）
        String originalUa = request.getOriginalUserAgent();
        int matchLength = calculateMatchLength(originalUa);

        String matchedUa = StringMatchUtils.risMatch(
                this.getFilter().getIndex().getUserAgents(), originalUa, matchLength);

        String deviceId = Constants.GENERIC;
        if (matchedUa != null) {
            deviceId = this.getFilter().getIndex().getDeviceIdByUserAgent(matchedUa);
        }
        if (deviceId == null) {
            deviceId = Constants.GENERIC;
        }
        return deviceId;
    }

    /**
     * 根据设备型号在原始 UA 中的位置计算 RIS 匹配长度。
     * <p>优先以设备型号名称所在位置+型号长度为匹配边界，
     * 确保模型名必须完整匹配；若无法提取型号则回退到
     * Build/ 或 AppleWebKit 边界。</p>
     */
    private static int calculateMatchLength(String originalUa) {
        String androidModel = UserAgentUtils.getAndroidModel(originalUa);
        if (androidModel != null && !androidModel.isEmpty()) {
            String trimmed = androidModel.trim();
            if (trimmed.length() >= 3) {
                int modelPos = originalUa.indexOf(trimmed);
                if (modelPos >= 0) {
                    return modelPos + trimmed.length();
                }
            }
        }
        return Math.min(
                StringMatchUtils.indexOfOrLength(originalUa, " Build/"),
                StringMatchUtils.indexOfOrLength(originalUa, " AppleWebKit")
        );
    }

    /**
     * 执行 RIS 匹配（保留供父类或其他途径调用）。
     */
    @Override
    protected String risMatch(String userAgent) {
        if (UserAgentUtils.getUcBrowserVersion(userAgent, false) == null) {
            return null;
        }

        if (!isValidUcwebPlatform(userAgent)) {
            return null;
        }

        int matchLength = userAgent.indexOf("---") + 3;
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
    }

    /**
     * 验证 UCWeb U3 请求是否来自已知平台且设备信息完整。
     *
     * @return 平台信息有效返回 {@code true}
     */
    private static boolean isValidUcwebPlatform(String userAgent) {
        if (userAgent.contains("Windows Phone")) {
            return UserAgentUtils.getWindowsPhoneModel(userAgent) != null
                    && UserAgentUtils.getWindowsPhoneVersion(userAgent) != null;
        }
        if (userAgent.contains("Android")) {
            return UserAgentUtils.getAndroidModel(userAgent) != null
                    && getAndroidMajorVersion(userAgent) != null;
        }
        if (userAgent.contains("iPhone;")) {
            return UcwebU3Normalizer.IPHONE.matcher(userAgent).find();
        }
        if (userAgent.contains("iPad")) {
            return UcwebU3Normalizer.IPAD.matcher(userAgent).find();
        }
        return false;
    }

    /**
     * 恢复匹配策略：根据 UC 浏览器运行的平台（Windows Phone、Android、iPhone、iPad）
     * 和操作系统版本构造对应的通用设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        String ua = request.getNormalizedDeviceUserAgent();

        if (ua.contains("Windows Phone")) {
            return resolveDeviceId(buildWindowsPhoneDeviceId(ua), GENERIC_MS_PHONE_OS8_SUBUAWCWEB);
        }
        if (ua.contains("Android")) {
            // 优先尝试按设备型号查找具体设备
            String modelBasedDeviceId = lookupAndroidDeviceByModel(request.getOriginalUserAgent());
            if (modelBasedDeviceId != null) {
                return modelBasedDeviceId;
            }
            return resolveDeviceId(buildAndroidDeviceId(ua), GENERIC_UCWEB_ANDROID_VER1);
        }
        if (ua.contains("iPhone")) {
            return resolveDeviceId(buildIphoneDeviceId(ua), APPLE_IPHONE_VER1_SUBUAWCWEB);
        }
        if (ua.contains("iPad")) {
            return resolveDeviceId(buildIpadDeviceId(ua), APPLE_IPAD_VER1_SUBUAWCWEB);
        }
        return "generic_ucweb";
    }

    /**
     * 从原始 UA 中提取设备型号，尝试在 WURFL 模型中查找对应的具体设备。
     * <p>优先使用已知品牌前缀（如 oneplus_）构建设备 ID 候选，若存在则直接返回。</p>
     *
     * @param originalUa 原始的 User-Agent 字符串
     * @return 查找到的设备 ID，未找到返回 null
     */
    private String lookupAndroidDeviceByModel(String originalUa) {
        String model = UserAgentUtils.getAndroidModel(originalUa);
        if (model == null || model.trim().length() < 3) {
            return null;
        }
        Map<String, String> index = getModelNameIndex();
        if (index.isEmpty()) {
            return null;
        }
        String modelLower = model.trim().toLowerCase(Locale.ENGLISH);
        String deviceId = index.get(modelLower);
        if (deviceId != null) {
            LOG.debug("Recovery match found device by model: {} -> {}", model, deviceId);
            return deviceId;
        }
        return null;
    }

    private static String buildWindowsPhoneDeviceId(String ua) {
        String version = UserAgentUtils.getWindowsPhoneVersion(ua);
        if (StringUtils.isEmpty(version)) {
            LOG.debug("user agent \"{}\" has no version information", ua);
            return null;
        }
        String[] parts = version.split("\\.");
        if (parts.length < 2) {
            return null;
        }
        String major = parts[0];
        String minor = parts[1];
        return StringUtils.isEmpty(minor)
                ? "generic_ms_phone_os" + major + "_subuaucweb"
                : "generic_ms_phone_os" + major + "_" + minor + "_subuaucweb";
    }

    private static String buildAndroidDeviceId(String ua) {
        String majorVersion = getAndroidMajorVersion(ua);
        return majorVersion != null ? "generic_ucweb_android_ver" + majorVersion : null;
    }

    /**
     * 从 User-Agent 中提取 Android 主版本号，支持整数版本（如 "Android 14"）和点分版本（如 "Android 4.4"）。
     */
    private static String getAndroidMajorVersion(String ua) {
        Matcher matcher = ANDROID_VERSION_U3_PATTERN.matcher(ua);
        return matcher.find() ? matcher.group(1) : null;
    }

    private static String buildIphoneDeviceId(String ua) {
        Matcher matcher = IPHONE_IOS_VERSION.matcher(ua);
        return matcher.find() ? "apple_iphone_ver" + matcher.group(1) + "_subuaucweb" : null;
    }

    private static String buildIpadDeviceId(String ua) {
        Matcher matcher = IPAD_IOS_VERSION.matcher(ua);
        return matcher.find() ? "apple_ipad_ver1_sub" + matcher.group(1) + "_subuaucweb" : null;
    }

    private static String resolveDeviceId(String deviceId, String fallback) {
        return SUPPORTED_DEVICE_IDS.contains(deviceId) ? deviceId : fallback;
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "UcwebU3Matcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "UcwebU3";
    }
}
