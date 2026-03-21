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
  public static void checkFileExtensions(String paramString1, String paramString2) {
    if ((!paramString1.endsWith(".gz") && !paramString1.endsWith(".zip")) || (!paramString2.endsWith(".gz") && !paramString2.endsWith(".zip")))
      throw new BadWurflExtensionException("WURFL local and remote path must have either .zip or .gz extension. Updater will not start"); 
  }
  
  static void a(String paramString) {
    File file2 = new File(paramString);
    String str = "WURFL file at path " + file2.getAbsolutePath() + " is not writable, please provide write permission for it and its enclosing directory";
    if (!file2.canWrite())
      throw new WurflFilePermissionException(str); 
    paramString = paramString + ".bk";
    try {
      File file = new File(paramString);
      FileUtils.copyFile(file2, file);
      file.delete();
    } catch (IOException iOException) {
      throw new WurflFilePermissionException(str, iOException);
    } 
    if (file2.isDirectory())
      return; 
    File file1;
    int i;
    if (!(file1 = new File(((i = (paramString = file2.getAbsolutePath()).lastIndexOf(File.separator)) != -1) ? paramString.substring(0, i) : paramString)).canWrite())
      throw new WurflFilePermissionException("Directory " + file1.getAbsolutePath() + " should be writable, please provide the proper permission"); 
  }
  
  static void a(String paramString, WURFLEngine paramWURFLEngine, ProxySettings paramProxySettings) {
    try {
      int i = UpdatePipeline.a(new URL(paramString), null, 10000, UserAgentUtils.createApiUserAgent(paramWURFLEngine), paramProxySettings);
    } catch (MalformedURLException malformedURLException) {
      throw new WURFLRuntimeException("An error occurred validating URL for WURFL file update, the URL is invalid", malformedURLException);
    } catch (IOException iOException) {
      throw new WURFLRuntimeException("An error occurred validating URL for WURFL file update", iOException);
    } catch (ClassCastException classCastException) {
      throw new WURFLRuntimeException("An class exception occurred validating URL for WURFL file update (using HTTPS is mandatory)", classCastException);
    } 
    if (classCastException == 'ƒ')
      throw new WURFLRuntimeException("Invalid WURFL license, please check it on ScientiaMobile customer vault, response code " + classCastException); 
    if (classCastException >= 'Ɛ' && classCastException < 'Ǵ')
      throw new WURFLRuntimeException("Validation of http connection failed, response code " + classCastException); 
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\updater\Validator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */