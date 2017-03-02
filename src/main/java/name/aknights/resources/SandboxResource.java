package name.aknights.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import name.aknights.services.SandboxService;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Principal;

@Path("/sandbox")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SandboxResource {
    private SandboxService sandboxService;

    @Inject
    public SandboxResource(SandboxService sandboxService) {
        this.sandboxService = sandboxService;
    }

    @GET
    @PermitAll
    @Timed
    public Response getSandboxPortfolio(@QueryParam("scaledMV") Double scaledMV, @Auth Principal principal) {
        return Response.ok(sandboxService.getScaledPortfolio(scaledMV, principal.getName())).build();
    }
}
