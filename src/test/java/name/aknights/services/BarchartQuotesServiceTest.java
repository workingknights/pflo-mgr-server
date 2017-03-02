package name.aknights.services;

import name.aknights.api.Ticker;
import name.aknights.config.QuotesServiceConfiguration;
import name.aknights.core.Currency;
import name.aknights.core.Exchange;
import name.aknights.core.quotes.Quote;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.client.Client;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BarchartQuotesServiceTest {

    private BarchartQuotesService quotesService;

    @Mock
    private Client client;
    @Mock
    private QuotesServiceConfiguration quotesServiceConfig;

    @Before
    public void setup() {
        quotesService  = new BarchartQuotesService(client, quotesServiceConfig);
    }

    /*
    Un-uncomment @Test annotation to test.  Should not be part of standard test cycle
     */
//    @Test
    public void quotesReturnedWhenParamsAreValid() throws Exception {
        Client client = new JerseyClientBuilder().build();
        QuotesServiceConfiguration quotesServiceConfiguration = new QuotesServiceConfiguration("", "http://marketdata.websol.barchart.com/getQuote.json", "440c3382a95ba20d096e2c34b6493ca1");

        BarchartQuotesService quotesService = new BarchartQuotesService(client, quotesServiceConfiguration);
        Collection<Quote> quotes = quotesService.getQuotes(new HashSet<>(Arrays.asList(
                new Ticker("VWO", Currency.USD.name(), Exchange.NYSEARCA.name(), ""),
                new Ticker("CPJ1.LS", Currency.GBp.name(), Exchange.LSE.name(), ""),
                new Ticker("^GBPUSD", Currency.GBP.name(), Exchange.LSE.name(), ""))));

        assertEquals(3, quotes.size());
    }


    @Test
    public void buildTickerQueryStringCorrectlyAddsExchangeSuffix() {
        Set<Ticker> tickers = new HashSet<>();
        tickers.add(new Ticker("VWO", Currency.USD.name(), Exchange.NYSEARCA.name(), ""));
        tickers.add(new Ticker("CPJ1", Currency.GBp.name(), Exchange.LSE.name(), ""));

        String tickerQueryString = quotesService.buildTickerQueryString(tickers);

        assertEquals("CPJ1.LS,VWO", tickerQueryString);
    }

}