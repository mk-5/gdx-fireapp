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

package pl.mk5.gdx.fireapp.ios.auth;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.moe.natj.general.NatJ;
import org.moe.natj.general.ptr.Ptr;
import org.moe.natj.general.ptr.impl.PtrFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import apple.foundation.NSError;
import apple.foundation.NSURL;
import bindings.google.firebaseauth.FIRAuth;
import bindings.google.firebaseauth.FIRUser;
import pl.mk5.gdx.fireapp.functional.BiConsumer;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.ios.GdxIOSAppTest;

@PrepareForTest({NatJ.class, FIRAuth.class, FIRUser.class, NSURL.class, NSError.class, PtrFactory.class, Ptr.class})
public class UserTest extends GdxIOSAppTest {

    private FIRAuth firAuth;
    private FIRUser firUser;

    @Override
    public void setup() {
        super.setup();
        PowerMockito.mockStatic(FIRAuth.class);
        PowerMockito.mockStatic(FIRUser.class);
        PowerMockito.mockStatic(NSError.class);
        PowerMockito.mockStatic(NSURL.class);
        PowerMockito.mockStatic(FIRUser.class);
        firAuth = PowerMockito.mock(FIRAuth.class);
        firUser = PowerMockito.mock(FIRUser.class);
        Mockito.when(FIRAuth.auth()).thenReturn(firAuth);
    }

    @Test(expected = IllegalStateException.class)
    public void updateEmail_noCurrentUser() {
        // Given
        User user = new User();

        // When
        user.updateEmail(null);

        // Then
        Assert.fail();
    }

    @Test(expected = IllegalStateException.class)
    public void sendEmailVerification_noCurrentUser() {
        // Given
        User user = new User();

        // When
        user.sendEmailVerification();

        // Then
        Assert.fail();
    }

    @Test(expected = IllegalStateException.class)
    public void updatePassword_noCurrentUser() {
        // Given
        User user = new User();

        // When
        user.updatePassword(null);

        // Then
        Assert.fail();
    }

    @Test(expected = IllegalStateException.class)
    public void delete_noCurrentUser() {
        // Given
        User user = new User();

        // When
        user.delete();

        // Then
        Assert.fail();
    }

