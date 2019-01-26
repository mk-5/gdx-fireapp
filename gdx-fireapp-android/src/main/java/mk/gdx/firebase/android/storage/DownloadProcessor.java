/*
 * Copyright 2019 mk
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

package mk.gdx.firebase.android.storage;

import android.support.annotation.NonNull;

import com.badlogic.gdx.files.FileHandle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import mk.gdx.firebase.GdxFIRLogger;
import mk.gdx.firebase.promises.FuturePromise;

class DownloadProcessor {

    private static final String TEMP_FILE_ERROR = "Error while creating temporary file.";
    private static final String TARGET_FILE_IS_NULL = "Target file is null and we couldn't create temporary file.";

    void processDownload(FirebaseStorage firebaseStorage, String path, long bytesLimit, final FuturePromise<byte[]> promise) {
        StorageReference pathRef = firebaseStorage.getReference().child(path);
        pathRef.getBytes(bytesLimit).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                promise.doFail(e);
            }
        }).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                promise.doComplete(bytes);
            }
        });
    }

    void processDownload(FirebaseStorage firebaseStorage, String path, FileHandle targetFile, final FuturePromise<FileHandle> promise) {
        StorageReference pathRef = firebaseStorage.getReference().child(path);
        if (targetFile == null) {
            try {
                targetFile = new FileHandle(File.createTempFile(UUID.randomUUID().toString(), ""));
            } catch (IOException e) {
                GdxFIRLogger.error(TEMP_FILE_ERROR, e);
            }
        }
        if (targetFile == null)
            throw new IllegalArgumentException(TARGET_FILE_IS_NULL);
        final FileHandle finalTargetFile = targetFile;
        pathRef.getFile(targetFile.file()).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                promise.doFail(e);
            }
        }).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                promise.doComplete(finalTargetFile);
            }
        });
    }
}
