package com.scientiamobile.wurfl.core;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

final class c implements Runnable {
  c(CheckConnection paramCheckConnection) {}
  
  public final void run() {
    try {
      HttpsURLConnection httpsURLConnection;
      (httpsURLConnection = (HttpsURLConnection)(new URL("https://core.scientiamobile.com/api/v1/checkconnectivity/update")).openConnection()).setRequestMethod("POST");
      httpsURLConnection.setUseCaches(false);
      httpsURLConnection.setDoOutput(true);
      httpsURLConnection.setConnectTimeout(10000);
      httpsURLConnection.setReadTimeout(120000);
      null = CheckConnection.a(this.a).getBytes("UTF-8");
      httpsURLConnection.setRequestProperty("charset", "UTF-8");
      httpsURLConnection.setRequestProperty("Content-Length", String.valueOf(null.length));
      httpsURLConnection.setRequestProperty("Content-Type", "application/json");
      httpsURLConnection.setUseCaches(false);
      DataOutputStream dataOutputStream = null;
      try {
        (dataOutputStream = new DataOutputStream(httpsURLConnection.getOutputStream())).write(null);
        Integer integer = Integer.valueOf(httpsURLConnection.getResponseCode());
        this.a.notifyObservers(integer);
        return;
      } catch (IOException iOException) {
      
      } finally {
        httpsURLConnection.disconnect();
        if (dataOutputStream != null)
          dataOutputStream.close(); 
      } 
      return;
    } catch (Exception exception) {
      return;
    } 
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\c.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */