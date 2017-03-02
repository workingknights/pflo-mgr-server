package name.aknights;

import dagger.Module;
import dagger.Provides;
import name.aknights.config.PortfolioManagerConfiguration;
import name.aknights.config.YahooQuotesConfiguration;
import name.aknights.services.QuotesService;
import name.aknights.services.YahooQuotesService;
import org.glassfish.jersey.client.JerseyClientBuilder;

import javax.inject.Singleton;
import javax.ws.rs.client.Client;

@Module
public class PortfolioManagerModule {

    private final PortfolioManagerConfiguration portfolioManagerConfiguration;

    public PortfolioManagerModule(PortfolioManagerConfiguration portfolioManagerConfiguration) {
        this.portfolioManagerConfiguration = portfolioManagerConfiguration;
    }
}
