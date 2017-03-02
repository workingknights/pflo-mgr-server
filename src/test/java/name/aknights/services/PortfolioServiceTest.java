package name.aknights.services;

import name.aknights.api.Holding;
import name.aknights.api.PortfolioEntry;
import name.aknights.api.Ticker;
import name.aknights.core.Currency;
import name.aknights.core.Exchange;
import name.aknights.core.quotes.Quote;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PortfolioServiceTest {

    private PortfolioService service;

    @Before
    public void setUp() {
        QuotesService quotesService = mock(QuotesService.class);
        FxRatesService fxRatesService = mock(FxRatesService.class);
        HoldingsService holdingsService = mock(HoldingsService.class);
        ModelService modelService = mock(ModelService.class);

        when(fxRatesService.getRateToUsd("GBp")).thenReturn(0.012334);
        when(fxRatesService.getRateToUsd("USD")).thenReturn(1.0);

        service = new PortfolioService(quotesService, fxRatesService, holdingsService, modelService);
    }

    @Test
    public void withSmallPercentChangeFromYearHighAndPositiveDailyChangeSellIsRecommended() throws Exception {
        Quote quote = generateQuote(0.011, 10.3, 2.0, 10.5);
        assertEquals("SELL", service.calcRecommendation(quote).getDirection());
    }

    @Test
    public void withLowPercentChangeFromYearHighSellIsRecommended() throws Exception {
        Quote quote = generateQuote(10.3, 2.0, 10.5);
        assertEquals("SELL", service.calcRecommendation(quote).getDirection());
    }

    @Test
    public void withLowPercentChangeFromYearLowBuyIsRecommended() throws Exception {
        Quote quote = generateQuote(2.1, 2.0, 10.5);

        assertEquals("BUY", service.calcRecommendation(quote).getDirection());
    }

    @Test
    public void withSingleNonUSDHolding_EntryCreatedCorrectly() throws Exception {
        Calendar tradeDate = Calendar.getInstance();
        tradeDate.set(2016, 7, 30);
        Ticker ticker = new Ticker("CPJ1.L", Currency.GBp.name(), Exchange.LSE.name(), null);
        Holding holding = new Holding(ticker, 20, tradeDate.getTime(), 9138.25, 0.0);

        Quote quote = new Quote(ticker.getSymbol(), ticker.getFullName(), 9739.00, 9739.00, 6.9, 44.0, 9700.0, 9800.0);

        PortfolioEntry entry = service.createEntry(Collections.singletonList(holding), quote);

        assertNotNull(entry);
        assertEquals("CPJ1", entry.getTicker());
        assertEquals("GBp",entry.getCurrency());
        assertEquals(20, (long) entry.getTotalShares());
        assertEquals(182765.0, entry.getTotalCost(), 0.001);
        assertEquals(194780.0, entry.getMarketValue(), 0.001);
        assertEquals(12015.0, entry.getTotalGain(), 0.01);
        assertEquals(148.193, entry.getTotalGainBase(), 0.01);
    }

    @Test
    public void withTwoUSDHoldings_EntryCreatedCorrectly() throws Exception {
        Calendar tradeDate = Calendar.getInstance();
        Ticker ticker = new Ticker("VBR", Currency.USD.name(), Exchange.NYSEARCA.name(), null);
        tradeDate.set(2016, 6, 20);
        Holding holding1 = new Holding(ticker, 30, tradeDate.getTime(), 109.44, 1.5);

        tradeDate.set(2016, 11, 6);
        Holding holding2 = new Holding(ticker, 10, tradeDate.getTime(), 121.32, 0.5);

        Quote quote = new Quote(ticker.getSymbol(), ticker.getFullName(), 123.8, 123.8, 6.9, 44.0, 120.0, 130.0);

        PortfolioEntry entry = service.createEntry(Arrays.asList(holding1, holding2), quote);

        assertNotNull(entry);
        assertEquals("VBR", entry.getTicker());
        assertEquals("USD",entry.getCurrency());
        assertEquals(40, (long) entry.getTotalShares());
        assertEquals(4498.4, entry.getTotalCost(), 0.001);
        assertEquals(4952.0, entry.getMarketValue(), 0.001);
        assertEquals(453.60, entry.getTotalGain(), 0.01);
        assertEquals(453.60, entry.getTotalGainBase(), 0.01);
    }


    private Quote generateQuote(double lastPrice, double yearLow, double yearHigh) {
        return new Quote("", "", 0.0, lastPrice, 0.1, 1.5, yearLow, yearHigh);
    }

    private Quote generateQuote(double percentChange, double lastPrice, double yearLow, double yearHigh) {
        return new Quote("", "", 0.0, lastPrice, percentChange, 1.5, yearLow, yearHigh);
    }
}
