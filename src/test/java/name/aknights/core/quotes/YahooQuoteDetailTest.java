package name.aknights.core.quotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class YahooQuoteDetailTest {

    @Test
    public void withPositivePercentChangeStringsTheNumericValuesArePositive() {
        YahooQuoteDetail quoteDetail = new YahooQuoteDetail();
        quoteDetail.setPercentChange("+5.0%");
        quoteDetail.setChange("+2.5");
        assertEquals(0.05, quoteDetail.getPercentChange(), 0.0);
        assertEquals(2.5, quoteDetail.getChange(), 0.0);
    }

    @Test
    public void withNegativePercentChangeStringsTheNumericValuesAreNegative() {
        YahooQuoteDetail quoteDetail = new YahooQuoteDetail();
        quoteDetail.setPercentChange("-4.0%");
        quoteDetail.setChange("-3.75");
        assertEquals(-0.04, quoteDetail.getPercentChange(), 0.0);
        assertEquals(-3.75, quoteDetail.getChange(), 0.0);
    }

    @Test
    public void isCreatedIfJsonIsValid() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        YahooQuoteDetail quoteDetail = mapper.readValue(jsonString, YahooQuoteDetail.class);

        assertEquals("VWO", quoteDetail.getSymbol());
        assertEquals(36.40, quoteDetail.getPreviousClose().get(), 0.0);
    }


    private String jsonString = "" +
            "{\n" +
            "          \"symbol\": \"VWO\",\n" +
            "          \"Ask\": \"36.81\",\n" +
            "          \"AverageDailyVolume\": \"16184400\",\n" +
            "          \"Bid\": \"36.76\",\n" +
            "          \"AskRealtime\": null,\n" +
            "          \"BidRealtime\": null,\n" +
            "          \"BookValue\": \"0.00\",\n" +
            "          \"Change_PercentChange\": \"+0.37 - +1.02%\",\n" +
            "          \"Change\": \"+0.37\",\n" +
            "          \"Commission\": null,\n" +
            "          \"Currency\": \"USD\",\n" +
            "          \"ChangeRealtime\": null,\n" +
            "          \"AfterHoursChangeRealtime\": null,\n" +
            "          \"DividendShare\": \"1.54\",\n" +
            "          \"LastTradeDate\": \"12/13/2016\",\n" +
            "          \"TradeDate\": null,\n" +
            "          \"EarningsShare\": \"-8.20\",\n" +
            "          \"ErrorIndicationreturnedforsymbolchangedinvalid\": null,\n" +
            "          \"EPSEstimateCurrentYear\": null,\n" +
            "          \"EPSEstimateNextYear\": null,\n" +
            "          \"EPSEstimateNextQuarter\": \"0.00\",\n" +
            "          \"DaysLow\": \"36.60\",\n" +
            "          \"DaysHigh\": \"36.85\",\n" +
            "          \"YearLow\": \"27.98\",\n" +
            "          \"YearHigh\": \"38.87\",\n" +
            "          \"HoldingsGainPercent\": null,\n" +
            "          \"AnnualizedGain\": null,\n" +
            "          \"HoldingsGain\": null,\n" +
            "          \"HoldingsGainPercentRealtime\": null,\n" +
            "          \"HoldingsGainRealtime\": null,\n" +
            "          \"MoreInfo\": null,\n" +
            "          \"OrderBookRealtime\": null,\n" +
            "          \"MarketCapitalization\": null,\n" +
            "          \"MarketCapRealtime\": null,\n" +
            "          \"EBITDA\": null,\n" +
            "          \"ChangeFromYearLow\": \"8.79\",\n" +
            "          \"PercentChangeFromYearLow\": \"+31.42%\",\n" +
            "          \"LastTradeRealtimeWithTime\": null,\n" +
            "          \"ChangePercentRealtime\": null,\n" +
            "          \"ChangeFromYearHigh\": \"-2.10\",\n" +
            "          \"PercebtChangeFromYearHigh\": \"-5.40%\",\n" +
            "          \"LastTradeWithTime\": \"6:30pm - <b>36.77</b>\",\n" +
            "          \"LastTradePriceOnly\": \"36.77\",\n" +
            "          \"HighLimit\": null,\n" +
            "          \"LowLimit\": null,\n" +
            "          \"DaysRange\": \"36.60 - 36.85\",\n" +
            "          \"DaysRangeRealtime\": null,\n" +
            "          \"FiftydayMovingAverage\": \"36.54\",\n" +
            "          \"TwoHundreddayMovingAverage\": \"36.60\",\n" +
            "          \"ChangeFromTwoHundreddayMovingAverage\": \"0.17\",\n" +
            "          \"PercentChangeFromTwoHundreddayMovingAverage\": \"+0.46%\",\n" +
            "          \"ChangeFromFiftydayMovingAverage\": \"0.23\",\n" +
            "          \"PercentChangeFromFiftydayMovingAverage\": \"+0.63%\",\n" +
            "          \"Name\": \"Vanguard FTSE Emerging Markets\",\n" +
            "          \"Notes\": null,\n" +
            "          \"Open\": \"36.65\",\n" +
            "          \"PreviousClose\": \"36.40\",\n" +
            "          \"PricePaid\": null,\n" +
            "          \"ChangeinPercent\": \"+1.02%\",\n" +
            "          \"PriceSales\": null,\n" +
            "          \"PriceBook\": null,\n" +
            "          \"ExDividendDate\": null,\n" +
            "          \"PERatio\": null,\n" +
            "          \"DividendPayDate\": null,\n" +
            "          \"PERatioRealtime\": null,\n" +
            "          \"PEGRatio\": \"0.00\",\n" +
            "          \"PriceEPSEstimateCurrentYear\": null,\n" +
            "          \"PriceEPSEstimateNextYear\": null,\n" +
            "          \"Symbol\": \"VWO\",\n" +
            "          \"SharesOwned\": null,\n" +
            "          \"ShortRatio\": null,\n" +
            "          \"LastTradeTime\": \"6:30pm\",\n" +
            "          \"TickerTrend\": null,\n" +
            "          \"OneyrTargetPrice\": null,\n" +
            "          \"Volume\": \"8788533\",\n" +
            "          \"HoldingsValue\": null,\n" +
            "          \"HoldingsValueRealtime\": null,\n" +
            "          \"YearRange\": \"27.98 - 38.87\",\n" +
            "          \"DaysValueChange\": null,\n" +
            "          \"DaysValueChangeRealtime\": null,\n" +
            "          \"StockExchange\": \"PCX\",\n" +
            "          \"DividendYield\": \"4.80\",\n" +
            "          \"PercentChange\": \"+1.02%\"\n" +
            "        }";
}