package mk.gdx.firebase;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.reflect.Whitebox;

import java.util.HashMap;
import java.util.Map;

import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.DataCallback;
import mk.gdx.firebase.callbacks.TransactionCallback;
import mk.gdx.firebase.database.FilterType;
import mk.gdx.firebase.database.OrderByMode;
import mk.gdx.firebase.deserialization.FirebaseMapConverter;
import mk.gdx.firebase.distributions.DatabaseDistribution;
import mk.gdx.firebase.listeners.ConnectedListener;
import mk.gdx.firebase.listeners.DataChangeListener;

import static org.junit.Assert.assertNotNull;


public class GdxFIRDatabaseTest extends GdxAppTest {

    @Mock
    private DatabaseDistribution databaseDistribution;

    @Override
    public void setup() {
        super.setup();
        GdxFIRDatabase.instance().platformObject = databaseDistribution;
    }

    @Test
    public void instance() throws Exception {
        assertNotNull(GdxFIRDatabase.instance());
    }

    @Test
    public void onConnect() {
        // Given
        ConnectedListener connectedListener = Mockito.mock(ConnectedListener.class);

        // When
        GdxFIRDatabase.instance().onConnect(connectedListener);

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).onConnect(Mockito.refEq(connectedListener));
    }

    @Test
    public void inReference() {
        // Given
        // When
        GdxFIRDatabase.instance().inReference("test");

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).inReference(Mockito.eq("test"));
    }

    @Test
    public void setValue() {
        // Given
        // When
        GdxFIRDatabase.instance().setValue("test");

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).setValue(Mockito.eq("test"));
    }

    @Test
    public void setValue1() {
        // Given
        // When
        GdxFIRDatabase.instance().setValue("test");

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).setValue(Mockito.eq("test"));
    }

    @Test
    public void readValue() {
        // Given
        // When
        GdxFIRDatabase.instance().readValue(String.class, Mockito.mock(DataCallback.class));

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).readValue(Mockito.eq(String.class), Mockito.any(DataCallback.class));
    }

    @Test
    public void readValue2() {
        // Given
        // When
        GdxFIRDatabase.instance().readValue(GdxFIRDatabaseTest.class, Mockito.mock(DataCallback.class));

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).readValue(Mockito.eq(Map.class), Mockito.any(DataCallback.class));
    }

    @Test
    public void onDataChange() {
        // Given
        // When
        GdxFIRDatabase.instance().onDataChange(String.class, Mockito.mock(DataChangeListener.class));

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).onDataChange(Mockito.eq(String.class), Mockito.any(DataChangeListener.class));
    }

    @Test
    public void onDataChange2() {
        // Given
        // When
        GdxFIRDatabase.instance().onDataChange(GdxFIRDatabaseTest.class, Mockito.mock(DataChangeListener.class));

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).onDataChange(Mockito.eq(Map.class), Mockito.any(DataChangeListener.class));
    }

    @Test
    public void onDataChange_withNull() {
        // Given
        // When
        GdxFIRDatabase.instance().onDataChange(Map.class, null);

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).onDataChange(Mockito.eq(Map.class), (DataChangeListener<Map>) Mockito.isNull());
    }

    @Test
    public void filter() {
        // Given
        // When
        GdxFIRDatabase.instance().filter(FilterType.EQUAL_TO, 2);
        GdxFIRDatabase.instance().filter(FilterType.LIMIT_FIRST, 3);

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).filter(Mockito.eq(FilterType.EQUAL_TO), Mockito.eq(2));
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).filter(Mockito.eq(FilterType.LIMIT_FIRST), Mockito.eq(3));
    }

    @Test
    public void orderBy() {
        // Given
        // When
        GdxFIRDatabase.instance().orderBy(OrderByMode.ORDER_BY_CHILD, "a");
        GdxFIRDatabase.instance().orderBy(OrderByMode.ORDER_BY_KEY, null);

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).orderBy(Mockito.eq(OrderByMode.ORDER_BY_CHILD), Mockito.eq("a"));
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).orderBy(Mockito.eq(OrderByMode.ORDER_BY_KEY), (String) Mockito.eq(null));
    }

    @Test
    public void push() {
        // Given
        // When
        GdxFIRDatabase.instance().push();

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).push();
    }

    @Test
    public void removeValue() {
        // Given
        // When
        GdxFIRDatabase.instance().removeValue();

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).removeValue();
    }

    @Test
    public void updateChildren() {
        // Given
        Map map = new HashMap();

        // When
        GdxFIRDatabase.instance().updateChildren(map);

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).updateChildren(Mockito.refEq(map));
    }

    @Test
    public void transaction() {
        // Given
        CompleteCallback completeCallback = Mockito.mock(CompleteCallback.class);

        // When
        GdxFIRDatabase.instance().transaction(Integer.class, Mockito.mock(TransactionCallback.class), completeCallback);

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).transaction(Mockito.eq(Integer.class), Mockito.any(TransactionCallback.class), Mockito.refEq(completeCallback));
    }

    @Test
    public void transaction2() {
        // Given
        CompleteCallback completeCallback = Mockito.mock(CompleteCallback.class);

        // When (simulate POJO)
        GdxFIRDatabase.instance().transaction(GdxFIRDatabaseTest.class, Mockito.mock(TransactionCallback.class), completeCallback);

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).transaction(Mockito.eq(Map.class), Mockito.any(TransactionCallback.class), Mockito.refEq(completeCallback));
    }

    @Test
    public void setPersistenceEnabled() {
        // Given
        // When
        GdxFIRDatabase.instance().setPersistenceEnabled(true);

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).setPersistenceEnabled(Mockito.eq(true));
    }

    @Test
    public void keepSynced() {
        // Given
        // When
        GdxFIRDatabase.instance().keepSynced(true);

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).keepSynced(Mockito.eq(true));
    }

    @Test
    public void setMapConverter() {
        // Given
        FirebaseMapConverter mapConverter = Mockito.mock(FirebaseMapConverter.class);

        // When
        GdxFIRDatabase.instance().setMapConverter(mapConverter);

        // Then
        Assert.assertTrue(Whitebox.getInternalState(GdxFIRDatabase.instance(), "mapConverter") == mapConverter);
    }

    @Test
    public void getIOSClassName() {
        Assert.assertEquals("mk.gdx.firebase.ios.database.Database", GdxFIRDatabase.instance().getIOSClassName());
    }

    @Test
    public void getAndroidClassName() {
        Assert.assertEquals("mk.gdx.firebase.android.database.Database", GdxFIRDatabase.instance().getAndroidClassName());
    }

    @Test
    public void getWebGLClassName() {
        Assert.assertEquals("mk.gdx.firebase.html.database.Database", GdxFIRDatabase.instance().getWebGLClassName());
    }
}