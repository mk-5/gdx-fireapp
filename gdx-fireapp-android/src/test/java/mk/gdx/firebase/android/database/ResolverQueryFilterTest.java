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

package mk.gdx.firebase.android.database;

import com.badlogic.gdx.utils.GdxNativesLoader;
import com.google.firebase.database.Query;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import mk.gdx.firebase.android.AndroidContextTest;
import mk.gdx.firebase.database.FilterType;

@PrepareForTest({GdxNativesLoader.class, Query.class})
public class ResolverQueryFilterTest extends AndroidContextTest {

    private Query query;

    @Override
    public void setup() throws Exception {
        super.setup();
        PowerMockito.mockStatic(Query.class);
        query = PowerMockito.mock(Query.class);
    }

    @Test
    public void resolve_limitFirst() {
        // Given
        ResolverQueryFilter resolver = new ResolverQueryFilter();

        // When
        resolver.resolve(FilterType.LIMIT_FIRST, query, 2);

        // Then
        Mockito.verify(query, VerificationModeFactory.times(1)).limitToFirst(Mockito.anyInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void resolve_limitFirstWrongArgument() {
        // Given
        ResolverQueryFilter resolver = new ResolverQueryFilter();

        // When
        resolver.resolve(FilterType.LIMIT_FIRST, query, "test");

        // Then
        Assert.fail();
    }

    @Test
    public void resolve_endAt() {
        // Given
        ResolverQueryFilter resolver = new ResolverQueryFilter();

        // When
        resolver.resolve(FilterType.END_AT, query, "test");

        // Then
        Mockito.verify(query, VerificationModeFactory.times(1)).endAt(Mockito.eq("test"));
    }

    @Test
    public void resolve_endAt2() {
        // Given
        ResolverQueryFilter resolver = new ResolverQueryFilter();

        // When
        resolver.resolve(FilterType.END_AT, query, 2.0);

        // Then
        Mockito.verify(query, VerificationModeFactory.times(1)).endAt(Mockito.any(Double.class));
    }

    @Test
    public void resolve_endAt3() {
        // Given
        ResolverQueryFilter resolver = new ResolverQueryFilter();

        // When
        resolver.resolve(FilterType.END_AT, query, true);

        // Then
        Mockito.verify(query, VerificationModeFactory.times(1)).endAt(Mockito.anyBoolean());
    }

    @Test(expected = IllegalArgumentException.class)
    public void resolve_endAtWrongArgument() {
        // Given
        ResolverQueryFilter resolver = new ResolverQueryFilter();

        // When
        resolver.resolve(FilterType.END_AT, query, 2);

        // Then
        Assert.fail();
    }

    @Test
    public void resolve_equalTo() {
        // Given
        ResolverQueryFilter resolver = new ResolverQueryFilter();

        // When
        resolver.resolve(FilterType.EQUAL_TO, query, "test");

        // Then
        Mockito.verify(query, VerificationModeFactory.times(1)).equalTo(Mockito.eq("test"));
    }

    @Test
    public void resolve_equalTo2() {
        // Given
        ResolverQueryFilter resolver = new ResolverQueryFilter();

        // When
        resolver.resolve(FilterType.EQUAL_TO, query, 10.0);

        // Then
        Mockito.verify(query, VerificationModeFactory.times(1)).equalTo(Mockito.anyDouble());
    }

    @Test
    public void resolve_equalTo3() {
        // Given
        ResolverQueryFilter resolver = new ResolverQueryFilter();

        // When
        resolver.resolve(FilterType.EQUAL_TO, query, true);

        // Then
        Mockito.verify(query, VerificationModeFactory.times(1)).equalTo(Mockito.anyBoolean());
    }

    @Test(expected = IllegalArgumentException.class)
    public void resolve_equalToWrongArgument() {
        // Given
        ResolverQueryFilter resolver = new ResolverQueryFilter();

        // When
        resolver.resolve(FilterType.EQUAL_TO, query, 10f);

        // Then
        Assert.fail();
    }

    @Test
    public void resolve_limitLast() {
        // Given
        ResolverQueryFilter resolver = new ResolverQueryFilter();

        // When
        resolver.resolve(FilterType.LIMIT_LAST, query, 2);

        // Then
        Mockito.verify(query, VerificationModeFactory.times(1)).limitToLast(Mockito.anyInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void resolve_limitLastWrongArgument() {
        // Given
        ResolverQueryFilter resolver = new ResolverQueryFilter();

        // When
        resolver.resolve(FilterType.LIMIT_LAST, query, "test");

        // Then
        Assert.fail();
    }

    @Test
    public void resolve_startAt() {
        // Given
        ResolverQueryFilter resolver = new ResolverQueryFilter();

        // When
        resolver.resolve(FilterType.START_AT, query, "test");

        // Then
        Mockito.verify(query, VerificationModeFactory.times(1)).startAt(Mockito.eq("test"));
    }

    @Test
    public void resolve_startAt2() {
        // Given
        ResolverQueryFilter resolver = new ResolverQueryFilter();

        // When
        resolver.resolve(FilterType.START_AT, query, 10.0);

        // Then
        Mockito.verify(query, VerificationModeFactory.times(1)).startAt(Mockito.anyDouble());
    }

    @Test
    public void resolve_startAt3() {
        // Given
        ResolverQueryFilter resolver = new ResolverQueryFilter();

        // When
        resolver.resolve(FilterType.START_AT, query, true);

        // Then
        Mockito.verify(query, VerificationModeFactory.times(1)).startAt(Mockito.anyBoolean());
    }

    @Test(expected = IllegalArgumentException.class)
    public void resolve_startAtWrongArgument() {
        // Given
        ResolverQueryFilter resolver = new ResolverQueryFilter();

        // When
        resolver.resolve(FilterType.START_AT, query, 10f);

        // Then
        Assert.fail();
    }
}