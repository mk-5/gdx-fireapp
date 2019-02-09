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
 * Firebase analytics events list.
 * <p>
 *
 * @see <a href="https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.Event">andoird docs</a>
 * @see <a href="https://firebase.google.com/docs/reference/ios/firebaseanalytics/api/reference/Constants">ios docs</a>
 */
public class AnalyticsEvent {

    private AnalyticsEvent() {
        //
    }

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Params for this {@code AnalyticsEvent} are undefined.
     */
    public static final String ADD_PAYMENT_INFO = "add_payment_info";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Available params for this {@code AnalyticsEvent} are:
     * <p>
     * <ul>
     * <li>{@link AnalyticsParam#ITEM_ID},
     * <li>{@link AnalyticsParam#ITEM_NAME},
     * <li>{@link AnalyticsParam#ITEM_CATEGORY},
     * <li>{@link AnalyticsParam#QUANTITY},
     * <li>{@link AnalyticsParam#PRICE},
     * <li>{@link AnalyticsParam#CURRENCY},
     * <li>{@link AnalyticsParam#ORIGIN},
     * <li>{@link AnalyticsParam#ITEM_LOCATION_ID},
     * <li>{@link AnalyticsParam#DESTINATION},
     * <li>{@link AnalyticsParam#START_DATE},
     * <li>{@link AnalyticsParam#END_DATE}
     * </ul><p>
     */
    public static final String ADD_TO_CART = "add_to_cart";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Available params for this {@code AnalyticsEvent} are:
     * <p>
     * <ul>
     * <li>{@link AnalyticsParam#ITEM_ID},
     * <li>{@link AnalyticsParam#ITEM_NAME},
     * <li>{@link AnalyticsParam#ITEM_CATEGORY},
     * <li>{@link AnalyticsParam#QUANTITY},
     * <li>{@link AnalyticsParam#PRICE},
     * <li>{@link AnalyticsParam#VALUE},
     * <li>{@link AnalyticsParam#CURRENCY},
     * <li>{@link AnalyticsParam#ITEM_LOCATION_ID}
     * </ul><p>
     */
    public static final String ADD_TO_WISHLIST = "add_to_wishlist";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Params for this {@code AnalyticsEvent} are undefined.
     */
    public static final String APP_OPEN = "app_open";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Available params for this {@code AnalyticsEvent} are:
     * <p>
     * <ul>
     * <li>{@link AnalyticsParam#VALUE},
     * <li>{@link AnalyticsParam#CURRENCY},
     * <li>{@link AnalyticsParam#TRANSACTION_ID},
     * <li>{@link AnalyticsParam#NUMBER_OF_NIGHTS},
     * <li>{@link AnalyticsParam#NUMBER_OF_ROOMS},
     * <li>{@link AnalyticsParam#NUMBER_OF_PASSENGERS},
     * <li>{@link AnalyticsParam#ORIGIN},
     * <li>{@link AnalyticsParam#DESTINATION},
     * <li>{@link AnalyticsParam#START_DATE},
     * <li>{@link AnalyticsParam#END_DATE},
     * <li>{@link AnalyticsParam#TRAVEL_CLASS}
     * </ul><p>
     */
    public static final String BEGIN_CHECKOUT = "begin_checkout";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Available params for this {@code AnalyticsEvent} are:
     * <p>
     * <ul>
     * <li>{@link AnalyticsParam#VIRTUAL_CURRENCY_NAME},
     * <li>{@link AnalyticsParam#VALUE}
     * </ul><p>
     */
    public static final String EARN_VIRTUAL_CURRENCY = "earn_virtual_currency";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Available params for this {@code AnalyticsEvent} are:
     * <p>
     * <ul>
     * <li>{@link AnalyticsParam#CURRENCY},
     * <li>{@link AnalyticsParam#VALUE},
     * <li>{@link AnalyticsParam#TRANSACTION_ID},
     * <li>{@link AnalyticsParam#TAX},
     * <li>{@link AnalyticsParam#SHIPPING},
     * <li>{@link AnalyticsParam#COUPON},
     * <li>{@link AnalyticsParam#LOCATION},
     * <li>{@link AnalyticsParam#NUMBER_OF_NIGHTS},
     * <li>{@link AnalyticsParam#NUMBER_OF_ROOMS},
     * <li>{@link AnalyticsParam#NUMBER_OF_PASSENGERS},
     * <li>{@link AnalyticsParam#ORIGIN},
     * <li>{@link AnalyticsParam#DESTINATION},
     * <li>{@link AnalyticsParam#START_DATE},
     * <li>{@link AnalyticsParam#END_DATE},
     * <li>{@link AnalyticsParam#TRAVEL_CLASS}
     * </ul><p>
     */
    public static final String ECOMMERCE_PURCHASE = "ecommerce_purchase";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Available params for this {@code AnalyticsEvent} are:
     * <p>
     * <ul>
     * <li>{@link AnalyticsParam#CURRENCY},
     * <li>{@link AnalyticsParam#VALUE}
     * </ul><p>
     */
    public static final String GENERATE_LEAD = "generate_lead";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Available params for this {@code AnalyticsEvent} are:
     * <p>
     * <ul>
     * <li>{@link AnalyticsParam#GROUP_ID}
     * </ul><p>
     */
    public static final String JOIN_GROUP = "join_group";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Available params for this {@code AnalyticsEvent} are:
     * <p>
     * <ul>
     * <li>{@link AnalyticsParam#LEVEL},
     * <li>{@link AnalyticsParam#CHARACTER}
     * </ul><p>
     */
    public static final String LEVEL_UP = "level_up";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Params for this {@code AnalyticsEvent} are undefined.
     */
    public static final String LOGIN = "login";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Available params for this {@code AnalyticsEvent} are:
     * <p>
     * <ul>
     * <li>{@link AnalyticsParam#SCORE},
     * <li>{@link AnalyticsParam#LEVEL},
     * <li>{@link AnalyticsParam#CHARACTER}
     * </ul><p>
     */
    public static final String POST_SCORE = "post_score";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Available params for this {@code AnalyticsEvent} are:
     * <p>
     * <ul>
     * <li>{@link AnalyticsParam#ITEM_ID},
     * <li>{@link AnalyticsParam#ITEM_NAME},
     * <li>{@link AnalyticsParam#ITEM_CATEGORY},
     * <li>{@link AnalyticsParam#QUANTITY},
     * <li>{@link AnalyticsParam#PRICE},
     * <li>{@link AnalyticsParam#VALUE},
     * <li>{@link AnalyticsParam#CURRENCY},
     * <li>{@link AnalyticsParam#ITEM_LOCATION_ID}
     * </ul><p>
     */
    public static final String PRESENT_OFFER = "present_offer";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Available params for this {@code AnalyticsEvent} are:
     * <p>
     * <ul>
     * <li>{@link AnalyticsParam#CURRENCY},
     * <li>{@link AnalyticsParam#VALUE},
     * <li>{@link AnalyticsParam#TRANSACTION_ID}
     * </ul><p>
     */
    public static final String PURCHASE_REFUND = "purchase_refund";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Available params for this {@code AnalyticsEvent} are:
     * <p>
     * <ul>
     * <li>{@link AnalyticsParam#SEARCH_TERM},
     * <li>{@link AnalyticsParam#NUMBER_OF_NIGHTS},
     * <li>{@link AnalyticsParam#NUMBER_OF_ROOMS},
     * <li>{@link AnalyticsParam#NUMBER_OF_PASSENGERS},
     * <li>{@link AnalyticsParam#ORIGIN},
     * <li>{@link AnalyticsParam#DESTINATION},
     * <li>{@link AnalyticsParam#START_DATE},
     * <li>{@link AnalyticsParam#END_DATE},
     * <li>{@link AnalyticsParam#TRAVEL_CLASS}
     * </ul><p>
     */
    public static final String SEARCH = "search";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Available params for this {@code AnalyticsEvent} are:
     * <p>
     * <ul>
     * <li>{@link AnalyticsParam#CONTENT_TYPE},
     * <li>{@link AnalyticsParam#ITEM_ID}
     * </ul><p>
     */
    public static final String SELECT_CONTENT = "select_content";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Available params for this {@code AnalyticsEvent} are:
     * <p>
     * <ul>
     * <li>{@link AnalyticsParam#CONTENT_TYPE},
     * <li>{@link AnalyticsParam#ITEM_ID}
     * </ul><p>
     */
    public static final String SHARE = "share";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Available params for this {@code AnalyticsEvent} are:
     * <p>
     * <ul>
     * {@link AnalyticsParam#SIGN_UP_METHOD}
     * </ul><p>
     */
    public static final String SIGN_UP = "sign_up";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Available params for this {@code AnalyticsEvent} are:
     * <p>
     * <ul>
     * <li>{@link AnalyticsParam#ITEM_NAME},
     * <li>{@link AnalyticsParam#VIRTUAL_CURRENCY_NAME},
     * <li>{@link AnalyticsParam#VALUE}
     * </ul><p>
     */
    public static final String SPEND_VIRTUAL_CURRENCY = "spend_virtual_currency";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Params for this {@code AnalyticsEvent} are undefined.
     */
    public static final String TUTORIAL_BEGIN = "tutorial_begin";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Params for this {@code AnalyticsEvent} are undefined.
     */
    public static final String TUTORIAL_COMPLETE = "tutorial_complete";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * <ul>
     * <li>{@link AnalyticsParam#ACHIEVEMENT_ID}
     * </ul><p>
     */
    public static final String UNLOCK_ACHIEVEMENT = "unlock_achievement";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Available params for this {@code AnalyticsEvent} are:
     * <p>
     * <ul>
     * <li>{@link AnalyticsParam#ITEM_ID},
     * <li>{@link AnalyticsParam#ITEM_NAME},
     * <li>{@link AnalyticsParam#ITEM_CATEGORY},
     * <li>{@link AnalyticsParam#ITEM_LOCATION_ID},
     * <li>{@link AnalyticsParam#PRICE},
     * <li>{@link AnalyticsParam#QUANTITY},
     * <li>{@link AnalyticsParam#CURRENCY},
     * <li>{@link AnalyticsParam#VALUE},
     * <li>{@link AnalyticsParam#FLIGHT_NUMBER},
     * <li>{@link AnalyticsParam#NUMBER_OF_NIGHTS},
     * <li>{@link AnalyticsParam#NUMBER_OF_ROOMS},
     * <li>{@link AnalyticsParam#NUMBER_OF_PASSENGERS},
     * <li>{@link AnalyticsParam#ORIGIN},
     * <li>{@link AnalyticsParam#DESTINATION},
     * <li>{@link AnalyticsParam#START_DATE},
     * <li>{@link AnalyticsParam#END_DATE},
     * <li>{@link AnalyticsParam#TRAVEL_CLASS}
     * </ul><p>
     */
    public static final String VIEW_ITEM = "view_item";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Available params for this {@code AnalyticsEvent} are:
     * <p>
     * <ul>
     * <li>{@link AnalyticsParam#ITEM_CATEGORY}
     * </ul><p>
     */
    public static final String VIEW_ITEM_LIST = "view_item_list";

    /**
     * Firebase analytics event: {@value}
     * <p>
     * Available params for this {@code AnalyticsEvent} are:
     * <p>
     * <ul>
     * <li>{@link AnalyticsParam#SEARCH_TERM}
     * </ul><p>
     */
    public static final String VIEW_SEARCH_RESULT = "view_search_results";
}