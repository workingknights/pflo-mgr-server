package name.aknights.core;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public enum Currency {
    GBP ("GBP", 1),
    GBp ("GBp", 0.01),
    USD ("USD", 1);

    private final String symbol;
    private final double factor;

    Currency(String symbol, double factor) {
        this.symbol = symbol;
        this.factor = factor;
    }

    public double getFactor() {
        return factor;
    }
}
