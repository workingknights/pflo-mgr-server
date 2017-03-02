package name.aknights.services;

import name.aknights.api.Holding;
import name.aknights.api.Model;
import name.aknights.api.ModelEntry;
import name.aknights.api.Portfolio;
import name.aknights.api.PortfolioEntry;
import name.aknights.api.Ticker;
import name.aknights.core.Recommendation;
import name.aknights.core.quotes.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;
import static name.aknights.core.Recommendation.Direction.BUY;
import static name.aknights.core.Recommendation.Direction.SELL;

public class PortfolioService {

    private static final Logger logger = LoggerFactory.getLogger(PortfolioService.class);

    private final QuotesService quotesService;
    private final FxRatesService fxRatesService;
    private final HoldingsService holdingsService;
    private final ModelService modelService;

    @Inject
    public PortfolioService(QuotesService quotesService, FxRatesService fxRatesService, HoldingsService holdingsService, ModelService modelService) {
        this.quotesService = quotesService;
        this.fxRatesService = fxRatesService;
        this.holdingsService = holdingsService;
        this.modelService = modelService;
    }

    public Portfolio getPortfolio(String userId) {

        Portfolio portfolio = getBasicPortfolio(userId);
        updateRequiredRebalances(portfolio.getEntries(), portfolio.getSummary().getMarketValueBase(), modelService.getModelForUser(userId));

        return portfolio;
    }

    Portfolio getBasicPortfolio(String userId) {
//        long start = System.currentTimeMillis();
        Collection<Holding> holdings = holdingsService.getHoldings(userId);
//        logger.debug("retrieve Holdings took {} ms", System.currentTimeMillis() - start);

//        start = System.currentTimeMillis();
        Map<Ticker, List<Holding>> holdingsMap =
                holdings.stream().collect(groupingBy(Holding::getTicker));

        Map<String, List<Holding>> holdingsMapBySymbol =
                holdings.stream().collect(groupingBy(h -> h.getTicker().getSymbol()));

//        logger.debug("create maps of Holdings took {} ms", System.currentTimeMillis() - start);

//        start = System.currentTimeMillis();
        Collection<Quote> quotes = quotesService.getQuotes(holdingsMap.keySet());
//        logger.debug("retrieve Quotes took {} ms", System.currentTimeMillis() - start);

//        start = System.currentTimeMillis();
        LinkedList<PortfolioEntry> entries = new LinkedList<>();
        quotes.forEach(q -> {
            PortfolioEntry entry = createEntry(holdingsMapBySymbol.get(q.getSymbol()), q);
            entries.add(entry);
        });
//        logger.debug("create Portfolio Entries took {} ms", System.currentTimeMillis() - start);

//        start = System.currentTimeMillis();
        PortfolioEntry summary = createSummaryRow(entries);
//        logger.debug("create Portfolio Summary took {} ms", System.currentTimeMillis() - start);

        return new Portfolio(entries, summary);
    }

    void updateRequiredRebalances(Collection<PortfolioEntry> portfolioEntries, double totalMarketValue, Model model) {
        for(PortfolioEntry entry: portfolioEntries) {
            Optional<ModelEntry> modelEntry;
            if (model != null && model.getEntries() != null)
                modelEntry = model.getEntries().stream().filter(m -> m.getTicker().equals(entry.getTicker())).findFirst();
            else
                modelEntry = Optional.empty();

            if (modelEntry.isPresent()) {
                double targetMV = totalMarketValue * (modelEntry.get().getPortfolioWeight());
                double rebal = targetMV - entry.getMarketValueBase();
                entry.setRebalToModel(rebal);

                int rebalShares = (int) (rebal / (entry.getCurrPrice() * fxRatesService.getRateToUsd(entry.getCurrency())));
                entry.setRebalShares(rebalShares);
            }
            else {
                entry.setRebalToModel(0.0);
                entry.setRebalShares(0);
            }
        }
    }

    private PortfolioEntry createSummaryRow(Collection<PortfolioEntry> entries) {
        Double totalMVBase = entries.stream().mapToDouble(PortfolioEntry::getMarketValueBase).sum();
        Double totalCost = entries.stream().mapToDouble(PortfolioEntry::getTotalCost).sum();
        Double overallTotalGain = entries.stream().mapToDouble(PortfolioEntry::getTotalGain).sum();
        Double overallTotalGainBase = entries.stream().mapToDouble(PortfolioEntry::getTotalGainBase).sum();
        Double overallTotalPercentGain = overallTotalGain / totalCost;
        Double overallDailyGain = entries.stream().mapToDouble(PortfolioEntry::getDailyGainBase).sum();

        return new PortfolioEntry(null,  null, null, null,
                null, null, overallDailyGain, totalCost, null,
                totalMVBase, overallTotalPercentGain,null, overallTotalGainBase, null, null, null, null);
    }

