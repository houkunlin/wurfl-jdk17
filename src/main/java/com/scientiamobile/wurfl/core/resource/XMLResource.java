package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.resource.exc.WURFLResourceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * XML 资源加载实现。
 * <p>从 XML 文件、URI 或输入流中加载 WURFL 数据。
 * 使用 SAX 解析器流式解析 XML，支持通过 {@link #getData(String...)} 方法
 * 选择性加载指定的功能点，以降低内存占用。
 * 同时支持 .zip 和 .gz 压缩格式的自动解压。</p>
 */

public class XMLResource implements WURFLResource {
    private static final SAXParserFactory SAX_PARSER_FACTORY;

    static {
        LoggerFactory.getLogger(XMLResource.class);
        SAX_PARSER_FACTORY = SAXParserFactory.newInstance();
    }

    /**
     * 资源输入源
     */
    private final ResourceInput resourceInput;
    /**
     * 解析得到的版本号
     */
    private String version;
    /**
     * 需要包含的功能点集合
     */
    private Set<String> includedCapabilities;
    /**
     * 资源的原始路径
     */
    private String originalPath;

    /**
     * 使用路径字符串创建 XML 资源。
     *
     * @param originalPath XML 文件路径（支持文件系统路径、classpath 路径、HTTP URL 等）
     */
    public XMLResource(String originalPath) {
        this.originalPath = originalPath;
        this.resourceInput = new ResourceInput(originalPath);
    }

    /**
     * 使用 File 对象创建 XML 资源。
     *
     * @param originalFile XML 文件
     */
    public XMLResource(File originalFile) {
        this.originalPath = originalFile.getAbsolutePath();
        this.resourceInput = new ResourceInput(originalFile);
    }

    /**
     * 使用 URI 创建 XML 资源。
     *
     * @param uri XML 资源的 URI
     */
    public XMLResource(URI uri) {
        this.resourceInput = new ResourceInput(uri);
    }

    /**
     * 使用输入流创建 XML 资源。
     *
     * @param inputStream  XML 数据输入流
     * @param originalPath 资源的原始路径或文件名（用于解压格式判断）
     */
    public XMLResource(InputStream inputStream, String originalPath) {
        this.resourceInput = new ResourceInput(inputStream, originalPath);
    }

    /**
     * 获取 WURFL 数据快照。
     * <p>打开资源输入流，通过 SAX 解析器解析 XML，返回包含
     * 设备集合和版本信息的快照对象。支持按功能点名称过滤加载。</p>
     *
     * @param includedCapabilities 可选的功能点过滤列表，仅加载指定的功能点
     * @return WURFL 数据快照
     * @throws WURFLResourceException 如果解析过程中发生错误
     */
    @Override
    public ModelDevicesSnapshot getData(String... includedCapabilities) {
        this.includedCapabilities = includedCapabilities != null
                ? new HashSet<>(Arrays.asList(includedCapabilities))
                : new HashSet<>(0);

        ModelDevicesSnapshot snapshot = this.parseSnapshot(this.resourceInput.openInputStream());
        this.resourceInput.reset();
        return snapshot;
    }

    /**
     * 获取资源的原始路径。
     *
     * @return 原始路径字符串
     */

    public String getOriginalPath() {
        return this.originalPath;
    }

    /**
     * 获取资源的描述信息（如文件名或路径）。
     *
     * @return 资源描述字符串
     */
    public String getInfo() {
        return this.resourceInput.getResourceName();
    }

    /**
     * 获取 WURFL 数据的版本号。
     *
     * @return 版本号字符串
     */
    @Override
    public String getVersion() {
        return this.version;
    }

    /**
     * 释放资源持有的输入流等底层系统资源。
     */
    public void release() {
        this.resourceInput.close();
    }

    /**
     * 解析输入流生成设备集合快照。
     * <p>创建 {@link WurflXmlHandler} 处理器，使用 SAXParser 解析 XML 数据，
     * 然后从处理器中提取版本信息和设备集合，构建快照对象。</p>
     *
     * @param inputStream XML 数据输入流
     * @return 设备集合快照
     */

    private ModelDevicesSnapshot parseSnapshot(InputStream inputStream) {
        WurflXmlHandler handler = new WurflXmlHandler(this.includedCapabilities);

        try {
            SAX_PARSER_FACTORY.newSAXParser().parse(inputStream, handler);
        } catch (Exception e) {
            throw new WURFLResourceException(this, e);
        }

        String info = this.getInfo();
        String wurflVersion = handler.getWurflVersion();
        String wurflLastUpdated = handler.getWurflLastUpdated();
        String wurflSmid = handler.getWurflSmid();
        // 版本号优先取 ver 元素，其次取 last_updated，最后取默认值
        this.version = StringUtils.defaultIfBlank(wurflVersion, StringUtils.defaultIfBlank(wurflLastUpdated, "(no version info)"));
        boolean patch = handler.isPatch();
        ModelDevices devices = handler.getDevices();
        return new ModelDevicesSnapshot(info, this.version, patch, devices, wurflSmid);
    }
}
