package name.aknights.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Collections;

public class DataWrapper<T> {
    private final Collection<T> data;

    public DataWrapper(Collection<T> data) {
        this.data = data;
    }

    @JsonProperty
    public Collection<T> getData() {
        return Collections.unmodifiableCollection(this.data);
    }

}
