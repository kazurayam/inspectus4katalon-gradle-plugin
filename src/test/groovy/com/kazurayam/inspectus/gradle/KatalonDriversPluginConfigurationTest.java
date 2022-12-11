package com.kazurayam.inspectus.gradle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KatalonDriversPluginConfigurationTest {

    @Test
    public void test_inspectusVersion() {
        KatalonDriversPluginConfiguration config = new KatalonDriversPluginConfiguration();
        assertEquals("0.7.2", config.inspectusVersion);
    }

    @Test
    public void test_update_inspectusVersion() {
        String version = "0.12.5";
        KatalonDriversPluginConfiguration config = new KatalonDriversPluginConfiguration();
        config.inspectusVersion = version;
        assertEquals(version, config.inspectusVersion);
    }
}
