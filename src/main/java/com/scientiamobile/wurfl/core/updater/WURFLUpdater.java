package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.WURFLEngine;
import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.updater.exc.BadWurflExtensionException;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WURFLUpdater {
  private final Logger a = LoggerFactory.getLogger(getClass());
  
  private String b;
  
  private WURFLEngine c;
  
  private Frequency d = Frequency.DAILY;
  
  private PeriodicUpdateTask e;
  
  private ScheduledExecutorService f;
  
  private Integer g;
  
  private String h;
  
  private String[] i;
  
  private Calendar j;
  
  private ProxySettings k;
  
  public WURFLUpdater(WURFLEngine paramWURFLEngine, String paramString1, String paramString2) {
    this(paramWURFLEngine, paramString2);
    b();
  }
  
  public WURFLUpdater(WURFLEngine paramWURFLEngine, String paramString) {
    this.a.info("WURFL path passed to Updater constructor: " + this.h);
    this.c = paramWURFLEngine;
    this.h = UpdatePipeline.a(paramWURFLEngine.getRootPath());
    this.a.info("WURFL path passed to Updater constructor after resolve: " + this.h);
    this.b = paramString;
    b();
  }
  
  public WURFLUpdater(WURFLEngine paramWURFLEngine, String paramString1, String paramString2, ProxySettings paramProxySettings) {
    this(paramWURFLEngine, paramString2);
    this.k = paramProxySettings;
  }
  
  public WURFLUpdater(WURFLEngine paramWURFLEngine, String paramString, ProxySettings paramProxySettings) {
    this.a.info("WURFL path passed to Updater constructor: " + this.h);
    this.c = paramWURFLEngine;
    this.h = UpdatePipeline.a(paramWURFLEngine.getRootPath());
    this.a.info("WURFL path passed to Updater constructor after resolve: " + this.h);
    this.b = paramString;
    this.k = paramProxySettings;
    b();
  }
  
  public boolean usesProxy() {
    return (this.k != null);
  }
  
  public void setFirstExecution(Calendar paramCalendar) {
    this.j = paramCalendar;
  }
  
  public void setFrequency(Frequency paramFrequency) {
    this.d = paramFrequency;
  }
  
  public List getLastPeriodicUpdateResults() {
    return (this.e != null) ? this.e.getLastResults() : new ArrayList(0);
  }
  
  public void setConnectionTimeout(Integer paramInteger) {
    this.g = paramInteger;
  }
  
  public void setPatches(String[] paramArrayOfString) {
    this.i = paramArrayOfString;
  }
  
  public synchronized UpdateResult performUpdate() {
    UpdateResult updateResult;
    try {
      UpdatePipeline updatePipeline;
      (updatePipeline = usesProxy() ? new UpdatePipeline(this.h, this.b, this.k) : new UpdatePipeline(this.h, this.b)).setUserAgent(UserAgentUtils.createApiUserAgent(this.c));
      updatePipeline.setConnectionTimeout(this.g);
      if (!(updateResult = updatePipeline.execute()).isUpdateProcessSuccessful()) {
        this.a.warn(updateResult.getMessage());
      } else if (updateResult.a()) {
        WURFLUpdater wURFLUpdater;
        if (ArrayUtils.isEmpty((Object[])(wURFLUpdater = this).i)) {
          wURFLUpdater.c.reload(wURFLUpdater.h);
        } else {
          wURFLUpdater.c.reload(wURFLUpdater.h, wURFLUpdater.i);
        } 
      } 
    } catch (WURFLRuntimeException wURFLRuntimeException) {
      String str = "Unable to start WURFL updater, cause: " + wURFLRuntimeException.getMessage();
      this.a.error(str, (Throwable)wURFLRuntimeException);
      updateResult = new UpdateResult(UpdateResultStatus.PIPELINE_TASK_FAILED, str);
    } 
    return updateResult;
  }
  
  public synchronized void performPeriodicUpdate() {
    if (a()) {
      this.a.warn("Periodic update is already running. Shutdown the current update process before invoking this method");
      return;
    } 
    try {
      UpdatePipeline updatePipeline;
      (updatePipeline = usesProxy() ? new UpdatePipeline(this.h, this.b, this.k) : new UpdatePipeline(this.h, this.b)).setUserAgent(UserAgentUtils.createApiUserAgent(this.c));
      updatePipeline.setConnectionTimeout(this.g);
      this.f = Executors.newScheduledThreadPool(1);
      this.e = new PeriodicUpdateTask(this.c, updatePipeline, this.h);
      WURFLUpdater wURFLUpdater;
      this.f.scheduleAtFixedRate(this.e, ((wURFLUpdater = this).j != null) ? (wURFLUpdater.j.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) : 100L, this.d.value(), TimeUnit.MILLISECONDS);
      return;
    } catch (BadWurflExtensionException badWurflExtensionException) {
      String str = "Unable to start WURFL updater, cause: " + badWurflExtensionException.getMessage();
      this.a.error(str, (Throwable)badWurflExtensionException);
      return;
    } 
  }
  
  private boolean a() {
    return (this.f != null && !this.f.isTerminated());
  }
  
  public void stopPeriodicUpdate() {
    if (a()) {
      this.f.shutdown();
      this.f = null;
      return;
    } 
    this.a.warn("Cannot stop an updater that is not running. Command ignored");
  }
  
  private void b() {
    Validator.checkFileExtensions(this.h, this.b);
    Validator.a(this.h);
    Validator.a(this.b, this.c, this.k);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\updater\WURFLUpdater.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */