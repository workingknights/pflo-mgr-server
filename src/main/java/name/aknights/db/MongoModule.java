package name.aknights.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import dagger.Module;
import dagger.Provides;
import io.dropwizard.setup.Environment;
import name.aknights.config.DbConfiguration;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.inject.Singleton;
import java.util.Collections;

@Module
public class MongoModule {

    private final DbConfiguration config;
    private final Environment environment;

    public MongoModule(DbConfiguration config, Environment environment) {
        this.config = config;
        this.environment = environment;
    }

    @Singleton
    @Provides
    Datastore provideDatastore() {
        MongoClient mongoClient = provideMongoClient();
        environment.lifecycle().manage(new ManagedMongo(mongoClient));

        Morphia morphia = new Morphia();
        morphia.mapPackage(MongoModule.class.getPackage().getName());
        Datastore datastore = morphia.createDatastore(mongoClient, config.getDbName());
        return datastore;
    }

    @Singleton
    @Provides
    MongoClient provideMongoClient() {
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