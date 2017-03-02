package name.aknights.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;

public class Recommendation {
    public enum Direction { BUY, SELL, HOLD }

    private Direction direction;
    private Collection<String> contributors;

    public Recommendation() {
        this.direction = Direction.HOLD;
        contributors = new ArrayList<>();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @JsonProperty
    public String getDirection() {
        return direction.toString();
    }

    public void addContributor(String contributor) {
        this.contributors.add(contributor);
    }

    @JsonProperty
    public Collection<String> getContributors() {
        return contributors;
    }
}
