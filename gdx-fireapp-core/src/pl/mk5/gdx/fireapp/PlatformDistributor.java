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

package pl.mk5.gdx.fireapp;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

import pl.mk5.gdx.fireapp.exceptions.PlatformDistributorException;
import pl.mk5.gdx.fireapp.promises.FuturePromise;
import pl.mk5.gdx.fireapp.promises.Promise;

/**
 * Provides clear and simple way to do platform specific code.
 * <p>
 * Using manual:
 * <p>
 * First of all you need to create an Interface that contains the methods you want to share between LibGDX modules (android, ios)<p>
 * then create {@code PlatformDistributor} which will be accessible from all modules.
 * <p>
 * {@code
 * class AndroidAndIosManager extends PlatformDistributor<YourInterface>{
 * }
 * }
 * <p>
 * Now you need to create implementation classes which implements YourInterface:
 * <p>
 * {@code
 * class MyAndroidLib implements YourInterface{}
 * class MyIOSLib implements YourInterface{}
 * }
 * <p>
 * The last step is to tell {@code PlatformDistributor} which classes should be used on each platform,<p>
 * you can do this by implementing {@link #getAndroidClassName()} and {@link #getIOSClassName()} methods.
 */
public abstract class PlatformDistributor<T> {

    protected T platformObject;

    /**
     * Creates platform specific object by reflection.
     * <p>
     * Uses class names given by {@link #getAndroidClassName()} and {@link #getIOSClassName()}
     * <p>
     * If you need to run project on different platform use {@link #setMockObject(Object)} to polyfill platform object.
     *
     * @throws PlatformDistributorException Throws when something is wrong with environment
     */
    @SuppressWarnings("unchecked")
    protected PlatformDistributor(Object... args) {
        String className = null;
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            className = getAndroidClassName();
        } else if (Gdx.app.getType() == Application.ApplicationType.iOS) {
            className = getIOSClassName();
        } else if (Gdx.app.getType() == Application.ApplicationType.WebGL) {
            className = getWebGLClassName();
        } else {
            return;
        }
        try {
            Class objClass = ClassReflection.forName(className);
            Class[] argumentsTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                argumentsTypes[i] = args[i].getClass();
            }
            platformObject = (T) ClassReflection.getConstructor(objClass, argumentsTypes).newInstance(args);
        } catch (ReflectionException e) {
            throw new PlatformDistributorException("Something wrong with environment", e);
        }
    }

    /**
     * Sets mock representation of platform distribution.
     * <p>
     * Useful when it is need to run application on not-supported platform.
     *
     * @param mockObject Mock representation of platform distribution object - {@code T}
     */
    public void setMockObject(T mockObject) {
        platformObject = mockObject;
    }

    /**
     * Returns empty promise.
     * <p>
     * This method allow you to chaining flow like this:
     * {@code
     * GdxFIRDatabase.promise()
     * .then(...)
     * .then(...)
     * .then(...)
     * }
     *
     * @param <R> The promise type
     * @return The empty promise, not null
     */
    public static <R> Promise<R> promise() {
        return FuturePromise.empty();
    }

    /**
     * Gives class name of object that will be create when application running on ios platform.
     *
     * @return Class name with package of the ios-moe module distribution object.
     */
    protected abstract String getIOSClassName();

    /**
     * Gives class name of object that will be create when application running on android platform.
     *
     * @return Class name with package of the android module distribution object.
     */
    protected abstract String getAndroidClassName();

    /**
     * Gives class name of object that will be create when application running on gwt platform.
     *
     * @return Class name with package of the gwt module distribution object.
     */
    protected abstract String getWebGLClassName();
}
