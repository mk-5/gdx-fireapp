/*
 * Copyright 2017 mk
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

package mk.gdx.firebase.android.database;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.google.firebase.database.ValueEventListener;

/**
 * Menages data listeners for db.
 * <p>
 * Keeps all current listeners inside {@link #listeners}
 */
public class DataListenersManager
{
    private ObjectMap<String, Array<ValueEventListener>> listeners;

    public DataListenersManager()
    {
        listeners = new ObjectMap<>();
    }

    public void addNewListener(String databasePath, ValueEventListener listener)
    {
        if (!listeners.containsKey(databasePath))
            listeners.put(databasePath, new Array<ValueEventListener>());
        listeners.get(databasePath).add(listener);
    }

    public void removeListenersForPath(String databasePath)
    {
        listeners.get(databasePath).clear();
    }

    public Array<ValueEventListener> getListeners(String databasePath)
    {
        return new Array<>(listeners.get(databasePath));
    }
}
