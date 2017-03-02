package name.aknights.core.quotes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

/**
 * {
 * "symbol":"VWO","Ask":null,"AverageDailyVolume":"16184400","Bid":null,"AskRealtime":null,"BidRealtime":null,"BookValue":"0.00",
 * "Change_PercentChange":null,"Change":null,"Commission":null,"Currency":"USD","ChangeRealtime":null,
 * "AfterHoursChangeRealtime":null,"DividendShare":"1.54","LastTradeDate":"12/12/2016","TradeDate":null,
 * "EarningsShare":"-8.20","ErrorIndicationreturnedforsymbolchangedinvalid":null,"EPSEstimateCurrentYear":null,
 * "EPSEstimateNextYear":null,"EPSEstimateNextQuarter":"0.00","DaysLow":null,"DaysHigh":null,"YearLow":"27.98",
 * "YearHigh":"38.87","HoldingsGainPercent":null,"AnnualizedGain":null,"HoldingsGain":null,
 * "HoldingsGainPercentRealtime":null,"HoldingsGainRealtime":null,"MoreInfo":null,"OrderBookRealtime":null,
 * "MarketCapitalization":null,"MarketCapRealtime":null,"EBITDA":null,"ChangeFromYearLow":"8.42",
 * "PercentChangeFromYearLow":"+30.09%","LastTradeRealtimeWithTime":null,"ChangePercentRealtime":null,
 * "ChangeFromYearHigh":"-2.47","PercebtChangeFromYearHigh":"-6.35%",
 * "LastTradeWithTime":"4:00pm - <b>36.40</b>","LastTradePriceOnly":"36.40","HighLimit":null,
 * "LowLimit":null,"DaysRange":null,"DaysRangeRealtime":null,"FiftydayMovingAverage":"36.54",
 * "TwoHundreddayMovingAverage":"36.60","ChangeFromTwoHundreddayMovingAverage":"-0.20",
 * "PercentChangeFromTwoHundreddayMovingAverage":"-0.55%","ChangeFromFiftydayMovingAverage":"-0.14",
 * "PercentChangeFromFiftydayMovingAverage":"-0.38%","Name":"Vanguard FTSE Emerging Markets","Notes":null,
 * "Open":null,"PreviousClose":"36.40","PricePaid":null,"ChangeinPercent":null,"PriceSales":null,
 * "PriceBook":null,"ExDividendDate":null,"PERatio":null,"DividendPayDate":null,"PERatioRealtime":null,
 * "PEGRatio":"0.00","PriceEPSEstimateCurrentYear":null,"PriceEPSEstimateNextYear":null,"Symbol":"VWO",
 * "SharesOwned":null,"ShortRatio":null,"LastTradeTime":"4:00pm","TickerTrend":null,"OneyrTargetPrice":null,
 * "Volume":"0","HoldingsValue":null,"HoldingsValueRealtime":null,"YearRange":"27.98 - 38.87",
 * "DaysValueChange":null,"DaysValueChangeRealtime":null,"StockExchange":"PCX","DividendYield":"4.80",
 * "PercentChange":null
 * }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YahooQuoteDetail extends Quote {

   /* @JsonProperty("Open")
    private Double open;

    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("Ask")
    private Double ask;

    @JsonProperty("FiftydayMovingAverage")
    private Double ma50Day;

    @JsonProperty("TwoHundreddayMovingAverage")
    private Double ma200Day;

    @JsonProperty("PercentChangeFromYearLow")
    private String percentChangeFromYearLow;

    @JsonProperty("PercebtChangeFromYearHigh")
    private String percentChangeFromYearHigh;*/

    YahooQuoteDetail() {
    }


    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("LastTradePriceOnly")
    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    @JsonProperty("PreviousClose")
    public void setPreviousClose(Double previousClose) {
        this.previousClose = previousClose;
    }

    @JsonProperty("PercentChange")
    public void setPercentChange(String percentChange) {
        if (percentChange.startsWith("+"))
            this.percentChange = Double.parseDouble(percentChange.substring(1, percentChange.length() - 1)) / 100;
        else
            this.percentChange = Double.parseDouble(percentChange.substring(0, percentChange.length() - 1)) / 100;
    }

    @JsonProperty("Change")
    public void setChange(String change) {
        if (change.startsWith("+"))
            this.change = Double.parseDouble(change.substring(1, change.length()));
        else
            this.change = Double.parseDouble(change.substring(0, change.length()));

    }

    @JsonProperty("YearLow")
    public void setYearLow(Double yearLow) {
        this.yearLow = yearLow;
    }

    @JsonProperty("YearHigh")
    public void setYearHigh(Double yearHigh) {
        this.yearHigh = yearHigh;
    }
}
