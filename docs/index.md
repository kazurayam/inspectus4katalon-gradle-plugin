# inspectus4katalon, a Gradle plugin

-   back to the [repository](https://github.com/kazurayam/inspectus4katalon-gradle-plugin)

published at [Gradle plugin portal](https://plugins.gradle.org/plugin/com.kazurayam.inspectus4katalon)

The `inspectus4katalon` is a Gradle plugin that enables a [Katalon Studio](https://katalon.com/katalon-studio) project to install several external libraries (jar files) into the `Drivers` directory of a Katalon project. The libraries include the [`inspectus`](https://github.com/kazurayam/inspectus), [`materialstore`](https://github.com/kazurayam/materialstore) and others.

## build.gradle

You would want to write the `<Katalon project directory>/build.gradle` like this:

    plugins {
        id "inspectus4katalon"
    }

## drivers task

The plugin will add the `drivers` task in the Gradle project dynamically. In the command line, you want to do the following:

    $ cd <Katalon project directory>
    $ gradle drivers

then the task will download a set of external jar files required for the `inspecuts` library, and locate them in the `Drivers` directory of the Katalon project, like this:

    $ cd <Katalon project directory>
    $ tree Drivers/
    Drivers
    ├── AUTOIMPORTED_ExecutionProfilesLoader-1.2.1.jar
    ├── AUTOIMPORTED_ashot-1.5.4.jar
    ├── AUTOIMPORTED_freemarker-2.3.31.jar
    ├── AUTOIMPORTED_inspectus-0.8.1.jar
    ├── AUTOIMPORTED_java-diff-utils-4.11.jar
    ├── AUTOIMPORTED_jsoup-1.14.3.jar
    └── AUTOIMPORTED_materialstore-0.14.2.jar

Please note that the exact version numbers may change in future as the software gets more developed.

Provided with those jar files located in the `Drivers` directory, you can start developing codes for `Visual Inspection` in Katalon Studio.

## deploy-visual-inspection-sample-for-katalon task

The plugin provides one more Gradle task which deploys a set of sample code that performs Visual Inspection practice.

    $ cd <Katalon project directory>
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

    BUILD SUCCESSFUL in 33s

You want to once close the Katalon project and reopen it so that Katalon Studio recognizes the newly deployed resources.

So, how to run the sample code?

Please refer to the following docs where you will find explanation in detail.

-   <https://kazurayam.github.io/inspectus4katalon-sample-project/>
