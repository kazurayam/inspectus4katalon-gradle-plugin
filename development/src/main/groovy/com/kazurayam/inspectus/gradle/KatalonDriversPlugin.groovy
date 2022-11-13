package com.kazurayam.inspectus.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class KatalonDriversPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def myConf = project.configurations.create("myConf")
        project.dependencies({
            add(myConf.getName(), [group: 'com.kazurayam', name: 'materialstore', version: "0.12.2"])
            add(myConf.getName(), [group: 'com.kazurayam', name: 'ExecutionProfilesLoader', version: '1.2.1'])
            add(myConf.getName(), [group: 'ru.yandex.qatools.ashot', name: 'ashot', version: '1.5.4'])
            add(myConf.getName(), [group: 'io.github.java-diff-utils', name: 'java-diff-utils', version: '4.11'])
            add(myConf.getName(), [group: 'org.jsoup', name: 'jsoup', version: '1.14.3'])
            add(myConf.getName(), [group: 'org.freemarker', name: 'freemarker', version: "2.3.31"])
        })
        project.repositories({
            mavenCentral()
            mavenLocal()
        })
        project.task("drivers") {
            String AUTO_IMPORTED_JAR_PREFIX = "AUTOIMPORTED_"
            doFirst {
                project.delete project.fileTree("Drivers").matching {
                    include("**/" + AUTO_IMPORTED_JAR_PREFIX + "*")
                }
            }
            doLast {
                project.copy { copySpec ->
                    copySpec
                        .from(myConf)
                        .into("Drivers")
                        .include(
                            "**/materialstore*.jar",
                            "**/ExecutionProfilesLoader*.jar",
                            "**/ashot*.jar",
                            "**/jsoup*.jar",
                            "**/java-diff-utils*.jar",
                            "**/freemarker*.jar"
                        )
                        .rename({ s ->
                            "${AUTO_IMPORTED_JAR_PREFIX}${s}"
                        })
                }
                println("Ya! I am the drivers task!")
            }
        }
    }
}
