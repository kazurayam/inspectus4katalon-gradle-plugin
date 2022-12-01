# A Gradle plugin: inspectus4katalon

## What is this?

The `inspectus4katalon` is a Gradle plugin that enables a Katalon Studio project to install several external libraries (jar files) into the `Drivers` directory of a Katalon project. The libraries include the [`inspectus`](https://github.com/kazurayam/inspectus), [`materialstore`](https://github.com/kazurayam/materialstore) and others.

You would want to write the `<Katalon project directory>/build.gradle` like this:

```
plugins {
    id "inspectus4katalon"
}
```

The plugin create the `drivers` task in the Gradle project dynamically. In the command line, you want to do the following:

```
$ cd <Katalon project directory>
$ gradle drivers
``` 

then the task will download a set of external jar files required for the `inspecuts` library, and locate them in the `Drivers` directory of the Katalon project, like this:

```
$ cd <Katalon project directory>
$ tree Drivers/
Drivers/
├── AUTOIMPORTED_ExecutionProfilesLoader-1.2.1.jar
├── AUTOIMPORTED_ashot-1.5.4.jar
├── AUTOIMPORTED_freemarker-2.3.31.jar
├── AUTOIMPORTED_java-diff-utils-4.11.jar
├── AUTOIMPORTED_jsoup-1.14.3.jar
└── AUTOIMPORTED_materialstore-0.12.1.jar

0 directories, 6 files
```

Provided with those jar files located in the `Drivers` directory, you can start developing codes for `Visual Inspection`.



[Publishing Plugins to the Gradle Plugin Portal](https://docs.gradle.org/current/userguide/publishing_gradle_plugins.html)

[Gradle Plugin を公開する (Gradle Plugin Portal)](https://qiita.com/irgaly/items/be46b93df477df668ab7)
