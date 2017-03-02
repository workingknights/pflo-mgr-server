package name.aknights.services;

import name.aknights.api.Ticker;
import name.aknights.core.quotes.Quote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class LocalQuotesService implements QuotesService {

    private Quote gbpToUsdFxRate = generateQuote(new Ticker("^GBPUSD", "", "", ""));

    @Override
    public Collection<Quote> getQuotes(Set<Ticker> tickers) {
        Collection<Quote> quotes = new ArrayList<>();
        for (Ticker ticker: tickers) {
            quotes.add(generateQuote(ticker));
        }
        return quotes;
    }

    @Override
    public Collection<Quote> getQuote(Ticker... tickers) {
        Collection<Quote> quotes = new HashSet<>();

        for(Ticker ticker: tickers) {
            if (ticker.getSymbol().equals("GBP"))
                quotes.add(gbpToUsdFxRate);
            else
                quotes.add(generateQuote(ticker));
        }

        return quotes;
    }

    private Quote generateQuote(Ticker ticker) {
        return new Quote(ticker.getSymbol(), ticker.getFullName(), rand(100.0),
                rand(100.0), rand(1.0), rand(1.0), rand(100.0), rand(100.0));
    }

    private String ccy() {
        double r = Math.random();
        if (r < 0.5) return "USD";
        else return "GBP";
    }

    private double rand(double cap) {
        return Math.random()*cap;
    }

}
