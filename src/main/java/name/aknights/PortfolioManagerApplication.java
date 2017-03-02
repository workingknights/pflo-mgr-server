package name.aknights;

import com.github.toastshaman.dropwizard.auth.jwt.JwtAuthFilter;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import name.aknights.config.PortfolioManagerConfiguration;
import name.aknights.core.BasicAuthenticator;
import name.aknights.core.auth.BasicAuthorizer;
import name.aknights.core.auth.User;
import name.aknights.db.MongoModule;
import name.aknights.module.AuthModule;
import name.aknights.module.QuotesModule;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;

public class PortfolioManagerApplication extends Application<PortfolioManagerConfiguration> {
    private static final Logger logger = LoggerFactory.getLogger(PortfolioManagerApplication.class);

    public static void main(final String[] args) throws Exception {
        new PortfolioManagerApplication().run(args);
    }

    @Override
    public String getName() {
        return "PortfolioManager";
    }

    @Override
    public void initialize(final Bootstrap<PortfolioManagerConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor())
        );
//        bootstrap.addBundle(new AssetsBundle( "/assets/", "/", "index.html") );   // required to serve static web content from same dropwizard server
    }

    @Override
    public void run(final PortfolioManagerConfiguration configuration,
                    final Environment environment) throws Exception {
        PortfolioManagerComponent component;

        if (configuration.getEnv() == null || configuration.getEnv().equalsIgnoreCase("prod")) {
            component = DaggerPortfolioManagerComponent.builder()
                    .quotesModule(new QuotesModule(configuration.getQuotesConfig()))
                    .mongoModule(new MongoModule(configuration.getDbConfig(), environment))
                    .authModule(new AuthModule(configuration))
                    .build();
        }
        else {
            logger.info("*** Building Test Instance of PortfolioManager ***");
            component = DaggerTestPortfolioManagerComponent.builder()
                    .quotesModule(new QuotesModule(configuration.getQuotesConfig()))
                    .mongoModule(new MongoModule(configuration.getDbConfig(), environment))
                    .authModule(new AuthModule(configuration))
                    .build();
        }

        /* Auth start */
        AuthDynamicFeature authDynamicFeature = new AuthDynamicFeature(
                new JwtAuthFilter.Builder<User>()
                        .setJwtConsumer(component.getJwtConsumer())
                        .setRealm("realm")
                        .setPrefix("Bearer")
                        .setAuthorizer(new BasicAuthorizer())
                        .setAuthenticator(new BasicAuthenticator())
                        .buildAuthFilter());

        environment.jersey().register(authDynamicFeature);

        environment.jersey().register(RolesAllowedDynamicFeature.class);
        // To allow use of @Auth to inject a custom Principal type into resources
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(Principal.class));
        /* Auth end */

        environment.jersey().setUrlPattern("/api/*");

        environment.jersey().register(component.getHoldingsResource());
        environment.jersey().register(component.getPortfolioResource());
        environment.jersey().register(component.getModelResource());
        environment.jersey().register(component.getSandboxResource());
        environment.jersey().register(component.getTickerResource());

//        environment.jersey().register(LogRequestFeature.class);

        environment.healthChecks().register("MongoHealthCheck", component.getMongoHealthCheck());

        component.getDatastore().ensureIndexes();
    }

}
