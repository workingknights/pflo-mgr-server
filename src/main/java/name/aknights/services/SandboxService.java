package name.aknights.services;

import name.aknights.api.Portfolio;

import javax.inject.Inject;

public class SandboxService {

    private final PortfolioService portfolioService;
    private ModelService modelService;

    @Inject
    public SandboxService(PortfolioService portfolioService, ModelService modelService) {
        this.portfolioService = portfolioService;
        this.modelService = modelService;
    }

    public Portfolio getScaledPortfolio(Double scaledMV, String userId) {

        Portfolio portfolio = this.portfolioService.getBasicPortfolio(userId);
        this.portfolioService.updateRequiredRebalances(portfolio.getEntries(), scaledMV, modelService.getModelForUser(userId));
        return portfolio;
    }

}
