package pl.mk5.gdx.fireapp.android;

import android.os.Bundle;

import java.util.HashSet;
import java.util.Set;

class TestUtils {

    private TestUtils() {}

    // Copied and simplified from https://stackoverflow.com/a/13238729
    public static boolean equalBundles(Bundle one, Bundle two) {
        if(one.size() != two.size())
            return false;

        Set<String> setOne = new HashSet<>(one.keySet());
        setOne.addAll(two.keySet());

        for(String key : setOne) {
            if (!one.containsKey(key) || !two.containsKey(key))
                return false;

            Object valueOne = one.get(key);
            Object valueTwo = two.get(key);
            if(valueOne instanceof Bundle && valueTwo instanceof Bundle &&
                    !equalBundles((Bundle) valueOne, (Bundle) valueTwo)) {
                return false;
            } else if(valueOne == null) {
                if(valueTwo != null)
                    return false;
            } else if(!valueOne.equals(valueTwo)) {
                return false;
            }
        }

        return true;
    }
}
