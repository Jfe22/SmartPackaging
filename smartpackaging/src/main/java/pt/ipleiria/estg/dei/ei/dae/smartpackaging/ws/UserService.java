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
        // Assuming your UserDTO includes id and other necessary fields
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword()); // Hash the password before setting
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }

    // Converts a list of entities to a list of DTOs
    private List<UserDTO> toDTOs(List<User> users) {
        // Conversion logic, possibly using streams
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<UserDTO> getAllUsers() {
        return toDTOs(userBean.getAllUsers());
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Long id, UserDTO userDTO) {
        if (id != userDTO.getId()) return Response.status(Response.Status.BAD_REQUEST).build();
        if (userBean.find(id) == null) return Response.status(Response.Status.NOT_FOUND).build();

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword()); // Hash the password before setting
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());

        User updatedUser = userBean.find(id);
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