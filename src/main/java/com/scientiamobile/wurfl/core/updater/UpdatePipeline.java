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

public class UpdatePipeline {
   public static final String ENV_SCIENTIA_URL = "WURFL_UPDATE_URL";
   public static final String CLASSPATH_PREFIX = "classpath:";
   private List<UpdatePipelineTask> tasks;
   private Map<String, Object> context = new HashMap<>();
   private String newWurflUrl;
   private String originalWurflPath;
   private String apiUserAgent;
   private Integer connectionTimeoutMs = 10000;

   public UpdatePipeline(String originalWurflPath, String newWurflUrl) {
      this.newWurflUrl = newWurflUrl;
      this.originalWurflPath = resolvePath(originalWurflPath);
      Validator.checkFileExtensions(originalWurflPath, newWurflUrl);
      this.tasks = new ArrayList<>();
      this.tasks.add(new CheckForNewWurflFileTask());
      this.tasks.add(new WurflBackupTask());
      this.tasks.add(new NewWurflFileDownloadTask());
      this.tasks.add(new OverwriteAndCheckConsistencyTask());
   }

   public UpdatePipeline(String originalWurflPath, String newWurflUrl, String[] patchPaths) {
      this.newWurflUrl = newWurflUrl;
      this.originalWurflPath = resolvePath(originalWurflPath);
      Validator.checkFileExtensions(originalWurflPath, newWurflUrl);
      this.tasks = new ArrayList<>();
      this.tasks.add(new CheckForNewWurflFileTask());
      this.tasks.add(new WurflBackupTask());
      this.tasks.add(new NewWurflFileDownloadTask());
      this.tasks.add(new OverwriteAndCheckConsistencyTask(patchPaths));
   }

   public UpdatePipeline(String originalWurflPath, String newWurflUrl, ProxySettings proxySettings) {
      this.newWurflUrl = newWurflUrl;
      this.originalWurflPath = resolvePath(originalWurflPath);
      Validator.checkFileExtensions(originalWurflPath, newWurflUrl);
      this.tasks = new ArrayList<>();
      this.tasks.add(new CheckForNewWurflFileTask(proxySettings));
      this.tasks.add(new WurflBackupTask());
      this.tasks.add(new NewWurflFileDownloadTask(proxySettings));
      this.tasks.add(new OverwriteAndCheckConsistencyTask());
   }

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

   public void setApiUserAgent(String apiUserAgent) {
      this.apiUserAgent = apiUserAgent;
   }

   public void setConnectionTimeoutMs(Integer connectionTimeoutMs) {
      this.connectionTimeoutMs = connectionTimeoutMs;
   }

   static String resolvePath(String path) {
      if (path.contains("classpath:")) {
         int classpathPrefixIndex;
         if ((classpathPrefixIndex = path.indexOf("classpath:")) > 0) {
            path = path.substring(classpathPrefixIndex);
         }

         String classpathResourcePath = path.replaceFirst("classpath:", "");
         return UpdatePipeline.class.getResource(classpathResourcePath).getFile();
      } else {
         return path;
      }
   }

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

            ((UpdatePipelineTask)taskIterator.next()).execute(this.context);
            taskResultStatus = (String)this.context.get("task_result_status");
         } while(StringUtils.isNotEmpty(taskResultStatus) && (taskResultStatus.equals(UpdateResultStatus.UPDATED.value()) || taskResultStatus.equals(UpdateResultStatus.PIPELINE_TASK_DONE.value())));

         (new RollbackTask()).execute(this.context);
         result = taskResultStatus.equals(UpdateResultStatus.UPDATE_SKIPPED.value()) ? new UpdateResult(UpdateResult.statusFromString(taskResultStatus), (String)this.context.get("task_result_message")) : new UpdateResult(UpdateResult.statusFromString(taskResultStatus), (String)this.context.get("task_error_message"));
      } finally {
         (new CleanupTask()).execute(this.context);
      }

      return result;
   }

   public static Integer getConnectionTimeoutMsOrDefault(Map<String, Object> context) {
      String timeoutValue;
      return StringUtils.isNotEmpty(timeoutValue = (String)context.get("CONN_TIMEOUT")) && StringUtils.isNumeric(timeoutValue) ? Integer.parseInt(timeoutValue) : 10000;
   }

   static int headRequest(URL url, String ifModifiedSince, int timeoutMs, String userAgent, ProxySettings proxySettings) {
      HttpsURLConnection connection = null;
      try {
         (connection = proxySettings != null ? (HttpsURLConnection)url.openConnection(proxySettings.getProxy()) : (HttpsURLConnection)url.openConnection()).setRequestMethod("HEAD");
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
}
