package name.aknights.db;

import name.aknights.api.Model;
import name.aknights.api.ModelEntry;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;

public class ModelDAO extends BasicDAO<Model, ObjectId> {
    @Inject
    public ModelDAO(Datastore datastore) {
        super(datastore);
    }

    public Model getModelForUser(String userId) {
        Query<Model> query = this.getDatastore()
                .createQuery(Model.class)
                .filter("userId", userId);

        return findOne(query);
    }

    public Object saveAndReturn(Model model) {
        return save(model).getId();
    }
}
