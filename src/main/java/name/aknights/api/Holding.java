package name.aknights.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.Tolerate;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.NotSaved;
import org.mongodb.morphia.annotations.Reference;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity("holdings")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class Holding {

    @Id
    @Getter(AccessLevel.NONE)
    private ObjectId id;

    @NotEmpty
    String userId;

    @Reference
    @NotEmpty
    Ticker ticker;

    @Min(0)
    int shares;

    @NotNull
    Date tradeDate;

    @Min(0)
    Double tradePrice;

    Double commission;

    Double initialMarketValue;

    @NotSaved
    Double initialMarketValueBase;
    
    @NotSaved
    Double cost;

    @JsonCreator
    public Holding(@JsonProperty("id") String id, @JsonProperty("userId") String userId, @JsonProperty("ticker") Ticker ticker,
                   @JsonProperty("shares") int shares, @JsonProperty("tradeDate") Date tradeDate, @JsonProperty("tradePrice") Double tradePrice,
                   @JsonProperty("commission") Double commission, @JsonProperty("initialMarketValue") Double initialMarketValue,
                   @JsonProperty("initialMarketValueBase") Double initialMarketValueBase) {
        this.id = (id == null || id.isEmpty()) ? null : new ObjectId(id);
        this.userId = userId;
        this.ticker = ticker;
        this.shares = shares;
        this.tradeDate = tradeDate;
        this.tradePrice = tradePrice;
        this.commission = commission;
        this.initialMarketValue = initialMarketValue;
        this.initialMarketValueBase = initialMarketValueBase;
        this.cost = initialMarketValue + ((commission != null) ? commission : 0.0);

    }

    /* For use in tests */
    @Tolerate
    public Holding(Ticker ticker, int shares, Date tradeDate, Double tradePrice, Double commission) {
        this.ticker = ticker;
        this.shares = shares;
        this.tradeDate = tradeDate;
        this.tradePrice = tradePrice;
        this.commission = commission;
        this.initialMarketValue = (shares * tradePrice) - ((commission != null) ? commission : 0.0);
        this.id = new ObjectId();
        this.userId = "";
        this.initialMarketValueBase = this.initialMarketValue;
        this.cost = initialMarketValue + ((commission != null) ? commission : 0.0);
    }

    @JsonProperty
    public String getId() {
        return (id != null) ? id.toHexString() : "";
    }

    public Double getCost() {
        return initialMarketValue + ((commission != null) ? commission : 0.0);
    }

    @Override
    public String toString() {
        return "Holding{" +
                "id=" + id +
                ", ticker ='" + ticker + '\'' +
                ", shares=" + shares +
                ", tradeDate=" + tradeDate +
                ", tradePrice=" + tradePrice +
                ", commission=" + commission +
                ", initialMarketValue=" + initialMarketValue +
                ", initialMarketValueBase=" + initialMarketValueBase +
                '}';
    }

}