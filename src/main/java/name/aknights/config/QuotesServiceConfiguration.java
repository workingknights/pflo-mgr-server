package name.aknights.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Optional;

public class QuotesServiceConfiguration {

    @NotEmpty
    private final String alphaVantageApiUrl;

    @NotEmpty
    private final String alphaVantageApiKey;

    @NotEmpty
    private final String barchartApiUrl;

    @NotEmpty
    private final String barchartApiKey;

    private final Long cacheTtl;

    private static final long DEFAULT_TTL = 600000;

    public QuotesServiceConfiguration(String alphaVantageApiUrl, String alphaVantageApiKey, String barchartApiUrl, String barchartApiKey) {
        this(alphaVantageApiUrl, alphaVantageApiKey, barchartApiUrl, barchartApiKey, DEFAULT_TTL);
    }

    public QuotesServiceConfiguration(@JsonProperty("alphaVantageApiUrl") String alphaVantageApiUrl,
                                      @JsonProperty("alphaVantageApiKey") String alphaVantageApiKey,
                                      @JsonProperty("barchartApiUrl") String barchartApiUrl,
                                      @JsonProperty("barchartApiKey") String barchartApiKey, @JsonProperty("cacheTtl") Long cacheTtl) {
        this.alphaVantageApiUrl = alphaVantageApiUrl;
        this.alphaVantageApiKey = alphaVantageApiKey;
        this.barchartApiUrl = barchartApiUrl;
        this.barchartApiKey = barchartApiKey;
        this.cacheTtl = cacheTtl;
    }

    public String getAlphaVantageApiUrl() {
        return alphaVantageApiUrl;
    }

    public String getAlphaVantageApiKey() {
        return alphaVantageApiKey;
    }

    public String getBarchartApiUrl() {
        return barchartApiUrl;
    }

    public String getBarchartApiKey() {
        return barchartApiKey;
    }

    public Optional<Long> getCacheTtl() {
        return Optional.ofNullable(cacheTtl);
    }

}
