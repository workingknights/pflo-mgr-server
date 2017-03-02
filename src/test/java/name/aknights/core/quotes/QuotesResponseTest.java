package name.aknights.core.quotes;

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
        YahooQuotesResponse response = mapper.readValue(resource, YahooQuotesResponse.class);

        assertEquals(3, response.getQuery().getResults().getQuote().size());
    }

}