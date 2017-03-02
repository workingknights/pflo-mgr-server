package name.aknights.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;

public class Data<T> {
    private Collection<T> data;

    public Data(Collection<T> payload) {
        this.data = payload;
    }

    public Data(T payload) {
        this.data = new ArrayList<>();
        data.add(payload);
    }

    @JsonProperty
    public Collection<T> getData() {
        return data;
    }

}
