# GDX Fireapp

A libGDX cross-platform API for Firebase.

[ ![Build Status](https://travis-ci.com/mk-5/gdx-fireapp.svg?branch=master)](https://travis-ci.com/mk-5/gdx-fireapp) ![Maven Central](https://img.shields.io/maven-central/v/pl.mk5.gdx-fireapp/gdx-fireapp-core) [_![Coverage](https://sonarcloud.io/api/project_badges/measure?project=mk-5_gdx-fireapp&metric=coverage)_](https://sonarcloud.io/dashboard?id=mk-5_gdx-fireapp)

## Getting Started

The first thing you should do is install Firebase SDK and add gradle dependencies.   

[Android  guide](https://github.com/mk-5/gdx-fireapp/wiki/Android-guide) - [iOS guide](https://github.com/mk-5/gdx-fireapp/wiki/iOS-Guide) - [HTML guide](https://github.com/mk-5/gdx-fireapp/wiki/GWT-guide) - [ProGuard required rules](https://github.com/mk-5/gdx-fireapp/wiki/Proguard-required-rules)

**Core**

```
implementation "pl.mk5.gdx-fireapp:gdx-fireapp-core:$gdxFireappVersion"
```
**Android**

```
implementation "pl.mk5.gdx-fireapp:gdx-fireapp-android:$gdxFireappVersion"
```
**iOS**

```
implementation "pl.mk5.gdx-fireapp:gdx-fireapp-ios:$gdxFireappVersion"
```

**GWT**

```
implementation "pl.mk5.gdx-fireapp:gdx-fireapp-html:$gdxFireappVersion"
```

&nbsp;

**The latest version** was built using libGDX 1.9.12, robovm 2.3.1, gwt 2.8.2, iOS Firebase sdk 7.x,
android Firebase BOM 25.12.0

&nbsp;

## Basics

The API is like a bridge between libGDX app and Firebase sdk. It covers Firebase functionality, so if you have some knowledge of the Firebase SDK, using this API should be intuitive.

&nbsp;

To initialize Firebase SDK, just put this line somewhere in your app initialization code:

```java
GdxFIRApp.inst().configure();
```

**Firebase Analytics** should start working just after this step.

&nbsp;


## Examples

To see some examples, please go to [examples wiki page](https://github.com/mk-5/gdx-fireapp/wiki/Examples).

&nbsp;

## Useful links

- [Javadoc](http://javadoc.io/doc/pl.mk5.gdx-fireapp/gdx-fireapp-core)
- [Wiki](https://github.com/mk-5/gdx-fireapp/wiki)

&nbsp;

## What's next?

- Better wiki/documentation
- Cloud Firestore
- Facebook, github authorization
- Messaging
- Upload task monitoring

&nbsp;  

## Features

- [x] Analytics
- [x] Authentication
- [x] Database
- [x] Storage
- [x] Crash reporting (Crashlytics)
- [ ] Messaging

&nbsp;  

## Platforms

- [x] Android
- [x] iOS (RoboVM)
- [x] GWT

&nbsp;  


## License

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
