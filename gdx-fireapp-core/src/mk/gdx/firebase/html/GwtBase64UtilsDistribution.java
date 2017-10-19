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

package mk.gdx.firebase.html;

/**
 * Provides access to GWT {@code Base64Utils} from core module.
 */
public interface GwtBase64UtilsDistribution
{
    /**
     * Decode a base64 string into a byte array.
     *
     * @param data the encoded data.
     * @return a byte array.
     * @see #fromBase64(String)
     */
    byte[] fromBase64(String data);

    /**
     * Decode a base64 string into a long value.
     */
    long longFromBase64(String value);

    /**
     * Converts a byte array into a base 64 encoded string. Null is encoded as
     * null, and an empty array is encoded as an empty string. Otherwise, the byte
     * data is read 3 bytes at a time, with bytes off the end of the array padded
     * with zeros. Each 24-bit chunk is encoded as 4 characters from the sequence
     * [A-Za-z0-9$_]. If one of the source positions consists entirely of padding
     * zeros, an '=' character is used instead.
     *
     * @param data a byte array, which may be null or empty
     * @return a String
     */
    String toBase64(byte[] data);

    /**
     * Return a string containing a base-64 encoded version of the given long
     * value.  Leading groups of all zero bits are omitted.
     */
    String toBase64(long value);
}
