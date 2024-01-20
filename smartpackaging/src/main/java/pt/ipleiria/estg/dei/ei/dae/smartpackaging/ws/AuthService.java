package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.AuthDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.security.TokenIssuer;

@Path("auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthService {
    @Inject
    private TokenIssuer issuer;
    @EJB
    private UserBean userBean;

    @POST
    @Path("/login")
    public Response authenticate(@Valid AuthDTO auth)
            throws MyEntityNotFoundException {
        if (userBean.canLogin(auth.getUsername(), auth.getPassword())) {
            String token = issuer.issue(auth.getUsername());
            return Response.ok(token).build();
        }
        return Response.ok(Response.Status.UNAUTHORIZED).build();
    }
}