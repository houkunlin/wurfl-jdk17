package com.scientiamobile.wurfl.core.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 资源工具类。
 * <p>提供与 WURFL 构建版本相关的工具方法。
 * 从 classpath 资源文件中读取构建设置信息，
 * 用于标识当前运行环境中 WURFL 资源的构建版本。</p>
 */

public class ResourceUtils {
    private ResourceUtils() {
    }

    /**
     * 获取构建 ID（去除前缀部分）。
     * <p>完整构建 ID 的格式为 "前缀:ID"，
     * 此方法仅返回冒号后的 ID 部分。</p>
     *
     * @return 构建 ID，如果无法获取则返回 "unknown"
     */
    public static String getBuildId() {
        String fullBuildId = getFullBuildId();
        String result = fullBuildId;
        if (fullBuildId != null && !"unknown".equals(fullBuildId)) {
            int colonIndex = fullBuildId.indexOf(":");
            result = colonIndex != -1 ? fullBuildId.substring(colonIndex + 1) : fullBuildId;
        }

        return result;
    }

    /**
     * 获取完整的构建 ID（包含前缀）。
     * <p>通过读取 classpath 根目录下的 "/ca" 资源文件获取构建版本信息。
     * 文件不存在或读取失败时返回 "unknown"。</p>
     *
     * @return 完整构建 ID 字符串
     */

    public static String getFullBuildId() {
        String buildId = "unknown";
        InputStream inputStream = ResourceUtils.class.getResourceAsStream("/ca");
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line = reader.readLine();
                if (line != null) {
                    buildId = line;
                }
                return buildId;
            } catch (IOException e) {
                // 文件读取失败不影响核心业务逻辑，静默忽略
            }
        }
        return buildId;
    }
}
