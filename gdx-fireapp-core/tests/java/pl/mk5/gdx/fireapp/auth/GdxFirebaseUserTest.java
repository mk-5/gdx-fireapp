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
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.powermock.reflect.Whitebox;

import pl.mk5.gdx.fireapp.GdxAppTest;

public class GdxFirebaseUserTest extends GdxAppTest {

    @Rule
    public final PowerMockRule powerMockRule = new PowerMockRule();

    @Test
    public void create() {
        // Given
        UserInfo userInfo = new UserInfo.Builder().build();

        // When
        GdxFirebaseUser user = GdxFirebaseUser.create(userInfo);

        // Then
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getUserInfo());
    }

    @Test
    public void getUserInfo() {
        // Given
        UserInfo userInfo = new UserInfo.Builder().setEmail("email").build();

        // When
        GdxFirebaseUser user = GdxFirebaseUser.create(userInfo);

        // Then
        Assert.assertNotNull(user.getUserInfo());
        Assert.assertNotEquals(user.getUserInfo(), userInfo);
        Assert.assertEquals(user.getUserInfo().getPhotoUrl(), userInfo.getPhotoUrl());
        Assert.assertEquals(user.getUserInfo().getUid(), userInfo.getUid());
        Assert.assertEquals(user.getUserInfo().getProviderId(), userInfo.getProviderId());
        Assert.assertEquals(user.getUserInfo().getEmail(), userInfo.getEmail());
        Assert.assertEquals(user.getUserInfo().getDisplayName(), userInfo.getDisplayName());
        Assert.assertEquals(user.getUserInfo().isEmailVerified(), userInfo.isAnonymous());
        Assert.assertEquals(user.getUserInfo().isAnonymous(), userInfo.isAnonymous());
    }

    @Test
    public void updateEmail() {
        // Given
        GdxFirebaseUser user = PowerMockito.spy(GdxFirebaseUser.create(new UserInfo.Builder().build()));
        String email = "email";
        GdxFIRUser userDistribution = Mockito.mock(GdxFIRUser.class);
        Whitebox.setInternalState(user, "userDistribution", userDistribution);

        // When
        user.updateEmail(email);

        // Then
        Mockito.verify(userDistribution, VerificationModeFactory.times(1)).updateEmail(Mockito.eq(email));
    }

    @Test
    public void sendEmailVerification() {
        // Given
        GdxFirebaseUser user = PowerMockito.spy(GdxFirebaseUser.create(new UserInfo.Builder().build()));
        GdxFIRUser userDistribution = Mockito.mock(GdxFIRUser.class);
        Whitebox.setInternalState(user, "userDistribution", userDistribution);

        // When
        user.sendEmailVerification();

        // Then
        Mockito.verify(userDistribution, VerificationModeFactory.times(1)).sendEmailVerification();
    }

    @Test
    public void updatePassword() {
        // Given
        GdxFirebaseUser user = PowerMockito.spy(GdxFirebaseUser.create(new UserInfo.Builder().build()));
        char[] password = {'a', 'b', 'c'};
        GdxFIRUser userDistribution = Mockito.mock(GdxFIRUser.class);
        Whitebox.setInternalState(user, "userDistribution", userDistribution);

        // When
        user.updatePassword(password);

        // Then
        Mockito.verify(userDistribution, VerificationModeFactory.times(1)).updatePassword(Mockito.refEq(password));
    }

    @Test
    public void delete() {
        // Given
        GdxFirebaseUser user = PowerMockito.spy(GdxFirebaseUser.create(new UserInfo.Builder().build()));
        GdxFIRUser userDistribution = Mockito.mock(GdxFIRUser.class);
        Whitebox.setInternalState(user, "userDistribution", userDistribution);

        // When
        user.delete();

        // Then
        Mockito.verify(userDistribution, VerificationModeFactory.times(1)).delete();
    }

    @Test
    public void reload() {
        // Given
        GdxFirebaseUser user = PowerMockito.spy(GdxFirebaseUser.create(new UserInfo.Builder().build()));
        GdxFIRUser userDistribution = Mockito.mock(GdxFIRUser.class);
        Whitebox.setInternalState(user, "userDistribution", userDistribution);

        // When
        user.reload();

        // Then
        Mockito.verify(userDistribution, VerificationModeFactory.times(1)).reload();
    }
}