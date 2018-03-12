package name.aknights.api.quotes;

import java.util.Optional;

public class Quote implements IQuote {

    protected String symbol;
    protected String name;
    protected Double previousClose;
    protected Double lastPrice;
    protected Double percentChange;
    protected Double change;

    public Quote() {
    }

    public Quote(String symbol, String name, Double previousClose, Double lastPrice, Double percentChange, Double change) {
        this.symbol = symbol;
        this.name = name;
        this.previousClose = previousClose;
        this.lastPrice = lastPrice;
        this.percentChange = percentChange;
        this.change = change;
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

    public Optional<Double> getPercentChange() {
        return Optional.ofNullable(percentChange);
    }

    public Optional<Double> getChange() {
        return Optional.ofNullable(change);
    }

    public Optional<Double> getLastPrice() {
        return Optional.ofNullable(lastPrice);
    }
}
