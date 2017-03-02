package name.aknights;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import name.aknights.api.Holding;
import name.aknights.api.Ticker;
import name.aknights.config.DbConfiguration;
import name.aknights.db.HoldingDAO;
import name.aknights.db.MongoModule;
import name.aknights.db.TickerDAO;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DbMigrate {

    public static void main(String[] args) {
        DbMigrate migrator = new DbMigrate();
        DbConfiguration config = new DbConfiguration("pflomgr", 27017, "localhost", "pflomgr", args[0]);
//        DbConfiguration config = new DbConfiguration("heroku_n91f12d8", 27968, "ds127968.mlab.com", "heroku_n91f12d8", args[0]);

        migrator.run(config);
    }

    private void run(DbConfiguration config) {
        Datastore datastore = provideDatastore(config);

        HoldingDAO holdingDAO = new HoldingDAO(datastore);
//        TickerDAO tickerDAO = new TickerDAO(datastore);

        List<Holding> holdings = holdingDAO.find().asList();

        for (Holding holding : holdings) {
            holding.setUserId("google-oauth2|118300408301077615291");
            holdingDAO.save(holding);
        }
    }


    Datastore provideDatastore(DbConfiguration config) {
        MongoClient mongoClient = provideMongoClient(config);

        Morphia morphia = new Morphia();
        morphia.mapPackage(MongoModule.class.getPackage().getName());
        Datastore datastore = morphia.createDatastore(mongoClient, config.getDbName());
        return datastore;
    }

    MongoClient provideMongoClient(DbConfiguration config) {
        MongoClient mongoClient;
        if (config.getUser() != null) {
            // create authenticated connection
            mongoClient = new MongoClient(new ServerAddress(config.getHost(), config.getPort()),
                    Collections.singletonList(MongoCredential.createCredential(config
                            .getUser(), config.getDbName(), config.getPassword().toCharArray())));
        } else {
            // create unauthenticated connection
            mongoClient = new MongoClient(new ServerAddress(config.getHost(), config.getPort()));
        }
        return mongoClient;
    }
}