    PortfolioEntry createEntry(List<Holding> holdings, Quote q) {
        String currency = holdings.get(0).getTicker().getCurrency();
        Double fxRateToBase = fxRatesService.getRateToUsd(currency);
        double currPrice = getCurrentPrice(q);

        int totalNumShares = holdings.stream().mapToInt(Holding::getShares).sum();
        double currMarketValue = totalNumShares * currPrice;
        double currMarketValueBase = currMarketValue * fxRateToBase;

        double totalCost = getTotalCost(holdings, q);

        double avgUnitCost = totalCost / totalNumShares;

        double dailyGainBase  = q.getChange() * totalNumShares * fxRateToBase;

        double totalGain = currMarketValue - totalCost;
        double totalGainBase = totalGain * fxRateToBase;
        double totalPercentGain = totalGain / totalCost;

        Recommendation recommendation = calcRecommendation(q);

        return new PortfolioEntry(q.getSymbol(), q.getName(), totalNumShares,
                currency, currPrice, q.getPercentChange(), dailyGainBase, totalCost, currMarketValue,
                currMarketValueBase, totalPercentGain, totalGain, totalGainBase, q.getYearLow(), q.getYearHigh(), avgUnitCost,
                recommendation);
    }


    Recommendation calcRecommendation(Quote quote) {

        Recommendation recommendation = new Recommendation();

        int score = 5;

//        score += checkGoldenCross(ma50Day, ma200Day, recommendation);

        score += checkDailyChange(quote.getPercentChange(), recommendation);

        score += checkMoveFromYearLowHigh(quote.getLastPrice(), quote.getYearLow(), quote.getYearHigh(), recommendation);

        if (score > 6) recommendation.setDirection(BUY);
        else if (score < 3) recommendation.setDirection(SELL);

        return recommendation;
    }

    int checkGoldenCross(Double ma50Day, Double ma200Day, Recommendation recommendation) {
        int adjust = 0;

        double percentOver200DayMA = (ma50Day - ma200Day) / ma200Day * 100;

        if(percentOver200DayMA < -10) {
            recommendation.addContributor("50 Day MA significantly below 200 Day MA");
            adjust = -2;
        }
        else if(percentOver200DayMA <= -6 && percentOver200DayMA >= -10) {
            recommendation.addContributor("50 Day MA below 200 Day MA");
            adjust = -1;
        }
        else if(percentOver200DayMA >= 5 && percentOver200DayMA < 15) {
            recommendation.addContributor("In a Golden Cross");
            adjust = 2;
        }
        else if(percentOver200DayMA >= 25) {
            adjust = -1;
        }

        return adjust;
    }

    int checkMoveFromYearLowHigh(Double lastPrice, Double yearLow, Double yearHigh, Recommendation recommendation) {
        int adjust = 0;

        double percentChangeFromYearHigh = (lastPrice- yearHigh) / yearHigh;
        double percentChangeFromYearLow = (lastPrice - yearLow) / yearLow;

        if (percentChangeFromYearLow >= 0 && percentChangeFromYearLow < 0.07) {
            recommendation.addContributor("Very close to Year Low");
            adjust += 4;
        }
        else if (percentChangeFromYearLow >= 0.07 && percentChangeFromYearLow < 0.11) {
            recommendation.addContributor("Close to Year Low");
            adjust += 2;
        }
        else if (percentChangeFromYearLow >= 0.12 && percentChangeFromYearLow < 0.25) adjust += 1;

        if (percentChangeFromYearHigh <= 0.0 && percentChangeFromYearHigh > -0.03) {
            recommendation.addContributor("Very close to Year High");
            adjust -=2;
        }
        else if (percentChangeFromYearHigh <= -0.03 && percentChangeFromYearHigh > -0.06) adjust -=1;

        return adjust;
    }

    int checkDailyChange(Double percentChange, Recommendation recommendation) {
        int adjust = 0;

        if (percentChange >= 0.0 && percentChange < 0.01) {
            recommendation.addContributor("Small positive change in price");
            adjust = 1;
        }
        else if (percentChange >= 0.01) {
            recommendation.addContributor("Too large a positive change in price");
            adjust = -1;
        }
        return adjust;
    }

    private Double getCurrentPrice(Quote q) {
        return Optional.ofNullable(q.getLastPrice()).orElse(
                        q.getPreviousClose().orElse(0.0));
    }

    private double getTotalCost(List<Holding> holdings, Quote q) {
        return holdings.stream().mapToDouble(Holding::getCost).sum();
    }

}
