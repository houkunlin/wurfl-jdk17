package com.scientiamobile.wurfl.core.updater;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * WURFL 更新管线，编排和执行一系列有序的更新任务。
 *
 * <p>更新管线定义了 WURFL 文件更新的完整流程，按顺序执行以下任务：
 * <ol>
 *   <li>检查远程是否有新版本（{@link CheckForNewWurflFileTask}）</li>
 *   <li>备份现有文件（{@link WurflBackupTask}）</li>
 *   <li>下载新文件（{@link NewWurflFileDownloadTask}）</li>
 *   <li>覆盖文件并验证一致性（{@link OverwriteAndCheckConsistencyTask}）</li>
 * </ol>
 * 如果任一任务失败，会自动触发回滚和清理操作。</p>
 */
public class UpdatePipeline {
    /**
     * 环境变量键名，用于指定 WURFL 更新 URL
     */
    public static final String ENV_SCIENTIA_URL = "WURFL_UPDATE_URL";
    /**
     * classpath 资源前缀标识
     */
    public static final String CLASSPATH_PREFIX = "classpath:";
    /**
     * 按顺序执行的更新任务列表
     */
    private List<UpdatePipelineTask> tasks;
    /**
     * 管线执行上下文，用于在各任务间传递数据
     */
    private Map<String, Object> context = new HashMap<>();
    /**
     * 新 WURFL 文件的下载 URL
     */
    private String newWurflUrl;
    /**
     * 原始 WURFL 文件的路径
     */
    private String originalWurflPath;
    /**
     * API 请求时使用的 User-Agent
     */
    private String apiUserAgent;
    /**
     * 连接超时时间，默认 10000 毫秒
     */
    private Integer connectionTimeoutMs = 10000;

    /**
     * 构造一个更新管线实例（不包含补丁路径和代理设置）。
     *
     * @param originalWurflPath 原始 WURFL 文件路径
     * @param newWurflUrl       新 WURFL 文件的下载 URL
     */
    public UpdatePipeline(String originalWurflPath, String newWurflUrl) {
        this(originalWurflPath, newWurflUrl, (String[]) null, null);
    }

    /**
     * 构造一个更新管线实例（包含补丁路径）。
     *
     * @param originalWurflPath 原始 WURFL 文件路径
     * @param newWurflUrl       新 WURFL 文件的下载 URL
     * @param patchPaths        补丁文件路径数组
     */
    public UpdatePipeline(String originalWurflPath, String newWurflUrl, String[] patchPaths) {
        this(originalWurflPath, newWurflUrl, patchPaths, null);
    }

    /**
     * 构造一个更新管线实例（包含代理设置）。
     *
     * @param originalWurflPath 原始 WURFL 文件路径
     * @param newWurflUrl       新 WURFL 文件的下载 URL
     * @param proxySettings     代理设置
     */
    public UpdatePipeline(String originalWurflPath, String newWurflUrl, ProxySettings proxySettings) {
        this(originalWurflPath, newWurflUrl, null, proxySettings);
    }

    /**
     * 构造一个更新管线实例（完整参数）。
     *
     * <p>初始化管线任务队列，按顺序注册以下任务：
     * <ol>
     *   <li>{@link CheckForNewWurflFileTask} — 检查远程更新</li>
     *   <li>{@link WurflBackupTask} — 备份当前文件</li>
     *   <li>{@link NewWurflFileDownloadTask} — 下载新文件</li>
     *   <li>{@link OverwriteAndCheckConsistencyTask} — 覆盖并校验一致性</li>
     * </ol></p>
     *
     * @param originalWurflPath 原始 WURFL 文件路径
     * @param newWurflUrl       新 WURFL 文件的下载 URL
     * @param patchPaths        补丁文件路径数组
     * @param proxySettings     代理设置
     */
    public UpdatePipeline(String originalWurflPath, String newWurflUrl, String[] patchPaths, ProxySettings proxySettings) {
        this.newWurflUrl = newWurflUrl;
        this.originalWurflPath = resolvePath(originalWurflPath);
        Validator.checkFileExtensions(originalWurflPath, newWurflUrl);
        this.tasks = new ArrayList<>();
        this.tasks.add(new CheckForNewWurflFileTask(proxySettings));
        this.tasks.add(new WurflBackupTask());
        this.tasks.add(new NewWurflFileDownloadTask(proxySettings));
        this.tasks.add(new OverwriteAndCheckConsistencyTask(patchPaths));
    }

    /**
     * 解析文件路径，将 classpath 协议转换为绝对路径。
     *
     * <p>如果路径中包含 "classpath:" 前缀，会将其转换为 classpath 资源的实际文件系统路径。
     * 当路径中包含多个 "classpath:" 时，只保留最后一个。</p>
     *
     * @param path 原始路径字符串
     * @return 解析后的绝对路径
     */
    static String resolvePath(String path) {
        if (path.contains("classpath:")) {
            int classpathPrefixIndex;
            classpathPrefixIndex = path.indexOf("classpath:");
            if (classpathPrefixIndex > 0) {
                path = path.substring(classpathPrefixIndex);
            }

            String classpathResourcePath = path.replaceFirst("classpath:", "");
            URL resourceUrl = UpdatePipeline.class.getResource(classpathResourcePath);
            if (resourceUrl == null) {
                throw new IllegalArgumentException("Classpath resource not found: " + classpathResourcePath);
            }
            return resourceUrl.getFile();
        } else {
            return path;
        }
    }

