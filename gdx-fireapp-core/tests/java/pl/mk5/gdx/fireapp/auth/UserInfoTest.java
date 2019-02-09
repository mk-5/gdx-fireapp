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

package pl.mk5.gdx.fireapp.auth;

import org.junit.Assert;
import org.junit.Test;

public class UserInfoTest {

    @Test
    public void test_builderReturnsValidInstance() {
        // Given
        UserInfo.Builder builder = new UserInfo.Builder();

        // When
        builder.setDisplayName("display_name");
        builder.setEmail("email");
        builder.setIsAnonymous(true);
        builder.setIsEmailVerified(true);
        builder.setProviderId("provider_id");
        builder.setUid("uid");
        builder.setPhotoUrl("photo_url");
        UserInfo userInfo = builder.build();

        // Then
        Assert.assertEquals("display_name", userInfo.getDisplayName());
        Assert.assertEquals("email", userInfo.getEmail());
        Assert.assertTrue(userInfo.isAnonymous());
        Assert.assertTrue(userInfo.isEmailVerified());
        Assert.assertEquals("provider_id", userInfo.getProviderId());
        Assert.assertEquals("uid", userInfo.getUid());
        Assert.assertEquals("photo_url", userInfo.getPhotoUrl());
    }

    @Test
    public void test_builderCreatesUserInfoFromOtherUserInfoAndReturnsValidInstance() {
        // Given
        UserInfo.Builder builder = new UserInfo.Builder();

        // When
        builder.setDisplayName("display_name");
        builder.setEmail("email");
        builder.setIsAnonymous(true);
        builder.setIsEmailVerified(true);
        builder.setProviderId("provider_id");
        builder.setUid("uid");
        builder.setPhotoUrl("photo_url");
        UserInfo sourceUserInfo = builder.build();
        UserInfo userInfo = new UserInfo.Builder().setUserInfo(sourceUserInfo).build();

        // Then
        Assert.assertEquals("display_name", userInfo.getDisplayName());
        Assert.assertEquals("email", userInfo.getEmail());
        Assert.assertTrue(userInfo.isAnonymous());
        Assert.assertTrue(userInfo.isEmailVerified());
        Assert.assertEquals("provider_id", userInfo.getProviderId());
        Assert.assertEquals("uid", userInfo.getUid());
        Assert.assertEquals("photo_url", userInfo.getPhotoUrl());
    }

}