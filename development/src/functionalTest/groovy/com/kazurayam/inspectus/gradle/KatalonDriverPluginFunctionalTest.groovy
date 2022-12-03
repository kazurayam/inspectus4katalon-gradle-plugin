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
    def "test showImmediateDependencies task"() {
        buildFile << """
           drivers {
               inspectusVersion = "0.5.4"
           }
        """
        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withArguments('showImmediateDependencies')
                .withPluginClasspath()
                .build()

        then:
        result.output.contains("0.5.4")
        result.task(":showImmediateDependencies").outcome == SUCCESS
    }

    /*
$ gradle dependencies --configuration Inspectus

> Task :dependencies

------------------------------------------------------------
Root project 'inspectus4katalon-gradle-plugin-application'
------------------------------------------------------------

Inspectus
+--- com.kazurayam:inspectus:0.5.5
+--- com.kazurayam:materialstore:0.12.5
+--- com.kazurayam:ExecutionProfilesLoader:1.2.1
|    \--- org.codehaus.groovy:groovy-all:2.4.21
+--- ru.yandex.qatools.ashot:ashot:1.5.4
|    +--- org.seleniumhq.selenium:selenium-remote-driver:2.53.0
|    |    +--- cglib:cglib-nodep:2.1_3
|    |    +--- com.google.code.gson:gson:2.3.1 -> 2.6.2
|    |    +--- org.seleniumhq.selenium:selenium-api:2.53.0
|    |    |    +--- com.google.guava:guava:19.0
|    |    |    \--- com.google.code.gson:gson:2.3.1 -> 2.6.2
|    |    +--- org.apache.httpcomponents:httpclient:4.5.1
|    |    |    +--- org.apache.httpcomponents:httpcore:4.4.3
|    |    |    +--- commons-logging:commons-logging:1.2
|    |    |    \--- commons-codec:commons-codec:1.9
|    |    +--- com.google.guava:guava:19.0
|    |    +--- org.apache.commons:commons-exec:1.3
|    |    +--- net.java.dev.jna:jna:4.1.0
|    |    \--- net.java.dev.jna:jna-platform:4.1.0
|    |         \--- net.java.dev.jna:jna:4.1.0
|    +--- commons-io:commons-io:2.5
|    +--- com.google.code.gson:gson:2.6.2
|    \--- org.hamcrest:hamcrest-core:1.3
+--- io.github.java-diff-utils:java-diff-utils:4.11
+--- org.jsoup:jsoup:1.14.3
\--- org.freemarker:freemarker:2.3.31
     */
    def "check if the Inspectus configuration includes the ExecutionProfilesLoader"() {
        buildFile << """
           drivers {
               inspectusVersion = "0.12.5"
           }
        """
        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withArguments('dependencies', '--configuration', 'Inspectus')
                .withPluginClasspath()
                .build()

        then:
        result.output.contains("com.kazurayam:ExecutionProfilesLoader:1.2.1")
        //result.output.contains("org.freemarker:freemarker:2.3.31")
        result.task(":dependencies").outcome == SUCCESS
    }
}
