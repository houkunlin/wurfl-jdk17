package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.DefaultWURFLModel;
import com.scientiamobile.wurfl.core.resource.ResourceUtils;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Implementation of Check Connection.
 */

public class CheckConnection {
    private static final Logger logger = LoggerFactory.getLogger(CheckConnection.class);
    private final List<CheckConnectionObserver> observers = new ArrayList<>();
    private String payloadJson;
    private String osNameAndVersion = System.getProperty("os.name") + " " + System.getProperty("os.version");
    private String platformName;
    private boolean enabled;

    public CheckConnection() {
        this.platformName = resolvePlatformName();
        this.enabled = ResourceUtils.getFullBuildId().startsWith("ch");
    }

    /**
     * Resolv elatfor mame.
     */

    private static String resolvePlatformName() {
        if (StringUtils.isNotEmpty(System.getProperty("jboss.boot.library.list"))) {
            return "JBoss/WildFly";
        }
        if (StringUtils.isNotEmpty(System.getProperty("oracle.j2ee.home"))) {
            return "OC4j/Oracle AS";
        }
        String classPathLowerCase = System.getProperty("java.class.path").toLowerCase(Locale.ENGLISH);
        if (classPathLowerCase.contains("websphere") || classPathLowerCase.contains("web sphere")) {
            return "IBM WebSphere";
        }
        if (classPathLowerCase.contains("tomcat") || classPathLowerCase.contains("juli")) {
            return "Tomcat";
        }
        if (classPathLowerCase.contains("jetty")) {
            return "Jetty";
        }
        return "Command line";
    }

    /**
     * Appen dso nield.
 */

    private static StringBuilder appendJsonField(StringBuilder builder, String key, String value, boolean hasMoreFields) {
        builder.append("\"").append(key).append("\": \"").append(value).append("\"");
        if (hasMoreFields) {
            builder.append(", ");
        } else {
            builder.append(" ");
        }

        return builder;
    }

    /**
     * Returns the hos tam e rnknown.
 */

    private static String getHostNameOrUnknown() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (IOException | RuntimeException e) {
            return "unknown";
        }
    }

    /**
     * Sets the up.
 */

    public void setup(WURFLEngine wurflEngine, WURFLModel wurflModel) {
        if (!this.enabled || !(wurflModel instanceof DefaultWURFLModel defaultWURFLModel)) {
            return;
        }
        String wurflSmid = StringUtils.defaultIfEmpty(defaultWURFLModel.getSmid(), "unknown");
        String wurflVersion = extractVersionInfo(wurflEngine);
        this.payloadJson = buildPayload(wurflSmid, wurflVersion);
    }

    /**
     * Extrac tersio nnfo.
 */

    private static String extractVersionInfo(WURFLEngine wurflEngine) {
        String wurflVersion = wurflEngine.getWURFLUtils().getVersion();
        int versionIndex = wurflVersion.indexOf("for WURFL");
        if (versionIndex == -1) {
            return wurflVersion;
        }
        int versionEnd = wurflVersion.indexOf(";");
        return versionEnd != -1 ? wurflVersion.substring(versionIndex, versionEnd) : wurflVersion.substring(versionIndex);
    }

    /**
     * Buil dayload.
 */

    private String buildPayload(String wurflSmid, String wurflVersion) {
        StringBuilder payloadBuilder = appendJsonField(
                appendJsonField(
                        appendJsonField(
                                appendJsonField(
                                        appendJsonField(
                                                appendJsonField(
                                                        appendJsonField(
                                                                appendJsonField(new StringBuilder("{ "), "api-smid", ResourceUtils.getBuildId(), true),
                                                                "wurfl-smid", wurflSmid, true
                                                        ),
                                                        "api", this.getApiName(), true
                                                ),
                                                "api_ver", "1.9.1.0", true
                                        ),
                                        "wurfl", wurflVersion, true
                                ),
                                "host", getHostNameOrUnknown(), true
                        ),
                        "os", this.osNameAndVersion, true
                ),
                "platform", this.platformName, false
        ).append(" }");
        return payloadBuilder.toString();
    }

    /**
     * Check.
 */

    public void check() {
        if (this.enabled) {
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

            try {
                executorService.execute(new ConnectivityCheckerTask(this));
            } finally {
                executorService.shutdown();
            }

        }
    }

    /**
     * Ad dbserver.
 */

    public synchronized void addObserver(CheckConnectionObserver observer) {
        this.observers.add(observer);
    }

    public synchronized void notifyObservers(Object argument) {

        for (CheckConnectionObserver observer : this.observers) {
            observer.update(this, argument);
            logger.info("observer notified");
        }

    }

    final String getPayloadJson() {
        return this.payloadJson;
    }

    /**
     * Returns the ap iame.
 */

    public String getApiName() {
        String classPath;
        classPath = System.getProperty("java.class.path");
        return classPath != null && classPath.contains("wurfl-scala") ? "WURFL_Scala_API" : "WURFL_Java_API";
    }

    /**
     * Implementation of Check Connection Observer.
 */

    public interface CheckConnectionObserver {
        void update(CheckConnection checkConnection, Object argument);
    }
}
