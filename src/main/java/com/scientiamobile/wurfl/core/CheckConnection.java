package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.DefaultWURFLModel;
import com.scientiamobile.wurfl.core.resource.ResourceUtils;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * WURFL 连通性检查器，用于定期向 ScientiaMobile 后台发送使用情况统计信息。
 * <p>仅当构建 ID 以 {@code ch} 开头（表示需要检查）时启用。
 * 在应用启动时收集 API 版本、WURFL 版本、操作系统、服务器平台等信息，
 * 通过后台线程异步发送到 ScientiaMobile 的连通性检查服务。</p>
 */

public class CheckConnection {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(CheckConnection.class);
    /**
     * 连通性检查观察者列表
     */
    private final List<CheckConnectionObserver> observers = new ArrayList<>();
    /**
     * 发送给服务端的 JSON 负载字符串
     */
    private String payloadJson;
    /**
     * 操作系统名称和版本
     */
    private String osNameAndVersion = System.getProperty("os.name") + " " + System.getProperty("os.version");
    /**
     * 解析出的应用服务器平台名称（如 Tomcat、Jetty 等）
     */
    private String platformName;
    /**
     * 是否启用连通性检查
     */
    private boolean enabled;

    /**
     * 默认构造函数，初始化平台名称并检查连通性是否启用
     */
    public CheckConnection() {
        this.platformName = resolvePlatformName();
        this.enabled = ResourceUtils.getFullBuildId().startsWith("ch");
    }

    /**
     * 通过分析 classpath 关键字，推断当前运行的应用服务器平台名称。
     *
     * @return 平台名称（如 Tomcat、JBoss/WildFly、Jetty 等），未知时返回 "Command line"
     */

    private static String resolvePlatformName() {
        if (StringUtils.isNotEmpty(System.getProperty("jboss.boot.library.list"))) {
            return "JBoss/WildFly";
        }
        if (StringUtils.isNotEmpty(System.getProperty("oracle.j2ee.home"))) {
            return "OC4j/Oracle AS";
        }
        String classPathLowerCase = System.getProperty("java.class.path").toLowerCase(Locale.ENGLISH);
        if (classPathLowerCase.contains("websphere") || classPathLowerCase.contains("web sphere")) {
            return "IBM WebSphere";
        }
        if (classPathLowerCase.contains("tomcat") || classPathLowerCase.contains("juli")) {
            return "Tomcat";
        }
        if (classPathLowerCase.contains("jetty")) {
            return "Jetty";
        }
        return "Command line";
    }

    /**
     * 向 JSON 构建器追加一个键值对字段。
     *
     * @param builder       JSON 字符串构建器
     * @param key           字段名
     * @param value         字段值
     * @param hasMoreFields 是否还有后续字段（添加逗号分隔符）
     * @return 构建器实例
     */

    private static StringBuilder appendJsonField(StringBuilder builder, String key, String value, boolean hasMoreFields) {
        builder.append("\"").append(key).append("\": \"").append(value).append("\"");
        if (hasMoreFields) {
            builder.append(", ");
        } else {
            builder.append(" ");
        }

        return builder;
    }

    /**
     * 获取本机主机名，获取失败时返回 "unknown"。
     *
     * @return 主机名字符串
     */

