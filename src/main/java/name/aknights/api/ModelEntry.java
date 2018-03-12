package name.aknights.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.annotation.security.DenyAll;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class ModelEntry {

    @Length(max = 6)
    String ticker;

    @Min(0)
    Double portfolioWeight;

    public ModelEntry(String ticker, double weight) {
        this.ticker = ticker;
        this.portfolioWeight = weight;
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