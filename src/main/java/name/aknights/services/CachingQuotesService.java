package name.aknights.services;

import name.aknights.api.Ticker;
import name.aknights.api.quotes.IQuote;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CachingQuotesService implements QuotesService {

    private Logger logger = LoggerFactory.getLogger(CachingQuotesService.class);
    private static final long DEFAULT_TTL = 600000;

    private final Map<String, IQuote> cacheMap = new HashMap<>();
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
    public Set<IQuote> getQuotes(Set<Ticker> tickers) {
        // get all cachedQuotes
        Set<IQuote> quotesToReturn = readFromCache(tickers);

        // if we don't have cached quotes for all tickers, fetch them
        if (quotesToReturn.size() != tickers.size()) {

            Set<Ticker> tickersToQuote = tickers.stream().filter(ticker -> !cacheMap.containsKey(ticker.getSymbol())).collect(Collectors.toSet());

            if (tickersToQuote.size() > 0) {
                Set<IQuote> fetchedQuotes = underlyingServices[0].getQuotes(tickersToQuote);
                // add them to the set of quotesToReturn
                quotesToReturn.addAll(fetchedQuotes);
                writeToCache(fetchedQuotes);

                logger.debug("Fetched quotes for {} tickers from {} service", fetchedQuotes.size(), underlyingServices[0].getClass().getName());

                fetchedQuotes.stream()
                        .forEach(q -> tickersToQuote.remove(new Ticker(ObjectId.get().toHexString(), q.getSymbol(), "", "", "")));

                if (tickersToQuote.size() > 0) { // check next service
                    fetchedQuotes = underlyingServices[1].getQuotes(tickersToQuote);
                    // add them to the set of quotesToReturn
                    quotesToReturn.addAll(fetchedQuotes);
                    writeToCache(fetchedQuotes);

                    logger.debug("Fetched quotes for {} tickers from {} service", fetchedQuotes.size(), underlyingServices[0].getClass().getName());

                    // check to see we have them all now
                    fetchedQuotes.stream()
                            .forEach(q -> tickersToQuote.remove(new Ticker(ObjectId.get().toHexString(), q.getSymbol(), "", "", "")));
                    if (tickersToQuote.size() != 0)
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
    public Set<IQuote> getQuote(Ticker... tickers) {
//        Set<Ticker> tickers = new HashSet<>();
//        tickers.add(ticker);

        return getQuotes(new HashSet<>(Arrays.asList(tickers)));
//        if (quoteIter.hasNext()) return Optional.ofNullable(quoteIter.next());
//        else return Optional.empty();
    }

    Set<IQuote> readFromCache(Set<Ticker> tickers) {
        //TODO debug this
        checkTtl();
        Set<IQuote> quotes = new HashSet<>();
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

    void writeToCache(Set<IQuote> fetchedQuotes) {
        checkTtl();
        cacheMap.putAll(
                fetchedQuotes.stream()
                        .collect(Collectors.toMap(IQuote::getSymbol, Function.identity())));
    }
}
