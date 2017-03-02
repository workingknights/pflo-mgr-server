package name.aknights;

import dagger.Component;
import name.aknights.db.MongoModule;
import name.aknights.health.MongoHealthCheck;
import name.aknights.module.AuthModule;
import name.aknights.module.QuotesModule;
import org.mongodb.morphia.Datastore;

import javax.inject.Singleton;

@Singleton
@Component(modules = {PortfolioManagerModule.class, QuotesModule.class, AuthModule.class, MongoModule.class})
public interface TestPortfolioManagerComponent extends PortfolioManagerComponent {
    // Resources
    Datastore getDatastore();

    MongoHealthCheck getMongoHealthCheck();
}
