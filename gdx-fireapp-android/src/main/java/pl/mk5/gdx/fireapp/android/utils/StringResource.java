/*
 * Copyright 2018 mk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.mk5.gdx.fireapp.android.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;

/**
 * Gets string from R by name.
 */
public class StringResource {

    private StringResource() {

    }

    /**
     * Gets value from R.string by name.
     *
     * @param name The resource name, not null
     * @return The R.string value
     */
    public static String getStringResourceByName(String name) {
        int id = ((AndroidApplication) Gdx.app).getResources().getIdentifier(name, "string", ((AndroidApplication) Gdx.app).getPackageName());
        return ((AndroidApplication) Gdx.app).getResources().getString(id);
    }
}
