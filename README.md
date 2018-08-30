# GDX Fireapp

A libGDX cross-platform API for Firebase.

[ ![Build Status](https://travis-ci.org/mk-5/gdx-fireapp.svg?branch=master)](https://travis-ci.org/mk-5/gdx-fireapp) ![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=pl.mk5.gdx-fireapp%3Agdx-fireapp&metric=alert_status) [ ![Download](https://api.bintray.com/packages/mk-5/maven/gdx-fireapp/images/download.svg) ](https://bintray.com/mk-5/maven/gdx-fireapp/_latestVersion)




## Getting Started

The first thing you should do is installing Firebase SDK and add gradle dependencies.   

- [Android  guide](https://github.com/mk-5/gdx-fireapp/wiki/Android-guide)
- [iOS guide](https://github.com/mk-5/gdx-fireapp/wiki/iOS-Guide)
- [GWT guide](https://github.com/mk-5/gdx-fireapp/wiki/GWT-guide)

**Core**

```
compile "pl.mk5.gdx-fireapp:gdx-fireapp-core:1.+"
```
**Android**

```
compile "pl.mk5.gdx-fireapp:gdx-fireapp-android:1.+"
```
**iOS**

```
compile "pl.mk5.gdx-fireapp:gdx-fireapp-ios-moe:1.+"
```

**GWT**

```
compile "pl.mk5.gdx-fireapp:gdx-fireapp-html:1.+"
```

If you are done with that do the last step: [Update proguard files](https://github.com/mk-5/gdx-fireapp/wiki/Proguard-required-rules)  

  

**The latest version** was built using libGDX 1.9.8, multi-os-engine 1.4.3, gwt 2.8.0, iOS firebase sdk 5.x, android firebase sdk 16.x



## Basics

API is something like bridge between libGDX app and firebase sdk. It's cover firebase functionality so if you have some knowledge about firebase SDK - using this API should be intuitive for you.



To initialize Firebase SDK just put this line somewhere in your app initialization code:

```java
GdxFIRApp.instance().configure();
```

**Firebase Analytics** should start working just after this step.  

If you added ***Fabric/Crashlytics*** to your project you should initialize it also:

````
GdxFIRCrash.instance().initialize();
````




Firebase SDK communication is done with following classes:

- **[GdxFIRApp](http://javadoc.io/page/pl.mk5.gdx-fireapp/gdx-fireapp-core/latest/mk/gdx/firebase/GdxFIRApp.html)**
- **[GdxFIRAnalytics](http://javadoc.io/page/pl.mk5.gdx-fireapp/gdx-fireapp-core/latest/mk/gdx/firebase/GdxFIRAnalytics.html)**
- **[GdxFIRAuth](http://javadoc.io/page/pl.mk5.gdx-fireapp/gdx-fireapp-core/latest/mk/gdx/firebase/GdxFIRAuth.html)**
- **[GdxFIRStorage](http://javadoc.io/page/pl.mk5.gdx-fireapp/gdx-fireapp-core/latest/mk/gdx/firebase/GdxFIRStorage.html)**
- **[GdxFIRDatabase](http://javadoc.io/page/pl.mk5.gdx-fireapp/gdx-fireapp-core/latest/mk/gdx/firebase/GdxFIRDatabase.html)**
- **[GdxFIRCrash](http://javadoc.io/page/pl.mk5.gdx-fireapp/gdx-fireapp-core/latest/mk/gdx/firebase/GdxFIRCrash.html)**




## Examples

For some examples please look at [examples wiki page](https://github.com/mk-5/gdx-fireapp/wiki/Examples).



## Useful links

- [Javadoc](http://javadoc.io/doc/pl.mk5.gdx-fireapp/gdx-fireapp-core)
- [Filtering API wiki](https://github.com/mk-5/gdx-fireapp/wiki/Filtering-API)




## What's next?



- GWT database filtering api
- Better wiki/documentation
- Facebook, github authorization
- Messaging
- Upload task monitoring




## Features

- [x] Analytics
- [x] Authentication
- [x] Database
- [x] Storage
- [x] Crash raporting (Crashlytics)
- [ ] Messaging




## Platforms

- [x] Android
- [x] iOS (Multi-os Engine)
- [x] GWT





## License

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.