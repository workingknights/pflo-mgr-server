package name.aknights.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Length;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity("tickers")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class Ticker {
    @Id ObjectId id;

    @NotNull @Length(max = 7)
    @Indexed(options = @IndexOptions(unique = true)) String symbol;

    @NotNull @Length(min = 3, max = 3) String currency;
    @NotNull String exchange;
    String fullName;

    @JsonCreator
    public Ticker(@JsonProperty("id") String id, @JsonProperty("symbol") String symbol, @JsonProperty("currency") String currency,
                   @JsonProperty("exchange") String exchange, @JsonProperty("fullName") String fullName) {
        this.id = (id == null || id.isEmpty()) ? null : new ObjectId(id);
        this.symbol = symbol;
        this.currency = currency;
        this.exchange = exchange;
        this.fullName = fullName;
    }

    @JsonProperty
    public String getId() {
        return (id != null) ? id.toHexString() : "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticker ticker = (Ticker) o;

        return symbol != null ? symbol.equals(ticker.symbol) : ticker.symbol == null;
    }

    @Override
    public int hashCode() {
        return symbol != null ? symbol.hashCode() : 0;
    }
}
