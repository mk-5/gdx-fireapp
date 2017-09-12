## Android sdk installation

Complete guide is here: [https://firebase.google.com/docs/android/setup](https://firebase.google.com/docs/android/setup)

In short:

- Add `classpath 'com.google.gms:google-services:3.1.0'` in root `build.gradle` file, inside buildscript dependencies.

- Modify `android/build.gradle` as follow:

  ```groovy
  // dependencies 
  dependencies{
    // your other dependencies..
    compile 'com.google.firebase:firebase-core:11.0.2'
    compile 'com.google.firebase:firebase-storage:11.0.2'
    compile 'com.google.firebase:firebase-auth:11.0.2'
    compile 'com.google.firebase:firebase-crash:11.0.2'
    compile 'com.google.firebase:firebase-database:11.0.2'
  }

  // put following line at bottom of the file
  apply plugin: 'com.google.gms.google-services'
  ```

- Add `google-services.json` at `{ANDROID_MODULE_PATH}/google-services.json`

- That's it ! ;)