    /**
     * 从上下文中获取连接超时时间，如果未配置则返回默认值。
     *
     * <p>从管线执行上下文中读取键为 {@code CONN_TIMEOUT} 的值，
     * 如果值为空或非数字格式，则返回默认值 10000 毫秒。</p>
     *
     * @param context 管线执行上下文 Map
     * @return 连接超时毫秒数（默认 10000）
     */
    public static Integer getConnectionTimeoutMsOrDefault(Map<String, Object> context) {
        String timeoutValue;
        timeoutValue = (String) context.get("CONN_TIMEOUT");
        return StringUtils.isNotEmpty(timeoutValue) && StringUtils.isNumeric(timeoutValue) ? Integer.parseInt(timeoutValue) : 10000;
    }

    /**
     * 发送 HTTP HEAD 请求到远程 URL，检查文件是否已更新。
     *
     * <p>该方法为包级可见性，供更新组件（如 {@link CheckForNewWurflFileTask}）使用。
     * 支持通过代理发送请求，并携带 {@code If-Modified-Since} 和 {@code User-Agent} 头信息。
     * 仅允许请求 scientiamobile.com 域名或本地地址。</p>
     *
     * @param url             远程 URL
     * @param ifModifiedSince 本地文件最后修改时间的格式化字符串，用于条件请求
     * @param timeoutMs       连接和读取超时毫秒数
     * @param userAgent       请求的 User-Agent 头信息
     * @param proxySettings   代理设置，可以为 {@code null}
     * @return HTTP 响应状态码
     * @throws RuntimeException 如果连接过程中发生 I/O 错误
     */
    static int headRequest(URL url, String ifModifiedSince, int timeoutMs, String userAgent, ProxySettings proxySettings) {
        Validate.isTrue(url.getHost() != null && (url.getHost().endsWith(".scientiamobile.com") || url.getHost().equals("localhost") || url.getHost().equals("127.0.0.1")), "Invalid URL host: " + url.getHost());
        HttpsURLConnection connection = null;
        try {
            connection = proxySettings != null ? (HttpsURLConnection) url.openConnection(proxySettings.getProxy()) : (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setUseCaches(false);
            if (StringUtils.isNotEmpty(ifModifiedSince)) {
                connection.setRequestProperty("If-Modified-Since", ifModifiedSince);
            }

            if (StringUtils.isNotEmpty(userAgent)) {
                connection.setRequestProperty("User-Agent", userAgent);
            }

            connection.setConnectTimeout(timeoutMs);
            connection.setReadTimeout(timeoutMs);
            connection.connect();
            return connection.getResponseCode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

        }
    }

    /**
     * 设置 API 请求的 User-Agent。
     *
     * @param apiUserAgent API 请求时使用的 User-Agent 字符串
     */
    public void setApiUserAgent(String apiUserAgent) {
        this.apiUserAgent = apiUserAgent;
    }

    /**
     * 设置连接超时时间。
     *
     * @param connectionTimeoutMs 连接超时毫秒数
     */
    public void setConnectionTimeoutMs(Integer connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    /**
     * 执行更新管线，按顺序运行所有注册的任务。
     *
     * <p>该方法为同步执行，依次调用管线中的各任务。如果所有任务均成功完成，
     * 则返回状态 {@link UpdateResultStatus#UPDATED}。如果某个任务失败，
     * 会立即执行回滚任务，然后执行清理任务。</p>
     *
     * <p>无论管线执行成功与否，最终都会执行清理操作（{@link CleanupTask}）。</p>
     *
     * @return 更新结果，包含最终状态和描述信息
     */
    public synchronized UpdateResult execute() {
        Map<String, Object> context = this.context;
        if (!MapUtils.isEmpty(context)) {
            context.clear();
        }

        context.put("original_wurfl_path", this.originalWurflPath);
        context.put("new_wurfl_url", this.newWurflUrl);
        context.put("API_USER_AGENT", this.apiUserAgent);
        context.put("CONN_TIMEOUT", String.valueOf(this.connectionTimeoutMs));

        UpdateResult result;
        try {
            Iterator<UpdatePipelineTask> taskIterator = this.tasks.iterator();
            String taskResultStatus;
            do {
                if (!taskIterator.hasNext()) {
                    return new UpdateResult(UpdateResultStatus.UPDATED, "Wurfl file update completed on " + (new SimpleDateFormat("yyyy/MM/dd hh:mm:ss")).format(Calendar.getInstance().getTime()));
                }

                taskIterator.next().execute(this.context);
                taskResultStatus = (String) this.context.get("task_result_status");
            } while (StringUtils.isNotEmpty(taskResultStatus) && (taskResultStatus.equals(UpdateResultStatus.UPDATED.value()) || taskResultStatus.equals(UpdateResultStatus.PIPELINE_TASK_DONE.value())));

            (new RollbackTask()).execute(this.context);
            result = taskResultStatus.equals(UpdateResultStatus.UPDATE_SKIPPED.value()) ? new UpdateResult(UpdateResult.statusFromString(taskResultStatus), (String) this.context.get("task_result_message")) : new UpdateResult(UpdateResult.statusFromString(taskResultStatus), (String) this.context.get("task_error_message"));
        } finally {
            (new CleanupTask()).execute(this.context);
        }

        return result;
    }
}
