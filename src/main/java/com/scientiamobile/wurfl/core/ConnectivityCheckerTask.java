package com.scientiamobile.wurfl.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * 连通性检查的后台任务，向 ScientiaMobile 服务端发送使用统计信息。
 * <p>通过 HTTPS POST 请求将 JSON 负载发送到连通性检查 API 端点，
 * 并在请求完成后通知 {@link CheckConnection} 中的观察者。</p>
 */

final class ConnectivityCheckerTask implements Runnable {
    /**
     * 日志记录器
     */
    private static final Logger log = LoggerFactory.getLogger(ConnectivityCheckerTask.class);
    /**
     * 关联的连通性检查器实例
     */
    private CheckConnection checkConnection;

    ConnectivityCheckerTask(CheckConnection checkConnection) {
        this.checkConnection = checkConnection;
    }

    /**
     * 执行连通性检查任务。
     * <p>创建 HTTPS 连接，发送 JSON 负载，获取响应码并通知观察者。</p>
     */
    @Override
    public void run() {
        try {
            HttpsURLConnection connection = createConnection();
            byte[] payloadBytes = this.checkConnection.getPayloadJson().getBytes("UTF-8");
            try {
                sendPayload(connection, payloadBytes);
                Integer responseCode = connection.getResponseCode();
                this.checkConnection.notifyObservers(responseCode);
            } catch (IOException e) {
                log.warn("Connectivity check request failed", e);
            } finally {
                connection.disconnect();
            }
        } catch (Exception e) {
            log.error("Connectivity check failed", e);
        }
    }

    /**
     * 创建并配置到连通性检查服务端的 HTTPS 连接。
     *
     * @return 已配置的 HTTPS 连接
     * @throws IOException 连接创建失败
     */

    private static HttpsURLConnection createConnection() throws IOException {
        URL url = URI.create("https://core.scientiamobile.com/api/v2/checkconnectivity/update").toURL();
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(120000);
        connection.setRequestProperty("charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "application/json");
        return connection;
    }

    /**
     * 向连接发送 JSON 负载数据。
     *
     * @param connection   HTTPS 连接
     * @param payloadBytes 负载字节数组
     * @throws IOException 发送失败
     */

    private static void sendPayload(HttpsURLConnection connection, byte[] payloadBytes) throws IOException {
        connection.setRequestProperty("Content-Length", String.valueOf(payloadBytes.length));
        try (DataOutputStream output = new DataOutputStream(connection.getOutputStream())) {
            output.write(payloadBytes);
        }
    }
}
