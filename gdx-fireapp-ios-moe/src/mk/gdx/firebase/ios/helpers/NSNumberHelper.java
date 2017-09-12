package mk.gdx.firebase.ios.helpers;

import apple.foundation.NSNumber;

/**
 * Transforms {@code NSNumber} to java {@code Number} equivalent.
 */
public class NSNumberHelper {

    /**
     * Transforms {@code NSNumber} to java {@code Number} equivalent.
     * <p>
     * Possible values are instances of {@link Number}
     *
     * @param nsNumber {@code NSNumber} that you want to transform.
     * @return Transformed value, default value is {@link NSNumber#integerValue()}
     */
    public static Object getNSNumberPrimitive(NSNumber nsNumber)
    {
        String cType = nsNumber.objCType();
        switch (cType) {
            case "c":
                return nsNumber.boolValue();
            case "i":
                return nsNumber.integerValue();
            case "q":
                return nsNumber.longValue();
            case "f":
                return nsNumber.floatValue();
            case "d":
                return nsNumber.doubleValue();
            default:
                return nsNumber.integerValue();
        }
    }
}
