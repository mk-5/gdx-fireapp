**[1.0.0]**
\- Initial release
\- Android: basic support for Database, Analytics, Storage, App, Authorization, Crash
\- Ios-moe: basic support for Database, Analytics, Storage, App, Authorization, Crash

**[1.1.0]**
\- GWT: Authorization, Database, Storage
\- GdxFirebaseUser: Add email field

**[1.1.1]**
\- Authorization API: Sign-out method for android, ios and gwt
\- LibGDX: Updated to version 1.9.8
\- Multi-OS engine: Updated to version 1.4.1

**[1.2.0]**
\- API Addition: The GdxFIRLogger is now available for debugging
\- API Change: ***Braking change*** @MapConversion annotation is needed now - all database pojo conversions should be provided by this annotation
\- API Addition: ***GdxFIRDatabase#setMapConverter*** allows to setup own MapConversion flow
\- API Fix: The new ios frameworks paths for latest Pods in firebase.nbc

**[1.3.0]**
\- API Addition: Database ordering&filtering API for Android and iOS

**[1.3.1]**
\- fix: TS in distributions singletons

**[1.4.1]**
\- API Change: The downloadUrl field in FileMetadata is loaded in async way
\- fix: #6 ios firebase sdk 5.x
\- API Change: android firebase sdk 16.x