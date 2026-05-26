package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.WURFLEngine;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeriodicUpdateTask implements Runnable {
   private final Logger log = LoggerFactory.getLogger(this.getClass());
   private UpdatePipeline updatePipeline;
   private Calendar lastSuccessfulUpdate;
   private final LinkedList<UpdateResult> lastResults = new LinkedList<>();
   private WURFLEngine wurflEngine;
   private String resolvedWurflPath;
   private String[] patchPaths;

   public PeriodicUpdateTask(WURFLEngine wurflEngine, UpdatePipeline updatePipeline, String resolvedWurflPath) {
      this.updatePipeline = updatePipeline;
      this.resolvedWurflPath = resolvedWurflPath;
      this.wurflEngine = wurflEngine;
   }

   public void setPatchPaths(String[] patchPaths) {
      this.patchPaths = patchPaths;
   }

   public void run() {
      this.log.info("WURFL periodic update started");

      try {
         UpdateResult updateResult = this.updatePipeline.execute();
         if (this.lastResults.size() >= 10) {
            this.lastResults.poll();
         }

         this.lastResults.add(updateResult);
         if (!updateResult.isUpdateProcessSuccessful()) {
            this.log.error("Update process failed. Reason: {}", updateResult.getMessage());
            if (this.lastSuccessfulUpdate != null) {
               this.log.warn("Last successful updated was completed on {}", CheckForNewWurflFileTask.LAST_MODIFIED_FORMAT.format(this.lastSuccessfulUpdate.getTime()));
            }
         } else if (updateResult.isUpdated()) {
            this.log.info("Free memory before reload process {}", Runtime.getRuntime().freeMemory());
            if (ArrayUtils.isEmpty(this.patchPaths)) {
               this.wurflEngine.reload(this.resolvedWurflPath);
            } else {
               this.wurflEngine.reload(this.resolvedWurflPath, this.patchPaths);
            }

            this.lastSuccessfulUpdate = Calendar.getInstance();
         }

         if (this.lastSuccessfulUpdate != null) {
            this.log.info("WURFL file update completed on {}", CheckForNewWurflFileTask.LAST_MODIFIED_FORMAT.format(this.lastSuccessfulUpdate.getTime()));
         }

      } catch (RuntimeException e) {
         this.log.error("Unexpected exception performing periodic update", e);
      }
   }

   public List<UpdateResult> getLastResults() {
      return this.lastResults;
   }
}
