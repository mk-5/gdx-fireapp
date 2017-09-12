## iOS Firebase SDK installation

iOS platform is more challenging than Android, please read all **4 steps** of following guide. 

Multi-OS engine 1.3.6

#### 1. Add firebase configuration file in to your project

- Open xcode project and drag `GoogleService-Info.plist` file - you can download it from Firebase console - to project root folder. Path should be as follows: `{IOS_MODULE_PATH}/xcode/ios-moe/GoogleService-Info.plist`

#### 2. Install framework (Pods)

- Init pods

```shell
$ cd {IOS_MODULE_PATH}/xcode
$ pod init
```

- Edit `{IOS_MODULE_PATH}/xcode/Podfile`:

```
target 'ios-moe' do
  # Uncomment the next line if you're using Swift or would like to use dynamic frameworks
  use_frameworks!           # !! Modification here !!

  # Pods for ios-moe
  pod 'Firebase/Core'      # !! and here !!
  pod 'Firebase/Storage'
  pod 'Firebase/Database'
  pod 'Firebase/Crash'
  pod 'Firebase/Auth'
  pod 'Firebase/Messaging'
  
end
# !! and here !!
post_install do |installer|
  installer.pods_project.targets.each do |target|
    target.build_configurations.each do |config|
      config.build_settings['ENABLE_BITCODE'] = 'NO'
    end
  end
end
```

- Install the pods:

```shell
$ pod install
```

- Add `Pods.xconfig` to `custom.xconfig` in gradle file: ( `{IOS_MODULE_PATH}/build.gradle` -  *copyNatives* task.) 

  Replace this:

  ```
  def outFlags = file("xcode/ios-moe/custom.xcconfig");
  outFlags.write LD_FLAGS
  ```

  With this:

  ```
  def outFlags = file("xcode/ios-moe/custom.xcconfig");
  def FIREBASE_PODS = "#include \"Pods/Target Support Files/Pods-ios-moe/Pods-ios-moe.release.xcconfig\"\n"
  outFlags.write FIREBASE_PODS + LD_FLAGS
  ```


#### 3. Change project launch configuration

- Add workspace and mainScheme to moe/xcode settings:  `{IOS_MODULE_PATH}/build.gradle`

```
// Setup Multi-OS Engine
moe {
    xcode {
        project 'xcode/ios-moe.xcodeproj'
        workspace 'xcode/ios-moe.xcworkspace'   // Workspace is needed when Pods was included in project
        mainScheme 'ios-moe'
        mainTarget 'ios-moe'
        testTarget 'ios-moe-Test'
    }
}
```

#### 4. Generate java bindings

- Put [firebase.nbc](../gdx-fireapp-ios-moe/firebase.nbc) file into your ios-moe module files, **double click on it** -> **Click at gear icon** -> **Generate bindings**


#### 5. All done!
