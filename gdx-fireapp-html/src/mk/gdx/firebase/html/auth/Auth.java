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

package mk.gdx.firebase.html.auth;

import mk.gdx.firebase.auth.GdxFirebaseUser;
import mk.gdx.firebase.callbacks.AuthCallback;
import mk.gdx.firebase.distributions.AuthDistribution;

/**
 * @see AuthDistribution
 */
public class Auth implements AuthDistribution
{
    @Override
    public GdxFirebaseUser getCurrentUser()
    {
//        JSONObject userFromApi = firebaseUser();
//        if (!userFromApi.containsKey("uid")) return null;
//        UserInfo.Builder builder = new UserInfo.Builder();
//        builder.setDisplayName(userFromApi.get("displayName").isString().stringValue())
//                .setPhotoUrl(userFromApi.get("photoURL").isString().stringValue())
//                .setProviderId(userFromApi.get("providerData").isObject().get("providerId").isString().stringValue())
//                .setUid(userFromApi.get("uid").isString().stringValue())
//                .setIsEmailVerified(userFromApi.get("emailVerified").isBoolean().booleanValue())
//                .setIsAnonymous(userFromApi.get("isAnonymous").isBoolean().booleanValue());
//        return GdxFirebaseUser.create(builder.build());
        return null;
    }

    @Override
    public void createUserWithEmailAndPassword(String email, char[] password, AuthCallback callback)
    {

    }

    @Override
    public void signInWithEmailAndPassword(String email, char[] password, AuthCallback callback)
    {

    }

    @Override
    public void signInWithToken(String token, AuthCallback callback)
    {

    }

    @Override
    public void signInAnonymously(AuthCallback callback)
    {

    }

//    private static native JSONObject firebaseUser(); /*-{ return $wnd.firebase.auth().currentUser; }-*/
}
