package com.scientiamobile.wurfl.core.web;

import com.scientiamobile.wurfl.core.EngineTarget;
import com.scientiamobile.wurfl.core.GeneralWURFLEngine;
import com.scientiamobile.wurfl.core.UserAgentPriority;
import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.resource.WURFLResources;
import com.scientiamobile.wurfl.core.resource.XMLResource;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * WURFL Servlet 上下文监听器，用于在 Web 应用启动时自动初始化 WURFL 引擎。
 * <p>该监听器从 web.xml 的上下文参数中读取 WURFL 配置（如 WURFL 数据文件路径、补丁文件路径等），
 * 创建并配置 {@link GeneralWURFLEngine} 实例，并将其存储在 ServletContext 属性中供后续使用。</p>
 */

public class WURFLServletContextListener implements WurflWebConstants, ServletContextListener {
    /**
     * WURFL 引擎实例在 ServletContext 中存储的属性键名
     */
    private String engineKeyAttributeName;

    public WURFLServletContextListener() {
        this.engineKeyAttributeName = WURFL_ENGINE_KEY;
    }

    /**
     * 将 ServletContext 中的资源路径解析为 URI 对象。
     * <p>优先通过 ServletContext 获取资源 URL，然后转换为 URI。如果资源不存在则返回 {@code null}。</p>
     *
     * @param servletContext Servlet 上下文，用于查找资源
     * @param resourcePath   资源路径（如 /WEB-INF/wurfl.zip）
     * @return 解析后的 URI，如果资源不存在则返回 {@code null}
     * @throws RuntimeException 如果 URL 无法转换为 URI
     */

