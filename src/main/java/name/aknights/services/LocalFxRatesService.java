package name.aknights.services;

import java.util.HashMap;
import java.util.Map;

public class LocalFxRatesService implements FxRatesService {

    private static final Map<String, Double> rateMap = new HashMap<>();
    static {
        rateMap.put("GBP", 0.82);
        rateMap.put("GBp", 82.0);
        rateMap.put("USD", 1.0);
    }

    @Override
    public Double getRateToUsd(String fromCurrency) {
        return rateMap.get(fromCurrency);
    }
}
