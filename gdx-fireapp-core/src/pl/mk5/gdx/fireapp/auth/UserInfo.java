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

package pl.mk5.gdx.fireapp.auth;

/**
 * POJO class that holds information about Firebase user.
 */
public class UserInfo {
    private String providerId;
    private String uid;
    private String displayName;
    private String photoUrl;
    private String email;
    private boolean isEmailVerified;
    private boolean isAnonymous;

    private UserInfo() {
    }

    /**
     * Gets information about user anonymity.
     *
     * @return True if user sign-in in anonymous way
     */
    public boolean isAnonymous() {
        return isAnonymous;
    }

    /**
     * Gets information if user email has been verified.
     *
     * @return True if email was verified
     */
    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    /**
     * Gets provider id.
     *
     * @return Provider id, may be null
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * Gets Firebase user UID.
     *
     * @return Firebase user UID, may be null
     */
    public String getUid() {
        return uid;
    }

    /**
     * Gets Firebase user display name.
     *
     * @return Firebase user display name, may be null
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets Firebase user photo url.
     *
     * @return Firebase user photo url, may be null
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * Gets Firebase user email.
     *
     * @return Firebase user email, may be null
     */
    public String getEmail() {
        return email;
    }

    /**
     * Builder pattern for {@code UserInfo}.
     */
    public static class Builder {
        private UserInfo inst;

        /**
         * Default {@code Builder} constructor.
         */
        public Builder() {
            inst = new UserInfo();
        }

        /**
         * Sets provider id.
         *
         * @param providerId Provider id, for ex. {@code facebook.com}
         * @return this {@code Builder} instance
         */
        public Builder setProviderId(String providerId) {
            inst.providerId = providerId;
            return this;
        }

        /**
         * Sets firebase user uid.
         *
         * @param uid User UID
         * @return this {@code Builder} instance
         */
        public Builder setUid(String uid) {
            inst.uid = uid;
            return this;
        }

        /**
         * Sets firebase user display name.
         *
         * @param displayName Display name
         * @return this {@code Builder} instance
         */
        public Builder setDisplayName(String displayName) {
            inst.displayName = displayName;
            return this;
        }

        /**
         * Sets firebase user photo url.
         *
         * @param photoUrl Photo url
         * @return this {@code Builder} instance
         */
        public Builder setPhotoUrl(String photoUrl) {
            inst.photoUrl = photoUrl;
            return this;
        }

        /**
         * Sets flag that describes if firebase user email was verified or not.
         *
         * @param isEmailVerified True if email was verified
         * @return this {@code Builder} instance
         */
        public Builder setIsEmailVerified(boolean isEmailVerified) {
            inst.isEmailVerified = isEmailVerified;
            return this;
        }

        /**
         * Sets firebase user photo url.
         *
         * @param email Firebase user email
         * @return this {@code Builder} instance
         */
        public Builder setEmail(String email) {
            inst.email = email;
            return this;
        }

        /**
         * Sets flag that describes if firebase user sign-in in anonymous way.
         *
         * @param isAnonymous True if user sign-in in anonymous way
         * @return this {@code Builder} instance
         */
        public Builder setIsAnonymous(boolean isAnonymous) {
            inst.isAnonymous = isAnonymous;
            return this;
        }

        /**
         * Sets all fields from the source {@code userInfo}.
         *
         * @param userInfo The source UserInfo, not null
         * @return this {@code Builder} instance
         */
        public Builder setUserInfo(UserInfo userInfo) {
            inst.displayName = userInfo.displayName;
            inst.email = userInfo.email;
            inst.isAnonymous = userInfo.isAnonymous;
            inst.isEmailVerified = userInfo.isEmailVerified;
            inst.photoUrl = userInfo.photoUrl;
            inst.uid = userInfo.uid;
            inst.providerId = userInfo.providerId;
            return this;
        }

        /**
         * Gets {@code UserInfo} instance.
         *
         * @return {@code UserInfo} instance created by given properties
         */
        public UserInfo build() {
            return inst;
        }
    }
}
