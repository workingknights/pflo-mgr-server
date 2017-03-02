package name.aknights.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Length;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import javax.validation.constraints.NotNull;

@Entity("tickers")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticker {
    @Id
    private ObjectId id;

    @NotNull
    @Length(max = 7)
    @Indexed(options = @IndexOptions(unique = true))
    private String symbol;

    @NotNull
    @Length(min = 3, max = 3)
    private String currency;

    @NotNull
    private String exchange;

    private String fullName;

    public Ticker() {
    }

    public Ticker(String symbol, String currency, String exchange, String fullName) {
        this.symbol = symbol;
        this.currency = currency;
        this.exchange = exchange;
        this.fullName = fullName;
    }

    public Ticker(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty
    public String getId() {
        return (id != null) ? id.toHexString() : "";
    }

    @JsonProperty
    public void setId(String id) {
        this.id = (id == null || id.isEmpty()) ? null : new ObjectId(id);
    }

    @JsonProperty
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    @JsonProperty
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @JsonProperty
    public String getCurrency() {
        return currency;
    }

    @JsonProperty
    public String getExchange() {
        return exchange;
    }

    @JsonProperty
    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "Ticker{" +
                "symbol='" + symbol + '\'' +
                ", currency='" + currency + '\'' +
                ", exchange='" + exchange + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
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
