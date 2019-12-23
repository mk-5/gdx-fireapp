/*
 * Copyright 2019 mk
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

package pl.mk5.gdx.fireapp.html.database;

import com.badlogic.gdx.utils.Array;

import pl.mk5.gdx.fireapp.database.ChildEventType;
import pl.mk5.gdx.fireapp.database.validators.ArgumentsValidator;
import pl.mk5.gdx.fireapp.database.validators.OnChildValidator;
import pl.mk5.gdx.fireapp.promises.FutureListenerPromise;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

class QueryOnChildChange extends GwtDatabaseQuery {

    private static final String CHILD_ADDED = "child_added";
    private static final String CHILD_REMOVED = "child_removed";
    private static final String CHILD_CHANGED = "child_changed";
    private static final String CHILD_MOVED = "child_moved";

    QueryOnChildChange(Database databaseDistribution, String databasePath) {
        super(databaseDistribution, databasePath);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void runJS() {
        Array<ChildEventType> childEventTypes = new Array<>();
        childEventTypes.addAll((ChildEventType[]) arguments.get(1));
        for (ChildEventType type : childEventTypes) {
            String jsEventName = getJsEventName(type);
            if (!GwtDataPromisesManager.hasPromise(databasePath + jsEventName)) {
                GwtDataPromisesManager.addDataPromise(databasePath, new JsonDataPromise((Class) arguments.get(0), (FuturePromise) promise));
                onEvent(databasePath, jsEventName, databaseReference);
                ((FutureListenerPromise) promise).onCancel(new CancelListenerAction(databasePath, jsEventName));
            }
        }
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return new OnChildValidator();
    }

    private String getJsEventName(ChildEventType eventType) {
        switch (eventType) {
            case ADDED:
                return CHILD_ADDED;
            case CHANGED:
                return CHILD_CHANGED;
            case MOVED:
                return CHILD_MOVED;
            case REMOVED:
                return CHILD_REMOVED;
            default:
                throw new IllegalStateException();
        }
    }

    public static native void onEvent(String reference, String event, DatabaseReference databaseReference) /*-{
         var ref = reference;
         var orderByCalled = databaseReference.orderByCalled_;
         $wnd.valueListenersOrderByCalled = $wnd.valueListenersOrderByCalled || {};
         $wnd.valueListenersOrderByCalled[reference] = orderByCalled;
         $wnd.valueListeners = $wnd.valueListeners || {};
         $wnd.valueListeners[reference] = databaseReference.on(event, function(snap){
           var val;
           if( !$wnd.valueListenersOrderByCalled[reference] ){
             val = JSON.stringify(snap.val());
           }else{
             var tmp = [];
             snap.forEach(function(child){
               tmp.push(child.val());
             });
             val = JSON.stringify(tmp);
          }
          @pl.mk5.gdx.fireapp.html.database.GwtDataPromisesManager::callPromise(Ljava/lang/String;Ljava/lang/String;)(ref+event,val);
        });
    }-*/;

    public static native void offEvent(String reference, String event) /*-{
        $wnd.valueListeners = $wnd.valueListeners || {};
        $wnd.valueListenersOrderByCalled = $wnd.valueListenersOrderByCalled || {};
        $wnd.valueListenersOrderByCalled[reference] = false;
        var listener = $wnd.valueListeners[reference] || null;
        $wnd.firebase.database().ref(reference).off(event, listener);
        @pl.mk5.gdx.fireapp.html.database.GwtDataPromisesManager::removeDataPromise(Ljava/lang/String;)(reference+event);
    }-*/;

    private static class CancelListenerAction implements Runnable {

        private final String databasePath;
        private final String event;

        private CancelListenerAction(String databasePath, String event) {
            this.databasePath = databasePath;
            this.event = event;
        }

        @Override
        public void run() {
            offEvent(databasePath, event);
        }
    }
}
