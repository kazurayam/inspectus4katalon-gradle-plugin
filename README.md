# inspectus4katalon-gradle-plugin

## What is this?

This is a Gradle plugin which is supposed to be in a Katalon Studio project. 

You can find a sample Katalon Studio project that utilizes this custom plugin:

- [inspectus4katalon-sample-project](https://github.com/kazurayam/inspectus4katalon-sample-project)

How to use this?

You want to write in the `build.gradle` file in your Katalon Studio project:

```
plugins {
    id 'com.kazurayam.inspectus4katalon'
}
```

That's all.

This custom Gradle plugin adds the following tasks in to the Gradle project.

1. `drivers` task
2. `showImmediateDependencies` task
3. `showAllDependenciesInInspectus` task

### `drivers` task

You want to execute:

```
$ cd <directory of your Katalon Studio project>
$ gradle drivers
```

The `drivers` task will download some jar files in the `Drivers` folder. It will look something like this:

```
$ cd <directory of your Katalon Studio project>
$ tree ./Drivers
$ tree Drivers
Drivers
├── AUTOIMPORTED_ExecutionProfilesLoader-1.2.1.jar
├── AUTOIMPORTED_ashot-1.5.4.jar
├── AUTOIMPORTED_freemarker-2.3.31.jar
├── AUTOIMPORTED_inspectus-0.6.0.jar
├── AUTOIMPORTED_java-diff-utils-4.11.jar
├── AUTOIMPORTED_jsoup-1.14.3.jar
└── AUTOIMPORTED_materialstore-0.13.0.jar

0 directories, 7 files
```

These jars are required to run Katalon Studio test cases that uses the `inspectus` library. These jars are downloaded from the Maven Central repository. These jars are NOT bundled in the Katalon Studio's binary distribution, therefore this custom Gradle plugin supplements the missing jars into the `Drivers` folder while downloading them from the public repository. You do not have to mind which jars & which version to download --- this plugin will take care.

### `showImmediateDependencies` task

You want to execute:
```
$ gradle showImmediateDependencies
com.kazurayam:inspectus:0.6.0
com.kazurayam:ExecutionProfileLoader:1.2.1
```

Your Katalon Studio test cases will be dependent on
- [inspectus](https://github.com/kazurayam/inspectus)
- [ExecutionProfilesLoader](https://github.com/kazurayam/ExecutionProfilesLoader)

This task shows the version of these 2 dependencies that will be used.

You can specify (change) the version of these 2 dependencies by writing the following in your `build.gradle` file:

```
drivers {
    inspectusVersion = '0.6.0'   // possible to specify other version
    ExecutionProfilesLoaderVersion = '1.2.1'
}
```



### `showAllDependenciesInInspectusConfiguration` task

You want to execute as follows:

```
$ gradle showAllDependenciesInInspectusConfiguration
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
```

Here you can see all dependencies, including transient dependencies, that are required to run a Katalon Studio test case that uses the `inspectus` library.

This task is meant for the sake of testing and debugging. But this long list of dependencies might be interesting for you.

## Developer's note

I studied the Gradle project's official document to understand how to make a custom Gradle plugin.

- https://docs.gradle.org/current/userguide/custom_plugins.html

How to perform functional test? See the following article:

- https://qiita.com/kazurayam/items/0d820ef357104cfe3248