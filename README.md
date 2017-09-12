# GDX Fireapp
![](https://libgdx.badlogicgames.com/img/logo.png)  +  ![](https://firebase.google.com/_static/2ceb35fd6c/images/firebase/lockup.png)

A libGDX cross-platform API for Firebase.




## How do I use it?

First thing is installing Firebase SDK. Here you can find some short tutorials describes installation inside LibGDX project:

- [Android Firebase SDK installation guide](wikis/ANDROID_FIREBASE_INSTALLATION.md)
- [iOS Firebase SDK installation guide](wikis/IOS_FIREBASE_INSTALLATION.md)

You did it? Now you need to add GDX Fireapp gradle dependencies, as follow:

**Core**

```
compile "mk.gdx-fireapp:gdx-fireapp-core-1.0.0"
```
**Android**

```
compile "mk.gdx-fireapp:gdx-fireapp-android-1.0.0"
```
**iOS**

```
compile "mk.gdx-fireapp:gdx-fireapp-ios-moe-1.0.0"
```

Last step is:

- [Update proguard files](wiki/PROGUARD.md)

**Version 1.0.0** was built using LibGDX v1.9.6, multi-os-engine 1.3.6.  

Docs are here: [Javadoc](http://fireappdocs.mk5.pl/)



## Basics

API is something like bridge beetwen LibGDX app and firebase sdk. It's cover firebase functionality so if you have some knowledge about firebase SDK - using this api should be intuitive for you.



To initialize Firebase SDK  just put this line  somewhere in your app initialization code:

```java
GdxFIRApp.instance().configure();
```

**Firebase Analytics** and **Firebase Crash** are features which start working just after this step.



## Few examples

To deal with Firebase SDK you have to use following classes:

- **[GdxFIRApp](http://fireappdocs.mk5.pl/mk/gdx/firebase/GdxFIRApp.html)**
- **[GdxFIRAnalytics](http://fireappdocs.mk5.pl/mk/gdx/firebase/GdxFIRAnalytics.html)**
- **[GdxFIRAuth](http://fireappdocs.mk5.pl/mk/gdx/firebase/GdxFIRAuth.html)**
- **[GdxFIRStorage](http://fireappdocs.mk5.pl/mk/gdx/firebase/GdxFIRStorage.html)**
- **[GdxFIRDatabase](http://fireappdocs.mk5.pl/mk/gdx/firebase/GdxFIRDatabase.html)**
- **[GdxFIRCrash](http://fireappdocs.mk5.pl/mk/gdx/firebase/GdxFIRCrash.html)**



### Analytics

Log some button tap event

```java
Map<String, String> params = new HashMap<String, String>();
params.put(AnalyticsParam.CONTENT_TYPE, "button");
params.put(AnalyticsParam.ITEM_ID, "my super button");
GdxFIRAnalytics.instance().logEvent(AnalyticsEvent.SELECT_CONTENT, params);
```

__

### Auth

Sign-in anonymously 

```java
GdxFIRAuth.instance().signInAnonymously(new AuthCallback() {
  @Override
  public void onSuccess(GdxFirebaseUser user)
  {
    // Deal with with current user.
    String userDisplayName = user.getUserInfo().getDisplayName();
  }
 
  @Override
  public void onFail(Exception e)
  {
    // handle failure
  }
});
```

__

### Storage

If your firebase storage need authorization remember to do auth first . (you can read something more [here](https://firebase.google.com/docs/storage/security/))

```java
GdxFIRAuth.instance().signInAnonymously(new AuthCallback() {
  
  @Override
  public void onSuccess(GdxFirebaseUser gdxFirebaseUser)
  {
    // storage actions should go here...
  }
  @Override
  public void onFail(Exception e)
  {
    e.printStackTrace();
  }
  
});
```

Download texture example:

```java
GdxFIRStorage.instance().downloadImage("/img.png", new DownloadCallback<TextureRegion>() {
    @Override
    public void onSuccess(TextureRegion region)
    {
        // Do something with region.
        // ....
        // Remember to dispose texture when you done! (now or later)
        region.getTexture().dispose();
    }

     @Override
     public void onFail(Exception e)
     {
       e.printStackTrace();
     }
});
```

__

### Realtime Database

Sample database usage, with following POJO class

```java
public class User{
  public String username;
  public String email;
  public User(){}
  public User(String username, String email){
    this.username = username;
    this.email = email;
  }
}
```

Put into database:

```java
GdxFIRDatabase.instance().inReference("users/"+userId)
.setValue(new User("Bob", "bob@bob.com"));
```

Listen for data change:

```java
GdxFIRDatabase.instance().inReference("users/"+userId)
.onDataChange(User.class, new DataChangeListener<User>() {
    @Override
    public void onChange(User user)
    {
    }
            
    @Override
    public void onCanceled(Exception e)
    {
    
    }
});
```

Read a list:

```java
GdxFIRDatabase.instance().inReference("users")
.readValue(List.class, new DataCallback<List<User>>(){
  @Override
  public void onData(List<User> list)
  {
    // Do something with list..
  }
  
  @Override
  public void onError(Exception e)
  {
    // Handle failure
  }
});
```

Do not forget about authorization if need it, more info [here](https://firebase.google.com/docs/database/security/quickstart)

__

### Crash raporting

Any custom errors or logs you can report as follow:

```java
GdxFIRCrash.log("i'm custom log.")
```



## Useful links

- [Javadoc](http://fireappdocs.mk5.pl)




## What's next?



- Wiki/documentation
- GWT support
- Database query flow (filtering, limiting etc..)
- Google, facebook authorization.




## Features

- [x] Analytics
- [x] Authentication
- [x] Database
- [x] Storage
- [x] Crash raporting
- [ ] Messaging




## Platforms

- [x] Android
- [x] iOS (Multi-os Engine)
- [ ] GWT





## License

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.