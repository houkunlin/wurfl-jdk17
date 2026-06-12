package com.scientiamobile.wurfl.core.resource;

/**
 * WURFL XML SAX 解析状态常量定义。
 * <p>定义在 SAX 解析 WURFL XML 文件过程中的所有状态标识。
 * 使用 int 常量作为状态机的状态值，配合 {@link WurflXmlHandler} 使用，
 * 跟踪当前解析的位置（如文档开始、version 节点、devices 节点、device 节点等）。</p>
 */

final class WurflXmlParseState {
    private WurflXmlParseState() {
        /* This utility class should not be instantiated */
    }

    /**
     * 文档开始状态
     */
    static final int START_DOCUMENT = 1;
    /**
     * 进入 wurfl / wurfl_patch 根元素
     */
    static final int WURFL = 2;
    /**
     * 进入 version 元素
     */
    static final int VERSION = 3;
    /**
     * 进入 version/ver 元素
     */
    static final int VERSION_VER = 4;
    /**
     * 进入 version/last_updated 元素
     */
    static final int VERSION_LAST_UPDATED = 5;
    /**
     * 进入 version/smid 元素
     */
    static final int VERSION_SMID = 6;
    /**
     * 进入 devices 元素
     */
    static final int DEVICES = 7;
    /**
     * 进入 device 元素
     */
    static final int DEVICE = 8;
    /**
     * 进入 group 元素
     */
    static final int GROUP = 9;
    /**
     * 进入 capability 元素
     */
    static final int CAPABILITY = 10;
    /**
     * 文档解析结束状态
     */
    static final int END = 11;
}
