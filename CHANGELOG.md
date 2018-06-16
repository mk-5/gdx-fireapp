**[1.0.0]**
\- Initial release
\- Android: basic support for Database, Analytics, Storage, App, Authorization, Crash
\- Ios-moe: basic support for Database, Analytics, Storage, App, Authorization, Crash

**[1.1.0]**
\- GWT support for Authorization, Database, Storage
\- Email field in GdxFirebaseUser

**[1.1.1]**
\- Sign-out method in authorization api (android, ios, gwt)
\- LibGDX updated to version 1.9.8
\- Multi-OS engine updated to version 1.4.1

**[1.2.0]**
\- GdxFIRLogger for debugging
\- ***Braking change***: @MapConversion annotation - all database maps-pojo conversion should be provided by this annotation
\- Unification of map serialization/deserialization for all platforms
\- GdxFIRDatabase is able now to do customization of database MapConversion by FirebaseMapConverter Interface
\- Correct frameworks paths for latest Pods in firebase.nbc

**[1.3.0]**
\- Database ordering&filtering API for Android and iOS

**[1.3.1]**
\- fix: TS in distributions singletons

**[1.4.1]**
\- Storage upload snapshot - async downloadUrl get
\- fix: #6 ios firebase sdk 5.x
\- android firebase sdk 16.x