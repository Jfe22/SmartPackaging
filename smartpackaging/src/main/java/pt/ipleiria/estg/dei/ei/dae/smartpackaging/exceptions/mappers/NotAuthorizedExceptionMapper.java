package pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.mappers;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;

public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {
    private static final java.util.logging.Logger Logger = java.util.logging.Logger.getLogger(MyEntityExistsException.class.getCanonicalName());

    @Override
    public Response toResponse(NotAuthorizedException e) {
        String errorMsg = e.getMessage();
        Logger.warning("ERROR: " + errorMsg);
        
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(errorMsg)
                .build();
    }
}
