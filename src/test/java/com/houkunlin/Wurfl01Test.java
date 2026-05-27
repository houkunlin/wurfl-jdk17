package com.houkunlin;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.GeneralWURFLEngine;
import com.scientiamobile.wurfl.core.resource.XMLResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

class Wurfl01Test {
    private static final File file = new File("libs/wurfl.zip");
    private static final GeneralWURFLEngine wurfl;

    static {
        try {
            wurfl = new GeneralWURFLEngine(new XMLResource(new FileInputStream(file), file.getName()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void setup() {
        wurfl.load();
    }

    @Test
    void testVersion() {
        Assertions.assertEquals("1.9.1.0", wurfl.getAPIVersion());
        Assertions.assertEquals("1.9.1.0", wurfl.getApiVersion());
        Assertions.assertTrue(wurfl.getWURFLUtils().getVersion().startsWith("Root:Stream resource:data.scientiamobile.com"));
    }

    @Test
    void testGoogleBrowser() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36");
        System.out.println(device);
        System.out.println(device.getVirtualCapabilities());
        System.out.println(device.getCapabilities());
        Assertions.assertEquals("google_chrome_147", device.getId());
        Assertions.assertEquals("conclusive", device.getMatchType().name());
        Assertions.assertEquals("false", device.getVirtualCapability("is_app_webview"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_app"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_mobile"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_phone"));
        Assertions.assertEquals("Chrome browser", device.getVirtualCapability("advertised_app_name"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_full_desktop"));
        Assertions.assertEquals("Chrome", device.getVirtualCapability("advertised_browser"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_smartphone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_robot"));
        Assertions.assertEquals("Google Chrome", device.getVirtualCapability("complete_device_name"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_largescreen"));
        Assertions.assertEquals("Windows", device.getVirtualCapability("advertised_device_os"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_android"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_xhtmlmp_preferred"));
        Assertions.assertEquals("Google Chrome", device.getVirtualCapability("device_name"));
        Assertions.assertEquals("147.0.0.0", device.getVirtualCapability("advertised_browser_version"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_html_preferred"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_windows_phone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_ios"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_touchscreen"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_wml_preferred"));
        Assertions.assertEquals("Desktop", device.getVirtualCapability("form_factor"));
        Assertions.assertEquals("10", device.getVirtualCapability("advertised_device_os_version"));
    }

    @Test
    void testOnePlusPLU110() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; Android 16; PLU110 Build/BP2A.250605.015; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/138.0.7204.179 Mobile Safari/537.36");
        System.out.println(device);
        System.out.println(device.getVirtualCapabilities());
        System.out.println(device.getCapabilities());
        Assertions.assertEquals("true", device.getVirtualCapability("is_app_webview"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_app"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_mobile"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_phone"));
        Assertions.assertEquals("Chrome browser", device.getVirtualCapability("advertised_app_name"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_full_desktop"));
        Assertions.assertEquals("Chromium", device.getVirtualCapability("advertised_browser"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_smartphone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_robot"));
        Assertions.assertEquals("OnePlus PLU110 (Turbo 6)", device.getVirtualCapability("complete_device_name"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_largescreen"));
        Assertions.assertEquals("Android", device.getVirtualCapability("advertised_device_os"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_android"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_xhtmlmp_preferred"));
        Assertions.assertEquals("OnePlus Turbo 6", device.getVirtualCapability("device_name"));
        Assertions.assertEquals("138.0.7204.179", device.getVirtualCapability("advertised_browser_version"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_html_preferred"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_windows_phone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_ios"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_touchscreen"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_wml_preferred"));
        Assertions.assertEquals("Smartphone", device.getVirtualCapability("form_factor"));
        Assertions.assertEquals("16", device.getVirtualCapability("advertised_device_os_version"));
    }


    @Test
    void testOnePlus12Quark() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; U; Android 16; zh-CN; PJD110 Build/BP2A.250605.015) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/144.0.7559.86 Quark/10.10.0.1075 Mobile Safari/537.36");
        System.out.println(device);
        System.out.println(device.getVirtualCapabilities());
        System.out.println(device.getCapabilities());
        Assertions.assertEquals("false", device.getVirtualCapability("is_app_webview"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_app"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_mobile"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_phone"));
        Assertions.assertEquals("Chrome browser", device.getVirtualCapability("advertised_app_name"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_full_desktop"));
        Assertions.assertEquals("Chromium", device.getVirtualCapability("advertised_browser"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_smartphone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_robot"));
        Assertions.assertEquals("Generic Android 4.0", device.getVirtualCapability("complete_device_name"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_largescreen"));
        Assertions.assertEquals("Android", device.getVirtualCapability("advertised_device_os"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_android"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_xhtmlmp_preferred"));
        Assertions.assertEquals("Generic Android 4.0", device.getVirtualCapability("device_name"));
        Assertions.assertEquals("144.0.7559.86", device.getVirtualCapability("advertised_browser_version"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_html_preferred"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_windows_phone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_ios"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_touchscreen"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_wml_preferred"));
        Assertions.assertEquals("Smartphone", device.getVirtualCapability("form_factor"));
        Assertions.assertEquals("16", device.getVirtualCapability("advertised_device_os_version"));
    }
}
