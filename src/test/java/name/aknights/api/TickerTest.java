package name.aknights.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import name.aknights.core.quotes.BarchartQuoteDetail;
import org.eclipse.jetty.util.DateCache;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TickerTest {

    @Test
    public void isCreatedIfJsonIsValid() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Ticker ticker = mapper.readValue(jsonString, Ticker.class);

        assertEquals("VWO", ticker.getSymbol());
    }


    private String jsonString = "" +
            "{\"id\":\"5880a81b35988618e2f7d269\",\"symbol\":\"VWO\",\"currency\":\"GBP\",\"exchange\":\"NYSEARCA\",\"fullName\":\"Vanguard FTSE Emerging Markets ETF\",\"$$index\":0}";
}