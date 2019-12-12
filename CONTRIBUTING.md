# Contributing to Durian

Pull requests are welcome, preferably against `master`.

## JitPack

You can get a build from any commit in this repository (including unmerged PRs) like this:

```gradle
repositories {
  maven {
    url 'https://jitpack.io'
    content {
      includeModule 'com.diffplug.durian-globals', 'durian-globals'
      includeModule 'com.diffplug.durian-globals', 'durian-globals.dev'
    }
  }
}
String SHA1_DURIAN_GLOBALS='0674e66fd6d2e818655274626b7f491535b3f77a'
dependencies {
  implementation     "com.diffplug.durian-globals:durian-globals:${SHA1_DURIAN_GLOBALS}"
  testImplementation "com.diffplug.durian-globals:durian-globals.dev:${SHA1_DURIAN_GLOBALS}"
}
```

## License

By contributing your code, you agree to license your contribution under the terms of the [Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0).  All code files carry this header:

```
Copyright 2019 DiffPlug

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
