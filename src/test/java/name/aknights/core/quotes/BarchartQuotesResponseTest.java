package name.aknights.core.quotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class BarchartQuotesResponseTest {

    @Test
    public void isCreatedIfJsonIsValid() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        BarchartQuotesResponse quotesResponse = mapper.readValue(jsonString, BarchartQuotesResponse.class);

        assertEquals(200, quotesResponse.getStatus().code);
        assertEquals(2, quotesResponse.getResults().size());
    }

    private String jsonString = "" +
        "{\n"+
        "  \"status\": {\n"+
        "    \"code\": 200,\n"+
        "    \"message\": \"Success.\"\n"+
        "  },\n"+
        "  \"results\": [\n"+
        "    {\n"+
        "      \"symbol\": \"^GBPUSD\",\n"+
        "      \"exchange\": \"FOREX\",\n"+
        "      \"name\": \"British Pound/U.S. Dollar\",\n"+
        "      \"dayCode\": \"H\",\n"+
        "      \"serverTimestamp\": \"2017-01-18T00:23:11-06:00\",\n"+
        "      \"mode\": \"i\",\n"+
        "      \"lastPrice\": 1.23381,\n"+
        "      \"tradeTimestamp\": \"2017-01-18T00:13:11-06:00\",\n"+
        "      \"netChange\": -0.00712,\n"+
        "      \"percentChange\": -0.57,\n"+
        "      \"previousLastPrice\": 1.24093,\n"+
        "      \"unitCode\": \"5\",\n"+
        "      \"open\": 1.2412,\n"+
        "      \"high\": 1.24133,\n"+
        "      \"low\": 1.23273,\n"+
        "      \"close\": \"\",\n"+
        "      \"flag\": \"\",\n"+
        "      \"previousClose\": 1.24093,\n"+
        "      \"volume\": 81292,\n"+
        "      \"fiftyTwoWkHigh\": 1.50155,\n"+
        "      \"fiftyTwoWkLow\": 1.1711\n"+
        "    },\n"+
        "    {\n"+
        "      \"symbol\": \"CPJ1.LS\",\n"+
        "      \"exchange\": \"LSE\",\n"+
        "      \"name\": \"Ishares Vii Plc\",\n"+
        "      \"dayCode\": \"G\",\n"+
        "      \"serverTimestamp\": \"2017-01-17T20:25:05-06:00\",\n"+
        "      \"mode\": \"d\",\n"+
        "      \"lastPrice\": 10005,\n"+
        "      \"tradeTimestamp\": \"2017-01-17T18:00:00-06:00\",\n"+
        "      \"netChange\": -231,\n"+
        "      \"percentChange\": -2.26,\n"+
        "      \"previousLastPrice\": 10236,\n"+
        "      \"unitCode\": \"3\",\n"+
        "      \"open\": 10203,\n"+
        "      \"high\": 10203,\n"+
        "      \"low\": 9986,\n"+
        "      \"close\": 10005,\n"+
        "      \"flag\": \"s\",\n"+
        "      \"previousClose\": 10236,\n"+
        "      \"volume\": 29423,\n"+
        "      \"fiftyTwoWkHigh\": 10269.25,\n"+
        "      \"fiftyTwoWkLow\": 6516\n"+
        "    }\n"+
        "  ]\n"+
        "}";
}