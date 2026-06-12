package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.utils.ExceptionUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

/**
 * 检查远程服务器是否存在新版本 WURFL 文件的管线任务。
 * <p>通过发送 HTTP HEAD 请求到 ScientiaMobile 更新服务器，携带本地文件的最后修改时间
 * 作为 {@code If-Modified-Since} 头，判断服务端是否有更新的文件版本。</p>
 *
 * <p>根据 HTTP 响应码决定后续流程：</p>
 * <ul>
 *   <li>200 - 存在新版本，继续执行后续下载任务</li>
 *   <li>304 - 文件未变更，跳过本次更新</li>
 *   <li>402 - 许可证已过期，终止更新</li>
 * </ul>
 */

public class CheckForNewWurflFileTask implements UpdatePipelineTask {
    static final ThreadLocal<SimpleDateFormat> LAST_MODIFIED_FORMAT = ThreadLocal.withInitial(() -> {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf;
    });
    private static final Logger log = LoggerFactory.getLogger(CheckForNewWurflFileTask.class);

    private ProxySettings proxySettings;

    public CheckForNewWurflFileTask() {
    }

    public CheckForNewWurflFileTask(ProxySettings proxySettings) {
        this.proxySettings = proxySettings;
    }

    /**
     * 执行检查新版本 WURFL 文件的任务。
     * <p>从上下文中读取本地文件路径和远程 URL，通过 HEAD 请求检测服务端文件变更情况，
     * 并将结果状态写入上下文供管线调度使用。</p>
     *
     * @param context 管线执行上下文 Map
     */
    public void execute(Map<String, Object> context) {
        String originalWurflPath = (String) context.get("original_wurfl_path");

        try {
            File originalWurflFile = new File(originalWurflPath).getCanonicalFile();
            String ifModifiedSince = originalWurflFile.exists() ? LAST_MODIFIED_FORMAT.get().format(new Date(originalWurflFile.lastModified())) : "";
            URL newWurflUrl = URI.create((String) context.get("new_wurfl_url")).toURL();
            Validate.isTrue(newWurflUrl.getHost() != null && (newWurflUrl.getHost().endsWith(".scientiamobile.com") || newWurflUrl.getHost().equals("localhost") || newWurflUrl.getHost().equals("127.0.0.1")), "Invalid URL host: " + newWurflUrl.getHost());
            Integer connectionTimeoutMs = UpdatePipeline.getConnectionTimeoutMsOrDefault(context);
            int responseCode = UpdatePipeline.headRequest(newWurflUrl, ifModifiedSince, connectionTimeoutMs, (String) context.get("API_USER_AGENT"), this.proxySettings);
            if (responseCode == 200) {
                context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
            } else if (responseCode == 304) {
                context.put("task_result_status", UpdateResultStatus.UPDATE_SKIPPED.value());
                log.info("WURFL file is already updated to the latest version, exiting file update process");
            } else if (responseCode == 402) {
                context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
                context.put("task_error_message", "Your WURFL LICENSE EXPIRED, WURFL file will not be updated. Please  renew you license to access newer versions of WURFL file and APIs");
                log.info("WURFL license is invalid or expired, exiting update process");
            } else {
                context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
                context.put("task_error_message", "Invalid HTTP response code " + responseCode);
            }
        } catch (Exception e) {
            if (e instanceof java.net.SocketTimeoutException) {
                context.put("task_error_message", "Error trying to check if a new WURFL file is available: connection timed out");
            } else {
                context.put("task_error_message", "Error trying to check if a new WURFL file is available: " + ExceptionUtils.getFirstAvailableMessage(e));
            }

            context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
        }
    }
}
