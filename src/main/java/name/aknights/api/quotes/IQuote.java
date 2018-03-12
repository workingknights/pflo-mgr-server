package name.aknights.api.quotes;

import java.util.Optional;

public interface IQuote {

    String getSymbol();

    String getName();

    Optional<Double> getPreviousClose();

    Optional<Double> getPercentChange();

    Optional<Double> getChange();

    Optional<Double> getLastPrice();

}
