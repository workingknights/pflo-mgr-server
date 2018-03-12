package name.aknights.api.quotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

public class AlphaVantageQuotesResponseTest {

    @Test
    public void isCreatedIfJsonIsValid() throws IOException {
        String jsonString = new String(Files.readAllBytes(
                FileSystems.getDefault().getPath("src", "test/resources/json/alphaVantageJsonResponse.txt")));

        System.out.println(jsonString);

        ObjectMapper mapper = new ObjectMapper();

        AlphaVantageQuotesResponse quoteResponse = mapper.readValue(jsonString, AlphaVantageQuotesResponse.class);

        assertEquals(3, quoteResponse.getResults().size());

        IQuote dailyQuote = quoteResponse.getTimeSeries().get("2018-03-05");
        assertEquals(Double.valueOf(288.2), dailyQuote.getLastPrice().get());
        assertEquals(Double.valueOf(0.004), dailyQuote.getPercentChange().get(), 0.0004);
    }

}