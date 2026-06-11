package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 桌面应用程序匹配器。
 * <p>识别桌面应用程序的 User-Agent，主要包括 Microsoft Office 系列（MSOffice、
 * Microsoft Office）和其他包含 "office" 或 "DesktopApp " 的桌面应用。
 * 通过检查请求是否非移动浏览器且 User-Agent 包含相关关键字来识别。</p>
 */

public final class DesktopApplicationMatcher extends MatcherBase {
    private static final String GENERIC_WEB_BROWSER = "generic_web_browser";
    private static final String GENERIC_DESKTOP_APPLICATION = "generic_desktop_application";
    private static final String MS_OFFICE = "ms_office";
    private static final Set<String> SUPPORTED_DEVICE_IDS = new HashSet<>();
    private static final Pattern MSOFFICE_PATTERN = Pattern.compile("MSOffice ([0-9]+)");
    private static final Pattern MICROSOFT_OFFICE_PATTERN = Pattern.compile("Microsoft Office/([0-9.]+)");

    static {
        SUPPORTED_DEVICE_IDS.add(GENERIC_DESKTOP_APPLICATION);
        SUPPORTED_DEVICE_IDS.add(MS_OFFICE);
    }

    public DesktopApplicationMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>(SUPPORTED_DEVICE_IDS);
        requiredDeviceIds.add(GENERIC_WEB_BROWSER);
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsMobileBrowser() && StringMatchUtils.containsAnyOf(request.getCleanedDeviceUserAgent(), "Microsoft Office", "MSOffice", "office", "DesktopApp ");
    }

    /**
     * 执行 RIS 匹配.
     */
    @Override
    protected String risMatch(String userAgent) {
        Matcher msOfficeMatcher = MSOFFICE_PATTERN.matcher(userAgent);
        Matcher microsoftOfficeMatcher = MICROSOFT_OFFICE_PATTERN.matcher(userAgent);
        if (msOfficeMatcher.find()) {
            userAgent = userAgent.substring(userAgent.indexOf("MSOffice"));
            int matchLength = StringMatchUtils.firstCloseParenthesis(userAgent);
            if (matchLength != -1) {
                return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
            }
        } else {
            if (microsoftOfficeMatcher.find()) {
                userAgent = userAgent.substring(userAgent.indexOf("Microsoft Office"));
                int dotIndex = userAgent.indexOf(46);
                if (dotIndex != -1) {
                    return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, dotIndex);
                }
            }
        }

        return "generic";
    }

    /**
     * 恢复匹配策略：根据 User-Agent 包含 "Office"/"office" 或 "DesktopApp " 关键字
     * 返回对应的桌面应用程序通用设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        String deviceUserAgent = request.getDeviceUserAgent();
        if (StringMatchUtils.containsAnyOf(deviceUserAgent, "Office", "office")) {
            return MS_OFFICE;
        } else {
            return deviceUserAgent.contains("DesktopApp ") ? GENERIC_DESKTOP_APPLICATION : GENERIC_WEB_BROWSER;
        }
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "DesktopApplicationMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "DesktopApplication";
    }
}
