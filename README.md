# <img align="left" src="logo.png"> DurianGlobals: Easy-to-test singletons

[![Maven artifact](https://img.shields.io/badge/mavenCentral-com.diffplug.durian%3Adurian--swt-blue.svg)](https://bintray.com/diffplug/opensource/durian-swt/view)
[![Latest version](https://img.shields.io/badge/latest-3.3.0-blue.svg)](https://github.com/diffplug/durian-swt/releases/latest)
[![Javadoc](https://img.shields.io/badge/javadoc-OK-blue.svg)](https://diffplug.github.io/durian-swt/javadoc/3.3.0/)
[![License Apache](https://img.shields.io/badge/license-Apache-blue.svg)](https://tldrlegal.com/license/apache-license-2.0-(apache-2.0))

[![Changelog](https://img.shields.io/badge/changelog-3.4.0--SNAPSHOT-brightgreen.svg)](CHANGES.md)
[![Travis CI](https://travis-ci.org/diffplug/durian-swt.svg?branch=master)](https://travis-ci.org/diffplug/durian-swt)
[![Live chat](https://img.shields.io/badge/gitter-live_chat-brightgreen.svg)](https://gitter.im/diffplug/durian)

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

## Built-ins

There are some globals that people frequently want control over during testing.

### Time

You can use `public static long Time.now()` (TODO: link) as a replacement for `System.currentTimeMillis()`.  And in a test, you can replace it with `TimeDev` (TODO: link).

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

## Requirements

DurianGlobals requires nothing but Java 8+.

## Acknowledgements

* Built by [gradle](http://gradle.org/).
* Tested by [junit](http://junit.org/).
* Maintained by [DiffPlug](http://www.diffplug.com/).
