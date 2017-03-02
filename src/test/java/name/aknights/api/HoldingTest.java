package name.aknights.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class HoldingTest {

    @Test
    public void whenJsonIsValidForExistingHoldingThenItIsCreated() throws IOException {
        String json = "{\"id\":\"58816e3c359886257795f254\",\"ticker\":{\"id\":\"5880a81b35988618e2f7d269\",\"symbol\":\"VWO\",\"currency\":\"USD\",\"exchange\":\"NYSEARCA\",\"fullName\":\"Vanguard FTSE Emerging Markets ETF\"},\"symbol\":null,\"shares\":60,\"tradeDate\":1484877366659,\"tradePrice\":23,\"commission\":0,\"initialMarketValue\":138,\"initialMarketValueBase\":138,\"cost\":138,\"$$index\":0}";

        ObjectMapper mapper = new ObjectMapper();
        Holding holding = mapper.readValue(json, Holding.class);

        assertNotNull(holding);
        assertEquals(60, holding.getShares());
        assertEquals("58816e3c359886257795f254", holding.getId());
        assertEquals("VWO", holding.getTicker().getSymbol());
        assertEquals("5880a81b35988618e2f7d269", holding.getTicker().getId());

//        String outputJson = mapper.writeValueAsString(holding);
//        assertNotNull(outputJson);
//        assertTrue(outputJson.contains("initialMarketValue\":146.0"));
    }

    @Test
    public void whenJsonIsValidForNewHoldingThenItIsCreated() throws IOException {
        String json = "{\"id\":\"\",\"ticker\":{\"id\":\"\",\"symbol\":\"VUSA\",\"currency\":\"\",\"exchange\":\"\",\"fullName\":\"\"},\"shares\":15,\"tradePrice\":34.5,\"commission\":0,\"tradeDate\":1484886786608,\"initialMarketValue\":0,\"initialMarketValueBase\":0}";

        ObjectMapper mapper = new ObjectMapper();
        Holding holding = mapper.readValue(json, Holding.class);

        assertNotNull(holding);
        assertEquals(15, holding.getShares());
        assertEquals("VUSA", holding.getTicker().getSymbol());
    }

}