package name.aknights.core.quotes;

import java.util.Optional;

public class Quote {

    protected String symbol;
    protected String name;
    protected Double previousClose;
    protected Double lastPrice;
    protected Double percentChange;
    protected Double change;
    protected Double yearLow;
    protected Double yearHigh;

    public Quote() {
    }

    public Quote(String symbol, String name, Double previousClose, Double lastPrice, Double percentChange, Double change, Double yearLow, Double yearHigh) {
        this.symbol = symbol;
        this.name = name;
        this.previousClose = previousClose;
        this.lastPrice = lastPrice;
        this.percentChange = percentChange;
        this.change = change;
        this.yearLow = yearLow;
        this.yearHigh = yearHigh;
    }

    public String getSymbol() {
        return symbol.contains(".") ? symbol.substring(0, symbol.lastIndexOf('.')) : symbol;
    }

    public String getName() {
        return name;
    }

    public Optional<Double> getPreviousClose() {
        return Optional.ofNullable(previousClose);
    }

    public Double getPercentChange() {
        return percentChange;
    }

    public Double getChange() {
        return change;
    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public Double getYearLow() {
        return yearLow;
    }

    public Double getYearHigh() {
        return yearHigh;
    }
}
