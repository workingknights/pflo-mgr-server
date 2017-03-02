package name.aknights.db;

import name.aknights.api.Ticker;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

public class TickerDAO extends BasicDAO<Ticker, ObjectId> {
    @Inject
    public TickerDAO(Datastore datastore) {
        super(datastore);
    }

    public Optional<Ticker> getTicker(String symbol) {
        Query<Ticker> query = this.getDatastore()
                .createQuery(Ticker.class)
                .filter("symbol", symbol);

        return Optional.ofNullable(findOne(query));
    }

    public Collection<Ticker> getTickers() {
        return find().asList();
    }
}
