package pl.mk5.gdxfireapp.e2e;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        try {
            initialize(new GdxFireappE2ETests(), config);
        } catch (Exception e) {
            System.err.println("Error while initializing the app");
            e.printStackTrace();
        }
    }
}
