package mk.gdx.firebase;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import java.io.File;

import mk.gdx.firebase.callbacks.DownloadCallback;
import mk.gdx.firebase.callbacks.UploadCallback;
import mk.gdx.firebase.distributions.StorageDistribution;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.helpers.ImageHelper;

import static org.junit.Assert.assertNotNull;

@PrepareForTest({ImageHelper.class})
public class GdxFIRStorageTest extends GdxAppTest {

    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

    private StorageDistribution storageDistribution;

    @Override
    public void setup() {
        super.setup();
        storageDistribution = Mockito.mock(StorageDistribution.class);
        GdxFIRStorage.instance().platformObject = storageDistribution;
    }

    @Test
    public void instance() {
        assertNotNull(GdxFIRStorage.instance());
    }

    @Test
    public void upload() {
        // Given
        UploadCallback callback = Mockito.mock(UploadCallback.class);
        byte[] bytes = new byte[]{1, 1, 1, 1, 0, 0, 0};

        // When
        GdxFIRStorage.instance().upload(bytes, "firebase/file.data", callback);

        // Then
        Mockito.verify(storageDistribution, VerificationModeFactory.times(1)).upload(Mockito.eq(bytes), Mockito.eq("firebase/file.data"), Mockito.refEq(callback));
    }

    @Test
    public void upload1() {
        // Given
        UploadCallback callback = Mockito.mock(UploadCallback.class);
        FileHandle fileHandle = Mockito.mock(FileHandle.class);

        // When
        GdxFIRStorage.instance().upload(fileHandle, "firebase/file.data", callback);

        // Then
        Mockito.verify(storageDistribution, VerificationModeFactory.times(1)).upload(Mockito.refEq(fileHandle), Mockito.eq("firebase/file.data"), Mockito.refEq(callback));
    }

    @Test
    public void download() {
        // Given
        DownloadCallback callback = Mockito.mock(DownloadCallback.class);
        File file = Mockito.mock(File.class);

        // When
        GdxFIRStorage.instance().download("firebase/file.data", file, callback);

        // Then
        Mockito.verify(storageDistribution, VerificationModeFactory.times(1)).download(Mockito.eq("firebase/file.data"), Mockito.refEq(file), Mockito.refEq(callback));
    }

    @Test
    public void download1() {
        // Given
        DownloadCallback callback = Mockito.mock(DownloadCallback.class);

        // When
        GdxFIRStorage.instance().download("firebase/file.data", Long.MAX_VALUE, callback);

        // Then
        Mockito.verify(storageDistribution, VerificationModeFactory.times(1)).download(Mockito.eq("firebase/file.data"), Mockito.eq(Long.MAX_VALUE), Mockito.refEq(callback));
    }

    @Test
    public void delete() {
        // Given
        // When
        GdxFIRStorage.instance().delete("firebase/file.data");

        // Then
        Mockito.verify(storageDistribution, VerificationModeFactory.times(1)).delete(Mockito.eq("firebase/file.data"));
    }

    @Test
    public void inBucket() {
        // Given
        // When
        GdxFIRStorage.instance().inBucket("firebase/file.data");

        // Then
        Mockito.verify(storageDistribution, VerificationModeFactory.times(1)).inBucket(Mockito.eq("firebase/file.data"));
    }

    @Test
    public void downloadImage() throws Exception {
        // Given
        PowerMockito.mockStatic(ImageHelper.class);
        PowerMockito.when(ImageHelper.class, "createTextureFromBytes", Mockito.any()).thenReturn(Mockito.mock(TextureRegion.class));
        DownloadCallback callback = Mockito.mock(DownloadCallback.class);
        final byte[] byteData = {0, 0, 0, 0};
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((DownloadCallback) invocation.getArgument(2)).onSuccess(byteData);
                return null;
            }
        }).when(storageDistribution).download(Mockito.anyString(), Mockito.anyLong(), Mockito.any(DownloadCallback.class));
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((Runnable) invocation.getArgument(0)).run();
                return null;
            }
        }).when(Gdx.app).postRunnable(Mockito.any(Runnable.class));
        String testPath = "/test";

        // When
        GdxFIRStorage.instance().downloadImage(testPath, callback);

        // Then
        Mockito.verify(callback, VerificationModeFactory.times(1)).onSuccess(Mockito.any(TextureRegion.class));
    }

    @Test
    public void downloadImage_webGl() throws Exception {
        // Given
        PowerMockito.mockStatic(ImageHelper.class);
        PowerMockito.when(ImageHelper.class, "createTextureFromBytes", Mockito.any()).thenReturn(Mockito.mock(TextureRegion.class));
        PowerMockito.when(ImageHelper.class, "createTextureFromBytes", Mockito.any(), Mockito.any(Consumer.class)).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((Consumer) invocation.getArgument(1)).accept(Mockito.mock(TextureRegion.class));
                return null;
            }
        });
        Mockito.when(Gdx.app.getType()).thenReturn(Application.ApplicationType.WebGL);
        DownloadCallback callback = Mockito.mock(DownloadCallback.class);
        final byte[] byteData = {0, 0, 0, 0};
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((DownloadCallback) invocation.getArgument(2)).onSuccess(byteData);
                return null;
            }
        }).when(storageDistribution).download(Mockito.anyString(), Mockito.anyLong(), Mockito.any(DownloadCallback.class));
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((Runnable) invocation.getArgument(0)).run();
                return null;
            }
        }).when(Gdx.app).postRunnable(Mockito.any(Runnable.class));
        String testPath = "/test";

        // When
        GdxFIRStorage.instance().downloadImage(testPath, callback);

        // Then
        Mockito.verify(callback, VerificationModeFactory.times(1)).onSuccess(Mockito.any(TextureRegion.class));
    }

    @Test
    public void getIOSClassName() {
        Assert.assertEquals("mk.gdx.firebase.ios.storage.Storage", GdxFIRStorage.instance().getIOSClassName());
    }

    @Test
    public void getAndroidClassName() {
        Assert.assertEquals("mk.gdx.firebase.android.storage.Storage", GdxFIRStorage.instance().getAndroidClassName());
    }

    @Test
    public void getWebGLClassName() {
        Assert.assertEquals("mk.gdx.firebase.html.storage.Storage", GdxFIRStorage.instance().getWebGLClassName());
    }

}