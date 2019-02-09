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
package pl.mk5.gdx.fireapp.android.auth;


import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import pl.mk5.gdx.fireapp.android.utils.StringResource;

class GoogleSignInOptionsFactory {

    private GoogleSignInOptionsFactory() {
        //
    }

    static GoogleSignInOptions factory() {
        String webClientId = StringResource.getStringResourceByName("default_web_client_id");
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestEmail()
                .build();
    }
}
