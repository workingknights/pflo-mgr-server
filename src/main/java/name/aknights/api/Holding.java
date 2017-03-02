package name.aknights.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity("holdings")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Holding {

    @Id
    private ObjectId id;

    private String userId;

    @Reference
    private Ticker ticker;

    @Min(0)
    private int shares;

    @NotNull
    private Date tradeDate;

    @Min(0)
    private Double tradePrice;

    private Double commission;

    private Double initialMarketValue;

    @NotSaved
    private Double initialMarketValueBase;

    public Holding() {
    }

    /* For use in tests */
    public Holding(Ticker ticker, int shares, Date tradeDate, Double tradePrice, Double commission) {
        this.ticker = ticker;
        this.shares = shares;
        this.tradeDate = tradeDate;
        this.tradePrice = tradePrice;
        this.commission = commission;
        this.initialMarketValue = (shares * tradePrice) - ((commission != null) ? commission : 0.0);
    }

    @JsonProperty
    public String getId() {
        return (id != null) ? id.toHexString() : "";
    }

    @JsonProperty
    public void setId(String id) {
        this.id = (id == null || id.isEmpty()) ? null : new ObjectId(id);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty
    public Ticker getTicker() {
        return ticker;
    }

    @JsonProperty
    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    @JsonProperty
    public int getShares() {
        return shares;
    }

    @JsonProperty
    public Date getTradeDate() {
        return tradeDate;
    }

    @JsonProperty
    public Double getTradePrice() {
        return tradePrice;
    }

    @JsonProperty
    public Double getCommission() {
        return commission;
    }

    @JsonProperty
    public Double getInitialMarketValue() {
        return (shares * tradePrice);
    }

    @JsonProperty
    public Double getCost() {
        return getInitialMarketValue() + ((commission != null) ? commission : 0.0);
    }

    @JsonProperty
    public void setInitialMarketValue(Double initialMarketValue) {
        this.initialMarketValue = initialMarketValue;
    }

    @JsonProperty
    public void setShares(int shares) {
        this.shares = shares;
    }

    @JsonProperty
    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    @JsonProperty
    public void setTradePrice(Double tradePrice) {
        this.tradePrice = tradePrice;
    }

    @JsonProperty
    public void setCommission(Double commission) {
        this.commission = commission;
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

    @JsonProperty
    public void setInitialMarketValueBase(Double initialMarketValueBase) {
        this.initialMarketValueBase = initialMarketValueBase;
    }

    @JsonProperty
    public Double getInitialMarketValueBase() {
        return initialMarketValueBase;
    }
}