# Todo

...



Screen

```java
class SuperApp extends App{
    @Override
    public void create()
    {
        GdxFIRApp.instance().configure();
        GdxFIRAnalytics.instance().setScreen("main_screen", SuperApp.class);
    }
}
```

Log event
```java
class ButtonTap implements Runnable{

    @Override
    public void run()
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AnalyticsParam.CONTENT_TYPE, "button");
        params.put(AnalyticsParam.ITEM_ID, "my super button");
        GdxFIRAnalytics.instance().logEvent(AnalyticsEvent.SELECT_CONTENT, params);
    }

}
```

