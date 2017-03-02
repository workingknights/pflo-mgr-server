package name.aknights.services;

import com.mongodb.DuplicateKeyException;
import name.aknights.api.Ticker;
import name.aknights.db.TickerDAO;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

public class TickerService {

    private static final Logger logger = LoggerFactory.getLogger(TickerService.class);
    private TickerDAO tickerDAO;

    @Inject
    public TickerService(TickerDAO tickerDAO) {
        this.tickerDAO = tickerDAO;
    }

    public Collection<Ticker> allTickers() {
        return tickerDAO.getTickers();
    }

    public String addTicker(Ticker ticker) {
        logger.debug("addTicker() - adding ticker: " + ticker);
        try {
            return tickerDAO.save(ticker).getId().toString();
        } catch (DuplicateKeyException e) {
            logger.info("Ticker with symbol [{}] already exists so returning link to that", ticker.getSymbol());
            Optional<Ticker> existingTicker = tickerDAO.getTicker(ticker.getSymbol());
            if (existingTicker.isPresent())
                return existingTicker.get().getId();
            else {
                String errMsg = String.format("Ticker with symbol [%s] already exists but cannot be found!", ticker.getSymbol());
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
        }
    }

    public Ticker getById(String id) {
        return tickerDAO.get(new ObjectId(id));
    }

    public void updateTicker(String tickerId, Ticker ticker) {
        if (ticker.getId() ==  null)
            ticker.setId(tickerId);

        tickerDAO.save(ticker);
    }

    public Optional<Ticker> getBySymbol(String symbol) {
        return tickerDAO.getTicker(symbol);
    }
}
