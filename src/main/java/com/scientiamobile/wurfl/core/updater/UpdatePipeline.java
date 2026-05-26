package com.scientiamobile.wurfl.core.updater;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

public class UpdatePipeline {
   public static final String ENV_SCIENTIA_URL = "WURFL_UPDATE_URL";
   public static final String CLASSPATH_PREFIX = "classpath:";
   private List a;
   private Map b = new HashMap();
   private String c;
   private String d;
   private String e;
   private Integer f = 10000;

   public UpdatePipeline(String var1, String var2) {
      this.c = var2;
      this.d = a(var1);
      Validator.checkFileExtensions(var1, var2);
      this.a = new ArrayList();
      this.a.add(new CheckForNewWurflFileTask());
      this.a.add(new WurflBackupTask());
      this.a.add(new NewWurflFileDownloadTask());
      this.a.add(new OverwriteAndCheckConsistencyTask());
   }

   public UpdatePipeline(String var1, String var2, String[] var3) {
      this.c = var2;
      this.d = a(var1);
      Validator.checkFileExtensions(var1, var2);
      this.a = new ArrayList();
      this.a.add(new CheckForNewWurflFileTask());
      this.a.add(new WurflBackupTask());
      this.a.add(new NewWurflFileDownloadTask());
      this.a.add(new OverwriteAndCheckConsistencyTask(var3));
   }

   public UpdatePipeline(String var1, String var2, ProxySettings var3) {
      this.c = var2;
      this.d = a(var1);
      Validator.checkFileExtensions(var1, var2);
      this.a = new ArrayList();
      this.a.add(new CheckForNewWurflFileTask(var3));
      this.a.add(new WurflBackupTask());
      this.a.add(new NewWurflFileDownloadTask(var3));
      this.a.add(new OverwriteAndCheckConsistencyTask());
   }

   public UpdatePipeline(String var1, String var2, String[] var3, ProxySettings var4) {
      this.c = var2;
      this.d = a(var1);
      Validator.checkFileExtensions(var1, var2);
      this.a = new ArrayList();
      this.a.add(new CheckForNewWurflFileTask(var4));
      this.a.add(new WurflBackupTask());
      this.a.add(new NewWurflFileDownloadTask(var4));
      this.a.add(new OverwriteAndCheckConsistencyTask(var3));
   }

   public void setUserAgent(String var1) {
      this.e = var1;
   }

   public void setConnectionTimeout(Integer var1) {
      this.f = var1;
   }

   static String a(String var0) {
      if (var0.contains("classpath:")) {
         int var1;
         if ((var1 = var0.indexOf("classpath:")) > 0) {
            var0 = var0.substring(var1);
         }

         StrBuilder var2;
         (var2 = new StrBuilder()).append(var0);
         var2.replaceFirst("classpath:", "");
         return UpdatePipeline.class.getResource(var2.toString()).getFile();
      } else {
         return var0;
      }
   }

   public synchronized UpdateResult execute() {
      Map var1 = this.b;
      if (!MapUtils.isEmpty(var1)) {
         var1.clear();
      }

      var1.put("original_wurfl_path", this.d);
      var1.put("new_wurfl_url", this.c);
      var1.put("API_USER_AGENT", this.e);
      var1.put("CONN_TIMEOUT", String.valueOf(this.f));

      UpdateResult var7;
      try {
         Iterator var6 = this.a.iterator();

         String var2;
         String var3;
         do {
            if (!var6.hasNext()) {
               UpdateResult var8 = new UpdateResult(UpdateResultStatus.UPDATED, "Wurfl file update completed on " + (new SimpleDateFormat("yyyy/MM/dd hh:mm:ss")).format(Calendar.getInstance().getTime()));
               return var8;
            }

            ((UpdatePipelineTask)var6.next()).execute(this.b);
         } while(!StringUtils.isEmpty(var3 = var2 = (String)this.b.get("task_result_status")) && (var3.equals(UpdateResultStatus.UPDATED.value()) || var3.equals(UpdateResultStatus.PIPELINE_TASK_DONE.value())));

         (new RollbackTask()).execute(this.b);
         var7 = var2.equals(UpdateResultStatus.UPDATE_SKIPPED.value()) ? new UpdateResult(UpdateResult.statusFromString(var2), (String)this.b.get("task_result_message")) : new UpdateResult(UpdateResult.statusFromString(var2), (String)this.b.get("task_error_message"));
      } finally {
         (new CleanupTask()).execute(this.b);
      }

      return var7;
   }

   public static Integer safeGetConnectionTimeout(Map var0) {
      String var1;
      return !StringUtils.isEmpty(var1 = (String)var0.get("CONN_TIMEOUT")) && StringUtils.isNumeric(var1) ? Integer.parseInt(var1) : 10000;
   }

   static int a(URL var0, String var1, int var2, String var3, ProxySettings var4) {
      HttpsURLConnection var8 = null;
      try {
         (var8 = var4 != null ? (HttpsURLConnection)var0.openConnection(var4.getProxy()) : (HttpsURLConnection)var0.openConnection()).setRequestMethod("HEAD");
         var8.setUseCaches(false);
         if (StringUtils.isNotEmpty(var1)) {
            var8.setRequestProperty("If-Modified-Since", var1);
         }

         if (StringUtils.isNotEmpty(var3)) {
            var8.setRequestProperty("User-Agent", var3);
         }

         var8.setConnectTimeout(var2);
         var8.setReadTimeout(var2);
         var8.connect();
         return var8.getResponseCode();
      } catch (IOException var10) {
         throw new RuntimeException(var10);
      } finally {
         if (var8 != null) {
            var8.disconnect();
         }

      }
   }
}
