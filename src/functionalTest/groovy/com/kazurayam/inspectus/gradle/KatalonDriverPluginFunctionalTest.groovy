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

    def "test deploy-visual-inspection-sample-for-katalon task"() {
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
                .withArguments('deploy-visual-inspection-sample-for-katalon')
                .withPluginClasspath()
                .build()
        then:
        /*
$ gradle deploy-visual-inspection-sample-for-katalon

> Task :deploy-visual-inspection-sample-for-katalon
Downloading https://github.com/kazurayam/inspectus4katalon-sample-project/archive/refs/tags/0.3.2.zip into /Users/kazuakiurayama/github/inspectus4katalon-gradle-plugin/rehearsal/build/tmp/sampleProject.zip
... Test Cases/CURA/run_materialize.tc
... Test Cases/CURA/main.tc
... Test Cases/CURA/materialize.tc
... Test Cases/DuckDuckGo/run_materialize.tc
... Test Cases/DuckDuckGo/main.tc
... Test Cases/DuckDuckGo/materialize.tc
... Test Cases/common/Scavenge.tc
... Test Cases/common/BackupAll.tc
... Test Cases/MyAdmin/processTargetList.tc
... Test Cases/MyAdmin/run_materialize.tc
... Test Cases/MyAdmin/main.tc
... Test Cases/MyAdmin/materialize.tc
... Include/data/MyAdmin/targetList.csv
... Object Repository/CURA/Page_CURA Healthcare Service/top/a_Make Appointment.rs
... Object Repository/CURA/Page_CURA Healthcare Service/appointment/select_Tokyo CURA Healthcare Center.rs
... Object Repository/CURA/Page_CURA Healthcare Service/appointment/input_visit_date.rs
... Object Repository/CURA/Page_CURA Healthcare Service/appointment/button_Book Appointment.rs
... Object Repository/CURA/Page_CURA Healthcare Service/appointment/input_Medicaid_programs.rs
... Object Repository/CURA/Page_CURA Healthcare Service/appointment/input_Apply for hospital readmission.rs
... Object Repository/CURA/Page_CURA Healthcare Service/appointment/textarea_Comment_comment.rs
... Object Repository/CURA/Page_CURA Healthcare Service/login/input_Username_username.rs
... Object Repository/CURA/Page_CURA Healthcare Service/login/button_Login.rs
... Object Repository/CURA/Page_CURA Healthcare Service/login/input_Password_password.rs
... Object Repository/CURA/Page_CURA Healthcare Service/summary/a_Go to Homepage.rs
... Scripts/CURA/materialize/Script1667709728945.groovy
... Scripts/CURA/main/Script1667709715867.groovy
... Scripts/CURA/run_materialize/Script1667709743309.groovy
... Scripts/DuckDuckGo/materialize/Script1667437527092.groovy
... Scripts/DuckDuckGo/main/Script1667437517277.groovy
... Scripts/DuckDuckGo/run_materialize/Script1667616595404.groovy
... Scripts/common/BackupAll/Script1668394619253.groovy
... Scripts/common/Scavenge/Script1668394684813.groovy
... Scripts/MyAdmin/processTargetList/Script1668563538525.groovy
... Scripts/MyAdmin/materialize/Script1667687365090.groovy
... Scripts/MyAdmin/main/Script1667687348266.groovy
... Scripts/MyAdmin/run_materialize/Script1667687380074.groovy
... Profiles/MyAdmin_DevelopmentEnv.glbl
... Profiles/CURA_DevelopmentEnv.glbl
... Profiles/MyAdmin_ProductionEnv.glbl
deployed the sample project v0.3.2

BUILD SUCCESSFUL in 31s
5 actionable tasks: 5 executed
         */
        result.output.contains("Test Cases/CURA/main")
        result.output.contains("Test Cases/DuckDuckGo/main")
        result.output.contains("Test Cases/MyAdmin/main")
        result.output.contains("Object Repository/CURA")
        result.output.contains("Profiles/CURA")
        result.output.contains("Scripts/common/Scavenge")
        result.task(":deploy-visual-inspection-sample-for-katalon").outcome == SUCCESS
    }
}
