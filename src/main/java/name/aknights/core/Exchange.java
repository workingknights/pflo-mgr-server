package name.aknights.core;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public enum Exchange {
    NYSEARCA, LSE, NONE
}
