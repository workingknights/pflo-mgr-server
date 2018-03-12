package name.aknights.services;

import name.aknights.api.Ticker;
import name.aknights.api.quotes.IQuote;
import name.aknights.api.quotes.Quote;
import org.bson.types.ObjectId;

import java.util.HashSet;
import java.util.Set;

public class LocalQuotesService implements QuotesService {

    private Quote gbpToUsdFxRate = generateQuote(new Ticker(ObjectId.get().toHexString(),"^GBPUSD", "", "", ""));

    @Override
    public Set<IQuote> getQuotes(Set<Ticker> tickers) {
        Set<IQuote> quotes = new HashSet<>();
        for (Ticker ticker: tickers) {
            quotes.add(generateQuote(ticker));
        }
        return quotes;
    }

    @Override
    public Set<IQuote> getQuote(Ticker... tickers) {
        Set<IQuote> quotes = new HashSet<>();

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
                rand(100.0), rand(1.0), rand(1.0));
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
