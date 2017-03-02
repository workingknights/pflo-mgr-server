package name.aknights.services;

import name.aknights.api.Ticker;
import name.aknights.core.quotes.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CachingQuotesService implements QuotesService {

    private Logger logger = LoggerFactory.getLogger(CachingQuotesService.class);
    private static final long DEFAULT_TTL = 600000;

    private final Map<String, Quote> cacheMap = new HashMap<>();
    private long ttl = DEFAULT_TTL;
    private long lastCacheClear = 0;
    private QuotesService[] underlyingServices;

    public CachingQuotesService(QuotesService... underlyingServices) {
        this.underlyingServices = underlyingServices;
    }

    public CachingQuotesService(long ttl, QuotesService... underlyingServices) {
        this(underlyingServices);
        this.ttl = ttl;
        logger.info("CachingQuotesService created with {}ms TTL", ttl);
    }

    @Override
    public Collection<Quote> getQuotes(Set<Ticker> tickers) {
        // get all cachedQuotes
        Set<Quote> quotesToReturn = readFromCache(tickers);

        if (quotesToReturn.size() != tickers.size()) {

            // for all cache misses, retrieve quotes from underlying service
            Set<Ticker> unCachedTickers = tickers.stream().filter(ticker -> !cacheMap.containsKey(ticker.getSymbol())).collect(Collectors.toSet());

            if (unCachedTickers.size() > 0) {
                Collection<Quote> fetchedQuotes = underlyingServices[0].getQuotes(unCachedTickers);
                // add them to the set of quotesToReturn
                quotesToReturn.addAll(fetchedQuotes);
                writeToCache(fetchedQuotes);

                fetchedQuotes.stream().forEach(q -> unCachedTickers.remove(new Ticker(q.getSymbol())));

                if (unCachedTickers.size() > 0) { // check next service
                    fetchedQuotes = underlyingServices[1].getQuotes(unCachedTickers);
                    // add them to the set of quotesToReturn
                    quotesToReturn.addAll(fetchedQuotes);
                    writeToCache(fetchedQuotes);

                    // check to see we have them all now
                    fetchedQuotes.stream().forEach(q -> unCachedTickers.remove(new Ticker(q.getSymbol())));
                    if (unCachedTickers.size() != 0)
                        logger.warn("Quotes for some tickers still can not be found!");
                }
            }
            else {
                logger.warn("Quotes not found in cache for all tickers, but apparently there are no un-cached tickers!!");
            }
        }

        return quotesToReturn;
    }

    @Override
    public Collection<Quote> getQuote(Ticker... tickers) {
//        Set<Ticker> tickers = new HashSet<>();
//        tickers.add(ticker);

        return getQuotes(new HashSet<>(Arrays.asList(tickers)));
//        if (quoteIter.hasNext()) return Optional.ofNullable(quoteIter.next());
//        else return Optional.empty();
    }

    Set<Quote> readFromCache(Set<Ticker> tickers) {
        //TODO debug this
        checkTtl();
        Set<Quote> quotes = new HashSet<>();
        for(Ticker ticker: tickers) {
            if (cacheMap.containsKey(ticker.getSymbol())) {
                quotes.add(cacheMap.get(ticker.getSymbol()));
            }
        }
        return quotes;
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

    void writeToCache(Collection<Quote> fetchedQuotes) {
        checkTtl();
        cacheMap.putAll(fetchedQuotes.stream().collect(Collectors.toMap(Quote::getSymbol, Function.identity())));
    }
}
