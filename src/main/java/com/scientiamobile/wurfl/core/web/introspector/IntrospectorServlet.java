package com.scientiamobile.wurfl.core.web.introspector;

import com.scientiamobile.wurfl.core.*;
import com.scientiamobile.wurfl.core.matchers.MatcherManager;
import com.scientiamobile.wurfl.core.request.DefaultWURFLRequestFactory;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.web.WurflWebConstants;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serial;
import java.util.*;
import java.util.regex.Pattern;

/**
 * WURFL 浏览诊断 Servlet，提供设备检测结果查询和调试功能。
 * <p>该 Servlet 支持以下操作模式：</p>
 * <ul>
 *   <li>{@code Request} - 根据请求头或表单参数检测设备并返回匹配结果</li>
 *   <li>{@code Info} - 返回 WURFL 引擎和服务器的版本信息</li>
 *   <li>{@code Buckets} - 对 WURFL 模型中所有设备的 UA 进行桶匹配，输出匹配统计</li>
 *   <li>{@code form} - 类似 Request 模式，但允许通过表单参数指定引擎目标和 UA 优先级</li>
 * </ul>
 */

public class IntrospectorServlet extends HttpServlet implements WurflWebConstants {
    /**
     * 用于拆分 header 对的分隔符（转义后的正则形式）
     */
    public static final String ESCAPED_SPLIT_CHAR = "\\|";
    /**
     * header 对的分隔符
     */
    public static final String SPLIT_CHAR = "|";
    /**
     * 键值对中的冒号分隔符
     */
    public static final String COLON = ":";
    /**
     * 制表符，用于格式化输出
     */
    public static final String TABULATION = "\t";
    /**
     * 请求参数名：操作类型
     */
    public static final String ACTION = "action";
    /**
     * 请求参数名：User-Agent
     */
    public static final String USER_AGENT_PARAMNAME = "ua";
    /**
     * 请求参数名：UAProfile URL
     */
    public static final String UAPROF_PARAMNAME = "uaprof";
    /**
     * 请求参数名：自定义请求头
     */
    public static final String HEADERS = "headers";
    /**
     * 请求参数名：需要查询的能力列表
     */
    public static final String CAPABILITIES = "capabilities";
    /**
     * 请求参数名：设备 ID
     */
    public static final String ID = "id";
    /**
     * JSON 响应中的能力映射字段名
     */
    public static final String CAPABILITY_MAP = "capabilityMap";
    /**
     * 设备 UAProfile 请求头名（首字母大写形式）
     */
    public static final String X_WAP_PROFILE = "X-Wap-Profile";
    /**
     * 设备 UAProfile 请求头名（小写形式）
     */
    public static final String X_WAP_PROFILE_LC = "x-wap-profile";
    /**
     * User-Agent 请求头名（小写）
     */
    public static final String USER_AGENT_LC = "user-agent";
    /**
     * User-Agent 请求头名
     */
    public static final String USER_AGENT = "User-Agent";
    /**
     * 设备原始 UA 请求头名
     */
    public static final String DEVICE_STOCK_UA = "Device-Stock-UA";
    /**
     * 操作类型：普通请求匹配
     */
    public static final String REQUEST = "Request";
    /**
     * 操作类型：性能模式请求匹配
     */
    public static final String REQUEST_PERFORMANCE = "RequestPerformance";
    /**
     * 操作类型：精度模式请求匹配
     */
    public static final String REQUEST_ACCURACY = "RequestAccuracy";
    /**
     * 操作类型：返回引擎和服务器信息
     */
    public static final String INFO = "Info";
    /**
     * 操作类型：返回桶匹配统计
     */
    public static final String BUCKETS = "Buckets";
    /**
     * 序列化版本号
     */
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 运行时的操作系统名称
     */
    private static final String OS_NAME = System.getProperty("os.name");
    /**
     * 运行时的操作系统版本
     */
    private static final String OS_VERSION = System.getProperty("os.version");
    /**
     * Java 运行环境的供应商
     */
    private static final String JAVA_VENDOR = System.getProperty("java.vendor");
    /**
     * Java 运行环境版本
     */
    private static final String JAVA_VERSION_PROP = System.getProperty("java.version");
    /**
     * 用于匹配换行符的正则模式
     */
    private static final Pattern LINE_BREAK_PATTERN = Pattern.compile("[\r\n]+");
    /**
     * 日志记录器
     */
    private static final Logger log = LoggerFactory.getLogger(IntrospectorServlet.class);
    /**
     * WURFL 引擎静态实例，通过 {@link #setWURFLEngine(WURFLEngine)} 注入
     */
    private static WURFLEngine wurflEngine = null;
    /**
     * WURFL API 版本号
     */
    private static String apiVersion;

