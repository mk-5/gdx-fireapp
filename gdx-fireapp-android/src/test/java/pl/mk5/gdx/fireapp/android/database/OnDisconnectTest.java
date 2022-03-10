package pl.mk5.gdx.fireapp.android.database;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.firebase.database.DatabaseReference;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import pl.mk5.gdx.fireapp.android.AndroidContextTest;

public class OnDisconnectTest extends AndroidContextTest {
    @Test
    public void shouldRemoveValue() {
        // given
        com.google.firebase.database.OnDisconnect androidDisconnect = mock(com.google.firebase.database.OnDisconnect.class);
        when(androidDisconnect.removeValue()).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                DatabaseReference.CompletionListener completionListener = invocation.getArgument(0);
                completionListener.onComplete(null, mock(DatabaseReference.class));
                return null;
            }
        });
        OnDisconnect onDisconnect = new OnDisconnect(androidDisconnect);

        // when
        onDisconnect.removeValue().subscribe();

        // then
        Assert.assertTrue(true);
    }

    @Test
    public void shouldSetValue() {
        // given
        com.google.firebase.database.OnDisconnect androidDisconnect = mock(com.google.firebase.database.OnDisconnect.class);
        when(androidDisconnect.setValue(any())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                DatabaseReference.CompletionListener completionListener = invocation.getArgument(1);
                completionListener.onComplete(null, mock(DatabaseReference.class));
                return null;
            }
        });
        OnDisconnect onDisconnect = new OnDisconnect(androidDisconnect);

        // when
        onDisconnect.setValue("abc").subscribe();

        // then
        Assert.assertTrue(true);
    }

    @Test
    public void shouldCancel() {
        // given
        com.google.firebase.database.OnDisconnect androidDisconnect = mock(com.google.firebase.database.OnDisconnect.class);
        when(androidDisconnect.cancel()).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                DatabaseReference.CompletionListener completionListener = invocation.getArgument(0);
                completionListener.onComplete(null, mock(DatabaseReference.class));
                return null;
            }
        });
        OnDisconnect onDisconnect = new OnDisconnect(androidDisconnect);

        // when
        onDisconnect.cancel().subscribe();

        // then
        Assert.assertTrue(true);
    }
}