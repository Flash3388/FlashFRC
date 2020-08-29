# FlashFRC

![Maven Central](https://img.shields.io/maven-central/v/com.flash3388.flashlib/flashlib-frc)
![Travis (.org) branch](https://img.shields.io/travis/Flash3388/FlashFRC/master)
![GitHub](https://img.shields.io/github/license/Flash3388/FlashFRC)

FRC Plugin for FlashLib, providing extensions and implementations of components
for use with FRC robots. 

The plugin is made up of 2 parts:
- `flashlib.frc.robot` is the main component, provide everything needed to use __FlashLib__ with 
and FRC robot.
- `flashlib.frc.nt` is a bunch of utilities for use with __NetworkTables__. It is entirely optional, and is not tied 
to the robot.

## Building

FlashLib uses the gradle build system. With the help of gradle-wrapper, all the is needed to build is to
run `./gradlew build` (on `Linux systems`) or `gradlew.bat build` (on `Windows systems`)

You can find the binary files in the `build` folder.

## Using

__FlashFRC__ releases are uploaded to _maven central_, so including it as a dependency is quite simple.

### Gradle

Add to your build script, under `dependencies`:

```Groovy
dependencies {
    implementation group: "com.flash3388.flashlib", name: "flashlib.frc.robot", version: "$version"
}
```
