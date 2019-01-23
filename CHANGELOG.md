**[1.0.0]**  
\- Initial release  
\- Android: basic support for Database, Analytics, Storage, App, Authorization, Crash  
\- Ios-moe: basic support for Database, Analytics, Storage, App, Authorization, Crash  

**[1.1.0]**   
\- GWT: Authorization, Database, Storage  
\- GdxFirebaseUser: Add the email field  

**[1.1.1]**  
\- API Addition: Sign-out method in authorization API for android, ios and gwt  
\- libGDX updated to version 1.9.8  
\- Multi-OS engine updated to version 1.4.1  

**[1.2.0]**  
\- API Addition: The GdxFIRLogger is now available for debugging  
\- API Change: ***Braking change*** @MapConversion annotation is needed now - all database pojo conversions   should be provided by this annotation  
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

**[1.5.1]**  
\- API Addition: Google authorization for android  
\- API Addition: Google authorization for ios  
\- API Addition: Google authorization for gwt  
\- API Fix: Fix the sign-out callback for gwt  
\- API Change: The multi-os engine bindings are delivery with library so no longer needs to generate bindings yourself  

**[1.6.1]**  
\- API Change: GdxFIRCrash use Crashlytics now, if you have project with older version  - go to the wiki page and update your project  
\- General: Minor bugs fix and improvements  

**[1.6.2]**  
\- fix: Fix for detaching of listeners when call ***GdxFIRDatabase#onDataChange(class, null)***    

**[1.6.3]**  
\- fix: Fix of wrong query execution for ***GdxFIRDatabase#readValue*** at GWT API   
\- fix: Fix of getting the wrong argument in query for ***GdxFIRDatabase#removeValue(callback)*** at GWT API   

**[1.7.1]**  
\- API Addition: Database Filtering&Sorting support for GWT platform  
\- General: Minor bugs fix and improvements for GWT platform  

**[1.8.1]**  
\- API Addition: User-manage methods (update email, update password, send verification email)   
\- API Addition: **GdxFIRAuth#sendPasswordResetEmail**   
\- GWT API: Fix of broken signing-in after user creation  

**[1.9.1]**  
\- API Addition: #reload() method in User API

**[1.9.2]**
\- fix: #13 [iOS Database] Can't read string value

**[1.9.3]**
\- fix Android API: Wrong string/number param recognizing while sending analytics event

**[1.9.4]**
\- fix Analytics API: Run setScreen on the main thread

