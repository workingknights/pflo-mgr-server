package name.aknights.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.HashSet;
import java.util.Set;

@Entity("models")
public class Model {
    @Id
    private ObjectId id;

    private String userId;

    @JsonProperty
    private Set<ModelEntry> entries = new HashSet<>();

    /**
     * For serializing
     */
    public Model() {
    }

    public Model(String userId) {
        this.userId = userId;
    }

    public Model(Set<ModelEntry> entries) {
        this.entries = entries;
    }

    @JsonProperty
    public String getId() {
        return (id != null) ? id.toHexString() : "";
    }

    @JsonProperty
    public void setId(String id) {
        this.id = null;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<ModelEntry> getEntries() {
        return entries;
    }

    public void setEntries(Set<ModelEntry> entries) {
        this.entries = entries;
    }
}
