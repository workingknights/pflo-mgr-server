package name.aknights;

import dagger.Component;
import name.aknights.db.MongoModule;
import name.aknights.health.MongoHealthCheck;
import name.aknights.module.AuthModule;
import name.aknights.module.QuotesModule;
import name.aknights.resources.HoldingsResource;
import name.aknights.resources.ModelResource;
import name.aknights.resources.PortfolioResource;
import name.aknights.resources.SandboxResource;
import name.aknights.resources.TickerResource;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.mongodb.morphia.Datastore;

import javax.inject.Singleton;

@Singleton
@Component(modules = {PortfolioManagerModule.class, QuotesModule.class, AuthModule.class, MongoModule.class})
public interface PortfolioManagerComponent {
    // Resources
    HoldingsResource getHoldingsResource();
    PortfolioResource getPortfolioResource();
    ModelResource getModelResource();
    SandboxResource getSandboxResource();
    TickerResource getTickerResource();

    Datastore getDatastore();

    MongoHealthCheck getMongoHealthCheck();

    JwtConsumer getJwtConsumer();
}
