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

    /**
     * test if we can specify a value for the materialstoreVersion property
     * @return
     */
    def "test drivers task"() {
        /*
        buildFile << """
           drivers {
               inspectusVersion = "0.8.2"
           }
        """
         */
        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withArguments('drivers')
                .withPluginClasspath()
                .build()
        then:
        result.output.contains("inspectus")
        result.output.contains("ExecutionProfilesLoader")
        result.output.contains("materialstore")
        result.output.contains("ashot")
        result.output.contains("commons-csv")
        result.output.contains("jsoup")
        result.output.contains("freemarker")
        result.output.contains("java-diff-utils")
        result.task(":drivers").outcome == SUCCESS
    }

}
