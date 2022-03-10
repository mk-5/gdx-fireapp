package pl.mk5.gdx.fireapp.android.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import pl.mk5.gdx.fireapp.distributions.DatabaseDistribution;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.promises.FuturePromise;
import pl.mk5.gdx.fireapp.promises.Promise;

public class OnDisconnect implements DatabaseDistribution.OnDisconnect {
    private final com.google.firebase.database.OnDisconnect onDisconnect;

    public OnDisconnect(com.google.firebase.database.OnDisconnect onDisconnect) {
        this.onDisconnect = onDisconnect;
    }

    @Override
    public Promise<Void> removeValue() {
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(final FuturePromise<Void> voidFuturePromise) {
                onDisconnect.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error != null) {
                            voidFuturePromise.doFail(error.getMessage(), error.toException());
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
                onDisconnect.cancel(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error != null) {
                            voidFuturePromise.doFail(error.getMessage(), error.toException());
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
                onDisconnect.setValue(value, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error != null) {
                            voidFuturePromise.doFail(error.getMessage(), error.toException());
                        } else {
                            voidFuturePromise.doComplete(null);
                        }
                    }
                });
            }
        });
    }
}
