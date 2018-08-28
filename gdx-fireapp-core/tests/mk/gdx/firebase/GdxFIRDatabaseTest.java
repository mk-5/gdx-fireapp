package mk.gdx.firebase;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import java.util.HashMap;
import java.util.Map;

import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.DataCallback;
import mk.gdx.firebase.callbacks.TransactionCallback;
import mk.gdx.firebase.database.FilterType;
import mk.gdx.firebase.database.OrderByMode;
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
        CompleteCallback completeCallback = Mockito.mock(CompleteCallback.class);

        // When
        GdxFIRDatabase.instance().setValue("test", completeCallback);

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).setValue(Mockito.eq("test"), Mockito.refEq(completeCallback));
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
    public void onDataChange() {
        // Given
        // When
        GdxFIRDatabase.instance().onDataChange(String.class, Mockito.mock(DataChangeListener.class));

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).onDataChange(Mockito.eq(String.class), Mockito.any(DataChangeListener.class));
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
    public void removeValue1() {
        // Given
        CompleteCallback completeCallback = Mockito.mock(CompleteCallback.class);

        // When
        GdxFIRDatabase.instance().removeValue(completeCallback);

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).removeValue(Mockito.refEq(completeCallback));
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
    public void updateChildren1() {
        // Given
        Map map = new HashMap();
        CompleteCallback completeCallback = Mockito.mock(CompleteCallback.class);

        // When
        GdxFIRDatabase.instance().updateChildren(map, completeCallback);

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).updateChildren(Mockito.refEq(map), Mockito.refEq(completeCallback));
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
    public void getIOSClassName() {
        Assert.assertEquals(GdxFIRDatabase.instance().getIOSClassName(), "mk.gdx.firebase.ios.database.Database");
    }

    @Test
    public void getAndroidClassName() {
        Assert.assertEquals(GdxFIRDatabase.instance().getAndroidClassName(), "mk.gdx.firebase.android.database.Database");
    }

    @Test
    public void getWebGLClassName() {
        Assert.assertEquals(GdxFIRDatabase.instance().getWebGLClassName(), "mk.gdx.firebase.html.database.Database");
    }
}