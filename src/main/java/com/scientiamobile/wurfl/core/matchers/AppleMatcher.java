package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Apple（苹果）设备匹配器。
 * <p>通过检查 User-Agent 是否包含 "iPhone"、"iPod" 或 "iPad"（排除 "Symbian" 和 "Nintendo"）
 * 来识别苹果品牌的移动设备。该匹配器是 WURFL 中最复杂的匹配器之一，因为它需要：</p>
 * <ul>
 *   <li>从 User-Agent 中解析 iOS 版本号</li>
 *   <li>从 User-Agent 中提取硬件标识（如 iPhone7,2）并映射到具体子型号</li>
 *   <li>支持 iPhone、iPad、iPod touch 三种产品线</li>
 * </ul>
 */

final class AppleMatcher extends AbstractMatcher {
    private static final String COREMEDIA_DEVICE_ID = "apple_iphone_coremedia_ver1";
    private static final String DEFAULT_IPHONE_DEVICE_ID = "apple_iphone_ver1";
    private static final String[] APPLE_DEVICE_KEYWORDS = new String[]{"iPhone", "iPod", "iPad"};
    private static final Pattern IOS_MAJOR_VERSION_PATTERN = Pattern.compile(" (\\d+)_\\d+[ _]");
    private static final Pattern APPLE_HARDWARE_ID_PATTERN = Pattern.compile("(?:iPhone|iPad|iPod) ?(\\d+,\\d+)");
    private static final List<String> SUPPORTED_DEVICE_IDS = new ArrayList<>();
    private static final Map<String, String> IPHONE_HW_TO_SUBHW = new HashMap<>();
    private static final Map<String, String> IPAD_HW_TO_SUBHW = new HashMap<>();
    private static final Map<String, String> IPOD_HW_TO_SUBHW = new HashMap<>();
    private static final List<String> SUPPORTED_SUBHW_DEVICE_IDS = new ArrayList<>();

