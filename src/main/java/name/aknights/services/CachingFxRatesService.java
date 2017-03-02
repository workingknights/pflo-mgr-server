package name.aknights.services;

import name.aknights.core.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static name.aknights.core.Currency.USD;

public class CachingFxRatesService implements FxRatesService {

    private Logger logger = LoggerFactory.getLogger(CachingFxRatesService.class);
    private static final long DEFAULT_TTL = 600000;

    private final Map<String, Double> cacheMap = new HashMap<>();
    private long ttl = DEFAULT_TTL;
    private long lastCacheClear = 0;
    private FxRatesService[] underlyingServices;

    public CachingFxRatesService(FxRatesService... underlyingServices) {

        this.underlyingServices = underlyingServices;
    }

    public CachingFxRatesService(long ttl, FxRatesService... underlyingServices) {
        this(underlyingServices);
        this.ttl = ttl;
        logger.info("CachingFxRatesService created with {}ms TTL", ttl);
    }

    @Override
    public Double getRateToUsd(String fromCurrency) {
        Currency currency = fromCurrency == null ? USD : Currency.valueOf(fromCurrency);

        Optional<Double> rate = readFromCache(fromCurrency);
        double rateToReturn = 1.0;

        if (!rate.isPresent()) {    // Cache miss
            switch (currency) {
                case GBp: {
                    rate = readFromCache(Currency.GBP.name());
                    if (rate.isPresent()) {
                        rateToReturn = rate.get() * currency.getFactor();
                    }
                    else {
                        rateToReturn = underlyingServices[0].getRateToUsd(fromCurrency);
                        writeToCache(fromCurrency, rateToReturn);
                        writeToCache(Currency.GBP.name(), rateToReturn / currency.getFactor());
                    }
                    break;
                }
                case USD:
                    rateToReturn = 1.0;
                    writeToCache(fromCurrency, rateToReturn);
                    break;
                default: {
                    rateToReturn = underlyingServices[0].getRateToUsd(fromCurrency);
                    writeToCache(fromCurrency, rateToReturn);
                }
            }

//            Double rate = underlyingServices[0].getRateToUsd(fromCurrency);
//            writeToCache(fromCurrency, rate);
//            rateToReturn = Optional.of(rate);

//            final Double[] rateToUsd = new Double[1];

        }
        else
            rateToReturn = rate.get();

        return rateToReturn; //rateToReturn.orElseThrow(() -> new RuntimeException(String.format("No rate found for %s currency", fromCurrency)));
    }

    Optional<Double> readFromCache(String currency) {
        checkTtl();
        return Optional.ofNullable(cacheMap.get(currency));
    }

    void writeToCache(String currency, Double rate) {
        checkTtl();
        cacheMap.put(currency, rate);
    }

    private void checkTtl() {
        if (lastCacheClear == 0) lastCacheClear = System.currentTimeMillis();
        else {
            if (lastCacheClear + ttl <= System.currentTimeMillis())
                expireCache();
        }
    }

    private void expireCache() {
        logger.debug("Expiring Cache..");
        this.cacheMap.clear();
        this.lastCacheClear = System.currentTimeMillis();
    }
}