    private static URI resolveResourceUri(ServletContext servletContext, String resourcePath) {
        try {
            URL resourceUrl;
            resourceUrl = servletContext.getResource(resourcePath);
            if (resourceUrl == null) {
                return null;
            } else {
                URI resourceUri = resourceUrl.toURI();
                return resourceUri;
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
/**
 * Web 应用初始化时调用，负责读取配置并初始化 WURFL 引擎。
 * <p>具体流程：</p>
 * <ul>
 *   <li>解析可选的引擎键名（{@code wurflEngineKey}）</li>
 *   <li>读取必填的 WURFL 数据文件路径（{@code wurfl}）</li>
 *   <li>读取可选的补丁文件路径列表（{@code wurflPatch}）</li>
 *   <li>创建 {@link GeneralWURFLEngine} 实例</li>
 *   <li>配置能力过滤器、引擎目标模式和 User-Agent 优先级</li>
 *   <li>将引擎实例存入 ServletContext 属性</li>
 * </ul>
 *
 * @param servletContextEvent Servlet 上下文事件，包含 ServletContext 引用
 */

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        resolveEngineKey(servletContext);
        String wurflPath = servletContext.getInitParameter("wurfl");
        Validate.notEmpty(wurflPath, "Please Specify a Valid Location for wurfl context parameter");
        String[] wurflPatchPaths = StringUtils.split(
                StringUtils.defaultString(servletContext.getInitParameter("wurflPatch")), " ,");
        XMLResource wurflResource = createXmlResource(servletContext, wurflPath);
        WURFLResources patchResources = createPatchResources(servletContext, wurflPatchPaths);
        GeneralWURFLEngine wurflEngine = new GeneralWURFLEngine(wurflResource, patchResources);
        configureCapabilityFilter(servletContext, wurflEngine);
        configureEngineTarget(servletContext, wurflEngine);
        configureUserAgentPriority(servletContext, wurflEngine);
        servletContext.setAttribute(this.engineKeyAttributeName, wurflEngine);
    }

    /**
     * 解析可选的 WURFL 引擎键名配置。
     * <p>如果 web.xml 中配置了 {@code wurflEngineKey} 参数，则使用该值作为引擎实例在
     * ServletContext 中的属性键名；否则使用默认键名。</p>
     *
     * @param servletContext Servlet 上下文，用于读取初始化参数
     */

    private void resolveEngineKey(ServletContext servletContext) {
        String wurflEngineKey = servletContext.getInitParameter("wurflEngineKey");
        if (!StringUtils.isEmpty(wurflEngineKey)) {
            this.engineKeyAttributeName = wurflEngineKey;
        }
    }

    /**
     * 根据给定的路径创建 XML 资源对象。
     * <p>优先尝试将路径解析为 Servlet 资源 URI，如果资源不在应用中则直接使用原始路径字符串。</p>
     *
     * @param servletContext Servlet 上下文
     * @param path           WURFL 数据文件路径
     * @return XML 资源对象
     */

    private static XMLResource createXmlResource(ServletContext servletContext, String path) {
        URI uri = resolveResourceUri(servletContext, path);
        return uri != null ? new XMLResource(uri) : new XMLResource(path);
    }

    /**
     * 根据补丁路径数组创建补丁资源集合。
     * <p>遍历补丁路径数组，为每个路径创建 XML 资源对象（优先使用 Servlet 资源 URI）。</p>
     *
     * @param servletContext Servlet 上下文
     * @param patchPaths     补丁文件路径数组
     * @return 补丁资源集合
     */

    private static WURFLResources createPatchResources(ServletContext servletContext, String[] patchPaths) {
        WURFLResources resources = new WURFLResources();
        for (String patchPath : patchPaths) {
            URI uri = resolveResourceUri(servletContext, patchPath);
            XMLResource resource = uri != null ? new XMLResource(uri) : new XMLResource(patchPath);
            resources.add(resource);
        }
        return resources;
    }

    /**
     * 配置引擎的能力过滤器。
     * <p>从 web.xml 初始化参数中读取换行分隔的能力名列表，过滤后的能力才会被引擎加载。</p>
     *
     * @param servletContext Servlet 上下文
     * @param wurflEngine    WURFL 引擎实例
     */

    private static void configureCapabilityFilter(ServletContext servletContext, GeneralWURFLEngine wurflEngine) {
        String filterValue = servletContext.getInitParameter("capability-filter");
        if (filterValue == null) {
            return;
        }
        String[] capabilityFilter = filterValue.split("\n");
        for (int i = 0; i < capabilityFilter.length; i++) {
            if (capabilityFilter[i] != null) {
                capabilityFilter[i] = capabilityFilter[i].trim();
            }
        }
        wurflEngine.setCapabilityFilter(capabilityFilter);
    }

    /**
     * 配置引擎的目标匹配模式（性能优先或精度优先）。
     * <p>从 web.xml 初始化参数中读取 {@code wurflEngineTarget}，将其转换为 {@link EngineTarget} 枚举后设置到引擎中。</p>
     *
     * @param servletContext Servlet 上下文
     * @param wurflEngine    WURFL 引擎实例
     * @throws WURFLRuntimeException 如果参数值不是有效的枚举值
     */

    private static void configureEngineTarget(ServletContext servletContext, GeneralWURFLEngine wurflEngine) {
        String value = servletContext.getInitParameter("wurflEngineTarget");
        if (value == null) {
            return;
        }
        try {
            wurflEngine.setEngineTarget(EngineTarget.valueOf(value));
        } catch (IllegalArgumentException e) {
            throw new WURFLRuntimeException(
                    "Invalid value for wurflEngineTarget; accepted values are: "
                            + EngineTarget.performance + " (default) and " + EngineTarget.accuracy, e);
        }
    }

    /**
     * 配置 User-Agent 优先级策略。
     * <p>从 web.xml 初始化参数中读取 {@code wurflUserAgentPriority}，决定是否使用浏览器侧载的 User-Agent
     * 覆盖设备 UA，还是直接使用原始 User-Agent。</p>
     *
     * @param servletContext Servlet 上下文
     * @param wurflEngine    WURFL 引擎实例
     * @throws WURFLRuntimeException 如果参数值不是有效的枚举值
     */

    private static void configureUserAgentPriority(ServletContext servletContext, GeneralWURFLEngine wurflEngine) {
        String value = servletContext.getInitParameter("wurflUserAgentPriority");
        if (value == null) {
            return;
        }
        try {
            wurflEngine.setUserAgentPriority(UserAgentPriority.valueOf(value));
        } catch (IllegalArgumentException e) {
            throw new WURFLRuntimeException(
                    "Invalid value for wurflUserAgentPriority; accepted values are: "
                            + UserAgentPriority.OverrideSideloadedBrowserUserAgent + " (default) and "
                            + UserAgentPriority.UsePlainUserAgent, e);
        }
    }

    /**
     * Web 应用关闭时调用，清理 ServletContext 中存储的 WURFL 引擎实例。
     *
     * @param servletContextEvent Servlet 上下文事件
 */

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().removeAttribute(this.engineKeyAttributeName);
    }
}
