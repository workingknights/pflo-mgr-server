package name.aknights.services;

import name.aknights.api.Ticker;
import name.aknights.api.quotes.IQuote;

import java.util.Set;

public interface QuotesService {
    Set<IQuote> getQuotes(Set<Ticker> tickers);

    Set<IQuote> getQuote(Ticker... ticker);
}