    static {
        Properties pomProperties = new Properties();
        try (InputStream pomPropertiesStream = IntrospectorServlet.class.getResourceAsStream("/META-INF/maven/com.scientiamobile.wurfl/wurfl/pom.properties")) {
            pomProperties.load(pomPropertiesStream);
            apiVersion = "1.9.1.0";
        } catch (Exception e) {
            apiVersion = "(Unavailable...)";
        }

        if (apiVersion == null || apiVersion.isEmpty()) {
            apiVersion = "(Unavailable...)";
        }

        log.info("WURFL core library running version {}", pomProperties.get("version"));
    }

    /**
     * Jackson JSON 对象映射器，用于序列化响应体
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 设置 WURFL 引擎静态实例，供诊断 Servlet 使用。
     * <p>在 Web 应用启动时需要先调用此方法注入已初始化的 WURFL 引擎实例。</p>
     *
     * @param wurflEngine WURFL 引擎实例
     */

    public static void setWURFLEngine(WURFLEngine wurflEngine) {
        IntrospectorServlet.wurflEngine = wurflEngine;
    }

    /**
     * 当 WURFL 引擎未初始化时，向响应输出错误信息。
     * <p>提示调用者需要通过 {@link #setWURFLEngine(WURFLEngine)} 方法注入引擎实例。</p>
     *
     * @param out 响应输出流
     */

    private static void writeMissingEngineError(PrintWriter out) {
        StringBuilder message;
        message = new StringBuilder();
        message.append("INTROSPECTOR SERVLET ERROR!\n");
        message.append("\n");
        message.append("No WURFLEngine was initialized in the IntrospectorServlet.\n");
        message.append("To use IntrospectorServlet, please call the method:\n");
        message.append("\tpublic static void IntrospectorServlet.setWURFLEngine(WURFLEngine wurflEngine)\n");
        message.append("to provide the servlet with the running WURFL instance.\n");
        message.append("\n");
        message.append("For more details, see documentation.\n");
        out.println(message);
    }

    /**
     * Servlet 初始化方法，记录服务器信息。
     *
     * @param config Servlet 配置对象
     * @throws ServletException 初始化异常
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.getServletContext().getServerInfo();
    }

    /**
     * GET 请求委托给 POST 方法处理。
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }

    /**
     * 处理 POST 请求，根据 {@code action} 参数分发到不同的处理逻辑。
     * <p>支持的 action 值：</p>
     * <ul>
     *   <li>{@code Request} - 精度模式下执行设备检测并返回 JSON</li>
     *   <li>{@code Info} - 返回引擎及服务器版本信息 JSON</li>
     *   <li>{@code form} - 使用表单参数指定的引擎目标和 UA 优先级执行检测</li>
     *   <li>{@code Buckets} - 输出桶匹配统计</li>
     * </ul>
     *
     * @param request  HTTP 请求
     * @param response HTTP 响应
     * @throws IOException 写入响应时可能发生的异常
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter(ACTION);
        response.setContentType("text/plain");
        response.setHeader("X-Content-Type-Options", "nosniff");
        PrintWriter out = response.getWriter();
        if (REQUEST.equalsIgnoreCase(action)) {
            wurflEngine.setEngineTarget(EngineTarget.accuracy);
            this.handleRequest(request, out);
        } else if (INFO.equalsIgnoreCase(action)) {
            wurflEngine.setEngineTarget(EngineTarget.accuracy);
            writeInfoResponse(out);
        } else if ("form".equalsIgnoreCase(action)) {
            wurflEngine.setEngineTarget(EngineTarget.valueOf(request.getParameter("wurflEngineTarget")));
            wurflEngine.setUserAgentPriority(UserAgentPriority.valueOf(request.getParameter("wurflUserAgentPriority")));
            this.handleRequest(request, out);
        } else if (BUCKETS.equalsIgnoreCase(action)) {
            this.writeBuckets(out);
        } else {
            out.println("action " + action + " not supported");
        }
        out.flush();
    }

    /**
     * 输出引擎和服务器版本信息（JSON 格式）。
     * <p>包含 API 版本、WURFL 数据版本、引擎目标、UA 优先级、服务器信息、OS 信息、Java 环境等。</p>
     *
     * @param out 响应输出流
     */

