package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.utils.ExceptionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Map;

/**
 * 从远程服务器下载新版本 WURFL 文件的管线任务。
 * <p>通过 HTTP GET 请求从 ScientiaMobile 更新服务器下载最新的 WURFL 文件，
 * 保存为临时文件（原路径加 {@code .wtmp} 后缀）。下载完成后将临时文件路径
 * 存入上下文，供后续的覆盖和一致性检查任务使用。</p>
 */

public class NewWurflFileDownloadTask implements UpdatePipelineTask {
    private static final Logger log = LoggerFactory.getLogger(NewWurflFileDownloadTask.class);
    private ProxySettings proxySettings;

    public NewWurflFileDownloadTask() {
    }

    public NewWurflFileDownloadTask(ProxySettings proxySettings) {
        this.proxySettings = proxySettings;
    }

    /**
     * 执行新 WURFL 文件的下载。
     * <p>建立 HTTPS 连接并发起 GET 请求，将响应流写入临时文件。
     * 下载完成后会设置文件的最后修改时间以匹配服务端时间戳。</p>
     *
     * @param context 管线执行上下文 Map
     */
    public void execute(Map<String, Object> context) {
        HttpsURLConnection connection = null;
        try {
            String tempWurflPath = context.get("original_wurfl_path") + ".wtmp";
            Integer connectionTimeoutMs = UpdatePipeline.getConnectionTimeoutMsOrDefault(context);
            URL newWurflUrl = URI.create((String) context.get("new_wurfl_url")).toURL();
            Validate.isTrue(newWurflUrl.getHost() != null && (newWurflUrl.getHost().endsWith(".scientiamobile.com") || newWurflUrl.getHost().equals("localhost") || newWurflUrl.getHost().equals("127.0.0.1")), "Invalid URL host: " + newWurflUrl.getHost());
            connection = this.proxySettings != null ? (HttpsURLConnection) newWurflUrl.openConnection(this.proxySettings.getProxy()) : (HttpsURLConnection) newWurflUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(connectionTimeoutMs);
            connection.setReadTimeout(connectionTimeoutMs);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                File tempWurflFile = new File(tempWurflPath).getCanonicalFile();
                if (!tempWurflFile.exists() && !tempWurflFile.createNewFile()) {
                    log.warn("Failed to create temp WURFL file: {}", tempWurflFile.getAbsolutePath());
                }

                FileUtils.copyInputStreamToFile(connection.getInputStream(), tempWurflFile);
                if (!tempWurflFile.setLastModified(connection.getLastModified())) {
                    log.warn("Failed to set last modified time on temp WURFL file");
                }
                log.info("WURFL updater: new WURFL file download completed");
                context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
                context.put("new_wurfl_temp_path", tempWurflPath);
            } else {
                log.error("Wurfl updater: unable to download new WURFL file, HTTP RESPONSE code: {}", responseCode);
                context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
                context.put("task_error_message", "Invalid HTTP response code " + responseCode);
            }
        } catch (Exception e) {
            context.put("task_error_message", "Error trying to check if a new WURFL file is available: " + ExceptionUtils.getFirstAvailableMessage(e));
            context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
