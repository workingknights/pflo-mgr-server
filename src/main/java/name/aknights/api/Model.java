package name.aknights.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Collections;
import java.util.Set;

@Entity("models")
@Data
@NoArgsConstructor
public class Model {
    @Id
    @Getter(AccessLevel.NONE)
    ObjectId id;

    String userId;

    Set<ModelEntry> entries;

    public Model(ObjectId objectId, String name, Set<ModelEntry> entries) {
        this.id = objectId;
        this.userId = name;
        this.entries = entries;
    }
//    Set<ModelEntry> entries = new HashSet<>();

//
//    public Model(String userId) {
//        this.userId = userId;
//    }
//
//    public Model(Set<ModelEntry> entries) {
//        this.entries = entries;
//    }

    public String getId() {
        return (id != null) ? id.toHexString() : "";
    }


    @JsonProperty
    public Set<ModelEntry> getEntries() {
        return Collections.unmodifiableSet(this.entries);
    }

//
//    public Set<ModelEntry> getEntries() {
//        return entries;
//    }
//
//    public void setEntries(Set<ModelEntry> entries) {
//        this.entries = entries;
//    }
}
