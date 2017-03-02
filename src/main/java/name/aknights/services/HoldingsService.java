package name.aknights.services;

import name.aknights.api.Holding;
import name.aknights.db.HoldingDAO;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Collection;

public class HoldingsService {

    private static final Logger logger = LoggerFactory.getLogger(HoldingsService.class);

    private HoldingDAO holdingDAO;
    private FxRatesService fxRatesService;

    @Inject
    public HoldingsService(HoldingDAO holdingDAO, FxRatesService fxRatesService) {
        this.holdingDAO = holdingDAO;
        this.fxRatesService = fxRatesService;
    }

    public Collection<Holding> getHoldings(String userId) {
        Collection<Holding> holdings = holdingDAO.getHoldings(userId);

        for (Holding holding: holdings) {
            long start = System.currentTimeMillis();

            double fxRateToBase = fxRatesService.getRateToUsd(holding.getTicker().getCurrency());
            double marketValueBase = holding.getInitialMarketValue() * fxRateToBase;
            holding.setInitialMarketValueBase(marketValueBase);

            if (logger.isTraceEnabled())
                logger.trace("process holdi ng took {} ms", System.currentTimeMillis()-start);
        }
        return holdings;

    }

    public String addNewHolding(Holding holding) {
        return holdingDAO.save(holding).getId().toString();
    }

    public boolean deleteHolding(String id) {
        return holdingDAO.deleteHolding(id);
    }

    public Holding getById(String holdingId) {
        return holdingDAO.get(new ObjectId(holdingId));
    }

    public void updateHoldingModel(String holdingId, Holding holding) {
        if (holding.getId() == null || holding.getId().isEmpty())
            holding.setId(holdingId);

        holdingDAO.save(holding);
    }
}
