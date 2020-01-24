package org.exoplatform.addons.repositorychecker;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.core.ManageableRepository;
import org.exoplatform.services.jcr.impl.checker.RepositoryCheckController;
import org.exoplatform.services.log.ExoLogger;
import io.swagger.annotations.*;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.rest.resource.ResourceContainer;


@Path("/repositorychecker")
@Api(value = "/repositorychecker")
public class RequestHandler implements ResourceContainer {
    private RepositoryCheckController repositoryCheckController;
    private RepositoryService repositoryService = PortalContainer.getInstance().getComponentInstanceOfType(RepositoryService.class);

    private static final Log LOG =
            ExoLogger.getLogger(RequestHandler.class);

    public RequestHandler() throws Exception {
        ManageableRepository repository = repositoryService.getCurrentRepository();
        this.repositoryCheckController = new RepositoryCheckController(repository);
    }

    @GET
    @RolesAllowed("administrators")
    @Path("checkValueStorage")
    @ApiOperation(value = "Perform repository check", httpMethod = "GET", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request fulfilled"),
            @ApiResponse(code = 400, message = "Invalid query input"),
            @ApiResponse(code = 403, message = "Unauthorized operation"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response checkValueStorage(@ApiParam(value = "nThreads", required = true) @QueryParam("nThreads") String nThreads) {
        int nbThreads = 0;
        if (nThreads == null || nThreads.trim().length() == 0) {
            LOG.warn("nbThreads Parameter is empty");
        } else if (nThreads.matches("\\d+")) {
           nbThreads = Integer.parseInt(nThreads);
        } else {
            LOG.warn("nThreads={} is invalid using 0 as default value!",nThreads);
        }
        LOG.info("checkValueStorage is performed");
        String result = repositoryCheckController.checkValueStorage(nbThreads);
        return Response.ok(result).build();
    }

    @GET
    @RolesAllowed("administrators")
    @Path("repairDatabase")
    @ApiOperation(value = "Perform Database repair", httpMethod = "GET", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request fulfilled"),
            @ApiResponse(code = 400, message = "Invalid query input"),
            @ApiResponse(code = 403, message = "Unauthorized operation"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response repairDatabase(@ApiParam(value = "confirmation", required = true) @QueryParam("confirmation") String confirmation,
                                   @ApiParam(value = "nThreads", required = true) @QueryParam("nThreads") String nThreads) {
        int nbThreads = 0;
        if(confirmation == null || confirmation.trim().isEmpty() || ! confirmation.toLowerCase().equals("yes")) {
            LOG.error("Confirmation is needed!");
            return Response.status(401).build();
        }
        if (nThreads == null || nThreads.trim().length() == 0) {
            LOG.warn("nbThreads Parameter is empty");
        } else if (nThreads.matches("\\d+")) {
            nbThreads = Integer.parseInt(nThreads);
        } else {
            LOG.warn("nThreads={} is invalid using 0 as default value!",nThreads);
        }
        LOG.info("repairDataBase is performed");
        String result = repositoryCheckController.repairDataBase(confirmation,nbThreads);
        return Response.ok(result).build();
    }

    @GET
    @RolesAllowed("administrators")
    @Path("checkDatabase")
    @ApiOperation(value = "Perform database check", httpMethod = "GET", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request fulfilled"),
            @ApiResponse(code = 400, message = "Invalid query input"),
            @ApiResponse(code = 403, message = "Unauthorized operation"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response checkDatabase(@ApiParam(value = "nThreads", required = true) @QueryParam("nThreads") String nThreads) {
        int nbThreads = 0;
        if (nThreads == null || nThreads.trim().length() == 0) {
            LOG.warn("nbThreads Parameter is empty");
        } else if (nThreads.matches("\\d+")) {
            nbThreads = Integer.parseInt(nThreads);
        } else {
            LOG.warn("nThreads={} is invalid using 0 as default value!",nThreads);
        }
        LOG.info("checkDataBase is performed");
        String result = repositoryCheckController.checkDataBase(nbThreads);
        return Response.ok(result).build();
    }

    @GET
    @RolesAllowed("administrators")
    @Path("checkIndex")
    @ApiOperation(value = "Perform index check", httpMethod = "GET", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request fulfilled"),
            @ApiResponse(code = 400, message = "Invalid query input"),
            @ApiResponse(code = 403, message = "Unauthorized operation"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response checkIndex(@ApiParam(value = "nThreads", required = true) @QueryParam("nThreads") String nThreads) {
        int nbThreads = 0;
        if (nThreads == null || nThreads.trim().length() == 0) {
            LOG.warn("nbThreads Parameter is empty");
        } else if (nThreads.matches("\\d+")) {
            nbThreads = Integer.parseInt(nThreads);
        } else {
            LOG.warn("nThreads={} is invalid using 0 as default value!",nThreads);
        }
        LOG.info("checkIndex is performed");
        String result = repositoryCheckController.checkIndex(nbThreads);
        return Response.ok(result).build();
    }

    @GET
    @RolesAllowed("administrators")
    @Path("checkAll")
    @ApiOperation(value = "Perform all checks", httpMethod = "GET", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request fulfilled"),
            @ApiResponse(code = 400, message = "Invalid query input"),
            @ApiResponse(code = 403, message = "Unauthorized operation"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response checkAll() {
        LOG.info("checkAll is performed");
        String result = repositoryCheckController.checkAll();
        return Response.ok(result).build();
    }
}
