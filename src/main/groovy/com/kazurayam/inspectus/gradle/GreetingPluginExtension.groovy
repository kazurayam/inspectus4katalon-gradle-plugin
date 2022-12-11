package com.kazurayam.inspectus.gradle

import org.gradle.api.provider.Property

abstract class GreetingPluginExtension {
    abstract Property<String> getMessage()

    GreetingPluginExtension() {
        message.convention('Hello from GreetingPlugin')
    }
}