    @Test
    public void updateEmail() {
        // Given
        User user = new User();
        Consumer consumer = Mockito.mock(Consumer.class);
        Mockito.when(firAuth.currentUser()).thenReturn(firUser);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((FIRUser.Block_updateEmailCompletion) invocation.getArgument(1)).call_updateEmailCompletion(null);
                return null;
            }
        }).when(firUser).updateEmailCompletion(Mockito.anyString(), Mockito.any(FIRUser.Block_updateEmailCompletion.class));
        String arg1 = "email";

        // When
        user.updateEmail(arg1).then(consumer);

        // Then
        Mockito.verify(firUser, VerificationModeFactory.times(1)).updateEmailCompletion(Mockito.eq(arg1), Mockito.any());
        Mockito.verify(consumer, VerificationModeFactory.times(1)).accept(Mockito.isNull());
    }

    @Test
    public void updateEmail_fail() {
        // Given
        User user = new User();
        BiConsumer consumer = Mockito.mock(BiConsumer.class);
        Mockito.when(firAuth.currentUser()).thenReturn(firUser);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((FIRUser.Block_updateEmailCompletion) invocation.getArgument(1)).call_updateEmailCompletion(Mockito.mock(NSError.class));
                return null;
            }
        }).when(firUser).updateEmailCompletion(Mockito.anyString(), Mockito.any(FIRUser.Block_updateEmailCompletion.class));
        String arg1 = "email";

        // When
        user.updateEmail(arg1).fail(consumer);

        // Then
        Mockito.verify(firUser, VerificationModeFactory.times(1)).updateEmailCompletion(Mockito.eq(arg1), Mockito.any());
        Mockito.verify(consumer, VerificationModeFactory.times(1)).accept(Mockito.nullable(String.class), Mockito.any(Exception.class));
    }

    @Test
    public void sendEmailVerification() {
        // Given
        User user = new User();
        Consumer consumer = Mockito.mock(Consumer.class);
        Mockito.when(firAuth.currentUser()).thenReturn(firUser);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((FIRUser.Block_sendEmailVerificationWithCompletion) invocation.getArgument(0)).call_sendEmailVerificationWithCompletion(null);
                return null;
            }
        }).when(firUser).sendEmailVerificationWithCompletion(Mockito.any(FIRUser.Block_sendEmailVerificationWithCompletion.class));

        // When
        user.sendEmailVerification().then(consumer);

        // Then
        Mockito.verify(firUser, VerificationModeFactory.times(1)).sendEmailVerificationWithCompletion(Mockito.any());
        Mockito.verify(consumer, VerificationModeFactory.times(1)).accept(Mockito.isNull());
    }

    @Test
    public void sendEmailVerification_fail() {
        // Given
        User user = new User();
        BiConsumer consumer = Mockito.mock(BiConsumer.class);
        Mockito.when(firAuth.currentUser()).thenReturn(firUser);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((FIRUser.Block_sendEmailVerificationWithCompletion) invocation.getArgument(0))
                        .call_sendEmailVerificationWithCompletion(Mockito.mock(NSError.class));
                return null;
            }
        }).when(firUser).sendEmailVerificationWithCompletion(Mockito.any(FIRUser.Block_sendEmailVerificationWithCompletion.class));

        // When
        user.sendEmailVerification().fail(consumer);

        // Then
        Mockito.verify(firUser, VerificationModeFactory.times(1)).sendEmailVerificationWithCompletion(Mockito.any());
        Mockito.verify(consumer, VerificationModeFactory.times(1)).accept(Mockito.nullable(String.class), Mockito.any(Exception.class));
    }

    @Test
    public void updatePassword() {
        // Given
        User user = new User();
        Consumer consumer = Mockito.mock(Consumer.class);
        Mockito.when(firAuth.currentUser()).thenReturn(firUser);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((FIRUser.Block_updatePasswordCompletion) invocation.getArgument(1)).call_updatePasswordCompletion(null);
                return null;
            }
        }).when(firUser).updatePasswordCompletion(Mockito.anyString(), Mockito.any(FIRUser.Block_updatePasswordCompletion.class));
        char[] arg1 = {'p', 'a', 's', 's'};

        // When
        user.updatePassword(arg1).then(consumer);

        // Then
        Mockito.verify(firUser, VerificationModeFactory.times(1)).updatePasswordCompletion(Mockito.eq(new String(arg1)), Mockito.any());
        Mockito.verify(consumer, VerificationModeFactory.times(1)).accept(Mockito.isNull());
    }

    @Test
    public void updatePassword_fail() {
        // Given
        User user = new User();
        BiConsumer consumer = Mockito.mock(BiConsumer.class);
        Mockito.when(firAuth.currentUser()).thenReturn(firUser);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((FIRUser.Block_updatePasswordCompletion) invocation.getArgument(1))
                        .call_updatePasswordCompletion(Mockito.mock(NSError.class));
                return null;
            }
        }).when(firUser).updatePasswordCompletion(Mockito.anyString(), Mockito.any(FIRUser.Block_updatePasswordCompletion.class));
        char[] arg1 = {'p', 'a', 's', 's'};

        // When
        user.updatePassword(arg1).fail(consumer);

        // Then
        Mockito.verify(firUser, VerificationModeFactory.times(1)).updatePasswordCompletion(Mockito.eq(new String(arg1)), Mockito.any());
        Mockito.verify(consumer, VerificationModeFactory.times(1)).accept(Mockito.nullable(String.class), Mockito.any(Exception.class));
    }

    @Test
    public void delete() {
        // Given
        User user = new User();
        Consumer consumer = Mockito.mock(Consumer.class);
        Mockito.when(firAuth.currentUser()).thenReturn(firUser);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((FIRUser.Block_deleteWithCompletion) invocation.getArgument(0)).call_deleteWithCompletion(null);
                return null;
            }
        }).when(firUser).deleteWithCompletion(Mockito.any(FIRUser.Block_deleteWithCompletion.class));

        // When
        user.delete().then(consumer);

        // Then
        Mockito.verify(firUser, VerificationModeFactory.times(1)).deleteWithCompletion(Mockito.any());
        Mockito.verify(consumer, VerificationModeFactory.times(1)).accept(Mockito.isNull());
    }

    @Test
    public void delete_fail() {
        // Given
        User user = new User();
        BiConsumer consumer = Mockito.mock(BiConsumer.class);
        Mockito.when(firAuth.currentUser()).thenReturn(firUser);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((FIRUser.Block_deleteWithCompletion) invocation.getArgument(0))
                        .call_deleteWithCompletion(Mockito.mock(NSError.class));
                return null;
            }
        }).when(firUser).deleteWithCompletion(Mockito.any(FIRUser.Block_deleteWithCompletion.class));

        // When
        user.delete().fail(consumer);

        // Then
        Mockito.verify(firUser, VerificationModeFactory.times(1)).deleteWithCompletion(Mockito.any());
        Mockito.verify(consumer, VerificationModeFactory.times(1)).accept(Mockito.nullable(String.class), Mockito.any(Exception.class));
    }
}