    private static String getHostNameOrUnknown() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (IOException | RuntimeException e) {
            return "unknown";
        }
    }

    /**
     * 初始化连通性检查，构建发送给服务端的 JSON 负载。
     * <p>如果连通性检查未启用或 WURFL 模型不是 {@link DefaultWURFLModel} 类型，则跳过。</p>
     *
     * @param wurflEngine WURFL 引擎实例
     * @param wurflModel  WURFL 数据模型
     */

    public void setup(WURFLEngine wurflEngine, WURFLModel wurflModel) {
        if (!this.enabled || !(wurflModel instanceof DefaultWURFLModel defaultWURFLModel)) {
            return;
        }
        String wurflSmid = StringUtils.defaultIfEmpty(defaultWURFLModel.getSmid(), "unknown");
        String wurflVersion = extractVersionInfo(wurflEngine);
        this.payloadJson = buildPayload(wurflSmid, wurflVersion);
    }

    /**
     * 从 WURFL 引擎中提取精简的版本信息字符串。
     *
     * @param wurflEngine WURFL 引擎实例
     * @return 精简版本字符串
     */

    private static String extractVersionInfo(WURFLEngine wurflEngine) {
        String wurflVersion = wurflEngine.getWURFLUtils().getVersion();
        int versionIndex = wurflVersion.indexOf("for WURFL");
        if (versionIndex == -1) {
            return wurflVersion;
        }
        int versionEnd = wurflVersion.indexOf(";");
        return versionEnd != -1 ? wurflVersion.substring(versionIndex, versionEnd) : wurflVersion.substring(versionIndex);
    }

    /**
     * 构建发送给连通性检查服务端的 JSON 负载字符串。
     * <p>包含 API 标识、WURFL 标识、API 名称和版本、WURFL 版本、主机名、操作系统和平台信息。</p>
     *
     * @param wurflSmid    WURFL 数据的 SMID
     * @param wurflVersion WURFL 版本字符串
     * @return JSON 格式的负载
     */

    private String buildPayload(String wurflSmid, String wurflVersion) {
        StringBuilder payloadBuilder = appendJsonField(
                appendJsonField(
                        appendJsonField(
                                appendJsonField(
                                        appendJsonField(
                                                appendJsonField(
                                                        appendJsonField(
                                                                appendJsonField(new StringBuilder("{ "), "api-smid", ResourceUtils.getBuildId(), true),
                                                                "wurfl-smid", wurflSmid, true
                                                        ),
                                                        "api", this.getApiName(), true
                                                ),
                                                "api_ver", "1.9.1.0", true
                                        ),
                                        "wurfl", wurflVersion, true
                                ),
                                "host", getHostNameOrUnknown(), true
                        ),
                        "os", this.osNameAndVersion, true
                ),
                "platform", this.platformName, false
        ).append(" }");
        return payloadBuilder.toString();
    }

    /**
     * 异步执行连通性检查。
     * <p>通过线程池启动 {@link ConnectivityCheckerTask} 发送 HTTP 请求。</p>
     */

    public void check() {
        if (this.enabled) {
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

            try {
                executorService.execute(new ConnectivityCheckerTask(this));
            } finally {
                executorService.shutdown();
            }

        }
    }

    /**
     * 添加连通性检查的观察者。
     *
     * @param observer 观察者实例
     */

    public synchronized void addObserver(CheckConnectionObserver observer) {
        this.observers.add(observer);
    }

    /**
     * 通知所有已注册的观察者。
     *
     * @param argument 通知参数
     */
    public synchronized void notifyObservers(Object argument) {

        for (CheckConnectionObserver observer : this.observers) {
            observer.update(this, argument);
            logger.info("observer notified");
        }

    }

    /**
     * 获取 JSON 负载字符串。
     *
     * @return JSON 格式的负载数据
     */
    final String getPayloadJson() {
        return this.payloadJson;
    }

    /**
     * 获取 API 名称，根据 classpath 中是否包含 "wurfl-scala" 判断是 Java API 还是 Scala API。
     *
     * @return API 名称
     */

    public String getApiName() {
        String classPath;
        classPath = System.getProperty("java.class.path");
        return classPath != null && classPath.contains("wurfl-scala") ? "WURFL_Scala_API" : "WURFL_Java_API";
    }

    /**
     * 连通性检查观察者接口。
     * <p>当连通性检查完成时，通知注册的观察者。</p>
     */

    public interface CheckConnectionObserver {
        /**
         * 当连通性检查完成时，通知观察者。
         *
         * @param checkConnection 连通性检查器实例
         * @param argument        通知参数（通常为 HTTP 响应码）
         */
        void update(CheckConnection checkConnection, Object argument);
    }
}
