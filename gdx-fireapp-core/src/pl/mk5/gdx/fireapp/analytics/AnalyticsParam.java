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

package pl.mk5.gdx.fireapp.analytics;

/**
 * Firebase analytics params list.
 * <p>
 *
 * @see <a href="https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.Param">andoird docs</a>
 * @see <a href="https://firebase.google.com/docs/reference/ios/firebaseanalytics/api/reference/Constants">ios docs</a>
 */
public class AnalyticsParam {

    private AnalyticsParam() {
        //
    }

    /**
     * Firebase Analytics Params
     */
    public static final String ACHIEVEMENT_ID = "achievement_id";
    public static final String CHARACTER = "character";
    public static final String CONTENT_TYPE = "content_type";
    public static final String COUPON = "coupon";
    public static final String CURRENCY = "currency";
    public static final String DESTINATION = "destination";
    public static final String END_DATE = "end_date";
    public static final String FLIGHT_NUMBER = "flight_number";
    public static final String GROUP_ID = "group_id";
    public static final String ITEM_CATEGORY = "item_category";
    public static final String ITEM_ID = "item_id";
    public static final String ITEM_LOCATION_ID = "item_location_id";
    public static final String ITEM_NAME = "item_name";
    public static final String LEVEL = "level";
    public static final String LOCATION = "location";
    public static final String NUMBER_OF_NIGHTS = "number_of_nights";
    public static final String NUMBER_OF_PASSENGERS = "number_of_passengers";
    public static final String NUMBER_OF_ROOMS = "number_of_rooms";
    public static final String ORIGIN = "origin";
    public static final String PRICE = "price";
    public static final String QUANTITY = "quantity";
    public static final String SCORE = "score";
    public static final String SEARCH_TERM = "search_term";
    public static final String SHIPPING = "shipping";
    public static final String SIGN_UP_METHOD = "sign_up_method";
    public static final String START_DATE = "start_date";
    public static final String TAX = "tax";
    public static final String TRANSACTION_ID = "transaction_id";
    public static final String TRAVEL_CLASS = "travel_class";
    public static final String VALUE = "value";
    public static final String VIRTUAL_CURRENCY_NAME = "virtual_currency_name";
}
