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

package pl.mk5.gdx.fireapp

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.g2d.TextureRegion
import pl.mk5.gdx.fireapp.GdxFIRStorage
import pl.mk5.gdx.fireapp.TextureRegionDownloader
import pl.mk5.gdx.fireapp.distributions.StorageDistribution
import pl.mk5.gdx.fireapp.functional.Consumer
import pl.mk5.gdx.fireapp.helpers.ImageHelper
import pl.mk5.gdx.fireapp.promises.FuturePromise
import spock.lang.Specification

import java.lang.reflect.Field
import java.lang.reflect.Modifier

class GdxFIRStorageTest extends Specification {

    def application = Mock(Application)
    def storageDistribution = Mock(StorageDistribution)

    void setup() {
        Gdx.app = application
        application.postRunnable(_) >> { args -> (args[0] as Runnable).run() }
    }

    def "should create instance"() {
        when:
        def gdxFIRStorage = GdxFIRStorage.instance()

        then:
        null != gdxFIRStorage
    }

    def "should call platform distribution"() {
        given:
        def gdxFIRStorage = GdxFIRStorage.instance()
        gdxFIRStorage.platformObject = storageDistribution

        when:
        gdxFIRStorage.delete(arg1)
        gdxFIRStorage.upload(arg1, arg3)
        gdxFIRStorage.upload(arg1, arg2)
        gdxFIRStorage.download(arg1, arg4)
        gdxFIRStorage.download(arg1, arg2)
        gdxFIRStorage.inBucket(arg1)

        then:
        1 * storageDistribution.upload(arg1, arg3)
        1 * storageDistribution.upload(arg1, arg2)
        1 * storageDistribution.delete(arg1)
        1 * storageDistribution.download(arg1, arg4)
        1 * storageDistribution.download(arg1, arg2)
        1 * storageDistribution.inBucket(arg1)

        where:
        arg1  | arg2             | arg3        | arg4
        "abc" | Mock(FileHandle) | new byte[0] | 5L
    }

    def "should create valid platform objects"() {
        when:
        def ios = GdxFIRStorage.instance().getIOSClassName()
        def android = GdxFIRStorage.instance().getAndroidClassName()
        def webgl = GdxFIRStorage.instance().getWebGLClassName()

        then:
        "pl.mk5.gdx.fireapp.ios.storage.Storage" == ios
        "pl.mk5.gdx.fireapp.android.storage.Storage" == android
        "pl.mk5.gdx.fireapp.html.storage.Storage" == webgl
    }

    def "should download image as TextureRegion"() {
        def field = TextureRegionDownloader.class.getDeclaredField("imageHelper")
        field.setAccessible(true)
        Field modifiersField = Field.class.getDeclaredField("modifiers")
        modifiersField.setAccessible(true)
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL)

        given:
        def gdxFIRStorage = GdxFIRStorage.instance()
        def storageDistribution = Mock(StorageDistribution)
        def consumer = Spy(new Consumer<TextureRegion>() {
            @Override
            void accept(TextureRegion textureRegion) {

            }
        })
        def textureRegion = Mock(TextureRegion)
        def downloadBytesPromise = Spy(FuturePromise)
        def path = "test"
        def imageHelper = Mock(ImageHelper)
        imageHelper.createTextureFromBytes(_ as byte[]) >> textureRegion
        gdxFIRStorage.platformObject = storageDistribution
        storageDistribution.download(_ as String, Long.MAX_VALUE) >> downloadBytesPromise
        downloadBytesPromise.then(_ as Consumer) >> {
            args -> (args[0] as Consumer).accept(new byte[0]); return downloadBytesPromise
        }
        field.set(null, imageHelper)

        when:
        gdxFIRStorage.downloadImage(path).then(consumer)

        then:
        1 * imageHelper.createTextureFromBytes(_)
        1 * consumer.accept(_)
    }

    def "should download image as TextureRegion on WebGL"() {
        def field = TextureRegionDownloader.class.getDeclaredField("imageHelper")
        field.setAccessible(true)
        Field modifiersField = Field.class.getDeclaredField("modifiers")
        modifiersField.setAccessible(true)
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL)

        given:
        def gdxFIRStorage = GdxFIRStorage.instance()
        def storageDistribution = Mock(StorageDistribution)
        def consumer = Spy(new Consumer<TextureRegion>() {
            @Override
            void accept(TextureRegion textureRegion) {

            }
        })
        def textureRegion = Mock(TextureRegion)
        def downloadBytesPromise = Spy(FuturePromise)
        def path = "test"
        def imageHelper = Mock(ImageHelper)
        application.type >> Application.ApplicationType.WebGL
        imageHelper.createTextureFromBytes(_ as byte[], _ as Consumer) >> {
            args -> (args[1] as Consumer).accept(textureRegion)
        }
        gdxFIRStorage.platformObject = storageDistribution
        storageDistribution.download(_ as String, Long.MAX_VALUE) >> downloadBytesPromise
        downloadBytesPromise.then(_ as Consumer) >> {
            args -> (args[0] as Consumer).accept(new byte[0]); return downloadBytesPromise
        }
        field.set(null, imageHelper)

        when:
        gdxFIRStorage.downloadImage(path).then(consumer)

        then:
        1 * imageHelper.createTextureFromBytes(_, _ as Consumer)
//        1 * consumer.accept(_)
    }
}
