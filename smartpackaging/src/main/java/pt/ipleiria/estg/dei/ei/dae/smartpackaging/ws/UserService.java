package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.ejb.EJB;
import jakarta.persistence.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.ConsumerDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Consumer;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.User;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserService {
    @EJB
    private UserBean userBean;

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword()); // Hash the password before setting
        dto.setRole(user.getRole());
        return dto;
    }

    private List<UserDTO> toDTOs(List<User> users) {
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<UserDTO> getAllUsers() {
        return toDTOs(userBean.getAllUsers());
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") int id, UserDTO userDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        userBean.update(
                id,
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.getRole()
        );
        User updatedUser = userBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(updatedUser)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id)
            throws MyEntityNotFoundException {
        userBean.delete(id);
        return Response.status(Response.Status.OK).build();
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
}