package name.aknights.services;

import name.aknights.api.Ticker;
import name.aknights.core.quotes.Quote;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CachingQuotesServiceTest {

    private CachingQuotesService service;
    private QuotesService underlier;
    private Set<Ticker> tickers = new HashSet<>();

    @Before
    public void setup() {
        underlier = new LocalQuotesService();
        service = new CachingQuotesService(200, underlier);

        tickers.add(new Ticker("ABC", "", "", ""));
        tickers.add(new Ticker("XYZ", "", "", ""));
    }

    @Test
    public void getQuote() throws Exception {
        Collection<Quote> quotes = service.getQuotes(tickers);

        assertEquals(2, quotes.size());
    }

    @Test
    public void whenCacheWrittenToReadAndReadAgainFromAfterTTLExpired_ThenReadReturnsNothing() throws Exception {
        Collection<Quote> quotes = underlier.getQuotes(tickers);

        service.writeToCache(quotes);
        assertEquals(2, service.readFromCache(tickers).size());
        Thread.sleep(250);
        assertEquals(0, service.readFromCache(tickers).size());
    }

    @Test
    public void whenCacheWrittenToAndReadFromAfterTTLExpired_ThenReadReturnsNothing() throws Exception {
        Collection<Quote> quotes = underlier.getQuotes(tickers);

        service.writeToCache(quotes);
        Thread.sleep(250);
        assertEquals(0, service.readFromCache(tickers).size());
    }

    @Test
    public void whenCacheWrittenToReadFromAndWrittenToAgainBeforeTTLExpired_ThenReadReturnsData() throws Exception {
        Collection<Quote> quotes = underlier.getQuotes(tickers);

        service.writeToCache(quotes);
        assertEquals(2, service.readFromCache(tickers).size());

//        tickers.add("AAA");
        Ticker aaaTicker = new Ticker("AAA", "", "", "");
        tickers.add(aaaTicker);

        service.writeToCache(underlier.getQuote(aaaTicker));
        assertEquals(3, service.readFromCache(tickers).size());
    }
}