package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 电子邮件客户端 User-Agent 匹配器。
 * <p>识别各种电子邮件客户端程序，包括 Thunderbird、Outlook、Lotus Notes、Eudora、Evolution、PocoMail、The Bat!、Postbox、Airmail、Spark 等。</p>
 */

public class EmailClientUserAgentMatcher extends MatcherBase {
    public static final List<String> EMAIL_CLIENTS;
    private static final String MOZILLA_THUNDERBIRD = "mozilla_thunderbird";
    private static final String MS_OUTLOOK = "ms_outlook";
    private static final Pattern MICROSOFT_OUTLOOK_PATTERN = Pattern.compile("Microsoft Outlook ([0-9]+).");
    private static final Pattern MAC_OUTLOOK_PATTERN = Pattern.compile("^MacOutlook ([0-9]+).");
    private static final String[] MOBILE_KEYWORDS;
    private static final List<String> REQUIRED_DEVICE_IDS;

    static {
        List<String> mobileKeywords = UserAgentUtils.getMobileKeywords();
        MOBILE_KEYWORDS = mobileKeywords.toArray(new String[0]);
        REQUIRED_DEVICE_IDS = new ArrayList<>();
        REQUIRED_DEVICE_IDS.add(MOZILLA_THUNDERBIRD);
        REQUIRED_DEVICE_IDS.add(MS_OUTLOOK);
        REQUIRED_DEVICE_IDS.add("mac_outlook");
        REQUIRED_DEVICE_IDS.add("ms_outlook_ios_ver1");
        REQUIRED_DEVICE_IDS.add("outlook_express");
        REQUIRED_DEVICE_IDS.add("generic_email_client");
        REQUIRED_DEVICE_IDS.add("generic_mobile_email_client");
        REQUIRED_DEVICE_IDS.add("lotus_notes_ver1");
        REQUIRED_DEVICE_IDS.add("eudora_ver1");
        REQUIRED_DEVICE_IDS.add("evolution_ver1");
        REQUIRED_DEVICE_IDS.add("pocomail_ver1");
        REQUIRED_DEVICE_IDS.add("thebat_ver1");
        REQUIRED_DEVICE_IDS.add("postbox_ver1");
        REQUIRED_DEVICE_IDS.add("airmail_ver1");
        REQUIRED_DEVICE_IDS.add("airmail_ios_ver1");
        REQUIRED_DEVICE_IDS.add("spark_ios_ver1");
        EMAIL_CLIENTS = Collections.unmodifiableList(Arrays.asList("Thunderbird", "Outlook", "Lotus-Notes", "Eudora/", "Evolution/", "PocoMail/", "The Bat!", "Postbox/", "Airmail"));
    }

    public EmailClientUserAgentMatcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
        super(userAgentNormalizer, wurflModel);
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String deviceUserAgent = request.getDeviceUserAgent();
        return request._internalIsEmailClient() && !StringMatchUtils.containsAnyOf(deviceUserAgent, "Office", "office") || deviceUserAgent.contains("Spark/") && deviceUserAgent.contains("CFNetwork/");
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "EmailClientMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "EmailClient";
    }

    /**
     * 执行 RIS 匹配.
     */
    @Override
    protected String risMatch(String userAgent) {
        if (userAgent.contains("Thunderbird")) {
            userAgent = userAgent.substring(userAgent.indexOf("Thunderbird"));
        }

        if (MICROSOFT_OUTLOOK_PATTERN.matcher(userAgent).find()) {
            userAgent = userAgent.substring(userAgent.indexOf("Microsoft Outlook"));
        }

        if (MAC_OUTLOOK_PATTERN.matcher(userAgent).find()) {
            userAgent = userAgent.substring(userAgent.indexOf("MacOutlook"));
        }

        int dotIndex = userAgent.indexOf(".");
        return dotIndex != -1 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, dotIndex) : "generic";
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        return new HashSet<>(REQUIRED_DEVICE_IDS);
    }
    /**
     * 恢复匹配策略：根据 User-Agent 中包含的邮件客户端标识返回对应的设备 ID。
     * <p>支持 Thunderbird、Outlook（多平台）、Lotus Notes、Eudora、Evolution、PocoMail、
     * The Bat!、Postbox、Airmail、Spark 等客户端的识别。</p>
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        String deviceUserAgent = request.getDeviceUserAgent();
        if (deviceUserAgent.contains("Thunderbird")) {
            return MOZILLA_THUNDERBIRD;
        } else if (deviceUserAgent.contains("Outlook-Express")) {
            return "outlook_express";
        } else if (deviceUserAgent.contains("MacOutlook")) {
            return "mac_outlook";
        } else if (deviceUserAgent.contains("Outlook") && deviceUserAgent.contains("CFNetwork")) {
            return "ms_outlook_ios_ver1";
        } else if (deviceUserAgent.contains("Outlook")) {
            return MS_OUTLOOK;
        } else if (deviceUserAgent.contains("Lotus-Notes")) {
            return "lotus_notes_ver1";
        } else if (deviceUserAgent.contains("Eudora/")) {
            return "eudora_ver1";
        } else if (deviceUserAgent.contains("Evolution/")) {
            return "evolution_ver1";
        } else if (deviceUserAgent.contains("PocoMail/")) {
            return "pocomail_ver1";
        } else if (deviceUserAgent.contains("The Bat!")) {
            return "thebat_ver1";
        } else if (deviceUserAgent.contains("Postbox/")) {
            return "postbox_ver1";
        } else if (deviceUserAgent.contains("Airmail") && deviceUserAgent.contains("CFNetwork") && !deviceUserAgent.contains("x86_64")) {
            return "airmail_ios_ver1";
        } else if (deviceUserAgent.contains("Airmail")) {
            return "airmail_ver1";
        } else if (deviceUserAgent.contains("Spark/") && deviceUserAgent.contains("CFNetwork")) {
            return "spark_ios_ver1";
        } else {
            return StringMatchUtils.containsAnyOf(deviceUserAgent, MOBILE_KEYWORDS) ? "generic_mobile_email_client" : "generic_email_client";
        }
    }
}
