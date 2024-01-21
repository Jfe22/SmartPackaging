package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ejb.EJB;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.ConsumerBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.ConsumerDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Consumer;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Operator;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.security.Authenticated;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Path("/consumers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsumerService {
    @EJB
    private ConsumerBean consumerBean;
    @Context
    private SecurityContext securityContext;

    private ConsumerDTO toDTO(Consumer consumer) {
        ConsumerDTO consumerDTO = new ConsumerDTO(
                consumer.getUsername(),
                consumer.getEmail(),
                consumer.getPassword()
        );
        return consumerDTO;
    }

    private List<ConsumerDTO> toDTOs(List<Consumer> consumers) {
        return consumers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private OrderDTO orderToDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getOrderDate().toString(),
                order.getEstDeleviryDate().toString(),
                order.getConsumer().getUsername()
        );
    }

    private List<OrderDTO> ordersToDTOs(List<Order> orders) {
        return orders.stream().map(this::orderToDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    @RolesAllowed({"CONSUMER"})
    public List<ConsumerDTO> getAllConsumers() {
        return toDTOs(consumerBean.getAllConsumers());
    }

    @GET
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @RolesAllowed({"CONSUMER"})
    public Response getConsumer(@PathParam("username") String username)
            throws MyEntityNotFoundException {
        Consumer consumer = consumerBean.find(username);
        return Response.status(Response.Status.OK).entity(toDTO(consumer)).build();
    }

    @GET
    @Path("{username}/orders")
    public Response getConsumerOrders(@PathParam("username") String username)
    throws MyEntityNotFoundException {
        Consumer consumer = consumerBean.getConsumerOrders(username);
        List<OrderDTO> consOrders = ordersToDTOs(consumer.getOrders());
        return Response.ok(consOrders).build();
    }

    @POST
    @Path("/")
    public Response createConsumer(ConsumerDTO consumerDTO)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        consumerBean.create(
                consumerDTO.getUsername(),
                consumerDTO.getEmail(),
                consumerDTO.getPassword(),
                UserRole.CONSUMER
        );
        Consumer newConsumer = consumerBean.find(consumerDTO.getUsername());
        return Response.status(Response.Status.CREATED).entity(toDTO(newConsumer)).build();
    }

    @PUT
    @Path("/{username}")
    public Response updateConsumer(@PathParam("username") String username, ConsumerDTO consumerDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        consumerBean.update(
                consumerDTO.getUsername(),
                consumerDTO.getEmail(),
                consumerDTO.getPassword(),
                UserRole.CONSUMER
        );
        Consumer updatedConsumer = consumerBean.find(username);
        return Response.status(Response.Status.OK).entity(toDTO(updatedConsumer)).build();
    }

    @DELETE
    @Path("{username}")
    public Response deleteConsumer(@PathParam("username") String username)
            throws MyEntityNotFoundException {
        consumerBean.delete(username);
        return Response.status(Response.Status.OK).build();
    }
}