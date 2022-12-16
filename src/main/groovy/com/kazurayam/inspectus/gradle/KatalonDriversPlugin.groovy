package com.kazurayam.inspectus.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ModuleVersionIdentifier
import org.gradle.api.artifacts.ResolvedArtifact
import org.gradle.api.artifacts.ResolvedModuleVersion

import java.nio.file.Files
import java.nio.file.Paths

class KatalonDriversPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.extensions.create("inspectus4katalon", KatalonDriversPluginConfiguration)

        /*
         * add the "showImmediateDependencies" task
         */
        project.task("showImmediateDependencies").doFirst {
            println "com.kazurayam:inspectus:${project.inspectus4katalon.inspectusVersion}"
            println "com.kazurayam:ExecutionProfileLoader:${project.inspectus4katalon.ExecutionProfilesLoaderVersion}"
        }

        /*
         * add the "showAllDependenciesInInspectusConfiguration" task
         */
        project.task("showAllDependenciesInInspectusConfiguration").doFirst {
            project.configurations.each { conf ->
                println "${conf} dependencies:"
                //println "conf.isCanBeResolved()=${conf.isCanBeResolved()}"
                if (conf.isCanBeResolved()) {
                    Set<ResolvedArtifact> resolvedArtifactSet =
                            conf.getResolvedConfiguration().getResolvedArtifacts()
                    List<ResolvedArtifact> resolvedArtifactList =
                            new ArrayList<>(resolvedArtifactSet)
                    // sort the list by the name of ResolvedArtifact
                    Collections.sort(resolvedArtifactList, new Comparator<ResolvedArtifact>() {
                        @Override
                        public int compare(ResolvedArtifact ra1, ResolvedArtifact ra2) {
                            return ra1.getName().compareTo(ra2.getName())
                        }
                    })
                    resolvedArtifactList.each { ra ->
                        ResolvedModuleVersion rmv = ra.getModuleVersion()
                        ModuleVersionIdentifier mvi = rmv.getId()
                        //println "${ra}"
                        //println "  ra.getType()=${ra.getType()}"
                        StringBuilder sb = new StringBuilder()
                        sb.append("name:'${mvi.getName()}'")
                        sb.append(", ")
                        sb.append("group:'${mvi.getGroup()}'")
                        sb.append(", ")
                        sb.append("version:'${mvi.getVersion()}'")
                        println sb.toString()
                    }
                }
            }
        }

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
                    add(conf.getName(), [group: 'com.kazurayam', name: 'ExecutionProfilesLoader',
                                         version: "${project.inspectus4katalon.ExecutionProfilesLoaderVersion}"])
                })

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
                                    "**/freemarker*.jar"
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
            String src = "https://github.com/kazurayam/${projectName}/archive/refs/tags/${project.inspectus4katalon.sampleProjectVersion}.zip"
            String workDir = "${project.buildDir}/tmp"
            String destFile = "${workDir}/sampleProject.zip"
            String sampleProjDir = "${workDir}/${projectName}-${project.inspectus4katalon.sampleProjectVersion}"
            doFirst {
                // download the zip file of the sample project, unzip it
                URL url = new URL(src)
                File zipFile = new File(destFile)
                if (zipFile.exists()) {
                    zipFile.delete()
                }
                Files.createDirectories(Paths.get("$workDir"))
                println "Downloading $url into $destFile"
                url.withInputStream { i -> zipFile.withOutputStream { it << i } }
                //
                project.copy {  // unzip it to a directory
                    from project.zipTree(zipFile)
                    into new File("$workDir")
                }
            }
            doLast {
                // deploy the files
                project.copy {
                    from new File("$sampleProjDir")
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
