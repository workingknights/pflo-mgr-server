package name.aknights.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import name.aknights.api.Model;
import name.aknights.api.ModelEntry;
import name.aknights.services.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
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

@Path("/model")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModelResource {
    private static final Logger logger = LoggerFactory.getLogger(ModelResource.class);

    private ModelService modelService;

    @Inject
    public ModelResource(ModelService modelService) {
        this.modelService = modelService;
    }

    @GET
    @PermitAll
    @Timed
    public Response getModelForUser(@Auth Principal principal) {
        Model model = modelService.getModelForUser(principal.getName());

        if(model == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No Model found for user: " + principal.getName()).build();
        }

        return Response.ok(model).build();
    }

    @GET
    @Path("/{uuid}")
    @PermitAll
    @Timed
    public Response getModel(@PathParam("uuid") String uuid, @Auth Principal principal) {
        Model model = modelService.getById(uuid);

        if(model == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No Model found with UUID: " + uuid).build();
        }

        return Response.ok(model).build();
    }

    @POST
    @PermitAll
    public Response createModel(@NotNull Model model, @Context HttpServletRequest request, @Auth Principal principal) {
        model.setUserId(principal.getName());
        String uuid = modelService.createModel(model);
        URI location = UriBuilder.fromPath(request.getRequestURI()).path(uuid).build();
        return Response.created(location).build();
    }

    @PUT
    @Path("/{uuid}/entry")
    @PermitAll
    @Timed
    public Response addEntry(@PathParam("uuid") String uuid, @NotNull @Valid ModelEntry modelEntry,
                             @Auth Principal principal) {
        if (uuid == null || uuid.trim().length() == 0) {
            return Response.serverError().entity("UUID cannot be blank").build();
        }
        Model existingModel = modelService.getById(uuid);
        if (existingModel == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No Model already exists for UUID: " + uuid).build();
        }

        modelService.addModelEntry(uuid, modelEntry);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{uuid}")
    @PermitAll
    @Timed
    public Response updateModel(@PathParam("uuid") String uuid, @NotNull @Valid Model model,
                                @Auth Principal principal) {
        if (uuid == null || uuid.trim().length() == 0) {
            return Response.serverError().entity("UUID cannot be blank").build();
        }
        Model existingModel = modelService.getById(uuid);
        if (existingModel == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No Model already exists for UUID: " + uuid).build();
        }

        modelService.updateModel(uuid, model);
        return Response.noContent().build();
    }
}
