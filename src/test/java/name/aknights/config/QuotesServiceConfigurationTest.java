package name.aknights.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class QuotesServiceConfigurationTest {

    private QuotesServiceConfiguration config;
    private String alphaVantageApiKey;
    private String alphaVantageApiUrl;
    private String barchartApiUrl;
    private String barchartApiKey;

    @Before
    public void setup() {
        barchartApiUrl = "http://marketdata.websol.barchart.com/getQuote.json";
        barchartApiKey = "440c3382a95ba20d096e2c34b6493ca1";
        alphaVantageApiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY";
        alphaVantageApiKey = "JXWZII6BBC1PKBPE";
        config = new QuotesServiceConfiguration(alphaVantageApiUrl, alphaVantageApiKey, barchartApiUrl, barchartApiKey);
    }

    @Test
    public void whenAccessorsCalledValueAreReturned() throws Exception {
        assertEquals(barchartApiKey, config.getBarchartApiKey());
        assertEquals(barchartApiUrl, config.getBarchartApiUrl());
        assertEquals(alphaVantageApiUrl, config.getAlphaVantageApiUrl());
        assertEquals(alphaVantageApiKey, config.getAlphaVantageApiKey());
    }

    @Test
    public void whenInitialisedFromConfigValuesAreInstantiatedCorrectly() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream configYml = this.getClass().getResourceAsStream("/quotesSvcConfig.yml");
        QuotesServiceConfiguration config = mapper.readValue(configYml, QuotesServiceConfiguration.class);

        assertEquals(barchartApiUrl, config.getBarchartApiUrl());
        assertEquals(barchartApiKey, config.getBarchartApiKey());
        assertEquals(alphaVantageApiUrl, config.getAlphaVantageApiUrl());
        assertEquals(alphaVantageApiKey, config.getAlphaVantageApiKey());
        assertEquals(Optional.of(50000l).get(), config.getCacheTtl().get());
    }
}