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

package mk.gdx.firebase.auth;

import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.distributions.AuthUserDistribution;

/**
 * Multi-module Firebase user representation.
 */
public class GdxFirebaseUser implements AuthUserDistribution {
    private UserInfo userInfo;
    private GdxFIRUser userDistribution;

    /**
     * @param userInfo User information wrapped by POJO object.
     */
    private GdxFirebaseUser(UserInfo userInfo) {
        this.userInfo = new UserInfo.Builder().setUserInfo(userInfo).build();
        this.userDistribution = new GdxFIRUser();
    }

    /**
     * @param userInfo Information about user.
     * @return New Firebase user representation, not null.
     */
    public static GdxFirebaseUser create(UserInfo userInfo) {
        return new GdxFirebaseUser(userInfo);
    }

    /**
     * Gets user information.
     *
     * @return Information about Firebase user
     */
    public UserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEmail(String newEmail, CompleteCallback callback) {
        userDistribution.updateEmail(newEmail, callback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendEmailVerification(CompleteCallback callback) {
        userDistribution.sendEmailVerification(callback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePassword(char[] newPassword, CompleteCallback callback) {
        userDistribution.updatePassword(newPassword, callback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(CompleteCallback callback) {
        userDistribution.delete(callback);
    }

    @Override
    public void reload(CompleteCallback callback) {
        userDistribution.reload(callback);
    }
}
