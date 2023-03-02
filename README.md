# Java ImageIO APNG Plug-in
A patched version of JDK's ImageIO PNG Plug-in, can read and write normal PNG and [APNG](https://en.wikipedia.org/wiki/APNG).  
This library also includes [the performance enhancement patch of JDK9's PNG Plug-in](https://bugs.openjdk.java.net/browse/JDK-6488522), 
and can be used on Java 8.

## Add the library to your project (gradle)
1. Add the Maven Central repository (if not exist) to your build file:
```groovy
repositories {
    ...
    mavenCentral()
}
```

2. Add the dependency:
```groovy
dependencies {
    ...
    implementation 'com.tianscar.imageio:imageio-apng:1.0.1'
}
```

## Usage
[Examples](/src/test/java/com/tianscar/imageio/plugins/png/test/)

## License
[GPLv2+CE](/LICENSE) (c) Tianscar  
[images for test](/src/test/resources) originally created by [Daniel Calan](https://github.com/DanielCalan), authorized [me](https://github.com/Tianscar) to use. 2023 (c) Daniel Calan, all rights reserved.