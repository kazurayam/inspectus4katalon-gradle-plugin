package com.kazurayam.inspectus.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class KatalonDriversPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.extensions.create("inspectus4katalon", KatalonDriversPluginConfiguration)

        /*
         * Add a new configuration named "Inspectus".
         * We will declare the following jars in the Inspectus configuration
         * 1. inspectus
         * 2. ExecutionProfilesLoader
         *
         * Consequently Gradle will automatically resolve all the transient dependencies in
         * the Inspectus configuration.
         */
        def conf = project.configurations.create("Inspectus")

        project.repositories({
            mavenCentral()
            mavenLocal()
        })

        /**
         * copy the jars that are
         * - required to use the inspectus library AND
         * - are NOT bundled in Katalon Studio's ver 8.x binary distribution.
         *
         * The exact versions of dependencies will be automatically
         * resolved by Gradle.
         */
        project.dependencies({
            add(conf.getName(), [group: 'com.kazurayam', name: 'inspectus',
                                 version: "${project.inspectus4katalon.inspectusVersion}"])
            // the value of project.inspectus4katalon.inspectusVersion is declared
            // by the com.kazurayam.inspectus.gradle.KatalonDriversPluginConfiguration class
            add(conf.getName(), [group: 'com.kazurayam', name: 'ExecutionProfilesLoader',
                                 version: "${project.inspectus4katalon.ExecutionProfilesLoaderVersion}"])
            add(conf.getName(), [group: 'com.kazurayam', name: 'TestObjectExtension',
                    version: "${project.inspectus4katalon.TestObjectExtensionVersion}"])
        })

        /*
         * add the "drivers" task
         */
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
                        .from(conf)
                        .into("Drivers")
                            .include(
                                    "**/inspectus*.jar",
                                    "**/materialstore*.jar",
                                    "**/commons-csv*.jar",
                                    "**/ExecutionProfilesLoader*.jar",
                                    "**/ashot*.jar",
                                    "**/jsoup*.jar",
                                    "**/java-diff-utils*.jar",
                                    "**/freemarker*.jar",
                                    "**/TestObjectExtension*.jar"
                        )
                        .eachFile({ fileCopyDetails -> println fileCopyDetails.getName()})
                        .rename({ s ->
                            "${AUTO_IMPORTED_JAR_PREFIX}${s}"
                        })
                }
                //println("Ya! I am the drivers task!")
            }
        }




        /*
         * add "deploy-visual-inspection-sample-for-katalon" task
         */
        project.task("deploy-visual-inspection-sample-for-katalon") {
            String projectName = "inspectus4katalon-sample-project"
            URL url = new URL("https://github.com/kazurayam/${projectName}/releases/download/${project.inspectus4katalon.sampleProjectVersion}/distributable.zip")
            Path workDir = Paths.get("${project.buildDir}").resolve("tmp")
            Path destFile = workDir.resolve("distributable.zip")
            Path sampleProjDir = workDir.resolve("sampleProject")
            doFirst {
                // download the zip file of the sample project, unzip it
                if (Files.exists(destFile)) {
                    destFile.toFile().delete()
                }
                Files.createDirectories(workDir)
                Files.createDirectories(sampleProjDir)
                println "Downloading $url into $destFile"
                url.withInputStream { i -> destFile.toFile().withOutputStream { it << i } }
                //
                project.copy {  // unzip it to a directory
                    from project.zipTree(destFile.toFile())
                    into sampleProjDir.toFile()
                    //eachFile { println ">>> " + it.getRelativePath() }
                }
            }
            doLast {
                // deploy the files
                project.copy {
                    from sampleProjDir.toFile()
                    into "."
                    exclude ".classpath"
                    exclude ".gitignore"
                    exclude ".project"
                    exclude "build.gradle"
                    exclude "gradlew"
                    exclude "gradlew.bat"
                    exclude "*.prj"
                    exclude "settings.gradle"
                    exclude "store/"
                    exclude "store-backup/"
                    exclude "settings/"
                    exclude "Profiles/default.glbl"
                    //
                    include "Include/data/**/*"
                    include "Object Repository/CURA/**/*"
                    include "Profiles/**/*"
                    include "Scripts/**/*"
                    include "Test Cases/**/*"
                    eachFile { println "... " + it.getRelativePath() }
                }
                println "deployed the sample project v${project.inspectus4katalon.sampleProjectVersion}"
            }
        }
    }
}
