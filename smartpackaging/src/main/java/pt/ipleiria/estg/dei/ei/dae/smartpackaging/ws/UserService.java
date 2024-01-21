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
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;
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

        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword()); // Hash the password before setting
        dto.setRole(user.getRole().toString());
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
    @Path("/{username}")
    public Response updateUser(@PathParam("username") String username, UserDTO userDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        userBean.update(
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.getRole().toString()
        );
        User updatedUser = userBean.find(username);
        return Response.status(Response.Status.OK).entity(toDTO(updatedUser)).build();
    }

    @DELETE
    @Path("/{username}")
    public Response deleteUser(@PathParam("username") String username)
            throws MyEntityNotFoundException {
        userBean.delete(username);
        return Response.status(Response.Status.OK).build();
    }
}