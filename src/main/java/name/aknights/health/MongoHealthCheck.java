package name.aknights.health;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;

import javax.inject.Inject;

public class MongoHealthCheck extends HealthCheck {
    private MongoClient mongoClient;

    @Inject
    public MongoHealthCheck(MongoClient mongo) {
        this.mongoClient = mongo;
    }

    @Override
    protected Result check() throws Exception {
        mongoClient.listDatabaseNames();
        return Result.healthy();
    }

}