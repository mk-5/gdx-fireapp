package mk.gdx.firebase.ios.helpers;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import libcore.reflect.ParameterizedTypeImpl;

/**
 * Is a class responsible for keeping generic type of interface with generic type.
 * <p>
 * Usage:
 * {@code
 * SomeInterface<String> si = new SomeInterface<>(){};
 * GenericPlaceholder gp = new GenericPlaceholder(si.getClass());
 * }
 * <p>
 * With the help of this class you can pass GenericType through multiple methods and still be able to get generic type {@link Class}.
 */
public class GenericPlaceholder {
    private Type genericType;

    /**
     * GenericPlaceholder constructor with {@link Class} of interface as argument.
     * <p>
     * {@link Type} of given interface generic type will be get here. If some error occur
     * when it is getting then {@link Exception#printStackTrace()} will be call.
     *
     * @param interfaceWithGenericClass {@link Class} of interface with some generic type. Every {@link Class} pass here should declare generic type
     */
    public GenericPlaceholder(Class<?> interfaceWithGenericClass) {
        try {
            genericType = ((ParameterizedType) (interfaceWithGenericClass.getGenericInterfaces()[0])).getActualTypeArguments()[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Generic type {@link Class}.  If {@link #genericType} do not has it {@code null} will be returned.
     */
    public Class<?> getGenericType() {
        try {
            return ((ParameterizedTypeImpl) genericType).getRawType();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @return Generic type of {@link #getGenericType()} {@link Class}. If {@link #genericType} do not has it {@code null} will be returned.
     */
    public Class<?> getGenericGenericType() {
        try {
            return (Class<?>) ((ParameterizedTypeImpl) genericType).getActualTypeArguments()[0];
        } catch (Exception e) {
            return null;
        }
    }


}
