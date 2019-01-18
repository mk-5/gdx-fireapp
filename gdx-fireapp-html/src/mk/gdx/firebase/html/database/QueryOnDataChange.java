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

package mk.gdx.firebase.html.database;

import mk.gdx.firebase.database.validators.ArgumentsValidator;
import mk.gdx.firebase.database.validators.OnDataValidator;
import mk.gdx.firebase.promises.FutureListenerPromise;
import mk.gdx.firebase.promises.FuturePromise;

/**
 * Provides setValue execution.
 */
class QueryOnDataChange extends GwtDatabaseQuery {
    QueryOnDataChange(Database databaseDistribution) {
        super(databaseDistribution);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void runJS() {
        if (!GwtDataPromisesManager.hasPromise(databaseReferencePath)) {
            GwtDataPromisesManager.addDataPromise(databaseReferencePath, new JsonDataPromise((Class) arguments.get(0), (FuturePromise) promise));
            onValue(databaseReferencePath, databaseReference);
            ((FutureListenerPromise) promise).onCancel(new CancelListenerAction(databaseReferencePath));
        }
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return new OnDataValidator();
    }

    /**
     * Attaches listener from {@link GwtDataPromisesManager} to given reference.
     *
     * @param reference Reference path, not null
     */
    public static native void onValue(String reference, DatabaseReference databaseReference) /*-{
         var ref = reference;
         var orderByCalled = databaseReference.orderByCalled_;
         $wnd.valueListenersOrderByCalled = $wnd.valueListenersOrderByCalled || {};
         $wnd.valueListenersOrderByCalled[reference] = orderByCalled;
         $wnd.valueListeners = $wnd.valueListeners || {};
         $wnd.valueListeners[reference] = databaseReference.on("value", function(snap){
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
          @mk.gdx.firebase.html.database.GwtDataListenersManager::callPromise(Ljava/lang/String;Ljava/lang/String;)(ref,val);
        });
    }-*/;

    /**
     * Remove value listeners for given path.
     * <p>
     * If listener was not declared before - all value listeners for given path will be cleared.
     *
     * @param reference Reference path, not null
     */
    public static native void offValue(String reference) /*-{
        $wnd.valueListeners = $wnd.valueListeners || {};
        $wnd.valueListenersOrderByCalled = $wnd.valueListenersOrderByCalled || {};
        $wnd.valueListenersOrderByCalled[reference] = false;
        var listener = $wnd.valueListeners[reference] || null;
        $wnd.firebase.database().ref(reference).off('value', listener);
        @mk.gdx.firebase.html.database.GwtDataListenersManager::removeDataPromise(Ljava/lang/String;)(reference);
    }-*/;

    private static class CancelListenerAction implements Runnable {

        private final String databasePath;

        private CancelListenerAction(String databasePath) {
            this.databasePath = databasePath;
        }

        @Override
        public void run() {
            offValue(databasePath);
        }
    }
}
