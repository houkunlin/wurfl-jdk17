package com.scientiamobile.wurfl.core;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

final class c implements Runnable {
   // $FF: synthetic field
   private CheckConnection a;

   c(CheckConnection var1) {
      this.a = var1;
      super();
   }

   public final void run() {
      try {
         HttpsURLConnection var1;
         (var1 = (HttpsURLConnection)(new URL("https://core.scientiamobile.com/api/v2/checkconnectivity/update")).openConnection()).setRequestMethod("POST");
         var1.setUseCaches(false);
         var1.setDoOutput(true);
         var1.setConnectTimeout(10000);
         var1.setReadTimeout(120000);
         byte[] var2 = CheckConnection.a(this.a).getBytes("UTF-8");
         var1.setRequestProperty("charset", "UTF-8");
         var1.setRequestProperty("Content-Length", String.valueOf(var2.length));
         var1.setRequestProperty("Content-Type", "application/json");
         var1.setUseCaches(false);
         DataOutputStream var3 = null;
         boolean var7 = false;

         label64: {
            try {
               var7 = true;
               (var3 = new DataOutputStream(var1.getOutputStream())).write(var2);
               Integer var11 = var1.getResponseCode();
               this.a.notifyObservers(var11);
               var7 = false;
               break label64;
            } catch (IOException var8) {
               var7 = false;
            } finally {
               if (var7) {
                  var1.disconnect();
                  if (var3 != null) {
                     var3.close();
                  }

               }
            }

            var1.disconnect();
            if (var3 != null) {
               var3.close();
               return;
            }

            return;
         }

         var1.disconnect();
         var3.close();
      } catch (Exception var10) {
      }
   }
}
