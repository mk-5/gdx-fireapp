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

package mk.gdx.firebase.html.database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import java.util.ArrayList;

import mk.gdx.firebase.database.FilterType;


@PrepareForTest({DatabaseReference.class})
public class ResolverDatabaseReferenceFilterTest {

    private DatabaseReference databaseReference;

    @Rule
    public final PowerMockRule powerMockRule = new PowerMockRule();

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(DatabaseReference.class);
        databaseReference = PowerMockito.mock(DatabaseReference.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void resolve_withoutArguments() {
        // Given
        ResolverDatabaseReferenceFilter filterResolver = new ResolverDatabaseReferenceFilter();

        // When
        filterResolver.resolve(FilterType.LIMIT_FIRST, databaseReference);

        // Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void resolve_limitFirst_wrongArgument() {
        // Given
        ResolverDatabaseReferenceFilter filterResolver = new ResolverDatabaseReferenceFilter();

        // When
        filterResolver.resolve(FilterType.LIMIT_FIRST, databaseReference, "wrong_argument");

        // Then
        Assert.fail();
    }

    @Test
    public void resolve_limitFirst() {
        // Given
        ResolverDatabaseReferenceFilter filterResolver = new ResolverDatabaseReferenceFilter();
        Integer arg = 2;

        // When
        filterResolver.resolve(FilterType.LIMIT_FIRST, databaseReference, arg);

        // Then
        Mockito.verify(databaseReference, VerificationModeFactory.times(1)).limitToFirst(Mockito.eq(arg.doubleValue()));
    }


    @Test(expected = IllegalArgumentException.class)
    public void resolve_limitLast_wrongArgument() {
        // Given
        ResolverDatabaseReferenceFilter filterResolver = new ResolverDatabaseReferenceFilter();

        // When
        filterResolver.resolve(FilterType.LIMIT_LAST, databaseReference, "wrong_argument");

        // Then
        Assert.fail();
    }

    @Test
    public void resolve_limitLast() {
        // Given
        ResolverDatabaseReferenceFilter filterResolver = new ResolverDatabaseReferenceFilter();
        Integer arg = 2;

        // When
        filterResolver.resolve(FilterType.LIMIT_LAST, databaseReference, arg);

        // Then
        Mockito.verify(databaseReference, VerificationModeFactory.times(1)).limitToLast(Mockito.eq(arg.doubleValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void resolve_startAt_wrongArgument() {
        // Given
        ResolverDatabaseReferenceFilter filterResolver = new ResolverDatabaseReferenceFilter();

        // When
        filterResolver.resolve(FilterType.START_AT, databaseReference, new ArrayList<>());

        // Then
        Assert.fail();
    }

    @Test
    public void resolve_startAt_number() {
        // Given
        ResolverDatabaseReferenceFilter filterResolver = new ResolverDatabaseReferenceFilter();
        Integer arg = 2;

        // When
        filterResolver.resolve(FilterType.START_AT, databaseReference, arg);

        // Then
        Mockito.verify(databaseReference, VerificationModeFactory.times(1)).startAt(Mockito.eq(arg.doubleValue()));
    }

    @Test
    public void resolve_startAt_boolean() {
        // Given
        ResolverDatabaseReferenceFilter filterResolver = new ResolverDatabaseReferenceFilter();
        Boolean arg = true;

        // When
        filterResolver.resolve(FilterType.START_AT, databaseReference, arg);

        // Then
        Mockito.verify(databaseReference, VerificationModeFactory.times(1)).startAt(Mockito.eq(arg));
    }

    @Test
    public void resolve_startAt_string() {
        // Given
        ResolverDatabaseReferenceFilter filterResolver = new ResolverDatabaseReferenceFilter();
        String arg = "test";

        // When
        filterResolver.resolve(FilterType.START_AT, databaseReference, arg);

        // Then
        Mockito.verify(databaseReference, VerificationModeFactory.times(1)).startAt(Mockito.eq(arg));
    }

    @Test(expected = IllegalArgumentException.class)
    public void resolve_endAt_wrongArgument() {
        // Given
        ResolverDatabaseReferenceFilter filterResolver = new ResolverDatabaseReferenceFilter();

        // When
        filterResolver.resolve(FilterType.END_AT, databaseReference, new ArrayList<>());

        // Then
        Assert.fail();
    }

    @Test
    public void resolve_endAt_number() {
        // Given
        ResolverDatabaseReferenceFilter filterResolver = new ResolverDatabaseReferenceFilter();
        Integer arg = 2;

        // When
        filterResolver.resolve(FilterType.END_AT, databaseReference, arg);

        // Then
        Mockito.verify(databaseReference, VerificationModeFactory.times(1)).endAt(Mockito.eq(arg.doubleValue()));
    }

    @Test
    public void resolve_endAt_boolean() {
        // Given
        ResolverDatabaseReferenceFilter filterResolver = new ResolverDatabaseReferenceFilter();
        Boolean arg = true;

        // When
        filterResolver.resolve(FilterType.END_AT, databaseReference, arg);

        // Then
        Mockito.verify(databaseReference, VerificationModeFactory.times(1)).endAt(Mockito.eq(arg));
    }

    @Test
    public void resolve_endAt_string() {
        // Given
        ResolverDatabaseReferenceFilter filterResolver = new ResolverDatabaseReferenceFilter();
        String arg = "test";

        // When
        filterResolver.resolve(FilterType.END_AT, databaseReference, arg);

        // Then
        Mockito.verify(databaseReference, VerificationModeFactory.times(1)).endAt(Mockito.eq(arg));
    }

    @Test(expected = IllegalArgumentException.class)
    public void resolve_equalTo_wrongArgument() {
        // Given
        ResolverDatabaseReferenceFilter filterResolver = new ResolverDatabaseReferenceFilter();

        // When
        filterResolver.resolve(FilterType.EQUAL_TO, databaseReference, new ArrayList<>());

        // Then
        Assert.fail();
    }

    @Test
    public void resolve_equalTo_number() {
        // Given
        ResolverDatabaseReferenceFilter filterResolver = new ResolverDatabaseReferenceFilter();
        Integer arg = 2;

        // When
        filterResolver.resolve(FilterType.EQUAL_TO, databaseReference, arg);

        // Then
        Mockito.verify(databaseReference, VerificationModeFactory.times(1)).equalTo(Mockito.eq(arg.doubleValue()));
    }

    @Test
    public void resolve_equalTo_boolean() {
        // Given
        ResolverDatabaseReferenceFilter filterResolver = new ResolverDatabaseReferenceFilter();
        Boolean arg = true;

        // When
        filterResolver.resolve(FilterType.EQUAL_TO, databaseReference, arg);

        // Then
        Mockito.verify(databaseReference, VerificationModeFactory.times(1)).equalTo(Mockito.eq(arg));
    }

    @Test
    public void resolve_equalTo_string() {
        // Given
        ResolverDatabaseReferenceFilter filterResolver = new ResolverDatabaseReferenceFilter();
        String arg = "test";

        // When
        filterResolver.resolve(FilterType.EQUAL_TO, databaseReference, arg);

        // Then
        Mockito.verify(databaseReference, VerificationModeFactory.times(1)).equalTo(Mockito.eq(arg));
    }
}