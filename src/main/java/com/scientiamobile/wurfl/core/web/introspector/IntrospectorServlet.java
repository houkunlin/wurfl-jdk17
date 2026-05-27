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
import java.util.*;
import java.util.regex.Pattern;

public class IntrospectorServlet extends HttpServlet implements WurflWebConstants {
    public static final String ESCAPED_SPLIT_CHAR = "\\|";
    public static final String SPLIT_CHAR = "|";
    public static final String COLON = ":";
    public static final String TABULATION = "\t";
    public static final String ACTION = "action";
    public static final String USER_AGENT_PARAMNAME = "ua";
    public static final String UAPROF_PARAMNAME = "uaprof";
    public static final String HEADERS = "headers";
    public static final String CAPABILITIES = "capabilities";
    public static final String ID = "id";
    public static final String CAPABILITY_MAP = "capabilityMap";
    public static final String X_WAP_PROFILE = "X-Wap-Profile";
    public static final String X_WAP_PROFILE_lc = "x-wap-profile";
    public static final String USER_AGENT_lc = "user-agent";
    public static final String USER_AGENT = "User-Agent";
    public static final String DEVICE_STOCK_UA = "Device-Stock-UA";
    public static final String REQUEST = "Request";
    public static final String REQUEST_PERFORMANCE = "RequestPerformance";
    public static final String REQUEST_ACCURACY = "RequestAccuracy";
    public static final String INFO = "Info";
    public static final String BUCKETS = "Buckets";
    private static final long serialVersionUID = 1L;
    private static final String OS_NAME = System.getProperty("os.name");
    private static final String OS_VERSION = System.getProperty("os.version");
    private static final String JAVA_VENDOR = System.getProperty("java.vendor");
    private static final String JAVA_VERSION_PROP = System.getProperty("java.version");
    private static final Pattern LINE_BREAK_PATTERN = Pattern.compile("[\r\n]+");
    private static final Logger log = LoggerFactory.getLogger(IntrospectorServlet.class);
    private static WURFLEngine wurflEngine = null;
    private static String apiVersion;

    static {
        String resourcePath = "/META-INF/maven/com.scientiamobile.wurfl/wurfl/pom.properties";
        InputStream pomPropertiesStream = IntrospectorServlet.class.getResourceAsStream(resourcePath);
        Properties pomProperties = new Properties();

        try {
            pomProperties.load(pomPropertiesStream);
            apiVersion = "1.9.1.0";
        } catch (IOException e) {
            apiVersion = "(Unavailable...)";
        } finally {
            try {
                pomPropertiesStream.close();
            } catch (IOException | RuntimeException ignore) {
            }

        }

        if (apiVersion == null || apiVersion.isEmpty()) {
            apiVersion = "(Unavailable...)";
        }

        log.info("WURFL core library running version {}", pomProperties.get("version"));
    }

    private final transient ObjectMapper objectMapper = new ObjectMapper();

    public static void setWURFLEngine(WURFLEngine wurflEngine) {
        IntrospectorServlet.wurflEngine = wurflEngine;
    }

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

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.getServletContext().getServerInfo();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.currentTimeMillis();
        String action = request.getParameter("action");
        boolean handled = false;
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        if ("Request".equalsIgnoreCase(action)) {
            wurflEngine.setEngineTarget(EngineTarget.accuracy);
            handled = this.handleRequest(request, out);
        } else if ("Info".equalsIgnoreCase(action)) {
            wurflEngine.setEngineTarget(EngineTarget.accuracy);
            if (wurflEngine == null) {
                writeMissingEngineError(out);
            } else {
                EngineTarget engineTarget = wurflEngine.getEngineTarget();
                IntrospectorInfoResponse responseBody = new IntrospectorInfoResponse();
                responseBody.apiVersion = apiVersion;
                responseBody.wurflVersion = wurflEngine.getWURFLUtils().getVersion();
                responseBody.engineTarget = engineTarget.name();
                responseBody.userAgentPriority = wurflEngine.getUserAgentPriority().name();
                responseBody.serverInfo = this.getServletContext().getServerInfo();
                responseBody.osName = OS_NAME;
                responseBody.osVersion = OS_VERSION;
                responseBody.javaVendor = JAVA_VENDOR;
                responseBody.javaVersion = JAVA_VERSION_PROP;
                String json = this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseBody);
                out.println(json);
            }

