package com.scientiamobile.wurfl.core;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * WURFL API 配置文件的 SAX 解析处理器。
 * <p>解析 {@code wurfl-api-config.xml} 配置文件，读取 {@code engine-target} 属性，
 * 仅在 WURFL 服务尚未设置引擎目标时，根据配置值设置默认的引擎目标模式。</p>
 */

final class ApiConfigHandler extends DefaultHandler {
    /**
     * 待配置的 WURFL 服务实例
     */
    private final WURFLServiceImpl wurflService;

    private ApiConfigHandler(WURFLServiceImpl wurflService) {
        this.wurflService = wurflService;
    }

    ApiConfigHandler(WURFLServiceImpl wurflService, byte ignored) {
        this(wurflService);
    }

    /**
     * 解析 XML 元素的起始标签。
     * <p>当遇到 {@code wurfl-api-config} 元素时，读取 {@code engine-target} 属性值，
     * 如果 WURFL 服务尚未设置引擎目标，则根据属性值设置：</p>
     * <ul>
     *   <li>如果值为 {@code accuracy} 或 {@code performance} 或未设置，则使用默认目标</li>
     *   <li>否则设置为 {@code fastDesktopBrowserMatch}</li>
     * </ul>
     *
     * @param uri        XML 命名空间 URI
     * @param localName  元素的本地名称（不含前缀）
     * @param qName      元素的限定名（含前缀）
     * @param attributes 元素的属性列表
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals("wurfl-api-config")) {
            String engineTarget = attributes.getValue("engine-target");
            if (WURFLServiceImpl.getEngineTarget(this.wurflService) == null) {
                if (engineTarget == null || "performance".equals(engineTarget) || "accuracy".equals(engineTarget)) {
                    WURFLServiceImpl.setEngineTarget(this.wurflService, EngineTarget.defaultTarget);
                    return;
                }

                WURFLServiceImpl.setEngineTarget(this.wurflService, EngineTarget.fastDesktopBrowserMatch);
            }
        }

    }
}

