package name.aknights.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import name.aknights.api.Data;
import name.aknights.api.Holding;
import name.aknights.api.Ticker;
import name.aknights.services.HoldingsService;
import name.aknights.services.TickerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.security.Principal;
import java.util.Optional;

@Path("/holding")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HoldingsResource {
    private static final Logger logger = LoggerFactory.getLogger(HoldingsResource.class);

    private HoldingsService holdingsService;
    private TickerService tickerService;

    @Inject
    public HoldingsResource(HoldingsService holdingsService, TickerService tickerService) {
        this.holdingsService = holdingsService;
        this.tickerService = tickerService;
    }

    @GET
    @PermitAll
    @Timed
    public Response listAll(@Auth Principal principal) {
        return Response.ok(new Data<>(holdingsService.getHoldings(principal.getName()))).build();
    }

    @GET
    @Path("/{uuid}")
    @PermitAll
    @Timed
    public Response getHolding(@PathParam("uuid") String uuid, @Auth Principal principal) {
        Holding holding = holdingsService.getById(uuid);

        if(holding == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No Holding found with UUID: " + uuid).build();
        }

        return Response.ok(holding).build();
    }

    @POST
    @PermitAll
    @Timed
    public Response addHolding(@NotNull @Valid Holding newHolding, @Context HttpServletRequest request, @Auth Principal principal) {
        logger.debug("addEntry() - newHolding = {}", newHolding);

        Ticker ticker = newHolding.getTicker();
        Optional<Ticker> tickerOptional = tickerService.getBySymbol(ticker.getSymbol());
        if (!tickerOptional.isPresent()) {
            String msg = String.format("Ticker [%s] does not exist.  Please set it up before adding holdings", newHolding.getTicker().getSymbol());
            logger.warn(msg);
            return Response.status(Response.Status.NOT_FOUND).entity(msg).build();
        }
        newHolding.setTicker(tickerOptional.get()); // Set retrieved ticker on holding so that ticker ID is not null
        newHolding.setUserId(principal.getName());

        String uuid = holdingsService.addNewHolding(newHolding);
        URI location = UriBuilder.fromPath(request.getRequestURI()).path(uuid).build();
        return Response.created(location).build();
    }

    @DELETE
    @PermitAll
    @Timed
    @Path("/{id}")
    public Response delete(@PathParam("id") String id, @Auth Principal principal) {
        if (!holdingsService.deleteHolding(id)) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return Response.ok().build();
    }

    @PUT
    @Path("/{uuid}")
    @PermitAll
    @Timed
    public Response updateHolding(@PathParam("uuid") String uuid, @NotNull @Valid Holding holding, @Auth Principal principal) {
        logger.debug("updateHolding() - holding = {}", holding);

        if (uuid == null || uuid.trim().length() == 0) {
            return Response.serverError().entity("UUID cannot be blank").build();
        }
        Holding existingHolding = holdingsService.getById(uuid);
        if (existingHolding == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No Holding already exists for UUID: " + uuid).build();
        }

        holdingsService.updateHoldingModel(uuid, holding);
        return Response.noContent().build();
    }
}
