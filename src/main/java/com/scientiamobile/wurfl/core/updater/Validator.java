package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.WURFLEngine;
import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.updater.exc.BadWurflExtensionException;
import com.scientiamobile.wurfl.core.updater.exc.WurflFilePermissionException;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.io.FileUtils;

public class Validator {
   private Validator() {
   }

   public static void checkFileExtensions(String var0, String var1) {
      if (!var0.endsWith(".gz") && !var0.endsWith(".zip") || !var1.endsWith(".gz") && !var1.endsWith(".zip")) {
         throw new BadWurflExtensionException("WURFL local and remote path must have either .zip or .gz extension. Updater will not start");
      }
   }

   static void a(String var0) {
      File var1 = new File(var0);
      String var2 = "WURFL file at path " + var1.getAbsolutePath() + " is not writable, please provide write permission for it and its enclosing directory";
      if (!var1.canWrite()) {
         throw new WurflFilePermissionException(var2);
      } else {
         var0 = var0 + ".bk";

         try {
            File var5 = new File(var0);
            FileUtils.copyFile(var1, var5);
            var5.delete();
         } catch (IOException var3) {
            throw new WurflFilePermissionException(var2, var3);
         }

         if (!var1.isDirectory()) {
            File var7;
            int var8;
            if (!(var7 = new File((var8 = (var0 = var1.getAbsolutePath()).lastIndexOf(File.separator)) != -1 ? var0.substring(0, var8) : var0)).canWrite()) {
               throw new WurflFilePermissionException("Directory " + var7.getAbsolutePath() + " should be writable, please provide the proper permission");
            }
         }
      }
   }

   static void a(String var0, WURFLEngine var1, ProxySettings var2) {
      URL var6;
      try {
         var6 = new URL(var0);
      } catch (MalformedURLException var3) {
         throw new WURFLRuntimeException("An error occurred validating URL for WURFL file update, the URL is invalid", var3);
      }

      int var7;
      try {
         var7 = UpdatePipeline.a(var6, (String)null, 10000, UserAgentUtils.createApiUserAgent(var1), var2);
      } catch (ClassCastException var4) {
         throw new WURFLRuntimeException("An class exception occurred validating URL for WURFL file update (using HTTPS is mandatory)", var4);
      } catch (RuntimeException var5) {
         throw new WURFLRuntimeException("An error occurred validating URL for WURFL file update", var5);
      }

      if (var7 == 402) {
         throw new WURFLRuntimeException("Invalid WURFL license, please check it on ScientiaMobile customer vault, response code " + var7);
      } else if (var7 >= 400 && var7 < 500) {
         throw new WURFLRuntimeException("Validation of http connection failed, response code " + var7);
      }
   }
}
