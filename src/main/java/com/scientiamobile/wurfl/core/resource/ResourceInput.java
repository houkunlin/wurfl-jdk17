package com.scientiamobile.wurfl.core.resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Locale;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;

/**
 * 资源输入处理类。
 * <p>封装 WURFL 数据源的输入逻辑，支持从文件系统路径、URI、
 * 或已存在的输入流中读取数据。自动处理 .zip 和 .gz 压缩格式的解压，
 * 支持 classpath 和 HTTP(S) 协议的资源加载。</p>
 */

final class ResourceInput {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceInput.class);
    private static boolean ASSERTIONS_DISABLED = !ResourceInput.class.desiredAssertionStatus();
    /**
     * 资源的 URI（可为 null，当直接使用输入流时）
     */
    private URI uri;
    /**
     * 已打开的输入流
     */
    private InputStream stream;

    /**
     * 使用文件路径创建资源输入。
     * 路径可以是文件系统路径、classpath 路径或 URL。
     *
     * @param path 资源路径
     */
    public ResourceInput(String path) {
        Validate.notEmpty(path, "The path must be not empty");
        this.uri = parseUri(path);
    }

    /**
     * 使用 File 对象创建资源输入。
     *
     * @param file 资源文件
     */
    public ResourceInput(File file) {
        Validate.notNull(file, "The file must be not null");
        this.uri = file.toURI();
    }

    /**
     * 使用 URI 创建资源输入。
     *
     * @param uri 资源 URI
     */
    public ResourceInput(URI uri) {
        Validate.notNull(uri, "The URI must be not null");
        this.uri = uri;
    }

    /**
     * 使用已打开的输入流创建资源输入。
     * 如果输入流不是 ZipInputStream 或 GZIPInputStream，会根据文件名后缀自动解压。
     *
     * @param stream   输入流
     * @param fileName 文件名（用于判断压缩格式）
     */
    public ResourceInput(InputStream stream, String fileName) {
        Validate.notNull(stream, "The stream must be not null");
        Validate.notNull(fileName, "The fileName must be not null");
        if (!(stream instanceof ZipInputStream) && !(stream instanceof GZIPInputStream)) {
            try {
                if (fileName.toLowerCase(Locale.ENGLISH).endsWith(".zip")) {
                    this.stream = unwrapZip(stream);
                } else if (fileName.toLowerCase(Locale.ENGLISH).endsWith(".gz")) {
                    this.stream = new GZIPInputStream(stream);
                } else {
                    this.stream = stream;
                }
            } catch (IOException e) {
                LOG.error(e.toString());
            }
        } else {
            this.stream = stream;
        }
    }

    /**
     * 解析路径字符串为 URI。
     * <p>优先尝试将路径解析为本地文件系统中的文件；如果文件不存在或不可读，
     * 则将其作为 URI 字符串处理（支持 file://、classpath:、http:// 等协议）。</p>
     *
     * @param path 路径字符串
     * @return 解析后的 URI
     */

    private static URI parseUri(String path) {
        if (!ASSERTIONS_DISABLED && !StringUtils.isNotBlank(path)) {
            throw new AssertionError("The path must be not blank");
        } else {
            File file;
            URI uri;
            try {
                file = new File(path).getCanonicalFile();
            } catch (IOException e) {
                file = new File(path);
            }
            if (file.exists() && file.isFile() && file.canRead()) {
                uri = file.toURI();
            } else {
                String uriString = path.replace(" ", "%20");
                if (SystemUtils.IS_OS_WINDOWS && path.contains("\\")) {
                    uriString = uriString.replace("\\", "/");
                }

                if (!uriString.contains(":")) {
                    while (uriString.startsWith("/")) {
                        uriString = uriString.substring(1);
                    }

                    uriString = "file:///" + uriString;
                }

                uri = URI.create(uriString);
            }

            return uri;
        }
    }

    /**
     * 从 Zip 输入流中解压出第一个条目。
     *
     * @param stream 原始 Zip 输入流
     * @return 定位到第一个条目后的 Zip 输入流
     */

    private static InputStream unwrapZip(InputStream stream) {
        try {
            ZipInputStream zipInputStream;
            zipInputStream = new ZipInputStream(stream);
            zipInputStream.getNextEntry();
            return zipInputStream;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取资源的名称（URI 字符串或 "Stream resource"）。
     *
     * @return 资源名称
     */

    public final String getResourceName() {
        if (this.uri != null) {
            return this.uri.toString();
        } else {
            return "Stream resource";
        }
    }

    /**
     * 根据 URI 打开输入流。
     * <p>支持 classpath: 协议从类路径加载资源，
     * 也支持 http/https 协议从远程加载（仅限 ScientiaMobile 相关域名）。
     * 自动处理 .zip 和 .gz 后缀的压缩格式。</p>
     *
     * @param uri 资源 URI
     * @return 打开的输入流
     */

    private InputStream openStream(URI uri) {
        try {
            InputStream inputStream;
            if (uri.getScheme().equals("classpath")) {
                String resourcePath = uri.toString().replaceFirst("classpath:", "");
                inputStream = this.getClass().getResourceAsStream(resourcePath);
            } else {
                // 限制 HTTP(S) 来源，仅允许可信域名
                if ("http".equals(uri.getScheme()) || "https".equals(uri.getScheme())) {
                    Validate.isTrue(uri.getHost() != null && (uri.getHost().endsWith(".scientiamobile.com") || uri.getHost().equals("localhost") || uri.getHost().equals("127.0.0.1")), "Invalid URL host: " + uri.getHost());
                }

                inputStream = uri.toURL().openConnection().getInputStream();
            }

            if (uri.getPath().toLowerCase(Locale.ENGLISH).endsWith(".zip")) {
                inputStream = unwrapZip(inputStream);
            } else if (uri.getPath().toLowerCase(Locale.ENGLISH).endsWith(".gz")) {
                inputStream = new GZIPInputStream(inputStream);
            }

            return inputStream;
        } catch (IOException e) {
            LOG.error("Error opening stream URI: {}", uri.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭当前打开的输入流。
     */

    private void closeStream() {
        try {
            LOG.info("closing input stream: {}", this.stream.getClass().getSimpleName());
            this.stream.close();
        } catch (IOException e) {
            LOG.warn("Error closing stream");
        }

        this.stream = null;
    }

    /**
     * 释放所有资源，关闭输入流并清空 URI。
     */

    public final void close() {
        if (this.stream != null) {
            this.closeStream();
        }

        this.uri = null;
    }

    /**
     * 打开并返回输入流。
     * <p>如果尚未打开流，则根据 URI 打开；如果已存在流则直接返回。</p>
     *
     * @return 输入流
     * @throws RuntimeException 如果既没有 URI 也没有流
     */

    public final InputStream openInputStream() {
        if (this.stream == null) {
            if (this.uri == null) {
                throw new RuntimeException("The resource can not be read, the stream is null");
            }

            this.stream = this.openStream(this.uri);
        }

        return this.stream;
    }

    /**
     * 重置输入流。
     * <p>如果当前流支持 mark/reset 则尝试重置到开头；
     * 否则关闭当前流，下次调用 {@link #openInputStream()} 时重新打开。</p>
     */

    public final void reset() {
        if (this.stream.markSupported()) {
            try {
                this.stream.reset();
                return;
            } catch (IOException e) {
                // 重置失败，关闭流下次重新打开
            }
        }

        this.closeStream();
    }
}
