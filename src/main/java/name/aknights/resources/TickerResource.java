package name.aknights.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import name.aknights.api.Data;
import name.aknights.api.Model;
import name.aknights.api.ModelEntry;
import name.aknights.api.Ticker;
import name.aknights.services.ModelService;
import name.aknights.services.TickerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.security.Principal;
import java.util.Collection;

@Path("/ticker")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TickerResource {
    private static final Logger logger = LoggerFactory.getLogger(TickerResource.class);

    private TickerService tickerService;

    @Inject
    public TickerResource(TickerService tickerService) {
        this.tickerService = tickerService;
    }

    @GET
    @Timed
    public Response getAllTickers(@Auth Principal principal) {
        Collection<Ticker> tickers = tickerService.allTickers();
        Data<Ticker> dataWrapper = new Data<Ticker>(tickers);

        return Response.ok(dataWrapper).build();
    }

    @GET
    @Path("/{uuid}")
    @PermitAll
    @Timed
    public Response getTicker(@PathParam("uuid") String uuid, @Auth Principal principal) {
        Ticker ticker = tickerService.getById(uuid);

        if(ticker == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No Ticker found with UUID: " + uuid).build();
        }

        return Response.ok(ticker).build();
    }

    @POST
    @RolesAllowed("ADMIN")
    @Timed
    public Response addTicker(@NotNull Ticker ticker, @Context HttpServletRequest request, @Auth Principal principal) {
        logger.debug("addTicker() - ticker = {}", ticker);
        String uuid = tickerService.addTicker(ticker);
        URI location = UriBuilder.fromPath(request.getRequestURI()).path(uuid).build();
        return Response.created(location).build();
    }

    @PUT
    @Path("/{uuid}")
    @RolesAllowed("ADMIN")
    @Timed
    public Response updateTicker(@PathParam("uuid") String uuid, @NotNull @Valid Ticker ticker, @Auth Principal principal) {
        if (uuid == null || uuid.trim().length() == 0) {
            return Response.serverError().entity("UUID cannot be blank").build();
        }
        Ticker existingTicker = tickerService.getById(uuid);
        if (existingTicker == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No Ticker already exists for UUID: " + uuid).build();
        }

        tickerService.updateTicker(uuid, ticker);
        return Response.noContent().build();
    }
}
