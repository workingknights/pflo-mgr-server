package name.aknights.services;

import name.aknights.api.Ticker;
import name.aknights.config.QuotesServiceConfiguration;
import name.aknights.config.YahooQuotesConfiguration;
import name.aknights.core.Currency;
import name.aknights.core.Exchange;
import name.aknights.core.quotes.Quote;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Test;

import javax.ws.rs.client.Client;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class YahooQuotesServiceTest {

    /*
    Un-uncomment @Test annotation to test.  Should not be part of standard test cycle
     */
//    @Test
    public void quotesReturnedWhenParamsAreValid() throws Exception {
        Client client = new JerseyClientBuilder().build();
        QuotesServiceConfiguration quotesServiceConfiguration = new QuotesServiceConfiguration("http://query.yahooapis.com/v1/public/yql", "","");

        YahooQuotesService quotesService = new YahooQuotesService(client, quotesServiceConfiguration);
        Collection<Quote> quotes = quotesService.getQuotes(new HashSet<>(Arrays.asList(
                new Ticker("VWO", Currency.USD.name(), Exchange.NYSEARCA.name(), ""),
                new Ticker("CPJ1.LS", Currency.GBp.name(), Exchange.LSE.name(), ""),
                new Ticker("^GBPUSD", Currency.GBP.name(), Exchange.LSE.name(), ""))));
//        Collection<YahooQuoteDetail> quotes = quotesService.allQuotes();

        assertEquals(3, quotes.size());
    }

}