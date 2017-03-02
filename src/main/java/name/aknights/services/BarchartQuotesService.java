package name.aknights.services;

import name.aknights.api.Ticker;
import name.aknights.config.QuotesServiceConfiguration;
import name.aknights.core.Exchange;
import name.aknights.core.quotes.BarchartQuoteDetail;
import name.aknights.core.quotes.BarchartQuotesResponse;
import name.aknights.core.quotes.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BarchartQuotesService implements QuotesService {

    public static final String EXCH_SUFFIX_LSE = ".LS";
    private Logger logger = LoggerFactory.getLogger(BarchartQuotesService.class);

    private Client client;
    private String apiUrl;
    private String apiKey;
    private final static String FIELDS = "previousLastPrice,previousClose,fiftyTwoWkHigh,fiftyTwoWkLow,oneMonthHigh,threeMonthLow";

    @Inject
    public BarchartQuotesService(Client client, QuotesServiceConfiguration quotesServiceConfiguration) {
        this.client = client;
        this.apiUrl = quotesServiceConfiguration.getBarchartApiUrl();
        this.apiKey = quotesServiceConfiguration.getBarchartApiKey();
    }

    @Override
    public Set<Quote> getQuotes(Set<Ticker> tickers) {
        String tickerQuery = buildTickerQueryString(tickers);
        BarchartQuotesResponse quotesData = getQuotesResponse(tickerQuery);

        Collection<BarchartQuoteDetail> results = quotesData.getResults();

        return results == null ? Collections.EMPTY_SET : new HashSet(results);
    }

    @Override
    public Set<Quote> getQuote(Ticker... tickers) {
        return getQuotes(new HashSet<>(Arrays.asList(tickers)));
/*
        BarchartQuotesResponse quotesData = getQuotesResponse(buildTickerQueryString(tickers));

        Collection<BarchartQuoteDetail> results = quotesData.getResults();

        return results == null ? new ArrayList<>() : new ArrayList<>(results);
*/
    }

    String buildTickerQueryString(Set<Ticker> tickers) {
        StringBuilder sb = new StringBuilder();

        for (Ticker ticker: tickers) {
            switch (Exchange.valueOf(ticker.getExchange())) {
                case LSE: sb.append(ticker.getSymbol()).append(EXCH_SUFFIX_LSE); break;
                default: sb.append(ticker.getSymbol()); break;
            }
            sb.append(",");
        }

        sb.deleteCharAt(sb.lastIndexOf(","));   // remove trailing ','

        return sb.toString();
    }

    private BarchartQuotesResponse getQuotesResponse(String symbols) {
        if (logger.isDebugEnabled()) {
            logger.debug("full query = {}{}{}{}{}{}{}", apiUrl, "?key=", apiKey, "&symbols=", symbols, "&fields=", FIELDS);
        }
        return client.target(apiUrl)
            .queryParam("key", apiKey)
            .queryParam("symbols", symbols)
            .queryParam("fields", FIELDS)
            .request(MediaType.APPLICATION_JSON)
            .get(BarchartQuotesResponse.class);
    }
}
