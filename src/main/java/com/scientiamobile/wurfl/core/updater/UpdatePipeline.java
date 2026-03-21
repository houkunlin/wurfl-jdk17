package com.scientiamobile.wurfl.core.updater;

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
  
  private Map b = new HashMap<Object, Object>();
  
  private String c;
  
  private String d;
  
  private String e;
  
  private Integer f = Integer.valueOf(10000);
  
  public UpdatePipeline(String paramString1, String paramString2) {
    this.c = paramString2;
    this.d = a(paramString1);
    Validator.checkFileExtensions(paramString1, paramString2);
    this.a = new ArrayList();
    this.a.add(new CheckForNewWurflFileTask());
    this.a.add(new WurflBackupTask());
    this.a.add(new NewWurflFileDownloadTask());
    this.a.add(new OverwriteAndCheckConsistencyTask());
  }
  
  public UpdatePipeline(String paramString1, String paramString2, String[] paramArrayOfString) {
    this.c = paramString2;
    this.d = a(paramString1);
    Validator.checkFileExtensions(paramString1, paramString2);
    String[] arrayOfString = paramArrayOfString;
    UpdatePipeline updatePipeline;
    (updatePipeline = this).a = new ArrayList();
    updatePipeline.a.add(new CheckForNewWurflFileTask());
    updatePipeline.a.add(new WurflBackupTask());
    updatePipeline.a.add(new NewWurflFileDownloadTask());
    updatePipeline.a.add(new OverwriteAndCheckConsistencyTask(arrayOfString));
  }
  
  public UpdatePipeline(String paramString1, String paramString2, ProxySettings paramProxySettings) {
    this.c = paramString2;
    this.d = a(paramString1);
    Validator.checkFileExtensions(paramString1, paramString2);
    this.a = new ArrayList();
    this.a.add(new CheckForNewWurflFileTask(paramProxySettings));
    this.a.add(new WurflBackupTask());
    this.a.add(new NewWurflFileDownloadTask(paramProxySettings));
    this.a.add(new OverwriteAndCheckConsistencyTask());
  }
  
  public UpdatePipeline(String paramString1, String paramString2, String[] paramArrayOfString, ProxySettings paramProxySettings) {
    this.c = paramString2;
    this.d = a(paramString1);
    Validator.checkFileExtensions(paramString1, paramString2);
    ProxySettings proxySettings = paramProxySettings;
    String[] arrayOfString = paramArrayOfString;
    UpdatePipeline updatePipeline;
    (updatePipeline = this).a = new ArrayList();
    updatePipeline.a.add(new CheckForNewWurflFileTask(proxySettings));
    updatePipeline.a.add(new WurflBackupTask());
    updatePipeline.a.add(new NewWurflFileDownloadTask(proxySettings));
    updatePipeline.a.add(new OverwriteAndCheckConsistencyTask(arrayOfString));
  }
  
  public void setUserAgent(String paramString) {
    this.e = paramString;
  }
  
  public void setConnectionTimeout(Integer paramInteger) {
    this.f = paramInteger;
  }
  
  static String a(String paramString) {
    if (paramString.contains("classpath:")) {
      int i;
      if ((i = paramString.indexOf("classpath:")) > 0)
        paramString = paramString.substring(i); 
      StrBuilder strBuilder;
      (strBuilder = new StrBuilder()).append(paramString);
      strBuilder.replaceFirst("classpath:", "");
      return UpdatePipeline.class.getResource(strBuilder.toString()).getFile();
    } 
    return paramString;
  }
  
  public synchronized UpdateResult execute() {
    null = this.b;
    UpdatePipeline updatePipeline = this;
    if (!MapUtils.isEmpty(null))
      null.clear(); 
    null.put("original_wurfl_path", updatePipeline.d);
    null.put("new_wurfl_url", updatePipeline.c);
    null.put("API_USER_AGENT", updatePipeline.e);
    null.put("CONN_TIMEOUT", String.valueOf(updatePipeline.f));
    try {
      Iterator<UpdatePipelineTask> iterator = this.a.iterator();
      while (iterator.hasNext()) {
        ((UpdatePipelineTask)iterator.next()).execute(this.b);
        String str1;
        String str2;
        if (!((!StringUtils.isEmpty(str2 = str1 = (String)this.b.get("task_result_status")) && (str2.equals(UpdateResultStatus.UPDATED.value()) || str2.equals(UpdateResultStatus.PIPELINE_TASK_DONE.value()))) ? 1 : 0)) {
          (new RollbackTask()).execute(this.b);
          return str1.equals(UpdateResultStatus.UPDATE_SKIPPED.value()) ? new UpdateResult(UpdateResult.statusFromString(str1), (String)this.b.get("task_result_message")) : new UpdateResult(UpdateResult.statusFromString(str1), (String)this.b.get("task_error_message"));
        } 
      } 
      return new UpdateResult(UpdateResultStatus.UPDATED, "Wurfl file update completed on " + (new SimpleDateFormat("yyyy/MM/dd hh:mm:ss")).format(Calendar.getInstance().getTime()));
    } finally {
      (new CleanupTask()).execute(this.b);
    } 
  }
  
  public static Integer safeGetConnectionTimeout(Map paramMap) {
    String str;
    return (StringUtils.isEmpty(str = (String)paramMap.get("CONN_TIMEOUT")) || !StringUtils.isNumeric(str)) ? Integer.valueOf(10000) : Integer.valueOf(Integer.parseInt(str));
  }
  
  static int a(URL paramURL, String paramString1, int paramInt, String paramString2, ProxySettings paramProxySettings) {
    HttpsURLConnection httpsURLConnection;
    (httpsURLConnection = (paramProxySettings != null) ? (HttpsURLConnection)paramURL.openConnection(paramProxySettings.getProxy()) : (HttpsURLConnection)paramURL.openConnection()).setRequestMethod("HEAD");
    httpsURLConnection.setUseCaches(false);
    if (StringUtils.isNotEmpty(paramString1))
      httpsURLConnection.setRequestProperty("If-Modified-Since", paramString1); 
    if (StringUtils.isNotEmpty(paramString2))
      httpsURLConnection.setRequestProperty("User-Agent", paramString2); 
    httpsURLConnection.setConnectTimeout(paramInt);
    httpsURLConnection.setReadTimeout(paramInt);
    try {
      httpsURLConnection.connect();
      int i = httpsURLConnection.getResponseCode();
    } finally {
      if (httpsURLConnection != null)
        httpsURLConnection.disconnect(); 
    } 
    return paramURL;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\updater\UpdatePipeline.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
