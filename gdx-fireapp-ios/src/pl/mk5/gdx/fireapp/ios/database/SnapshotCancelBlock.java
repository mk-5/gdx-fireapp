package pl.mk5.gdx.fireapp.ios.database;


import org.robovm.apple.foundation.NSError;
import org.robovm.objc.block.VoidBlock1;

import pl.mk5.gdx.fireapp.promises.FuturePromise;

class SnapshotCancelBlock implements VoidBlock1<NSError> {

    private final FuturePromise promise;

    SnapshotCancelBlock(FuturePromise promise) {
        this.promise = promise;
    }

    @Override
    public void invoke(NSError arg0) {
        promise.doFail(new Exception(arg0.getLocalizedDescription()));
    }
}
