package com.kazurayam.inspectus.gradle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KatalonDriversPluginConfigurationTest {

    @Test
    public void test_inspectusVersion() {
        KatalonDriversPluginConfiguration config = new KatalonDriversPluginConfiguration();
        assertEquals("0.8.1", config.inspectusVersion);
    }

    @Test
    public void test_ExecutionProfilesLoaderVersion() {
        KatalonDriversPluginConfiguration config = new KatalonDriversPluginConfiguration();
        assertEquals("1.2.1", config.ExecutionProfilesLoaderVersion);
    }

    @Test
    public void test_sampleProjectVersion() {
        KatalonDriversPluginConfiguration config = new KatalonDriversPluginConfiguration();
        assertEquals("0.3.2", config.sampleProjectVersion);
    }

    @Test
    public void test_update_inspectusVersion() {
        String version = "999.999.999";
        KatalonDriversPluginConfiguration config = new KatalonDriversPluginConfiguration();
        config.inspectusVersion = version;
        assertEquals(version, config.inspectusVersion);
    }

    @Test
    public void test_update_sampleProjectVersion() {
        String version = "999.999.999";
        KatalonDriversPluginConfiguration config = new KatalonDriversPluginConfiguration();
        config.sampleProjectVersion = version;
        assertEquals(version, config.sampleProjectVersion);
    }
}
