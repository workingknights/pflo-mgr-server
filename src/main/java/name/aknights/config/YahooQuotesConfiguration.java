package name.aknights.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Optional;

public class YahooQuotesConfiguration {
    @NotEmpty
    @JsonProperty
    private String apiUrl;

    @JsonProperty
    private Long cacheTtl;

    public YahooQuotesConfiguration() {
    }

    /* Used for tests */
    public YahooQuotesConfiguration(String apiUrl) {
        this.apiUrl = apiUrl;
    }

//    @JsonCreator
//    public YahooQuotesConfiguration(@JsonProperty("apiUrl") String apiUrl) {
//        this.apiUrl = apiUrl;
//    }
//
//    @JsonCreator
//    public YahooQuotesConfiguration(@JsonProperty("apiUrl") String apiUrl, @JsonProperty("cacheTtl") long cacheTtl) {
//        this.apiUrl = apiUrl;
//        this.cacheTtl = cacheTtl;
//    }

    public String getApiUrl() {
        return apiUrl;
    }

    public Optional<Long> getCacheTtl() {
        return Optional.ofNullable(cacheTtl);
    }
}
