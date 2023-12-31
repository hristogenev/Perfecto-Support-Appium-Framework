package io.perfecto.tests.utilities.capabilities;

import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.MobilePlatform;
import io.perfecto.utilities.capabilities.CommonCapabilities;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class CommonCapabilitiesTests {

    @Test
    public void duplicate_capability_overrides_initial() throws Exception {
        CommonCapabilities capabilities = new CommonCapabilities();

        capabilities.addPerfectoOption("openDeviceTimeout", 3);
        Map<String, Object> map = capabilities.getPerfectoOptionsMap();
        assertEquals(3, map.get("openDeviceTimeout"));

        capabilities.openDeviceTimeout = 2;
        map = capabilities.getPerfectoOptionsMap();
        assertEquals(2, map.get("openDeviceTimeout"));
    }


    @Test
    public void perfecto_token_is_automatically_added() throws Exception {
        CommonCapabilities capabilities = new CommonCapabilities("dummy");

        Map<String, Object> map = capabilities.getPerfectoOptionsMap();
        assertEquals("dummy", map.get("securityToken"));
    }


    @Test
    public void is_local_execution_returns_true_for_local_executions() throws Exception {
        CommonCapabilities capabilities = new CommonCapabilities();
        assertTrue(capabilities.isLocalExecution());

        capabilities = new CommonCapabilities("local");
        assertTrue(capabilities.isLocalExecution());

        capabilities = new CommonCapabilities("LOCAL");
        assertTrue(capabilities.isLocalExecution());

        capabilities = new CommonCapabilities("localhost");
        assertTrue(capabilities.isLocalExecution());

        capabilities = new CommonCapabilities("127.0.0.1");
        assertTrue(capabilities.isLocalExecution());
    }

    @Test
    public void is_local_execution_returns_false_for_perfecto_executions() throws Exception {
        CommonCapabilities capabilities = new CommonCapabilities("demo");
        assertFalse(capabilities.isLocalExecution());

    }

    @Test
    public void for_local_execution_perfecto_options_is_null() throws Exception {
        CommonCapabilities capabilities = new CommonCapabilities("localhost");
        assertNull(capabilities.getPerfectoOptionsMap());
    }


    @Test
    public void as_map_returns_appium_and_perfecto_capabilities() throws Exception {
        CommonCapabilities capabilities = new CommonCapabilities("dummy");
        capabilities.platformName = MobilePlatform.IOS;
        XCUITestOptions opts = (XCUITestOptions) capabilities.toOptions();
        assertNotNull(opts.getCapability("perfecto:options"));
    }
}
