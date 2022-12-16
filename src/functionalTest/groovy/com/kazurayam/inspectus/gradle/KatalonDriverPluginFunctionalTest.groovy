package com.kazurayam.inspectus.gradle

import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification
import spock.lang.TempDir
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class KatalonDriverPluginFunctionalTest extends Specification {

    @TempDir File testProjectDir
    File buildFile

    def setup() {
        buildFile = new File(testProjectDir, 'build.gradle')
        buildFile << """
            plugins {
                id 'com.kazurayam.inspectus4katalon'
            }
        """
    }

}
