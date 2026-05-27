package com.scientiamobile.wurfl.core.web.introspector;

import com.scientiamobile.wurfl.core.*;
import com.scientiamobile.wurfl.core.matchers.MatcherManager;
import com.scientiamobile.wurfl.core.request.DefaultWURFLRequestFactory;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
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
import java.lang.reflect.Field;
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

    private static String readStringField(String fieldName, Object target) {
        try {
            Field field;
            field = target.getClass().getDeclaredField(fieldName);
            boolean originalAccess = field.canAccess(target);
            field.setAccessible(true);
            String value = (String) field.get(target);
            field.setAccessible(originalAccess);
            return value;
        } catch (ReflectiveOperationException | RuntimeException e) {
            return null;
        }
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
        out.println(message.toString());
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
        } else {
            try {
                out.println("WURFL Java API " + apiVersion);
                if (wurflEngine instanceof GeneralWURFLEngine engine) {
                    WURFLModel wurflModel = engine.getWurflModel();
                    WURFLService wurflService = engine.getWurflService();
                    ArrayList<String> resultLines = null;
                    if (wurflModel != null && wurflService != null) {
                        EngineTarget originalEngineTarget = wurflService.getEngineTarget();
                        MatcherManager matcherManager = wurflService.getMatcherManager();
                        Set<ModelDevice> allDevices = wurflModel.getAllDevices();
                        ArrayList<String> userAgents = new ArrayList<>(allDevices.size());

                        for (ModelDevice device : allDevices) {
                            if (!StringUtils.isEmpty(device.getUserAgent())) {
                                userAgents.add(device.getUserAgent());
                            }
                        }

                        wurflService.setEngineTarget(EngineTarget.accuracy);
                        resultLines = new ArrayList<>(userAgents.size());
                        ArrayList<MatchResultRow> matchResults = new ArrayList<>(userAgents.size());
                        DefaultWURFLRequestFactory requestFactory = new DefaultWURFLRequestFactory();
                        log.info("BUCKETS (1/3): start matching...");
                        long start = System.currentTimeMillis();

                        for (String ua : userAgents) {
                            DeviceInfo deviceInfo = matcherManager.matchRequest(requestFactory.createRequest(ua, EngineTarget.accuracy));
                            String matcherName = deviceInfo.getMatcherName();
                            String normalizedUserAgent = deviceInfo.getNormalizedUserAgent();
                            String originalUserAgent = deviceInfo.getOriginalUserAgent();
                            matchResults.add(new MatchResultRow(matcherName, deviceInfo.getId(), normalizedUserAgent, originalUserAgent));
                        }

                        log.info("BUCKETS (1/3): finished matching. Took {} ms", System.currentTimeMillis() - start);
                        log.info("BUCKETS (2/3): start sorting...");
                        start = System.currentTimeMillis();
                        Collections.sort(matchResults);
                        log.info("BUCKETS (2/3): finished sorting. Took {} ms", System.currentTimeMillis() - start);
                        log.info("BUCKETS (3/3): start building strings...");
                        start = System.currentTimeMillis();

                        for (MatchResultRow row : matchResults) {
                            resultLines.add(row.toString());
                        }

                        log.info("BUCKETS (3/3): finished building strings. Took {} ms", System.currentTimeMillis() - start);
                        wurflService.setEngineTarget(originalEngineTarget);
                    }

                    if (resultLines != null) {
                        for (String lineObj : resultLines) {
                            out.println(lineObj);
                        }
                    }

                    return true;
                }
                return false;
            } catch (RuntimeException e) {
                log.debug("{} - {}", e.getClass().getSimpleName(), e.getMessage());
                return false;
            }
        }
    }

    private boolean handleRequest(HttpServletRequest request, PrintWriter out) {
        if (wurflEngine == null) {
            writeMissingEngineError(out);
            return true;
        } else {
            String uaProfile;
            uaProfile = request.getParameter("uaprof");
            if (uaProfile == null || uaProfile.trim().isEmpty()) {
                uaProfile = request.getHeader("x-wap-profile");
            }

            if (uaProfile == null) {
                uaProfile = request.getHeader("X-Wap-Profile");
            }

            HeaderOnlyHttpServletRequest headerOnlyRequest = new HeaderOnlyHttpServletRequest();
            if (request.getParameter("form") == null) {
                HashMap<String, String> headers = new HashMap<>();
                Enumeration<String> headerNames = request.getHeaderNames();

                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    if ("User-Agent".equalsIgnoreCase(headerName)) {
                        headers.put("User-Agent", request.getHeader(headerName));
                    } else {
                        headers.put(headerName, request.getHeader(headerName));
                    }
                }

                headerOnlyRequest.addHeaders(headers);
                UserAgentUtils.getUserAgent(request);
            } else {
                String userAgent;
                userAgent = request.getParameter("ua");
                if (userAgent == null || userAgent.trim().isEmpty()) {
                    userAgent = request.getHeader("User-Agent");
                }

                if (userAgent != null) {
                    headerOnlyRequest.addHeader("User-Agent", userAgent);
                }

                String rawHeaders;
                rawHeaders = request.getParameter("headers");
                if (rawHeaders != null && !rawHeaders.trim().isEmpty()) {
                    rawHeaders = rawHeaders.trim();
                    String[] headerPairs = LINE_BREAK_PATTERN.matcher(rawHeaders).replaceAll("|").split("\\|");

                    for (String headerPair : headerPairs) {
                        if (headerPair.contains(":")) {
                            String[] headerKeyValue = headerPair.split(":");
                            headerOnlyRequest.addHeader(headerKeyValue[0].trim(), headerKeyValue[1].trim());
                        }
                    }
                }
            }

            if (uaProfile != null) {
                headerOnlyRequest.addHeader("X-Wap-Profile", uaProfile);
            }

            String rawCapabilities = request.getParameter("capabilities");
            String[] capabilities = null;
            if (rawCapabilities != null && !rawCapabilities.trim().isEmpty()) {
                rawCapabilities = rawCapabilities.trim();
                capabilities = LINE_BREAK_PATTERN.matcher(rawCapabilities).replaceAll("|").split("\\|");
            }

            IntrospectorRequestResponse responseBody = new IntrospectorRequestResponse();
            Device device = wurflEngine.getDeviceForRequest(headerOnlyRequest);
            responseBody.deviceId = device.getId();
            responseBody.userAgent = headerOnlyRequest.getHeader("User-Agent");
            responseBody.requestType = request.getParameter("form") == null ? "request" : "form";
            if (capabilities != null && capabilities.length > 0) {
                HashMap<String, String> capabilityMap = new HashMap<>();

                for (String capability : capabilities) {
                    String capabilityName = capability.trim();
                    capabilityMap.put(capabilityName, device.getCapability(capabilityName));
                }
                responseBody.capabilities = capabilityMap;
            }

            String json = this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseBody);
            out.println(json);
            return true;
        }
    }
}
