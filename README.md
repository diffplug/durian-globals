# <img align="left" src="logo.png"> DurianGlobals: Easy-to-test singletons

<!---freshmark shields
output = [
    link(shield('Maven central', 'mavencentral', 'com.diffplug.durian-globals:durian-globals', 'blue'), 'https://search.maven.org/search?q=g:com.diffplug.durian-globals'),
    link(shield('Apache 2.0', 'license', 'apache-2.0', 'blue'), 'https://tldrlegal.com/license/apache-license-2.0-(apache-2.0)'),
    '',
    link(shield('Changelog', 'changelog', versionLast, 'brightgreen'), 'CHANGELOG.md'),
    link(shield('Javadoc', 'javadoc', 'yes', 'brightgreen'), 'https://javadoc.jitpack.io/com/github/diffplug/durian-globals/durian-globals-agg/release~{{versionLast}}/javadoc/'),
    link(shield('Live chat', 'gitter', 'chat', 'brightgreen'), 'https://gitter.im/diffplug/durian'),
    link(image('JitCI', 'https://jitci.com/gh/diffplug/durian-globals/svg'), 'https://jitci.com/gh/diffplug/durian-globals')
    ].join('\n');
-->
[![Maven central](https://img.shields.io/badge/mavencentral-com.diffplug.durian--globals%3Adurian--globals-blue.svg)](https://search.maven.org/search?q=g:com.diffplug.durian-globals)
[![Apache 2.0](https://img.shields.io/badge/license-apache--2.0-blue.svg)](https://tldrlegal.com/license/apache-license-2.0-(apache-2.0))

[![Changelog](https://img.shields.io/badge/changelog-1.0.0-brightgreen.svg)](CHANGELOG.md)
[![Javadoc](https://img.shields.io/badge/javadoc-yes-brightgreen.svg)](https://javadoc.jitpack.io/com/github/diffplug/durian-globals/durian-globals-agg/release~1.0.0/javadoc/)
[![Live chat](https://img.shields.io/badge/gitter-chat-brightgreen.svg)](https://gitter.im/diffplug/durian)
[![JitCI](https://jitci.com/gh/diffplug/durian-globals/svg)](https://jitci.com/gh/diffplug/durian-globals)
<!---freshmark /shields -->

<!---freshmark javadoc
output = prefixDelimiterReplace(input, 'https://javadoc.jitpack.io/com/github/diffplug/durian-globals/durian-globals-agg/release~', '/', versionLast)
output = prefixDelimiterReplace(output, "'com.diffplug.durian-globals:durian-globals:", "'", versionLast)
output = prefixDelimiterReplace(output, "'com.diffplug.durian-globals:durian-globals.dev:", "'", versionLast)
-->

## Usage

Provides an easy way to initialize a singleton exactly once:

```java
public class Singleton {
  public static Singleton instance() {
    return Globals.getOrSetTo(Singleton.class, Singleton::new);
  }

  protected Singleton() {}
  ...
}
```

In a testing environment, you can wipe the globals to get a clean state or to replace the standard implementation with a testing one.

```java
public class SingletonTest {
  static class SingletonDev extends Singleton {
    ...
  }

  @Test
  public void someTest() {
    try (AutoCloseable wipeGlobals = GlobalsDev.wipe()) {
      SingletonDev dev = new SingletonDev();
      GlobalsDev.install(Singleton.class, dev);
      ...
    }
  }
}
```

The "trick" is that `GlobalsDev` is shipped in a different artifact than the rest, so you can be sure that your `Globals` can only be changed in tests, and never in production code:

```gradle
dependencies {
  implementation     'com.diffplug.durian-globals:durian-globals:1.0.0'
  testImplementation 'com.diffplug.durian-globals:durian-globals.dev:1.0.0'
}
```

## Built-ins

There are some globals that people frequently want control over during testing.

### Time

You can use [`public static long Time.now()`](https://javadoc.jitpack.io/com/github/diffplug/durian-globals/durian-globals-agg/release~1.0.0/javadoc/com/diffplug/common/globals/Time.html) as a replacement for `System.currentTimeMillis()`.  And in a test, you can replace it with [`TimeDev`](https://javadoc.jitpack.io/com/github/diffplug/durian-globals/durian-globals-agg/release~1.0.0/javadoc/com/diffplug/common/globals/TimeDev.html).

```java
@Test
public void someTimeDependentTest() {
  try (AutoCloseable wipeGlobals = GlobalsDev.wipe()) {
    TimeDev time = TimeDev.install();
    time.setUTC(LocalDate.parse("2019-03-30"));
    ... // exercise code that uses `Time.now()`
  }
}
```

<!---freshmark /javadoc -->

## Requirements

DurianGlobals requires nothing but Java 8+.

## In the wild

- Integration testing the [spotless-changelog](https://github.com/diffplug/spotless-changelog) gradle plugin
  - [build setup](https://github.com/diffplug/spotless-changelog/blob/c6c66eb12e5ce8a23d42fcb34ef3753382ee4cb4/build.gradle#L52-L54)
  - [integration code](https://github.com/diffplug/spotless-changelog/blob/11fb5f5cd13921f8ecf8151092fdbad44b6f3004/spotless-changelog-plugin-gradle/src/test/java/com/diffplug/spotless/changelog/gradle/ChangelogPluginTest.java#L33-L34)

## Acknowledgements

* Built by [gradle](http://gradle.org/).
* Tested by [junit](http://junit.org/).
* Maintained by [DiffPlug](http://www.diffplug.com/).
