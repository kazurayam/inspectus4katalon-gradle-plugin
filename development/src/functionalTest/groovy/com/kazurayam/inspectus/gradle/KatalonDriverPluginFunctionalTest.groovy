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
        result.task(":dependencies").outcome == SUCCESS
    }

    /**
     * test if the "showAllDependenciesInInspectusConfiguration" task works
     */
    def "test showAllDependenciesInInspectusConfiguration task"() {
        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withArguments('showAllDependenciesInInspectusConfiguration')
                .withPluginClasspath()
                .build()

        then:
        //println result.output
        /*
        > Task :showAllDependenciesInInspectusConfiguration
configuration ':Inspectus' dependencies:
name:'ExecutionProfilesLoader', group:'com.kazurayam', version:'1.2.1'
name:'FontVerter', group:'net.mabboud.fontverter', version:'1.2.22'
name:'annotations', group:'com.google.code.findbugs', version:'2.0.1'
name:'ashot', group:'ru.yandex.qatools.ashot', version:'1.5.4'
name:'byte-buddy', group:'net.bytebuddy', version:'1.8.15'
name:'checker-qual', group:'org.checkerframework', version:'3.12.0'
name:'commons-codec', group:'commons-codec', version:'1.10'
name:'commons-collections4', group:'org.apache.commons', version:'4.1'
name:'commons-csv', group:'org.apache.commons', version:'1.9.0'
name:'commons-exec', group:'org.apache.commons', version:'1.3'
name:'commons-io', group:'commons-io', version:'2.11.0'
name:'commons-lang3', group:'org.apache.commons', version:'3.4'
name:'commons-logging', group:'commons-logging', version:'1.2'
name:'curvesapi', group:'com.github.virtuald', version:'1.04'
name:'error_prone_annotations', group:'com.google.errorprone', version:'2.11.0'
name:'failureaccess', group:'com.google.guava', version:'1.0.1'
name:'fontbox', group:'org.apache.pdfbox', version:'2.0.26'
name:'freemarker', group:'org.freemarker', version:'2.3.31'
name:'groovy-all', group:'org.codehaus.groovy', version:'2.4.21'
name:'gson', group:'com.google.code.gson', version:'2.8.2'
name:'guava', group:'com.google.guava', version:'31.1-jre'
name:'hamcrest-core', group:'org.hamcrest', version:'1.3'
name:'httpclient5', group:'org.apache.httpcomponents.client5', version:'5.2'
name:'httpcore5', group:'org.apache.httpcomponents.core5', version:'5.2'
name:'httpcore5-h2', group:'org.apache.httpcomponents.core5', version:'5.2'
name:'inspectus', group:'com.kazurayam', version:'0.6.0'
name:'j2objc-annotations', group:'com.google.j2objc', version:'1.3'
name:'java-diff-utils', group:'io.github.java-diff-utils', version:'4.11'
name:'javassist', group:'org.javassist', version:'3.18.2-GA'
name:'jdom2', group:'org.jdom', version:'2.0.6.1'
name:'jsoup', group:'org.jsoup', version:'1.14.3'
name:'jsr305', group:'com.google.code.findbugs', version:'3.0.2'
name:'listenablefuture', group:'com.google.guava', version:'9999.0-empty-to-avoid-conflict-with-guava'
name:'materialstore', group:'com.kazurayam', version:'0.13.0'
name:'okhttp', group:'com.squareup.okhttp3', version:'3.11.0'
name:'okio', group:'com.squareup.okio', version:'1.14.0'
name:'pdf2dom', group:'net.sf.cssbox', version:'2.0.1'
name:'pdfbox', group:'org.apache.pdfbox', version:'2.0.26'
name:'pdfbox-debugger', group:'org.apache.pdfbox', version:'2.0.26'
name:'pdfbox-tools', group:'org.apache.pdfbox', version:'2.0.26'
name:'poi', group:'org.apache.poi', version:'3.17'
name:'poi-ooxml', group:'org.apache.poi', version:'3.17'
name:'poi-ooxml-schemas', group:'org.apache.poi', version:'3.17'
name:'reflections', group:'org.reflections', version:'0.9.9'
name:'rome', group:'com.rometools', version:'1.18.0'
name:'rome-utils', group:'com.rometools', version:'1.18.0'
name:'selenium-api', group:'org.seleniumhq.selenium', version:'3.141.59'
name:'selenium-chrome-driver', group:'org.seleniumhq.selenium', version:'3.141.59'
name:'selenium-edge-driver', group:'org.seleniumhq.selenium', version:'3.141.59'
name:'selenium-firefox-driver', group:'org.seleniumhq.selenium', version:'3.141.59'
name:'selenium-ie-driver', group:'org.seleniumhq.selenium', version:'3.141.59'
name:'selenium-java', group:'org.seleniumhq.selenium', version:'3.141.59'
name:'selenium-opera-driver', group:'org.seleniumhq.selenium', version:'3.141.59'
name:'selenium-remote-driver', group:'org.seleniumhq.selenium', version:'3.141.59'
name:'selenium-safari-driver', group:'org.seleniumhq.selenium', version:'3.141.59'
name:'selenium-support', group:'org.seleniumhq.selenium', version:'3.141.59'
name:'slf4j-api', group:'org.slf4j', version:'1.7.36'
name:'stax-api', group:'stax', version:'1.0.1'
name:'subprocessj', group:'com.kazurayam', version:'0.3.4'
name:'xmlbeans', group:'org.apache.xmlbeans', version:'2.6.0'
         */
        result.output.contains("name:'freemarker', group:'org.freemarker', version:'2.3.31'")
        result.task(":showAllDependenciesInInspectusConfiguration").outcome == SUCCESS
    }
}
