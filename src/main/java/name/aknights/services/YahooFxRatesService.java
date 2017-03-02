package name.aknights.services;

import name.aknights.api.Ticker;
import name.aknights.config.QuotesServiceConfiguration;
import name.aknights.core.Currency;
import name.aknights.core.quotes.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import java.util.Set;

public class YahooFxRatesService extends YahooQuotesService implements FxRatesService {

    Logger logger = LoggerFactory.getLogger(YahooFxRatesService.class);

    @Inject
    public YahooFxRatesService(Client client, QuotesServiceConfiguration quotesServiceConfiguration) {
        super(client, quotesServiceConfiguration);
    }

    @Override
    public Double getRateToUsd(String fromCurrency) {
        Currency currency = fromCurrency == null ? Currency.USD : Currency.valueOf(fromCurrency);
        Set<Quote> quotes;

        switch (currency) {
            case GBp: {
                quotes = this.getQuote(new Ticker("GBP=X", null, "", null));
                break;
            }
            default: {
                quotes = this.getQuote(new Ticker(fromCurrency + "=X", null, "", null));
                break;
            }
        }

        if (!quotes.isEmpty())
            return quotes.iterator().next().getLastPrice() * currency.getFactor();
        else
            return 1.0;
    }
}
