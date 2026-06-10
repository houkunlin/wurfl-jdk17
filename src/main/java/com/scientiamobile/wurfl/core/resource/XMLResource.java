package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.resource.exc.WURFLResourceException;
import org.slf4j.LoggerFactory;

import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of XML Resource.
 */

public class XMLResource implements WURFLResource {
    private static final SAXParserFactory SAX_PARSER_FACTORY;

    static {
        LoggerFactory.getLogger(XMLResource.class);
        SAX_PARSER_FACTORY = SAXParserFactory.newInstance();
    }

    private final ResourceInput resourceInput;
    private String version;
    private Set<String> includedCapabilities;
    private String originalPath;

    public XMLResource(String originalPath) {
        this.originalPath = originalPath;
        this.resourceInput = new ResourceInput(originalPath);
    }

    public XMLResource(File originalFile) {
        this.originalPath = originalFile.getAbsolutePath();
        this.resourceInput = new ResourceInput(originalFile);
    }

    public XMLResource(URI uri) {
        this.resourceInput = new ResourceInput(uri);
    }

    public XMLResource(InputStream inputStream, String originalPath) {
        this.resourceInput = new ResourceInput(inputStream, originalPath);
    }

    @Override
/**
 * Returns the data.
 */

    public ModelDevicesSnapshot getData(String... includedCapabilities) {
        if (includedCapabilities != null) {
            this.includedCapabilities = new HashSet<>(includedCapabilities.length);
            for (String capabilityName : includedCapabilities) {
                this.includedCapabilities.add(capabilityName);
            }
        } else {
            this.includedCapabilities = new HashSet<>(0);
        }

        ModelDevicesSnapshot snapshot = this.parseSnapshot(this.resourceInput.openInputStream());
        this.resourceInput.reset();
        return snapshot;
    }

    /**
     * Returns the origina lath.
     */

    public String getOriginalPath() {
        return this.originalPath;
    }

    public String getInfo() {
        return this.resourceInput.getResourceName();
    }

    @Override
/**
 * Returns the version.
 */

    public String getVersion() {
        return this.version;
    }

    public void release() {
        this.resourceInput.close();
    }

    /**
     * Pars enapshot.
 */

    private ModelDevicesSnapshot parseSnapshot(InputStream inputStream) {
        WurflXmlHandler handler = new WurflXmlHandler(this.includedCapabilities);

        try {
            SAX_PARSER_FACTORY.newSAXParser().parse(inputStream, handler);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new WURFLResourceException(this, e);
        }

        String info = this.getInfo();
        String wurflVersion = handler.getWurflVersion();
        String wurflLastUpdated = handler.getWurflLastUpdated();
        String wurflSmid = handler.getWurflSmid();
        this.version = wurflVersion != null && wurflVersion.length() != 0 ? wurflVersion : (wurflLastUpdated != null && wurflLastUpdated.length() != 0 ? wurflLastUpdated : "(no version info)");
        boolean patch = handler.isPatch();
        ModelDevices devices = handler.getDevices();
        return new ModelDevicesSnapshot(info, this.version, patch, devices, wurflSmid);
    }
}
