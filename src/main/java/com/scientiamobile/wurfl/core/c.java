package com.scientiamobile.wurfl.core;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * Connectivity checker task for updating connectivity status
 */
final class ConnectivityChecker implements Runnable {
    private final CheckConnection checkConnection;

    ConnectivityChecker(CheckConnection checkConnection) {
        this.checkConnection = checkConnection;
    }

    @Override
    public void run() {
        HttpsURLConnection connection = null;
        DataOutputStream outputStream = null;
        try {
            // Create connection to update endpoint
            connection = (HttpsURLConnection) new URL("https://core.scientiamobile.com/api/v1/checkconnectivity/update").openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(120000);

            // Prepare request body
            byte[] requestBody = checkConnection.a(checkConnection).getBytes("UTF-8");
            connection.setRequestProperty("charset", "UTF-8");
            connection.setRequestProperty("Content-Length", String.valueOf(requestBody.length));
            connection.setRequestProperty("Content-Type", "application/json");

            // Write request body
            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.write(requestBody);
            outputStream.flush();

            // Get response code and notify observers
            int responseCode = connection.getResponseCode();
            checkConnection.notifyObservers(responseCode);
        } catch (IOException e) {
            // Handle IO exception
            checkConnection.notifyObservers(-1);
        } catch (Exception e) {
            // Handle general exception
            checkConnection.notifyObservers(-1);
        } finally {
            // Clean up resources
            if (connection != null) {
                connection.disconnect();
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // Ignore close exception
                }
            }
        }
    }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\c.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
