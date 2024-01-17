package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.ejb.EJB;
import jakarta.persistence.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.User;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserService {

    @EJB
    private UserBean userBean;

    // Existing methods

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Long id, UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword()); // Hash the password before setting
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());

        User updatedUser = userBean.updateUser(id, user);
        if (updatedUser == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new UserDTO(updatedUser)).build();
    }

    @POST
    @Path("/authenticate")
    public Response authenticateUser(UserDTO userDTO) {
        User authenticatedUser = userBean.authenticateUser(userDTO.getUsername(), userDTO.getPassword()); // Hash the password before sending
        if (authenticatedUser == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(new UserDTO(authenticatedUser)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        userBean.deleteUser(id);
        return Response.noContent().build(); // or appropriate status code
    }
}