    private void writeInfoResponse(PrintWriter out) {
        if (wurflEngine == null) {
            writeMissingEngineError(out);
            return;
        }
        IntrospectorInfoResponse responseBody = new IntrospectorInfoResponse();
        responseBody.apiVersion = apiVersion;
        responseBody.wurflVersion = wurflEngine.getWURFLUtils().getVersion();
        responseBody.engineTarget = wurflEngine.getEngineTarget().name();
        responseBody.userAgentPriority = wurflEngine.getUserAgentPriority().name();
        responseBody.serverInfo = this.getServletContext().getServerInfo();
        responseBody.osName = OS_NAME;
        responseBody.osVersion = OS_VERSION;
        responseBody.javaVendor = JAVA_VENDOR;
        responseBody.javaVersion = JAVA_VERSION_PROP;
        String json = this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseBody);
        out.println(json);
    }

    /**
     * 输出桶匹配统计结果。
     * <p>遍历 WURFL 模型中所有设备的 User-Agent，使用匹配器进行匹配，按匹配器分组排序后输出。</p>
     *
     * @param out 响应输出流
     */

    private void writeBuckets(PrintWriter out) {
        if (wurflEngine == null) {
            writeMissingEngineError(out);
            return;
        }
        if (!(wurflEngine instanceof GeneralWURFLEngine engine)) {
            return;
        }
        try {
            out.println("WURFL Java API " + apiVersion);
            WURFLModel wurflModel = engine.getWurflModel();
            WURFLService wurflService = engine.getWurflService();
            List<String> resultLines = runBucketMatching(wurflModel, wurflService);
            for (String lineObj : resultLines) {
                out.println(lineObj);
            }
        } catch (RuntimeException e) {
            log.debug("{} - {}", e.getClass().getSimpleName(), e.getMessage());
        }
    }

    /**
     * 对模型中所有设备的 User-Agent 执行桶匹配。
     * <p>先收集所有设备的 UA，然后逐一匹配，最后对匹配结果排序。</p>
     *
     * @param wurflModel   WURFL 数据模型
     * @param wurflService WURFL 服务实例
     * @return 排序后的匹配结果行列表
     */

    private static List<String> runBucketMatching(WURFLModel wurflModel, WURFLService wurflService) {
        if (wurflModel == null || wurflService == null) {
            return Collections.emptyList();
        }
        EngineTarget originalEngineTarget = wurflService.getEngineTarget();
        MatcherManager matcherManager = wurflService.getMatcherManager();
        ArrayList<String> userAgents = collectUserAgents(wurflModel);
        wurflService.setEngineTarget(EngineTarget.accuracy);
        ArrayList<MatchResultRow> matchResults = matchUserAgents(userAgents, matcherManager);
        log.info("BUCKETS (2/3): start sorting...");
        long start = System.currentTimeMillis();
        Collections.sort(matchResults);
        log.info("BUCKETS (2/3): finished sorting. Took {} ms", System.currentTimeMillis() - start);
        log.info("BUCKETS (3/3): start building strings...");
        start = System.currentTimeMillis();
        List<String> resultLines = new ArrayList<>(matchResults.size());
        for (MatchResultRow row : matchResults) {
            resultLines.add(row.toString());
        }
        log.info("BUCKETS (3/3): finished building strings. Took {} ms", System.currentTimeMillis() - start);
        wurflService.setEngineTarget(originalEngineTarget);
        return resultLines;
    }

    /**
     * 从 WURFL 模型中收集所有有效的 User-Agent。
     *
     * @param wurflModel WURFL 数据模型
     * @return 所有非空的 User-Agent 列表
     */

    private static ArrayList<String> collectUserAgents(WURFLModel wurflModel) {
        Set<ModelDevice> allDevices = wurflModel.getAllDevices();
        ArrayList<String> userAgents = new ArrayList<>(allDevices.size());
        for (ModelDevice device : allDevices) {
            if (!StringUtils.isEmpty(device.getUserAgent())) {
                userAgents.add(device.getUserAgent());
            }
        }
        return userAgents;
    }

    /**
     * 对收集到的 User-Agent 逐一执行匹配，生成匹配结果行。
     *
     * @param userAgents     需要匹配的 User-Agent 列表
     * @param matcherManager 匹配器管理器
     * @return 匹配结果行列表
     */

    private static ArrayList<MatchResultRow> matchUserAgents(ArrayList<String> userAgents, MatcherManager matcherManager) {
        ArrayList<MatchResultRow> matchResults = new ArrayList<>(userAgents.size());
        DefaultWURFLRequestFactory requestFactory = new DefaultWURFLRequestFactory();
        log.info("BUCKETS (1/3): start matching...");
        long start = System.currentTimeMillis();
        for (String ua : userAgents) {
            DeviceInfo deviceInfo = matcherManager.matchRequest(requestFactory.createRequest(ua, EngineTarget.accuracy));
            matchResults.add(new MatchResultRow(
                    deviceInfo.getMatcherName(),
                    deviceInfo.getId(),
                    deviceInfo.getNormalizedUserAgent(),
                    deviceInfo.getOriginalUserAgent()
            ));
        }
        log.info("BUCKETS (1/3): finished matching. Took {} ms", System.currentTimeMillis() - start);
        return matchResults;
    }

    /**
     * 处理设备检测请求，返回匹配结果 JSON。
     * <p>从请求中提取请求头（或表单参数），使用 WURFL 引擎进行设备检测，获取指定能力的值。</p>
     *
     * @param request HTTP 请求
     * @param out     响应输出流
     */

    private void handleRequest(HttpServletRequest request, PrintWriter out) {
        if (wurflEngine == null) {
            writeMissingEngineError(out);
            return;
        }
        HeaderOnlyHttpServletRequest headerOnlyRequest = buildHeaderOnlyRequest(request);
        String rawCapabilities = request.getParameter(CAPABILITIES);
        String[] capabilities = parseCapabilities(rawCapabilities);
        Device device = wurflEngine.getDeviceForRequest(headerOnlyRequest);
        IntrospectorRequestResponse responseBody = new IntrospectorRequestResponse();
        responseBody.deviceId = device.getId();
        responseBody.userAgent = headerOnlyRequest.getHeader(USER_AGENT);
        responseBody.requestType = request.getParameter("form") == null ? "request" : "form";
        if (capabilities.length > 0) {
            HashMap<String, String> capabilityMap = new HashMap<>();
            for (String capability : capabilities) {
                capabilityMap.put(capability.trim(), device.getCapability(capability.trim()));
            }
            responseBody.capabilities = capabilityMap;
        }
        String json = this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseBody);
        out.println(json);
    }

    /**
     * 解析请求中的 UAProfile URL。
     * <p>优先从请求参数获取，其次从小写头名获取，最后从大写头名获取。</p>
     *
     * @param request HTTP 请求
     * @return UAProfile URL，如果未提供则返回 {@code null}
     */

    private static String resolveUaProfile(HttpServletRequest request) {
        String uaProfile = request.getParameter(UAPROF_PARAMNAME);
        if (uaProfile != null && !uaProfile.trim().isEmpty()) {
            return uaProfile.trim();
        }
        uaProfile = request.getHeader(X_WAP_PROFILE_LC);
        if (uaProfile != null && !uaProfile.isEmpty()) {
            return uaProfile;
        }
        return request.getHeader(X_WAP_PROFILE);
    }

    /**
     * 构建只包含请求头的 HTTP Servlet 请求对象，用于设备检测。
     * <p>如果请求包含表单参数（{@code form} 模式），则从表单中提取 UA 和自定义请求头；
     * 否则复制原始请求的所有请求头。同时会解析并添加 UAProfile 头。</p>
     *
     * @param request 原始 HTTP 请求
     * @return 仅包含请求头的请求对象
     */

    private static HeaderOnlyHttpServletRequest buildHeaderOnlyRequest(HttpServletRequest request) {
        HeaderOnlyHttpServletRequest headerOnlyRequest = new HeaderOnlyHttpServletRequest();
        if (request.getParameter("form") == null) {
            copyAllHeaders(request, headerOnlyRequest);
        } else {
            addFormHeaders(request, headerOnlyRequest);
        }
        String uaProfile = resolveUaProfile(request);
        if (uaProfile != null) {
            headerOnlyRequest.addHeader(X_WAP_PROFILE, uaProfile);
        }
        return headerOnlyRequest;
    }

    /**
     * 从原始请求中复制所有请求头到 HeaderOnlyHttpServletRequest。
     *
     * @param request           原始 HTTP 请求
     * @param headerOnlyRequest 目标请求头对象
     */

    private static void copyAllHeaders(HttpServletRequest request, HeaderOnlyHttpServletRequest headerOnlyRequest) {
        HashMap<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        headerOnlyRequest.addHeaders(headers);
    }

    /**
     * 从表单参数中提取请求头信息。
     * <p>获取表单提交的 User-Agent 和自定义请求头（格式为 {@code headerName:headerValue}，以竖线或换行分隔）。</p>
     *
     * @param request           原始 HTTP 请求
     * @param headerOnlyRequest 目标请求头对象
     */

    private static void addFormHeaders(HttpServletRequest request, HeaderOnlyHttpServletRequest headerOnlyRequest) {
        String userAgent = resolveFormUserAgent(request);
        if (userAgent != null) {
            headerOnlyRequest.addHeader(USER_AGENT, userAgent);
        }
        String rawHeaders = request.getParameter(HEADERS);
        if (rawHeaders != null && !rawHeaders.trim().isEmpty()) {
            addHeaderPairs(rawHeaders.trim(), headerOnlyRequest);
        }
    }

    /**
     * 从表单参数或请求头中解析 User-Agent。
     *
     * @param request HTTP 请求
     * @return User-Agent 字符串，如果两者都未提供则返回 {@code null}
     */

    private static String resolveFormUserAgent(HttpServletRequest request) {
        String userAgent = request.getParameter("ua");
        if (userAgent != null && !userAgent.trim().isEmpty()) {
            return userAgent.trim();
        }
        return request.getHeader(USER_AGENT);
    }

    /**
     * 按 {@code name:value} 格式解析 header 键值对字符串，添加到请求头对象中。
     * <p>每个 header 对以竖线或换行分隔。</p>
     *
     * @param rawHeaders        原始 header 字符串
     * @param headerOnlyRequest 目标请求头对象
     */

    private static void addHeaderPairs(String rawHeaders, HeaderOnlyHttpServletRequest headerOnlyRequest) {
        String[] headerPairs = LINE_BREAK_PATTERN.matcher(rawHeaders).replaceAll("|").split("\\|");
        for (String headerPair : headerPairs) {
            int colonIndex = headerPair.indexOf(':');
            if (colonIndex > 0) {
                headerOnlyRequest.addHeader(
                        headerPair.substring(0, colonIndex).trim(),
                        headerPair.substring(colonIndex + 1).trim()
                );
            }
        }
    }

    /**
     * 解析能力筛选参数，以换行或竖线分隔。
     *
     * @param rawCapabilities 原始能力名称字符串
     * @return 能力名称数组，如果未提供则返回空数组
     */

    private static String[] parseCapabilities(String rawCapabilities) {
        if (rawCapabilities == null || rawCapabilities.trim().isEmpty()) {
            return new String[0];
        }
        return LINE_BREAK_PATTERN.matcher(rawCapabilities.trim()).replaceAll("|").split("\\|");
    }
}
