package com.scientiamobile.wurfl.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

final class ConnectivityCheckerTask implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(ConnectivityCheckerTask.class);
    private CheckConnection checkConnection;

    ConnectivityCheckerTask(CheckConnection checkConnection) {
        this.checkConnection = checkConnection;
    }

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

    private static void sendPayload(HttpsURLConnection connection, byte[] payloadBytes) throws IOException {
        connection.setRequestProperty("Content-Length", String.valueOf(payloadBytes.length));
        try (DataOutputStream output = new DataOutputStream(connection.getOutputStream())) {
            output.write(payloadBytes);
        }
    }
}
