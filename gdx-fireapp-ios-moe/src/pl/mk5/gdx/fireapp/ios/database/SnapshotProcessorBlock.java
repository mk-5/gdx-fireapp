package pl.mk5.gdx.fireapp.ios.database;


import com.badlogic.gdx.utils.reflect.ClassReflection;

import java.util.Collections;
import java.util.List;

import bindings.google.firebasedatabase.FIRDataSnapshot;
import bindings.google.firebasedatabase.FIRDatabaseQuery;
import pl.mk5.gdx.fireapp.GdxFIRLogger;
import pl.mk5.gdx.fireapp.database.OrderByClause;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

class SnapshotProcessorBlock implements FIRDatabaseQuery.Block_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_1,
        FIRDatabaseQuery.Block_observeEventTypeWithBlockWithCancelBlock_1 {

    private static final String GIVEN_DATABASE_PATH_RETURNED_NULL_VALUE = "Given database path returned null value";

    private final FuturePromise promise;
    private final OrderByClause orderByClause;
    private final Class wantedType;

    SnapshotProcessorBlock(FuturePromise promise, OrderByClause orderByClause, Class wantedType) {
        this.promise = promise;
        this.orderByClause = orderByClause;
        this.wantedType = wantedType;
    }

    @Override
    public void call_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_1(FIRDataSnapshot arg0, String arg1) {
        process(arg0, arg1);
    }

    @Override
    public void call_observeEventTypeWithBlockWithCancelBlock_1(FIRDataSnapshot arg0) {
        process(arg0, null);
    }

    @SuppressWarnings("unchecked")
    private void process(FIRDataSnapshot arg0, String arg1) {
        if (arg0.value() == null) {
            // TODO - consider about this fail
            promise.doFail(new Exception(GIVEN_DATABASE_PATH_RETURNED_NULL_VALUE));
        } else {
            Object data = arg0.value();
            try {
                if (ClassReflection.isAssignableFrom(List.class, wantedType)
                        && ResolverFIRDataSnapshotList.shouldResolveList(arg0)) {
                    GdxFIRLogger.log("Process list for " +
                            (data != null ? data.getClass() : "") + "" + data);
                    data = ResolverFIRDataSnapshotList.resolve(arg0);
                } else {
                    GdxFIRLogger.log("Process data for " +
                            (data != null ? data.getClass() : "") + "" + data);
                    data = DataProcessor.iosDataToJava(data, wantedType);
                }
                GdxFIRLogger.log("Processed data: " + data);
            } catch (Exception e) {
                promise.doFail(e);
                return;
            }
            if (data == null && ClassReflection.isAssignableFrom(List.class, wantedType)) {
                data = Collections.emptyList();
            }
            promise.doComplete(data);
        }
    }
}
