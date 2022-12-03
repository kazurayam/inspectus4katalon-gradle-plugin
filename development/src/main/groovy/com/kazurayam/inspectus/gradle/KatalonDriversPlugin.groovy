package com.kazurayam.inspectus.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ResolvedArtifact
import org.gradle.api.artifacts.ResolvedModuleVersion
import org.gradle.api.artifacts.ModuleVersionIdentifier

class KatalonDriversPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.extensions.create("drivers", KatalonDriversPluginConfiguration)

        project.task("showImmediateDependencies").doFirst {
            println "com.kazurayam:inspectus:${project.drivers.inspectusVersion}"
            println "com.kazurayam:ExecutionProfileLoader:${project.drivers.ExecutionProfilesLoaderVersion}"
        }

        project.task("showInspectusDependencies").doFirst {
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

        /**
         * Declare the configuration "Inspectus".
         * The Inspectus configuration will include the inspectus library and
         * the other transient dependencies.
         */
        def conf = project.configurations.create("Inspectus")
        project.dependencies({
            add(conf.getName(), [group: 'com.kazurayam', name: 'inspectus',
                                 version: "${project.drivers.inspectusVersion}"])
            add(conf.getName(), [group: 'com.kazurayam', name: 'ExecutionProfilesLoader',
                                 version: "${project.drivers.ExecutionProfilesLoaderVersion}"])
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

                /*
                project.dependencies({
                    add(conf.getName(), [group: 'com.kazurayam', name: 'materialstore', version: "0.12.5"])
                    add(conf.getName(), [group: 'ru.yandex.qatools.ashot', name: 'ashot', version: '1.5.4'])
                    add(conf.getName(), [group: 'io.github.java-diff-utils', name: 'java-diff-utils', version: '4.11'])
                    add(conf.getName(), [group: 'org.jsoup', name: 'jsoup', version: '1.14.3'])
                    add(conf.getName(), [group: 'org.freemarker', name: 'freemarker', version: "2.3.31"])
                })

                 */
            }
            doLast {
                /**
                 * copy the jars are required to use the inspectus library AND
                 * are NOT bundled in Katalon Studio's ver 8.x binary distribution.
                 */
                project.copy { copySpec ->
                    copySpec
                        .from(conf)
                        .into("Drivers")
                            .include(
                            "**/inspectus*.jar",
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
                //println("Ya! I am the drivers task!")
            }
        }
    }
}
