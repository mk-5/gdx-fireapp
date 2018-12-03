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

package mk.gdx.firebase.html.database;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * GWT Adapter for firebase.database.Reference.
 * <p>
 * More info here: <a href="https://firebase.google.com/docs/reference/js/firebase.database.Reference">https://firebase.google.com/docs/reference/js/firebase.database.Reference</a>
 */
class DatabaseReference extends JavaScriptObject {

    protected DatabaseReference() {
    }

    public final native DatabaseReference child(String key) /*-{
        return this.child(key);
    }-*/;

    public final native DatabaseReference orderByKey() /*-{
        return this.orderByKey();
    }-*/;

    public final native DatabaseReference orderByChild(String childKey) /*-{
        return this.orderByChild(childKey);
    }-*/;

    public final native DatabaseReference orderByValue() /*-{
        return this.orderByValue();
    }-*/;

    public final native DatabaseReference endAt(String key) /*-{
        return this.endAt(key);
    }-*/;

    public final native DatabaseReference endAt(Boolean key) /*-{
        return this.endAt(key);
    }-*/;

    public final native DatabaseReference endAt(double key) /*-{
        return this.endAt(key);
    }-*/;

    public final native DatabaseReference startAt(String key) /*-{
        return this.startAt(key);
    }-*/;

    public final native DatabaseReference startAt(Boolean key) /*-{
        return this.startAt(key);
    }-*/;

    public final native DatabaseReference startAt(double key) /*-{
        return this.startAt(key);
    }-*/;

    public final native DatabaseReference equalTo(String key) /*-{
        return this.equalTo(key);
    }-*/;

    public final native DatabaseReference equalTo(Boolean key) /*-{
        return this.equalTo(key);
    }-*/;

    public final native DatabaseReference equalTo(double key) /*-{
        return this.equalTo(key);
    }-*/;

    public final native DatabaseReference limitToFirst(double number) /*-{
        return this.limitToFirst(number);
    }-*/;

    public final native DatabaseReference limitToLast(double number) /*-{
        return this.limitToLast(number);
    }-*/;

    public final native Boolean orderByCalled_() /*-{
        return this.orderByCalled_;
    }-*/;

    static native DatabaseReference of(String path) /*-{
        return $wnd.firebase.database().ref(path);
    }-*/;
}
