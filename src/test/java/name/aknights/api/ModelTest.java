package name.aknights.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class ModelTest {

    @Test(expected = UnsupportedOperationException.class)
    public void whenDataIsModifiedAnExceptionIsThrown() throws Exception {
        Set<ModelEntry> entries = new HashSet<>();
        entries.add(new ModelEntry("AAPL", 0.4));
        entries.add(new ModelEntry("MSFT", 0.6));
        Model model = new Model(new ObjectId(), "userA", entries);

        model.getEntries().add(new ModelEntry("GS", 0.2));
    }

    @Test
    public void whenObjectIsMarshalledJsonIsCorrect() throws Exception {
        Set<ModelEntry> entries = new HashSet<>();
        entries.add(new ModelEntry("AAPL", 0.4));
        entries.add(new ModelEntry("MSFT", 0.6));
        Model model = new Model(new ObjectId(), "userA", entries);

        String jsonStr = new ObjectMapper().writeValueAsString(model);

        Assert.assertTrue(jsonStr.contains("\"userId\":\"userA\""));
        Assert.assertTrue(jsonStr.contains("{\"ticker\":\"MSFT\",\"portfolioWeight\":0.6}"));
    }
}