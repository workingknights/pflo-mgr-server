package name.aknights.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.Collection;
import java.util.Collections;

@Value
public class Portfolio {
    @JsonProperty
    Collection<PortfolioEntry> entries;
    @JsonProperty
    PortfolioEntry summary;

    public Collection<PortfolioEntry> getEntries() {
        return Collections.unmodifiableCollection(entries);
    }
}
