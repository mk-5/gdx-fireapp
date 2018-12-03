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

import com.badlogic.gdx.utils.Json;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import mk.gdx.firebase.callbacks.TransactionCallback;

@PrepareForTest({JsonProcessor.class, Json.class, JsonDataModifier.class})
public class JsonDataModifierTest {

    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

    @Test
    @SuppressWarnings("unchecked")
    public void modify() throws Exception {
        // Given
        Class wantedType = String.class;
        String jsonData = "test";
        PowerMockito.mockStatic(JsonProcessor.class);
        PowerMockito.mockStatic(Json.class);
        TransactionCallback transactionCallback = Mockito.mock(TransactionCallback.class);
        Json json = PowerMockito.mock(Json.class);
        PowerMockito.whenNew(Json.class).withAnyArguments().thenReturn(json);
        JsonDataModifier<String> jsonDataModifier = new JsonDataModifier<String>(wantedType, transactionCallback);
        Mockito.when(transactionCallback.run(Mockito.any())).thenReturn("test_transaction_callback");
        Mockito.when(json.toJson(Mockito.any(), Mockito.any(Class.class))).thenReturn("test_json");

        // When
        Object result = jsonDataModifier.modify(jsonData);

        // Then
        Assert.assertEquals("test_json", result);
        Mockito.verify(json, VerificationModeFactory.times(1)).toJson(Mockito.eq("test_transaction_callback"), Mockito.eq(wantedType));
        PowerMockito.verifyStatic(JsonProcessor.class, VerificationModeFactory.times(1));
        JsonProcessor.process(Mockito.eq(String.class), Mockito.eq(jsonData));
        Mockito.verify(transactionCallback, VerificationModeFactory.times(1)).run(Mockito.isNull());
    }
}