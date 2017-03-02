package name.aknights.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Optional;

public class QuotesServiceConfiguration {

    @NotEmpty
    @JsonProperty
    private String yahooApiUrl;

    @NotEmpty
    @JsonProperty
    private String barchartApiUrl;

    @NotEmpty
    @JsonProperty
    private String barchartApiKey;

    @JsonProperty
    private Long cacheTtl;

    public QuotesServiceConfiguration() {
    }

    public QuotesServiceConfiguration(String yahooApiUrl, String barchartApiUrl, String barchartApiKey) {
        this.yahooApiUrl = yahooApiUrl;
        this.barchartApiUrl = barchartApiUrl;
        this.barchartApiKey = barchartApiKey;
    }

    public String getYahooApiUrl() {
        return yahooApiUrl;
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
