package com.scientiamobile.wurfl.core;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ConnectivityCheckerTask implements Runnable {
   private static final Logger log = LoggerFactory.getLogger(ConnectivityCheckerTask.class);
   private CheckConnection checkConnection;

   ConnectivityCheckerTask(CheckConnection checkConnection) {
      this.checkConnection = checkConnection;
   }

   @Override
   public final void run() {
      try {
         HttpsURLConnection connection;
         URL url = URI.create("https://core.scientiamobile.com/api/v2/checkconnectivity/update").toURL();
         (connection = (HttpsURLConnection)url.openConnection()).setRequestMethod("POST");
         connection.setUseCaches(false);
         connection.setDoOutput(true);
         connection.setConnectTimeout(10000);
         connection.setReadTimeout(120000);
         byte[] payloadBytes = this.checkConnection.getPayloadJson().getBytes("UTF-8");
         connection.setRequestProperty("charset", "UTF-8");
         connection.setRequestProperty("Content-Length", String.valueOf(payloadBytes.length));
         connection.setRequestProperty("Content-Type", "application/json");
         connection.setUseCaches(false);
         DataOutputStream output = null;
         boolean failed = false;

         label64: {
            try {
               failed = true;
               (output = new DataOutputStream(connection.getOutputStream())).write(payloadBytes);
               Integer responseCode = connection.getResponseCode();
               this.checkConnection.notifyObservers(responseCode);
               failed = false;
               break label64;
            } catch (IOException e) {
               failed = false;
            } finally {
               if (failed) {
                  connection.disconnect();
                  if (output != null) {
                     output.close();
                  }

               }
            }

            connection.disconnect();
            if (output != null) {
               output.close();
               return;
            }

            return;
         }

         connection.disconnect();
         output.close();
      } catch (Exception e) {
         log.error("Connectivity check failed", e);
      }
   }
}
