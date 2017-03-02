package name.aknights.core.quotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class BarchartQuoteDetailTest {

    @Test
    public void isCreatedIfJsonIsValid() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        BarchartQuoteDetail quoteDetail = mapper.readValue(jsonString, BarchartQuoteDetail.class);

        assertEquals("CPJ1", quoteDetail.getSymbol());
        assertEquals(10167, quoteDetail.getPreviousClose().get(), 0.0);
    }


    private String jsonString = "" +
            "{\n" +
            " \"symbol\": \"CPJ1.LS\",\n" +
            " \"exchange\": \"LSE\",\n" +
            " \"name\": \"Ishares Vii Plc\",\n" +
            " \"dayCode\": \"B\",\n" +
            " \"serverTimestamp\": \"2017-01-12T20:09:31-06:00\",\n" +
            " \"mode\": \"d\",\n" +
            " \"lastPrice\": 10136.5,\n" +
            " \"tradeTimestamp\": \"2017-01-12T18:00:00-06:00\",\n" +
            " \"netChange\": -30.5,\n" +
            " \"percentChange\": -0.3,\n" +
            " \"previousLastPrice\": 10167,\n" +
            " \"unitCode\": \"3\",\n" +
            " \"open\": 10094,\n" +
            " \"high\": 10136.5,\n" +
            " \"low\": 10069,\n" +
            " \"close\": 10136.5,\n" +
            " \"flag\": \"s\",\n" +
            " \"previousClose\": 10167,\n" +
            " \"volume\": 2968,\n" +
            " \"fiftyTwoWkHigh\": 10210,\n" +
            " \"fiftyTwoWkLow\": 6516\n" +
            " }";
}