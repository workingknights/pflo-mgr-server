package name.aknights.api;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

public class ModelEntry {
    @Length(max = 6)
    private String ticker;

    @Min(0)
    private Double portfolioWeight;

   public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Double getPortfolioWeight() {
        return portfolioWeight;
    }

    public void setPortfolioWeight(Double portfolioWeight) {
        this.portfolioWeight = portfolioWeight;
    }

    @Override
    public String toString() {
        return "ModelEntry{" +
                ", ticker='" + ticker + '\'' +
                ", portfolioWeight=" + portfolioWeight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModelEntry that = (ModelEntry) o;

        return ticker.equals(that.ticker);
    }

    @Override
    public int hashCode() {
        return ticker.hashCode();
    }
}