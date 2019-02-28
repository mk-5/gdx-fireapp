package pl.mk5.gdx.fireapp.ios.database;


import apple.foundation.NSError;
import bindings.google.firebasedatabase.FIRDatabaseQuery;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

class SnapshotCancelBlock implements FIRDatabaseQuery.Block_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_2,
        FIRDatabaseQuery.Block_observeEventTypeWithBlockWithCancelBlock_2 {

    private final FuturePromise promise;

    SnapshotCancelBlock(FuturePromise promise) {
        this.promise = promise;
    }

    @Override
    public void call_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_2(NSError arg0) {
        process(arg0);
    }

    @Override
    public void call_observeEventTypeWithBlockWithCancelBlock_2(NSError arg0) {
        process(arg0);
    }

    private void process(NSError arg0) {
        promise.doFail(new Exception(arg0.localizedDescription()));
    }
}
