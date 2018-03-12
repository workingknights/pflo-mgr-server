package name.aknights.api.quotes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

/**
    "2018-03-05": {
        "1. open": "287.0000",
        "2. high": "289.5000",
        "3. low": "286.5900",
        "4. close": "288.2000",
        "5. volume": "4822848"
    },
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlphaVantageDailyQuote implements IQuote {

    private String symbol;
    private String name;

    @JsonProperty("1. open")
    private double open;

    @JsonProperty("2. high")
    private double high;

    @JsonProperty("3. low")
    private double low;

    @JsonProperty("4. close")
    private double close;

    @JsonProperty("5. volume")
    @JsonIgnore
    private double volume;


    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSymbol() {
        return this.symbol;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Optional<Double> getPreviousClose() {
        return Optional.of(open);
    }

    @Override
    public Optional<Double> getPercentChange() {
        return Optional.of((close - open) / open);
    }

    @Override
    public Optional<Double> getChange() {
        return Optional.of(close - open);
    }

    @Override
    public Optional<Double> getLastPrice() {
        return Optional.of(close);
    }
}
