package com.scientiamobile.wurfl.core.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * 通用 XML 文件加载器。
 * <p>使用 SAX 解析器加载 XML 文件并将其内容传递给指定的
 * {@link org.xml.sax.helpers.DefaultHandler} 处理器。
 * 适用于那些不需要返回处理结果的场景（如仅验证 XML 格式）。</p>
 */

public class XmlFileLoader {
    private static final Logger logger = LoggerFactory.getLogger(XmlFileLoader.class);
    /**
     * 资源输入源
     */
    private final ResourceInput resourceInput;
    /**
     * SAX 事件处理器
     */
    private final DefaultHandler handler;

    /**
     * 创建 XML 文件加载器。
     *
     * @param path    XML 文件路径
     * @param handler SAX 事件处理器
     */
    public XmlFileLoader(String path, DefaultHandler handler) {
        this.resourceInput = new ResourceInput(path);
        this.handler = handler;
    }

    /**
     * 解析 XML 文件。
     * <p>打开资源输入流，使用 SAXParser 解析 XML，将 SAX 事件传递给
     * 构造函数中指定的 {@link DefaultHandler}。</p>
     *
     * @return 始终返回 true（解析成功），异常时抛出运行时异常
     * @throws RuntimeException 如果解析过程中发生错误
     */

    public final boolean parseFile() {
        InputStream inputStream = this.resourceInput.openInputStream();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            try {
                factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
                factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            } catch (Exception ignored) {
                logger.debug("SAX features not supported by this parser, continuing: {}", ignored.getMessage());
            }
            factory.newSAXParser().parse(inputStream, this.handler);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                // 关闭流失败不影响业务逻辑，忽略异常
            }
        }

        return true;
    }
}
