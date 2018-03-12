package name.aknights.api.quotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class QuotesResponseTest {

    @Test
    public void isCreatedIfJsonIsValid() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Convert JSON string from file to Object
        InputStream resource = this.getClass().getResourceAsStream("/validQuote.json");
        AlphaVantageQuotesResponse response = mapper.readValue(resource, AlphaVantageQuotesResponse.class);

        assertEquals(2, response.getResults().size());
    }

}