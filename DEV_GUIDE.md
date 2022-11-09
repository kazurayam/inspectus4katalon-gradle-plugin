# Developer Guide

## How to build the plugins jar

```
$ cd inspectus-gradle-plugin/development
$ gradle build
```
then you will find
```
$ cd inspectus-gradle-plugin/development
$ ls build/libs
inspectus-gradle-plugin-0.1.0.jar
```

## How to publish the jar into the mavenLocal repository
Do this:
```
$ cd inspectus-gradle-plugin/development
$ gradle publishToMavenLocal
```

Then you will find:
```
$ ls ~/.m2/repository/com/kazurayam/ | grep inspectus
inspectus
inspectus-gradle-plugin
inspectus4katalon-gradle-plugin
```



