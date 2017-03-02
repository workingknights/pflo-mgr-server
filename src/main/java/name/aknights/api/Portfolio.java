package name.aknights.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

public class Portfolio {
    @JsonProperty
    private Collection<PortfolioEntry> entries;
    @JsonProperty
    private PortfolioEntry summary;

    /**
     * For serializing
     */
    public Portfolio() {
    }

    public Portfolio(Collection<PortfolioEntry> entries, PortfolioEntry summary) {
        this.entries = entries;
        this.summary = summary;
    }

    public Collection<PortfolioEntry> getEntries() {
        return entries;
    }

    public PortfolioEntry getSummary() {
        return summary;
    }
}
