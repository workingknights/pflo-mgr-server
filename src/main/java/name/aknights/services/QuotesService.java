package name.aknights.services;

import name.aknights.api.Ticker;
import name.aknights.core.quotes.Quote;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface QuotesService {
    Collection<Quote> getQuotes(Set<Ticker> tickers);

    Collection<Quote> getQuote(Ticker... ticker);
}
