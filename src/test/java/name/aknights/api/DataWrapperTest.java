package name.aknights.api;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DataWrapperTest {
    @Test
    public void whenDataIsReadItIsSameAsWasPassedToWrapper() throws Exception {
        Collection<String> data = Arrays.asList("Hello", "World");
        DataWrapper<String> dataWrapper = new DataWrapper<>(data);
        assertNotEquals(data, dataWrapper.getData());
        assertEquals("Hello", dataWrapper.getData().iterator().next());
    }

    @Test(expected=UnsupportedOperationException.class)
    public void whenDataIsModifiedAnExceptionIsThrown() throws Exception {
        Collection<String> data = Arrays.asList("Hello", "World");
        DataWrapper<String> dataWrapper = new DataWrapper<>(data);
        dataWrapper.getData().add("Foo");
    }

}