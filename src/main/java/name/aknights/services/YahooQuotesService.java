package name.aknights.services;

import name.aknights.api.Ticker;
import name.aknights.config.QuotesServiceConfiguration;
import name.aknights.core.Exchange;
import name.aknights.core.quotes.Quote;
import name.aknights.core.quotes.YahooQuoteDetail;
import name.aknights.core.quotes.YahooQuotesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class YahooQuotesService implements QuotesService {

    public static final String YAHOO_EXCH_SUFFIX_LSE = ".LS";
    Logger logger = LoggerFactory.getLogger(YahooQuotesService.class);

    private Client client;
    private String apiUrl = "http://query.yahooapis.com/v1/public/yql";

    @Inject
    public YahooQuotesService(Client client, QuotesServiceConfiguration quotesServiceConfiguration) {
        this.client = client;
        this.apiUrl = quotesServiceConfiguration.getYahooApiUrl();
    }

    @Override
    public Set<Quote> getQuotes(Set<Ticker> tickers) {
        String origSymbolQueryParam = String.format("select * from yahoo.finance.quotes where symbol in (%s)", buildTickerQueryString(tickers));
        String symbolQueryParam = origSymbolQueryParam.replace(" ", "%20");
        symbolQueryParam = symbolQueryParam.replace("(", "%28%22");
        symbolQueryParam = symbolQueryParam.replace(")", "%22%29");

        YahooQuotesResponse quotesData = getQuotesResponse(symbolQueryParam);

        return new HashSet(quotesData.getQuery().getResults().getQuote());
//        return new ArrayList<>(quoteDetails);
    }

    @Override
    public Set<Quote> getQuote(Ticker... tickers) {
        return getQuotes(new HashSet<>(Arrays.asList(tickers)));

    }

    String buildTickerQueryString(Set<Ticker> tickers) {
        StringBuilder sb = new StringBuilder();

        for (Ticker ticker: tickers) {
            switch (Exchange.valueOf(ticker.getExchange())) {
                case LSE: sb.append(ticker.getSymbol()).append(YAHOO_EXCH_SUFFIX_LSE); break;
                default: sb.append(ticker.getSymbol()); break;
            }
            sb.append(",");
        }

        sb.deleteCharAt(sb.lastIndexOf(","));   // remove trailing ','

        return sb.toString();
    }

    private YahooQuotesResponse getQuotesResponse(String symbolQueryParam) {
        if (logger.isDebugEnabled()) {
            logger.debug("full query = {}{}{}{}{}", apiUrl, "?q=", symbolQueryParam, "format=json", "env=store://datatables.org/alltableswithkeys");
        }
        return client.target(apiUrl)
            .queryParam("q", symbolQueryParam)
            .queryParam("format", "json")
            .queryParam("env", "store://datatables.org/alltableswithkeys")
            .request(MediaType.APPLICATION_JSON)
            .get(YahooQuotesResponse.class);
    }
}
