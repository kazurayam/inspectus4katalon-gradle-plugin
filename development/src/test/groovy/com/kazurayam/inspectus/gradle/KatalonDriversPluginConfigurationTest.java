package com.kazurayam.inspectus.gradle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KatalonDriversPluginConfigurationTest {

    @Test
    public void test_materialstoreVersion() {
        KatalonDriversPluginConfiguration config = new KatalonDriversPluginConfiguration();
        assertEquals("0.12.4", config.materialstoreVersion);
    }

    @Test
    public void test_update_materialstoreVersion() {
        String version = "0.12.5";
        KatalonDriversPluginConfiguration config = new KatalonDriversPluginConfiguration();
        config.materialstoreVersion = version;
        assertEquals(version, config.materialstoreVersion);
    }
}