            handled = true;
        } else if ("form".equalsIgnoreCase(action)) {
            wurflEngine.setEngineTarget(EngineTarget.valueOf(request.getParameter("wurflEngineTarget")));
            wurflEngine.setUserAgentPriority(UserAgentPriority.valueOf(request.getParameter("wurflUserAgentPriority")));
            handled = this.handleRequest(request, out);
        } else if ("Buckets".equalsIgnoreCase(action)) {
            handled = this.writeBuckets(out);
        }

        if (!handled) {
            out.println("action " + action + " not supported");
        }

        out.flush();
    }

    private boolean writeBuckets(PrintWriter out) {
        if (wurflEngine == null) {
            writeMissingEngineError(out);
            return true;
        }
        if (!(wurflEngine instanceof GeneralWURFLEngine engine)) {
            return false;
        }
        try {
            out.println("WURFL Java API " + apiVersion);
            WURFLModel wurflModel = engine.getWurflModel();
            WURFLService wurflService = engine.getWurflService();
            List<String> resultLines = runBucketMatching(wurflModel, wurflService);
            for (String lineObj : resultLines) {
                out.println(lineObj);
            }
            return true;
        } catch (RuntimeException e) {
            log.debug("{} - {}", e.getClass().getSimpleName(), e.getMessage());
            return false;
        }
    }

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

    private boolean handleRequest(HttpServletRequest request, PrintWriter out) {
        if (wurflEngine == null) {
            writeMissingEngineError(out);
            return true;
        }
        HeaderOnlyHttpServletRequest headerOnlyRequest = buildHeaderOnlyRequest(request);
        String rawCapabilities = request.getParameter("capabilities");
        String[] capabilities = parseCapabilities(rawCapabilities);
        Device device = wurflEngine.getDeviceForRequest(headerOnlyRequest);
        IntrospectorRequestResponse responseBody = new IntrospectorRequestResponse();
        responseBody.deviceId = device.getId();
        responseBody.userAgent = headerOnlyRequest.getHeader("User-Agent");
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
        return true;
    }

    private static String resolveUaProfile(HttpServletRequest request) {
        String uaProfile = request.getParameter("uaprof");
        if (uaProfile != null && !uaProfile.trim().isEmpty()) {
            return uaProfile.trim();
        }
        uaProfile = request.getHeader("x-wap-profile");
        if (uaProfile != null && !uaProfile.isEmpty()) {
            return uaProfile;
        }
        return request.getHeader("X-Wap-Profile");
    }

    private static HeaderOnlyHttpServletRequest buildHeaderOnlyRequest(HttpServletRequest request) {
        HeaderOnlyHttpServletRequest headerOnlyRequest = new HeaderOnlyHttpServletRequest();
        if (request.getParameter("form") == null) {
            copyAllHeaders(request, headerOnlyRequest);
        } else {
            addFormHeaders(request, headerOnlyRequest);
        }
        String uaProfile = resolveUaProfile(request);
        if (uaProfile != null) {
            headerOnlyRequest.addHeader("X-Wap-Profile", uaProfile);
        }
        return headerOnlyRequest;
    }

    private static void copyAllHeaders(HttpServletRequest request, HeaderOnlyHttpServletRequest headerOnlyRequest) {
        HashMap<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        headerOnlyRequest.addHeaders(headers);
    }

    private static void addFormHeaders(HttpServletRequest request, HeaderOnlyHttpServletRequest headerOnlyRequest) {
        String userAgent = resolveFormUserAgent(request);
        if (userAgent != null) {
            headerOnlyRequest.addHeader("User-Agent", userAgent);
        }
        String rawHeaders = request.getParameter("headers");
        if (rawHeaders != null && !rawHeaders.trim().isEmpty()) {
            addHeaderPairs(rawHeaders.trim(), headerOnlyRequest);
        }
    }

    private static String resolveFormUserAgent(HttpServletRequest request) {
        String userAgent = request.getParameter("ua");
        if (userAgent != null && !userAgent.trim().isEmpty()) {
            return userAgent.trim();
        }
        return request.getHeader("User-Agent");
    }

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

    private static String[] parseCapabilities(String rawCapabilities) {
        if (rawCapabilities == null || rawCapabilities.trim().isEmpty()) {
            return new String[0];
        }
        return LINE_BREAK_PATTERN.matcher(rawCapabilities.trim()).replaceAll("|").split("\\|");
    }
}
