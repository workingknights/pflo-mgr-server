package name.aknights.api;

import name.aknights.core.Recommendation;

public class PortfolioEntry {
    private String ticker;
    private String name;
    private Integer totalShares;
    private Double percentChange;
    private Double dailyGainBase;
    private Double avgUnitCost;
    private Double totalCost;
    private Double marketValue;
    private Double marketValueBase;
    private Double totalPercentGain;
    private Double totalGain;
    private String currency;
    private Double totalGainBase;
    private Double yearLow;
    private Double yearHigh;
    private Recommendation recommendation;
    private Double rebalToModel;
    private Integer rebalShares;
    private Double currPrice;
    private Double ma50Day;
    private Double ma200Day;

    public PortfolioEntry() {
    }

    public PortfolioEntry(String ticker, String name, Integer totalShares, String currency, Double currPrice,
                          Double percentChange, Double dailyGainBase, Double totalCost,
                          Double marketValue, Double marketValueBase, Double totalPercentGain, Double totalGain,
                          Double totalGainBase, Double yearLow, Double yearHigh, Double avgUnitCost, Recommendation recommendation) {
        this.ticker = ticker;
        this.name = name;
        this.totalShares = totalShares;
        this.avgUnitCost = avgUnitCost;
        this.currency = currency;
        this.currPrice = currPrice;
        this.ma50Day = ma50Day;
        this.ma200Day = ma200Day;
        this.percentChange = percentChange;
        this.dailyGainBase = dailyGainBase;
        this.totalCost = totalCost;
        this.marketValue = marketValue;
        this.marketValueBase = marketValueBase;
        this.totalPercentGain = totalPercentGain;
        this.totalGain = totalGain;
        this.totalGainBase = totalGainBase;
        this.yearLow = yearLow;
        this.yearHigh = yearHigh;
        this.recommendation = recommendation;
    }

    public String getTicker() {
        return ticker;
    }

    public String getName() {
        return name;
    }

    public Integer getTotalShares() {
        return totalShares;
    }

    public Double getAvgUnitCost() {
        return avgUnitCost;
    }

    public Double getPercentChange() {
        return percentChange;
    }

    public Double getDailyGainBase() {
        return dailyGainBase;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public Double getMarketValue() {
        return marketValue;
    }

    public Double getMarketValueBase() {
        return marketValueBase;
    }

    public Double getTotalPercentGain() {
        return totalPercentGain;
    }

    public Double getTotalGain() {
        return totalGain;
    }

    public Double getTotalGainBase() {
        return totalGainBase;
    }

    public String getCurrency() {
        return currency;
    }

    public Double getYearLow() {
        return yearLow;
    }

    public Double getYearHigh() {
        return yearHigh;
    }

    public Double getCurrPrice() {
        return currPrice;
    }

    public Double getMa50Day() {
        return ma50Day;
    }

    public Double getMa200Day() {
        return ma200Day;
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public void setRebalToModel(Double rebalToModel) {
        this.rebalToModel = rebalToModel;
    }

    public Double getRebalToModel() {
        return rebalToModel;
    }

    public Integer getRebalShares() {
        return rebalShares;
    }

    public void setRebalShares(Integer rebalShares) {
        this.rebalShares = rebalShares;
    }
}
