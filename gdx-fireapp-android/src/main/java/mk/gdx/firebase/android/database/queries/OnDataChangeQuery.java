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

package mk.gdx.firebase.android.database.queries;

import com.badlogic.gdx.utils.Array;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import mk.gdx.firebase.android.database.AndroidDatabaseQuery;
import mk.gdx.firebase.android.database.DataListenersManager;
import mk.gdx.firebase.android.database.Database;
import mk.gdx.firebase.android.database.providers.DatabaseQueryFilteringProvider;
import mk.gdx.firebase.android.database.resolvers.DataListenerOnDataChangeResolver;
import mk.gdx.firebase.database.pojos.OrderByClause;
import mk.gdx.firebase.listeners.DataChangeListener;

/**
 * Provides call to {@link com.google.firebase.database.Query#addValueEventListener(ValueEventListener)}.
 */
public class OnDataChangeQuery extends AndroidDatabaseQuery<Void>
{

    private DataListenersManager dataListenersManager;

    public OnDataChangeQuery(Database databaseDistribution)
    {
        super(databaseDistribution);
        dataListenersManager = new DataListenersManager();
    }

    @Override
    protected void prepare()
    {
        if (arguments.size != 2)
            throw new IllegalStateException();
        if (!(arguments.get(0) instanceof Class))
            throw new IllegalArgumentException();
        if (arguments.get(1) != null && !(arguments.get(1) instanceof DataChangeListener))
            throw new IllegalArgumentException();
        super.prepare();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Void run()
    {
        if (arguments.get(1) != null) {
            DataChangeValueListener dataChangeListener = new DataChangeValueListener<>((Class) arguments.get(0), (DataChangeListener) arguments.get(1), orderByClause);
            dataListenersManager.addNewListener(databasePath, dataChangeListener);
            new DatabaseQueryFilteringProvider(filters, orderByClause, query).addValueEventListener(dataChangeListener);
        } else {
            Array<ValueEventListener> listeners = dataListenersManager.getListeners(databasePath);
            for (ValueEventListener v : listeners) {
                query.removeEventListener(v);
            }
            dataListenersManager.removeListenersForPath(databasePath);
        }
        return null;
    }

    /**
     * Wrapper for {@link ValueEventListener} used when need to deal with {@link DatabaseReference#addValueEventListener(ValueEventListener)}
     *
     * @param <T> Class of object that we want to listen for change. For ex. List
     * @param <R> Return type of object that we want to listen for change. For ex. List<MyClass>
     */
    private class DataChangeValueListener<T, R extends T> implements ValueEventListener
    {

        private Class<T> dataType;
        private DataChangeListener<R> dataChangeListener;
        private OrderByClause orderByClause;

        public DataChangeValueListener(Class<T> dataType, DataChangeListener<R> dataChangeListener, OrderByClause orderByClause)
        {
            this.dataChangeListener = dataChangeListener;
            this.dataType = dataType;
            this.orderByClause = orderByClause;
        }


        @Override
        @SuppressWarnings("unchecked")
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            DataListenerOnDataChangeResolver.resolve(dataType, orderByClause, dataSnapshot, dataChangeListener);
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {
            dataChangeListener.onCanceled(databaseError.toException());
        }


    }
}
