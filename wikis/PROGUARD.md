## Proguard required rules

Add following rules to `android/proguard-project.txt` and `ios-moe/proguard.append.cfg`:

```
-keep class mk.gdx.firebase.**{*;}
-keepattributes Signature
-keepattributes *Annotation*
# Keep all POJO models which you had used as Database models.
-keepclassmembers class com.yourcompany.models.** {*;}
```

