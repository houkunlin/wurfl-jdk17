package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.WURFLEngine;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeriodicUpdateTask implements Runnable {
  private final Logger a = LoggerFactory.getLogger(getClass());
  
  private UpdatePipeline b;
  
  private Calendar c;
  
  private Queue d = new LinkedList();
  
  private WURFLEngine e;
  
  private String f;
  
  private String[] g;
  
  public PeriodicUpdateTask(WURFLEngine paramWURFLEngine, UpdatePipeline paramUpdatePipeline, String paramString) {
    this.b = paramUpdatePipeline;
    this.f = paramString;
    this.e = paramWURFLEngine;
  }
  
  public void setPatches(String[] paramArrayOfString) {
    this.g = paramArrayOfString;
  }
  
  public void run() {
    this.a.info("WURFL periodic update started");
    try {
      UpdateResult updateResult1 = this.b.execute();
      UpdateResult updateResult2 = updateResult1;
      PeriodicUpdateTask periodicUpdateTask;
      if ((periodicUpdateTask = this).d.size() >= 10)
        periodicUpdateTask.d.poll(); 
      periodicUpdateTask.d.add(updateResult2);
      if (!updateResult1.isUpdateProcessSuccessful()) {
        this.a.error("Update process failed. Reason: " + updateResult1.getMessage());
        if (this.c != null)
          this.a.warn("Last successful updated was completed on " + CheckForNewWurflFileTask.a.format(this.c)); 
      } else if (updateResult1.a()) {
        this.a.info("Free memory before reload process " + Runtime.getRuntime().freeMemory());
        if (ArrayUtils.isEmpty((Object[])(periodicUpdateTask = this).g)) {
          periodicUpdateTask.e.reload(periodicUpdateTask.f);
        } else {
          periodicUpdateTask.e.reload(periodicUpdateTask.f, periodicUpdateTask.g);
        } 
        this.c = Calendar.getInstance();
      } 
      if (this.c != null)
        this.a.info("WURFL file update completed on " + CheckForNewWurflFileTask.a.format(this.c.getTime())); 
      return;
    } catch (Exception exception) {
      this.a.error("Unexpected exception performing periodic update", exception);
      return;
    } 
  }
  
  public List getLastResults() {
    return (List)this.d;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\updater\PeriodicUpdateTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */