package mk.gdx.firebase;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.reflect.Whitebox;

import java.util.HashMap;
import java.util.Map;

import mk.gdx.firebase.database.FilterType;
import mk.gdx.firebase.database.OrderByMode;
import mk.gdx.firebase.deserialization.FirebaseMapConverter;
import mk.gdx.firebase.distributions.DatabaseDistribution;
import mk.gdx.firebase.functional.Function;

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
    public void instance() {
        assertNotNull(GdxFIRDatabase.instance());
    }

    @Test
    public void onConnect() {
        // Given
        // When
        GdxFIRDatabase.instance().onConnect();

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).onConnect();
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
        GdxFIRDatabase.instance().readValue(String.class);

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).readValue(Mockito.eq(String.class));
    }

    @Test
    public void readValue2() {
        // Given
        // When
        GdxFIRDatabase.instance().readValue(Map.class);

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).readValue(Mockito.eq(Map.class));
    }

    @Test
    public void onDataChange() {
        // Given
        // When
        GdxFIRDatabase.instance().onDataChange(String.class);

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).onDataChange(Mockito.eq(String.class));
    }

    @Test
    public void onDataChange2() {
        // Given
        // When
        GdxFIRDatabase.instance().onDataChange(GdxFIRDatabaseTest.class);

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).onDataChange(Mockito.eq(GdxFIRDatabaseTest.class));
    }

    @Test
    public void onDataChange_withNull() {
        // Given
        // When
        GdxFIRDatabase.instance().onDataChange(null);

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).onDataChange(Mockito.nullable(Class.class));
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
        // When
        GdxFIRDatabase.instance().transaction(Integer.class, Mockito.mock(Function.class));

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).transaction(Mockito.eq(Integer.class), Mockito.any(Function.class));
    }

    @Test
    public void transaction2() {
        // Given
        // When (simulate POJO)
        GdxFIRDatabase.instance().transaction(GdxFIRDatabaseTest.class, Mockito.mock(Function.class));

        // Then
        Mockito.verify(databaseDistribution, VerificationModeFactory.times(1)).transaction(Mockito.eq(GdxFIRDatabaseTest.class), Mockito.any(Function.class));
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