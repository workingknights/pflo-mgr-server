package name.aknights.api.quotes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

/**
 * {
 "symbol": "CPJ1.LS",
 "exchange": "LSE",
 "name": "Ishares Vii Plc",
 "dayCode": "B",
 "serverTimestamp": "2017-01-12T20:09:31-06:00",
 "mode": "d",
 "lastPrice": 10136.5,
 "tradeTimestamp": "2017-01-12T18:00:00-06:00",
 "netChange": -30.5,
 "percentChange": -0.3,
 "previousLastPrice": 10167,
 "unitCode": "3",
 "open": 10094,
 "high": 10136.5,
 "low": 10069,
 "close": 10136.5,
 "flag": "s",
 "previousClose": 10167,
 "volume": 2968,
 "fiftyTwoWkHigh": 10210,
 "fiftyTwoWkLow": 6516
 }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BarchartQuoteDetail extends Quote {

    public BarchartQuoteDetail() {
    }

    @JsonProperty
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public void setPreviousClose(Double previousClose) {
        this.previousClose = previousClose;
    }

    @JsonProperty
    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    @JsonProperty
    public void setPercentChange(Double percentChange) {
        this.percentChange = percentChange;
    }

    @Override
    public Optional<Double> getPercentChange() {
        return Optional.of(super.getPercentChange().orElse(1.0)/100);
    }

    @JsonProperty("netChange")
    public void setChange(Double change) {
        this.change = change;
    }

}
