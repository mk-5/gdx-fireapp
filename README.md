# GDX Fireapp

A libGDX cross-platform API for Firebase.

[ ![Build Status](https://travis-ci.org/mk-5/gdx-fireapp.svg?branch=master)](https://travis-ci.org/mk-5/gdx-fireapp) [ ![Download](https://api.bintray.com/packages/mk-5/maven/gdx-fireapp/images/download.svg) ](https://bintray.com/mk-5/maven/gdx-fireapp/_latestVersion)



## How do I use it?

First thing is installing Firebase SDK. Here you can find some short tutorials describes installation inside LibGDX project:

- [Android Firebase SDK installation guide](https://github.com/mk-5/gdx-fireapp/wiki/Android-SDK-installation)
- [iOS Firebase SDK installation guide](https://github.com/mk-5/gdx-fireapp/wiki/iOS-Firebase-SDK-installation)

Now you need to add GDX Fireapp gradle dependencies, as follow:

**Core**

```
compile "pl.mk5.gdx-fireapp:gdx-fireapp-core:1.0.1"
```
**Android**

```
compile "pl.mk5.gdx-fireapp:gdx-fireapp-android:1.0.1"
```
**iOS**

```
compile "pl.mk5.gdx-fireapp:gdx-fireapp-ios-moe:1.0.1"
```

Last step is:

- [Update proguard files](https://github.com/mk-5/gdx-fireapp/wiki/Proguard-required-rules)



**Version 1.1.0** was built using LibGDX v1.9.6, multi-os-engine 1.3.12, gwt 2.8.0

Docs are here: [Javadoc](http://fireappdocs.mk5.pl/)

If you want to use GWT platform read this wiki page: [GWT support](https://github.com/mk-5/gdx-fireapp/wiki/GDX-Fireapp-GWT)



## Basics

API is something like bridge beetwen LibGDX app and firebase sdk. It's cover firebase functionality so if you have some knowledge about firebase SDK - using this api should be intuitive for you.



To initialize Firebase SDK  just put this line  somewhere in your app initialization code:

```java
GdxFIRApp.instance().configure();
```

**Firebase Analytics** and **Firebase Crash** are features which start working just after this step.



## Examples

To deal with Firebase SDK you have to use following classes:

- **[GdxFIRApp](http://fireappdocs.mk5.pl/mk/gdx/firebase/GdxFIRApp.html)**
- **[GdxFIRAnalytics](http://fireappdocs.mk5.pl/mk/gdx/firebase/GdxFIRAnalytics.html)**
- **[GdxFIRAuth](http://fireappdocs.mk5.pl/mk/gdx/firebase/GdxFIRAuth.html)**
- **[GdxFIRStorage](http://fireappdocs.mk5.pl/mk/gdx/firebase/GdxFIRStorage.html)**
- **[GdxFIRDatabase](http://fireappdocs.mk5.pl/mk/gdx/firebase/GdxFIRDatabase.html)**
- **[GdxFIRCrash](http://fireappdocs.mk5.pl/mk/gdx/firebase/GdxFIRCrash.html)**




#### Analytics

Log some button tap event

```java
Map<String, String> params = new HashMap<String, String>();
params.put(AnalyticsParam.CONTENT_TYPE, "button");
params.put(AnalyticsParam.ITEM_ID, "my super button");
GdxFIRAnalytics.instance().logEvent(AnalyticsEvent.SELECT_CONTENT, params);
```



#### Auth

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



#### Storage

If yours firebase storage requires authorization remember to do some auth first . (you can read something more about it [here](https://firebase.google.com/docs/storage/security/)). Some examples of downloading from firebase storage:

Download byte data:

```java
GdxFIRStorage.instance().download("/file.data", Long.MAX_VALUE, new DownloadCallback<byte[]>() {
  @Override
  public void onSuccess(byte[] result)
  {
    // Process just downloaded byte[] data.
  }

  @Override
  public void onFail(Exception e)
  {
    e.printStackTrace();
  }
});
```

Download texture:

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



#### Realtime Database

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

If yours database requires authorization do not forget about it, more info [here](https://firebase.google.com/docs/database/security/quickstart)



#### Crash raporting

Any custom errors or logs you can report as follow:

```java
GdxFIRCrash.log("i'm custom log.")
```



## Useful links

- [Javadoc](http://fireappdocs.mk5.pl)




## What's next?



- Better wiki/documentation
- Database query flow (filtering, limiting etc..)
- Authorization sign-out
- Google, facebook authorization
- Messaging
- Upload task monitoring




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
- [x] GWT





## License

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.