    static {
        SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver1");
        SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver2");
        SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver3");
        SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver4");
        SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver5");
        SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver6");
        SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver7");
        SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver8");
        SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver9");
        SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver10");
        SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver11");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_subua32");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub42");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub5");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub6");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub7");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub8");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub9");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub10");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub11");
        SUPPORTED_DEVICE_IDS.add(DEFAULT_IPHONE_DEVICE_ID);
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver2");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver3");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver4");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver5");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver6");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver7");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver8");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver9");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver10");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver11");
        IPHONE_HW_TO_SUBHW.put("1,1", "2g");
        IPHONE_HW_TO_SUBHW.put("1,2", "3g");
        IPHONE_HW_TO_SUBHW.put("2,1", "3gs");
        IPHONE_HW_TO_SUBHW.put("3,1", "4");
        IPHONE_HW_TO_SUBHW.put("3,2", "4");
        IPHONE_HW_TO_SUBHW.put("3,3", "4");
        IPHONE_HW_TO_SUBHW.put("4,1", "4s");
        IPHONE_HW_TO_SUBHW.put("5,1", "5");
        IPHONE_HW_TO_SUBHW.put("5,2", "5");
        IPHONE_HW_TO_SUBHW.put("5,3", "5c");
        IPHONE_HW_TO_SUBHW.put("5,4", "5c");
        IPHONE_HW_TO_SUBHW.put("6,1", "5s");
        IPHONE_HW_TO_SUBHW.put("6,2", "5s");
        IPHONE_HW_TO_SUBHW.put("7,1", "6plus");
        IPHONE_HW_TO_SUBHW.put("7,2", "6");
        IPHONE_HW_TO_SUBHW.put("8,2", "6splus");
        IPHONE_HW_TO_SUBHW.put("8,1", "6s");
        IPHONE_HW_TO_SUBHW.put("8,4", "se");
        IPHONE_HW_TO_SUBHW.put("9,1", "7");
        IPHONE_HW_TO_SUBHW.put("9,2", "7plus");
        IPHONE_HW_TO_SUBHW.put("9,3", "7");
        IPHONE_HW_TO_SUBHW.put("9,4", "7plus");
        IPHONE_HW_TO_SUBHW.put("10,1", "8");
        IPHONE_HW_TO_SUBHW.put("10,2", "8plus");
        IPHONE_HW_TO_SUBHW.put("10,3", "x");
        IPHONE_HW_TO_SUBHW.put("10,4", "8");
        IPHONE_HW_TO_SUBHW.put("10,5", "8plus");
        IPHONE_HW_TO_SUBHW.put("10,6", "x");
        IPAD_HW_TO_SUBHW.put("1,1", "1");
        IPAD_HW_TO_SUBHW.put("2,1", "2");
        IPAD_HW_TO_SUBHW.put("2,2", "2");
        IPAD_HW_TO_SUBHW.put("2,3", "2");
        IPAD_HW_TO_SUBHW.put("2,4", "2");
        IPAD_HW_TO_SUBHW.put("2,5", "mini1");
        IPAD_HW_TO_SUBHW.put("2,6", "mini1");
        IPAD_HW_TO_SUBHW.put("2,7", "mini1");
        IPAD_HW_TO_SUBHW.put("3,1", "3");
        IPAD_HW_TO_SUBHW.put("3,2", "3");
        IPAD_HW_TO_SUBHW.put("3,3", "3");
        IPAD_HW_TO_SUBHW.put("3,4", "4");
        IPAD_HW_TO_SUBHW.put("3,5", "4");
        IPAD_HW_TO_SUBHW.put("3,6", "4");
        IPAD_HW_TO_SUBHW.put("4,1", "air");
        IPAD_HW_TO_SUBHW.put("4,2", "air");
        IPAD_HW_TO_SUBHW.put("4,3", "air");
        IPAD_HW_TO_SUBHW.put("4,4", "mini2");
        IPAD_HW_TO_SUBHW.put("4,5", "mini2");
        IPAD_HW_TO_SUBHW.put("4,6", "mini2");
        IPAD_HW_TO_SUBHW.put("4,7", "mini3");
        IPAD_HW_TO_SUBHW.put("4,8", "mini3");
        IPAD_HW_TO_SUBHW.put("4,9", "mini3");
        IPAD_HW_TO_SUBHW.put("5,3", "air2");
        IPAD_HW_TO_SUBHW.put("5,4", "air2");
        IPAD_HW_TO_SUBHW.put("5,1", "mini4");
        IPAD_HW_TO_SUBHW.put("5,2", "mini4");
        IPAD_HW_TO_SUBHW.put("6,7", "pro");
        IPAD_HW_TO_SUBHW.put("6,8", "pro");
        IPAD_HW_TO_SUBHW.put("6,3", "pro97");
        IPAD_HW_TO_SUBHW.put("6,4", "pro97");
        IPAD_HW_TO_SUBHW.put("6,11", "5");
        IPAD_HW_TO_SUBHW.put("6,12", "5");
        IPAD_HW_TO_SUBHW.put("7,1", "pro2");
        IPAD_HW_TO_SUBHW.put("7,2", "pro2");
        IPAD_HW_TO_SUBHW.put("7,3", "pro2105");
        IPAD_HW_TO_SUBHW.put("7,4", "pro2105");
        IPOD_HW_TO_SUBHW.put("1,1", "1");
        IPOD_HW_TO_SUBHW.put("2,1", "2");
        IPOD_HW_TO_SUBHW.put("3,1", "3");
        IPOD_HW_TO_SUBHW.put("4,1", "4");
        IPOD_HW_TO_SUBHW.put("5,1", "5");
        IPOD_HW_TO_SUBHW.put("7,1", "6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_subhw1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub42_subhw1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub43_subhw1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub43_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub5_subhw1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub5_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub51_subhw1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub51_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub51_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub6_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub6_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub6_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub6_subhwmini1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub61_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub61_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub61_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub61_subhwmini1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub7_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub7_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub7_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub7_subhwmini1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub7_subhwmini2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub7_subhwair");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub71_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub71_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub71_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub71_subhwmini1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub71_subhwmini2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub71_subhwair");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_subhwmini1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_subhwmini2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_subhwair");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_1_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_1_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_1_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_1_subhwair");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_1_subhwair2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_1_subhwmini1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_1_subhwmini2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_1_subhwmini3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_2_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_2_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_2_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_2_subhwair");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_2_subhwair2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_2_subhwmini1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_2_subhwmini2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_2_subhwmini3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_3_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_3_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_3_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_3_subhwair");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_3_subhwair2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_3_subhwmini1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_3_subhwmini2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_3_subhwmini3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_4_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_4_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_4_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_4_subhwair");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_4_subhwair2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_4_subhwmini1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_4_subhwmini2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_4_subhwmini3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_subhwair");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_subhwair2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_subhwmini1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_subhwmini2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_subhwmini3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_subhwmini4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_1_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_1_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_1_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_1_subhwair");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_1_subhwair2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_1_subhwmini1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_1_subhwmini2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_1_subhwmini3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_1_subhwmini4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_1_subhwpro");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_2_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_2_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_2_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_2_subhwair");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_2_subhwair2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_2_subhwmini1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_2_subhwmini2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_2_subhwmini3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_2_subhwmini4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_2_subhwpro");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_3_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_3_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_3_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_3_subhwair");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_3_subhwair2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_3_subhwmini1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_3_subhwmini2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_3_subhwmini3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_3_subhwmini4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_3_subhwpro");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub9_3_subhwpro97");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_subhwair");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_subhwair2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_subhwmini2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_subhwmini3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_subhwmini4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_subhwpro");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_subhwpro97");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_1_subhwmini2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_1_subhwmini3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_1_subhwmini4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_1_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_1_subhwair");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_1_subhwair2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_1_subhwpro");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_1_subhwpro97");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_1_subhwpro2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_1_subhwpro2105");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver1_subhw2g");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver2_subhw2g");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver2_subhw3g");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver2_1_subhw2g");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver2_1_subhw3g");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver2_2_subhw2g");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver2_2_subhw3g");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver3_subhw2g");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver3_subhw3g");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver3_subhw3gs");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver3_1_subhw3g");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver3_1_subhw3gs");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver4_subhw3g");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver4_subhw3gs");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver4_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver4_1_subhw3g");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver4_1_subhw3gs");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver4_1_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver4_2_subhw3g");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver4_2_subhw3gs");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver4_2_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver4_3_subhw3gs");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver4_3_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver5_subhw3gs");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver5_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver5_subhw4s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver5_1_subhw3gs");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver5_1_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver5_1_subhw4s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver6_subhw3gs");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver6_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver6_subhw4s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver6_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver6_1_subhw3gs");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver6_1_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver6_1_subhw4s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver6_1_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver7_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver7_subhw4s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver7_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver7_subhw5c");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver7_subhw5s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver7_1_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver7_1_subhw4s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver7_1_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver7_1_subhw5c");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver7_1_subhw5s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_subhw4s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_subhw5c");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_subhw5s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_subhw6plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_subua802_subhw4s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_subua802_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_subua802_subhw5c");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_subua802_subhw5s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_subua802_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_subua802_subhw6plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_1_subhw4s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_1_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_1_subhw5c");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_1_subhw5s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_1_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_1_subhw6plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_2_subhw4s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_2_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_2_subhw5c");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_2_subhw5s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_2_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_2_subhw6plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_3_subhw4s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_3_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_3_subhw5c");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_3_subhw5s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_3_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_3_subhw6plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_4_subhw4s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_4_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_4_subhw5c");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_4_subhw5s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_4_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver8_4_subhw6plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_subhw4s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_subhw5c");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_subhw5s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_subhw6plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_subhw6s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_subhw6splus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_1_subhw4s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_1_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_1_subhw5c");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_1_subhw5s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_1_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_1_subhw6plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_1_subhw6s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_1_subhw6splus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_2_subhw4s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_2_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_2_subhw5c");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_2_subhw5s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_2_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_2_subhw6plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_2_subhw6s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_2_subhw6splus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_3_subhw4s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_3_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_3_subhw5s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_3_subhw5c");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_3_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_3_subhw6plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_3_subhw6s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_3_subhw6splus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver9_3_subhwse");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_subhw5c");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_subhw5s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_subhw6plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_subhw6s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_subhw6splus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_subhwse");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_subhw7");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_subhw7plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_1_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_1_subhw5c");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_1_subhw5s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_1_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_1_subhw6plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_1_subhw6s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_1_subhw6splus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_1_subhwse");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_1_subhw7");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_1_subhw7plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_2_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_2_subhw5c");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_2_subhw5s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_2_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_2_subhw6plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_2_subhw6s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_2_subhw6splus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_2_subhwse");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_2_subhw7");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_2_subhw7plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_3_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_3_subhw5c");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_3_subhw5s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_3_subhwse");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_3_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_3_subhw6plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_3_subhw6s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_3_subhw6splus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_3_subhw7");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver10_3_subhw7plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_subhw5s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_subhwse");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_subhw6plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_subhw6s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_subhw6splus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_subhw7");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_subhw7plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_subhw8");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_subhw8plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_subhwx");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_1_subhw5s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_1_subhwse");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_1_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_1_subhw6plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_1_subhw6s");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_1_subhw6splus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_1_subhw7");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_1_subhw7plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_1_subhw8");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_1_subhw8plus");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_iphone_ver11_1_subhwx");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_1_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_1_subhwair");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_1_subhwair2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_1_subhwmini2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_1_subhwmini3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_1_subhwmini4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_1_subhwpro");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_1_subhwpro97");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_2_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_2_subhwair");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_2_subhwair2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_2_subhwmini2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_2_subhwmini3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_2_subhwmini4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_2_subhwpro");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_2_subhwpro97");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_3_subhwmini2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_3_subhwmini3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_3_subhwmini4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_3_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_3_subhwair");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_3_subhwair2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_3_subhwpro97");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_3_subhwpro");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_3_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_3_subhwpro2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub10_3_subhwpro2105");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_subhwmini2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_subhwmini3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_subhwmini4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_subhwair");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_subhwair2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_subhwpro");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_subhwpro97");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_subhwpro2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub11_subhwpro2105");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver1_subhw1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver2_subhw1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver2_1_subhw1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver2_1_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver2_2_subhw1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver2_2_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver3_subhw1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver3_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver3_1_subhw1");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver3_1_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver3_1_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver4_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver4_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver4_1_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver4_1_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver4_1_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver4_2_subhw2");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver4_2_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver4_2_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver4_3_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver4_3_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver5_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver5_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver5_1_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver5_1_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver6_subhw3");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver6_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver6_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver6_1_subhw4");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver6_1_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver7_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver7_1_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver8_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver8_1_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver8_2_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver8_3_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver8_4_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver9_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver9_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver9_1_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver9_1_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver9_2_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver9_2_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver9_3_subhw5");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver9_3_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver10_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver10_1_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver10_2_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver10_3_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver11_subhw6");
        SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipod_touch_ver11_1_subhw6");
    }

    public AppleMatcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
        super(userAgentNormalizer, wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.addAll(SUPPORTED_DEVICE_IDS);
        requiredDeviceIds.addAll(SUPPORTED_SUBHW_DEVICE_IDS);
        requiredDeviceIds.add(COREMEDIA_DEVICE_ID);
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, APPLE_DEVICE_KEYWORDS) && !StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Symbian", "Nintendo");
    }
    /**
     * 执行确定匹配：先尝试从 User-Agent 中解析硬件标识（如 iPhone7,2），
     * 如果有则映射到具体的子型号 ID（例如 "apple_iphone_ver7_subhw6"）。
     * 然后使用 RIS 算法在 iOS 版本对应的位置截断进行匹配。
     *
     * @param request WURFL 请求对象
     * @return 匹配到的设备 ID，含可能的子型号信息
     */
    @Override
    protected String applyConclusiveMatch(WURFLRequest request) {
        String userAgent = request.getNormalizedDeviceUserAgent();
        String subHw = null;
        Matcher hwMatcher;
        hwMatcher = APPLE_HARDWARE_ID_PATTERN.matcher(userAgent);
        if (hwMatcher.find()) {
            String hwId = hwMatcher.group(1);
            if (userAgent.contains("iPod")) {
                subHw = IPOD_HW_TO_SUBHW.get(hwId);
            } else if (userAgent.contains("iPad")) {
                subHw = IPAD_HW_TO_SUBHW.get(hwId);
            } else if (userAgent.contains("iPhone")) {
                subHw = IPHONE_HW_TO_SUBHW.get(hwId);
            }
        }

        int matchLength;
        matchLength = StringMatchUtils.firstChar(userAgent, '_');
        if (matchLength < 0) {
            matchLength = userAgent.indexOf("like Mac OS X;");
            if (matchLength >= 0) {
                matchLength += 14;
            } else {
                matchLength = userAgent.length();
            }
        } else {
            ++matchLength;
        }

        String matchedUserAgent = StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
        if (matchedUserAgent != null) {
            String matchedDeviceId = this.getFilter().getIndex().getDeviceIdByUserAgent(matchedUserAgent);
            if (subHw != null && matchedDeviceId != null) {
                String subHwDeviceId = matchedDeviceId + "_subhw" + subHw;
                if (SUPPORTED_SUBHW_DEVICE_IDS.contains(subHwDeviceId)) {
                    return subHwDeviceId;
                }
            }

            return matchedDeviceId;
        } else {
            return null;
        }
    }
    /**
     * 恢复匹配策略：根据 User-Agent 中的 iOS 主版本号构造对应的通用设备 ID。
     * <p>分别处理 CoreMedia、iPod、iPad 和 iPhone 四种情况，
     * 对于 iPad 还会特别处理 iOS 3.x 和 4.x 的特殊设备 ID 格式。</p>
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        String userAgent = request.getNormalizedDeviceUserAgent();
        Matcher versionMatcher = IOS_MAJOR_VERSION_PATTERN.matcher(userAgent);
        String majorVersion = "-1";
        if (versionMatcher.find()) {
            majorVersion = versionMatcher.group(1);
        }

        if (userAgent.contains("CoreMedia")) {
            return COREMEDIA_DEVICE_ID;
        } else if (userAgent.contains("iPod")) {
            userAgent = "apple_ipod_touch_ver".concat(majorVersion);
            return SUPPORTED_DEVICE_IDS.contains(userAgent) ? userAgent : "apple_ipod_touch_ver".concat("1");
        } else if (userAgent.contains("iPad")) {
            if ("3".equals(majorVersion)) {
                return "apple_ipad_ver1".concat("_subua32");
            } else if ("4".equals(majorVersion)) {
                return "apple_ipad_ver1".concat("_sub42");
            } else {
                userAgent = "apple_ipad_ver1".concat("_sub").concat(majorVersion);
                return SUPPORTED_DEVICE_IDS.contains(userAgent) ? userAgent : "apple_ipad_ver1";
            }
        } else {
            userAgent = "apple_iphone_ver".concat(majorVersion);
            return SUPPORTED_DEVICE_IDS.contains(userAgent) ? userAgent : DEFAULT_IPHONE_DEVICE_ID;
        }
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "AppleMatcher";
    }
    /**
     * 获取桶匹配器名称。
     *
     * @return 固定返回 {@code "Apple"}
     */
    @Override
    public String getBucketMatcherName() {
        return "Apple";
    }
}
