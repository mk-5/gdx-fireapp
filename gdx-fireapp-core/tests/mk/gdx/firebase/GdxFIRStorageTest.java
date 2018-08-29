package mk.gdx.firebase;

import com.badlogic.gdx.files.FileHandle;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import java.io.File;

import mk.gdx.firebase.callbacks.DeleteCallback;
import mk.gdx.firebase.callbacks.DownloadCallback;
import mk.gdx.firebase.callbacks.UploadCallback;
import mk.gdx.firebase.distributions.StorageDistribution;

import static org.junit.Assert.assertNotNull;

public class GdxFIRStorageTest extends GdxAppTest {

    @Mock
    private StorageDistribution storageDistribution;

    @Override
    public void setup() {
        super.setup();
        GdxFIRStorage.instance().platformObject = storageDistribution;
    }

    @Test
    public void instance() throws Exception {
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
        DeleteCallback callback = Mockito.mock(DeleteCallback.class);

        // When
        GdxFIRStorage.instance().delete("firebase/file.data", callback);

        // Then
        Mockito.verify(storageDistribution, VerificationModeFactory.times(1)).delete(Mockito.eq("firebase/file.data"), Mockito.refEq(callback));
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