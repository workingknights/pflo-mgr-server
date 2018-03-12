package name.aknights.services;

import name.aknights.api.Ticker;
import name.aknights.api.quotes.AlphaVantageDailyQuote;
import name.aknights.api.quotes.AlphaVantageQuotesResponse;
import name.aknights.api.quotes.IQuote;
import name.aknights.config.QuotesServiceConfiguration;
import name.aknights.core.Exchange;
import org.bouncycastle.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class AlphaVantageQuotesService implements QuotesService {

    private static final String AV_EXCH_SUFFIX_LSE = ".L";

    private final Logger logger = LoggerFactory.getLogger(AlphaVantageQuotesService.class);
    private final Client client;
    private final String apiUrl;
    private final String apiKey;

    @Inject
    public AlphaVantageQuotesService(Client client, QuotesServiceConfiguration quotesServiceConfiguration) {
        this.client = client;
        this.apiUrl = quotesServiceConfiguration.getAlphaVantageApiUrl();
        this.apiKey = quotesServiceConfiguration.getAlphaVantageApiKey();
    }

    @Override
    public Set<IQuote> getQuotes(Set<Ticker> tickers) {
        String tickerQuery = buildTickerQueryString(tickers);
        Collection<String> symbols = Arrays.asList(Strings.split(tickerQuery, ','));
        return getQuotes(symbols);
    }

    @Override
    public Set<IQuote> getQuote(Ticker... tickers) {
        return getQuotes(new HashSet<>(Arrays.asList(tickers)));

    }

    String buildTickerQueryString(Set<Ticker> tickers) {
        StringBuilder sb = new StringBuilder();

        for (Ticker ticker: tickers) {
            switch (Exchange.valueOf(ticker.getExchange())) {
                case LSE: sb.append(ticker.getSymbol()).append(AV_EXCH_SUFFIX_LSE); break;
                default: sb.append(ticker.getSymbol()); break;
            }
            sb.append(",");
        }

        sb.deleteCharAt(sb.lastIndexOf(","));   // remove trailing ','

        return sb.toString();
    }


    private Set<IQuote> getQuotes(Collection<String> symbols) {
        return symbols.stream()
                .map(this::getQuoteResponse)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    /*
    Request URL of form: https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&apikey=JXWZII6BBC1PKBPE&symbol=MKS.L
     */
    private Optional<IQuote> getQuoteResponse(String symbol) {
        if (logger.isDebugEnabled()) {
            logger.debug("full query = {}{}{}{}{}", this.apiUrl, "?function=TIME_SERIES_DAILY&apikey=", this.apiKey, "&symbol=", symbol);
        }
        AlphaVantageQuotesResponse alphaVantageQuotesResponse = client.target(apiUrl)
                .queryParam("function", "TIME_SERIES_DAILY")
                .queryParam("apikey", this.apiKey)
                .queryParam("symbol", symbol)
                .request(MediaType.APPLICATION_JSON)
                .get(AlphaVantageQuotesResponse.class);

        Optional<AlphaVantageDailyQuote> quote = alphaVantageQuotesResponse.getResults().stream().findFirst();
        if (quote.isPresent()) {
            quote.get().setSymbol(symbol.substring(0, symbol.indexOf('.')));
            return Optional.of(quote.get());
        }
        else
            return Optional.empty();
    }
}
