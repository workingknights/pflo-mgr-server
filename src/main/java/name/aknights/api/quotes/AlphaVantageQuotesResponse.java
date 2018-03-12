package name.aknights.api.quotes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.HashMap;

/*
{
    "Meta Data": {
        "1. Information": "Daily Prices (open, high, low, close) and Volumes",
        "2. Symbol": "MKS.L",
        "3. Last Refreshed": "2018-03-05",
        "4. Output Size": "Compact",
        "5. Time Zone": "US/Eastern"
    },
    "Time Series (Daily)": {
        "2018-03-05": {
            "1. open": "287.0000",
            "2. high": "289.5000",
            "3. low": "286.5900",
            "4. close": "288.2000",
            "5. volume": "4822848"
        },
        "2018-03-02": {
            "1. open": "289.9000",
            "2. high": "291.3000",
            "3. low": "286.2000",
            "4. close": "286.7000",
            "5. volume": "8340815"
        },
        ...
    }
}
 */
@JsonIgnoreProperties({ "Meta Data" })
public class AlphaVantageQuotesResponse {

    @JsonProperty("Time Series (Daily)")
    private HashMap<String, AlphaVantageDailyQuote> timeSeries;

    public HashMap<String, AlphaVantageDailyQuote> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(HashMap<String, AlphaVantageDailyQuote> timeSeries) {
        this.timeSeries = timeSeries;
    }

    public Collection<AlphaVantageDailyQuote> getResults() {
        return timeSeries.values();
    }

}
