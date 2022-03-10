package pl.mk5.gdx.fireapp.ios.database;

import org.robovm.apple.foundation.NSError;
import org.robovm.objc.block.VoidBlock2;
import org.robovm.pods.firebase.database.FIRDatabaseReference;

import pl.mk5.gdx.fireapp.distributions.DatabaseDistribution;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.promises.FuturePromise;
import pl.mk5.gdx.fireapp.promises.Promise;

public class OnDisconnect implements DatabaseDistribution.OnDisconnect {
    private final FIRDatabaseReference reference;

    public OnDisconnect(FIRDatabaseReference reference) {
        this.reference = reference;
    }

    @Override
    public Promise<Void> removeValue() {
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(final FuturePromise<Void> voidFuturePromise) {
                reference.onDisconnectRemoveValue(new VoidBlock2<NSError, FIRDatabaseReference>() {
                    @Override
                    public void invoke(NSError nsError, FIRDatabaseReference firDatabaseReference) {
                        if (nsError != null) {
                            voidFuturePromise.doFail(nsError.getLocalizedFailureReason(), new Exception(nsError.getLocalizedFailureReason()));
                        } else {
                            voidFuturePromise.doComplete(null);
                        }
                    }
                });
            }
        });
    }

    @Override
    public Promise<Void> cancel() {
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(final FuturePromise<Void> voidFuturePromise) {
                reference.cancelDisconnectOperations(new VoidBlock2<NSError, FIRDatabaseReference>() {
                    @Override
                    public void invoke(NSError nsError, FIRDatabaseReference firDatabaseReference) {
                        if (nsError != null) {
                            voidFuturePromise.doFail(nsError.getLocalizedFailureReason(), new Exception(nsError.getLocalizedFailureReason()));
                        } else {
                            voidFuturePromise.doComplete(null);
                        }
                    }
                });
            }
        });
    }

    @Override
    public Promise<Void> setValue(final Object value) {
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(final FuturePromise<Void> voidFuturePromise) {
                reference.onDisconnectSetValue(DataProcessor.javaDataToIos(value), new VoidBlock2<NSError, FIRDatabaseReference>() {
                    @Override
                    public void invoke(NSError nsError, FIRDatabaseReference firDatabaseReference) {
                        if (nsError != null) {
                            voidFuturePromise.doFail(nsError.getLocalizedFailureReason(), new Exception(nsError.getLocalizedFailureReason()));
                        } else {
                            voidFuturePromise.doComplete(null);
                        }
                    }
                });
            }
        });
    }
}
