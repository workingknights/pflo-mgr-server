package name.aknights.services;

import name.aknights.api.Ticker;
import name.aknights.api.quotes.IQuote;
import name.aknights.config.QuotesServiceConfiguration;
import name.aknights.core.Currency;
import name.aknights.core.Exchange;
import name.aknights.api.quotes.Quote;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import java.util.Set;

public class BarchartFxRatesService extends BarchartQuotesService implements FxRatesService {

    @Inject
    public BarchartFxRatesService(Client client, QuotesServiceConfiguration quotesServiceConfiguration) {
        super(client, quotesServiceConfiguration);
    }

    @Override
    public Double getRateToUsd(String fromCurrency) {
        Currency currency = fromCurrency == null ? Currency.USD : Currency.valueOf(fromCurrency);
        final Double[] rateToUsd = new Double[1];

        switch (currency) {
            case GBp : {
                Set<IQuote> quotes = this.getQuote(new Ticker(ObjectId.get().toHexString(), "^GBPUSD", null, Exchange.NONE.name(), null));
                if (!quotes.isEmpty())
                    rateToUsd[0] = quotes.iterator().next().getLastPrice().get() * currency.getFactor();
                break;
            }
            case USD : rateToUsd[0] = 1.0; break;
            default: {
                Set<IQuote> quotes = this.getQuote(new Ticker(ObjectId.get().toHexString(), String.format("^%sUSD", fromCurrency), null, Exchange.NYSEARCA.name(), null));
                if (!quotes.isEmpty())
                    rateToUsd[0] = quotes.iterator().next().getLastPrice().get() * currency.getFactor();
                break;
            }
        }

        return rateToUsd[0];
    }
}
