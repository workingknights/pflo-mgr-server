package name.aknights;

import dagger.Module;
import name.aknights.config.PortfolioManagerConfiguration;

@Module
public class PortfolioManagerModule {

    private final PortfolioManagerConfiguration portfolioManagerConfiguration;

    public PortfolioManagerModule(PortfolioManagerConfiguration portfolioManagerConfiguration) {
        this.portfolioManagerConfiguration = portfolioManagerConfiguration;
    }
}
