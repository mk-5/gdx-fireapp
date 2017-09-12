package mk.gdx.firebase.ios.helpers;

import com.badlogic.gdx.utils.Json;

import java.util.Map;

/**
 * Class which deserialize {@code Map<String, Object>} to POJO.
 */
public class MapDeserializator {

    /**
     * Transforms {@code Map<String,Object>} to {@code T object}.
     * <p>
     * Transformation flow is as follow:
     * <ul>
     * <li>Transform {@code map} to Json string by {@link Json#toJson(Object)}
     * <li>Transform Json string to POJO object by {@link Json#fromJson(Class, String)}
     * </ul>
     *
     * @param map        Map which we want to transform.
     * @param wantedType Class type we want to get
     * @param <T>        Generic type of class we want to get. Needed if type we want have nested generic type.
     * @return Deserialized object, may be null
     */
    public static <T> T deserialize(Map<String, Object> map, Class<T> wantedType)
    {
        try {
            String jsonString = new Json().toJson(map);
            return new Json().fromJson(wantedType, jsonString);
        } catch (Exception e) {
            return null;
        }
    }
}
