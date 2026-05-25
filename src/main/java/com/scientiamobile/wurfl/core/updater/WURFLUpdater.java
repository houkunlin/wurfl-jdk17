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
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WURFLUpdater {
   private final Logger a;
   private String b;
   private WURFLEngine c;
   private Frequency d;
   private PeriodicUpdateTask e;
   private ScheduledExecutorService f;
   private Integer g;
   private String h;
   private String[] i;
   private Calendar j;
   private ProxySettings k;

   public WURFLUpdater(WURFLEngine var1, String var2, String var3) {
      this(var1, var3);
      this.b();
   }

   public WURFLUpdater(WURFLEngine var1, String var2) {
      this.a = LoggerFactory.getLogger(this.getClass());
      this.d = Frequency.DAILY;
      this.a.info("WURFL path passed to Updater constructor: " + this.h);
      this.c = var1;
      this.h = UpdatePipeline.a(var1.getRootPath());
      this.a.info("WURFL path passed to Updater constructor after resolve: " + this.h);
      this.b = var2;
      this.b();
   }

   public WURFLUpdater(WURFLEngine var1, String var2, String var3, ProxySettings var4) {
      this(var1, var3);
      this.k = var4;
   }

   public WURFLUpdater(WURFLEngine var1, String var2, ProxySettings var3) {
      this.a = LoggerFactory.getLogger(this.getClass());
      this.d = Frequency.DAILY;
      this.a.info("WURFL path passed to Updater constructor: " + this.h);
      this.c = var1;
      this.h = UpdatePipeline.a(var1.getRootPath());
      this.a.info("WURFL path passed to Updater constructor after resolve: " + this.h);
      this.b = var2;
      this.k = var3;
      this.b();
   }

   public boolean usesProxy() {
      return this.k != null;
   }

   public void setFirstExecution(Calendar var1) {
      this.j = var1;
   }

   public void setFrequency(Frequency var1) {
      this.d = var1;
   }

   public List getLastPeriodicUpdateResults() {
      return (List)(this.e != null ? this.e.getLastResults() : new ArrayList(0));
   }

   public void setConnectionTimeout(Integer var1) {
      this.g = var1;
   }

   public void setPatches(String[] var1) {
      this.i = var1;
   }

   public synchronized UpdateResult performUpdate() {
      UpdateResult var1;
      try {
         UpdatePipeline var4;
         (var4 = this.usesProxy() ? new UpdatePipeline(this.h, this.b, this.k) : new UpdatePipeline(this.h, this.b)).setUserAgent(UserAgentUtils.createApiUserAgent(this.c));
         var4.setConnectionTimeout(this.g);
         if (!(var1 = var4.execute()).isUpdateProcessSuccessful()) {
            this.a.warn(var1.getMessage());
         } else if (var1.a()) {
            if (ArrayUtils.isEmpty(this.i)) {
               this.c.reload(this.h);
            } else {
               this.c.reload(this.h, this.i);
            }
         }
      } catch (WURFLRuntimeException var3) {
         String var2 = "Unable to start WURFL updater, cause: " + var3.getMessage();
         this.a.error(var2, var3);
         var1 = new UpdateResult(UpdateResultStatus.PIPELINE_TASK_FAILED, var2);
      }

      return var1;
   }

   public synchronized void performPeriodicUpdate() {
      if (this.a()) {
         this.a.warn("Periodic update is already running. Shutdown the current update process before invoking this method");
      } else {
         try {
            UpdatePipeline var1;
            (var1 = this.usesProxy() ? new UpdatePipeline(this.h, this.b, this.k) : new UpdatePipeline(this.h, this.b)).setUserAgent(UserAgentUtils.createApiUserAgent(this.c));
            var1.setConnectionTimeout(this.g);
            this.f = Executors.newScheduledThreadPool(1);
            this.e = new PeriodicUpdateTask(this.c, var1, this.h);
            this.f.scheduleAtFixedRate(this.e, this.j != null ? this.j.getTimeInMillis() - Calendar.getInstance().getTimeInMillis() : 100L, this.d.value(), TimeUnit.MILLISECONDS);
         } catch (BadWurflExtensionException var3) {
            String var2 = "Unable to start WURFL updater, cause: " + var3.getMessage();
            this.a.error(var2, var3);
         }
      }
   }

   private boolean a() {
      return this.f != null && !this.f.isTerminated();
   }

   public void stopPeriodicUpdate() {
      if (this.a()) {
         this.f.shutdown();
         this.f = null;
      } else {
         this.a.warn("Cannot stop an updater that is not running. Command ignored");
      }
   }

   private void b() {
      Validator.checkFileExtensions(this.h, this.b);
      Validator.a(this.h);
      Validator.a(this.b, this.c, this.k);
   }
}
