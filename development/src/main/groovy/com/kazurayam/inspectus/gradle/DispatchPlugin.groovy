package com.kazurayam.inspectus.gradle


import org.gradle.api.Plugin
import org.gradle.api.Project

class DispatchPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.dependencies({
            testImplementation group: 'com.kazurayam', name: 'materialstore', version: "0.12.1"
            testImplementation group: 'com.kazurayam', name: 'ExecutionProfilesLoader', version: '1.2.1'
            testImplementation group: 'ru.yandex.qatools.ashot', name: 'ashot', version: '1.5.4'
            testImplementation group: 'io.github.java-diff-utils', name: 'java-diff-utils', version: '4.11'
            testImplementation group: 'org.jsoup', name: 'jsoup', version: '1.14.3'
            testImplementation group: 'org.freemarker', name: 'freemarker', version: "2.3.31"
        })
        project.repositories({
            mavenCentral()
            mavenLocal()
        })
        project.task("initInspectus") {
            dependsOn("dependencies")
            doLast {
                println("Ya! I am initInspectus task!")
            }
        }
    }
}
