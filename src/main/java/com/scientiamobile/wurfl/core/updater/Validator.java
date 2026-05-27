package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.WURFLEngine;
import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.updater.exc.BadWurflExtensionException;
import com.scientiamobile.wurfl.core.updater.exc.WurflFilePermissionException;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class Validator {
    private Validator() {
    }

    public static void checkFileExtensions(String localWurflPath, String remoteWurflPath) {
        if (!localWurflPath.endsWith(".gz") && !localWurflPath.endsWith(".zip") || !remoteWurflPath.endsWith(".gz") && !remoteWurflPath.endsWith(".zip")) {
            throw new BadWurflExtensionException("WURFL local and remote path must have either .zip or .gz extension. Updater will not start");
        }
    }

    static void validateWritableFile(String localWurflPath) {
        File wurflFile;
        try {
            wurflFile = new File(localWurflPath).getCanonicalFile();
        } catch (IOException e) {
            throw new WURFLRuntimeException("Cannot access WURFL file: " + localWurflPath, e);
        }
        String errorMessage = "WURFL file at path " + wurflFile.getAbsolutePath() + " is not writable, please provide write permission for it and its enclosing directory";
        if (!wurflFile.canWrite()) {
            throw new WurflFilePermissionException(errorMessage);
        } else {
            String backupPath = localWurflPath + ".bk";

            try {
                File backupFile = new File(backupPath);
                FileUtils.copyFile(wurflFile, backupFile);
                if (!backupFile.delete()) {
                    throw new WurflFilePermissionException("Failed to delete backup file at " + backupFile.getAbsolutePath());
                }
            } catch (IOException e) {
                throw new WurflFilePermissionException(errorMessage, e);
            }

            if (!wurflFile.isDirectory()) {
                String wurflFilePath = wurflFile.getAbsolutePath();
                int separatorIndex = wurflFilePath.lastIndexOf(File.separator);
                File wurflDirectory = new File(separatorIndex != -1 ? wurflFilePath.substring(0, separatorIndex) : wurflFilePath);
                if (!wurflDirectory.canWrite()) {
                    throw new WurflFilePermissionException("Directory " + wurflDirectory.getAbsolutePath() + " should be writable, please provide the proper permission");
                }
            }
        }
    }

    static void validateRemoteUrl(String remoteWurflUrl, WURFLEngine wurflEngine, ProxySettings proxySettings) {
        URL remoteUrl;
        try {
            remoteUrl = URI.create(remoteWurflUrl).toURL();
        } catch (IllegalArgumentException | MalformedURLException e) {
            throw new WURFLRuntimeException("An error occurred validating URL for WURFL file update, the URL is invalid", e);
        }

        int responseCode;
        try {
            responseCode = UpdatePipeline.headRequest(remoteUrl, (String) null, 10000, UserAgentUtils.createApiUserAgent(wurflEngine), proxySettings);
        } catch (ClassCastException e) {
            throw new WURFLRuntimeException("An class exception occurred validating URL for WURFL file update (using HTTPS is mandatory)", e);
        } catch (RuntimeException e) {
            throw new WURFLRuntimeException("An error occurred validating URL for WURFL file update", e);
        }

        if (responseCode == 402) {
            throw new WURFLRuntimeException("Invalid WURFL license, please check it on ScientiaMobile customer vault, response code " + responseCode);
        } else if (responseCode >= 400 && responseCode < 500) {
            throw new WURFLRuntimeException("Validation of http connection failed, response code " + responseCode);
        }
    }
}
