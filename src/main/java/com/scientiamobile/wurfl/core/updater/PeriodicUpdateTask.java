package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.WURFLEngine;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeriodicUpdateTask implements Runnable {
   private final Logger a = LoggerFactory.getLogger(this.getClass());
   private UpdatePipeline b;
   private Calendar c;
   private Queue d = new LinkedList();
   private WURFLEngine e;
   private String f;
   private String[] g;

   public PeriodicUpdateTask(WURFLEngine var1, UpdatePipeline var2, String var3) {
      this.b = var2;
      this.f = var3;
      this.e = var1;
   }

   public void setPatches(String[] var1) {
      this.g = var1;
   }

   public void run() {
      this.a.info("WURFL periodic update started");

      try {
         UpdateResult var1 = this.b.execute();
         if (this.d.size() >= 10) {
            this.d.poll();
         }

         this.d.add(var1);
         if (!var1.isUpdateProcessSuccessful()) {
            this.a.error("Update process failed. Reason: " + var1.getMessage());
            if (this.c != null) {
               this.a.warn("Last successful updated was completed on " + CheckForNewWurflFileTask.a.format(this.c));
            }
         } else if (var1.a()) {
            this.a.info("Free memory before reload process " + Runtime.getRuntime().freeMemory());
            if (ArrayUtils.isEmpty(this.g)) {
               this.e.reload(this.f);
            } else {
               this.e.reload(this.f, this.g);
            }

            this.c = Calendar.getInstance();
         }

         if (this.c != null) {
            this.a.info("WURFL file update completed on " + CheckForNewWurflFileTask.a.format(this.c.getTime()));
         }

      } catch (Exception var4) {
         this.a.error("Unexpected exception performing periodic update", var4);
      }
   }

   public List getLastResults() {
      return (List)this.d;
   }
}
