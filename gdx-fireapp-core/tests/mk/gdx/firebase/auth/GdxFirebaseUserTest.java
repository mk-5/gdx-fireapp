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

package mk.gdx.firebase.auth;

import org.junit.Assert;
import org.junit.Test;

public class GdxFirebaseUserTest {

    @Test
    public void create() {
        // Given
        UserInfo userInfo = new UserInfo.Builder().build();

        // When
        GdxFirebaseUser user = GdxFirebaseUser.create(userInfo);

        // Then
        Assert.assertNotNull(user);
    }

    @Test
    public void getUserInfo() {
        // Given
        UserInfo userInfo = new UserInfo.Builder().build();

        // When
        GdxFirebaseUser user = GdxFirebaseUser.create(userInfo);

        // Then
        Assert.assertNotEquals(user.getUserInfo(), userInfo);
        Assert.assertEquals(user.getUserInfo().getPhotoUrl(), userInfo.getPhotoUrl());
        Assert.assertEquals(user.getUserInfo().getUid(), userInfo.getUid());
        Assert.assertEquals(user.getUserInfo().getProviderId(), userInfo.getProviderId());
        Assert.assertEquals(user.getUserInfo().getEmail(), userInfo.getEmail());
        Assert.assertEquals(user.getUserInfo().getDisplayName(), userInfo.getDisplayName());
        Assert.assertEquals(user.getUserInfo().isEmailVerified(), userInfo.isAnonymous());
        Assert.assertEquals(user.getUserInfo().isAnonymous(), userInfo.isAnonymous());
    }
}