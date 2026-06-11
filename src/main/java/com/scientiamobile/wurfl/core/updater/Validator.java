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

/**
 * WURFL 更新配置的校验器。
 * <p>提供 WURFL 更新前的静态校验方法，包括文件扩展名检查、
 * 本地文件可写性验证以及远程 URL 可达性验证。</p>
 */

public class Validator {
    private Validator() {
    }

    /**
     * 检查本地和远程 WURFL 文件的扩展名是否为 .zip 或 .gz。
     *
     * @param localWurflPath  本地 WURFL 文件路径
     * @param remoteWurflPath 远程 WURFL 文件 URL
     * @throws BadWurflExtensionException 如果扩展名不是 .zip 或 .gz
     */
    public static void checkFileExtensions(String localWurflPath, String remoteWurflPath) {
        if (!localWurflPath.endsWith(".gz") && !localWurflPath.endsWith(".zip") || !remoteWurflPath.endsWith(".gz") && !remoteWurflPath.endsWith(".zip")) {
            throw new BadWurflExtensionException("WURFL local and remote path must have either .zip or .gz extension. Updater will not start");
        }
    }

    /**
     * 验证本地 WURFL 文件是否可写。
     * <p>通过尝试复制文件到临时备份文件并删除来测试文件系统的写入能力。
     * 还会检查文件所在目录是否可写。</p>
     *
     * @param localWurflPath 本地 WURFL 文件路径
     * @throws WurflFilePermissionException 如果文件或目录不可写
     * @throws WURFLRuntimeException        如果无法访问文件
     */
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

    /**
     * 验证远程 WURFL URL 是否可达且有效。
     * <p>发送 HEAD 请求到远程 URL，检查响应状态码。仅允许访问 scientiamobile.com
     * 域名或本地地址。如果响应码为 402 表示许可证过期，
     * 如果响应码为 4xx 表示 URL 无效。</p>
     *
     * @param remoteWurflUrl 远程 WURFL 文件的 URL
     * @param wurflEngine    WURFL 引擎实例（用于构造 User-Agent）
     * @param proxySettings  代理设置，可以为 {@code null}
     * @throws WURFLRuntimeException 如果 URL 验证失败
     */
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
