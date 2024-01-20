package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ejb.EJB;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.ConsumerBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.ConsumerDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Consumer;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Path("/consumers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsumerService {
    @EJB
    private ConsumerBean consumerBean;

    private ConsumerDTO toDTO(Consumer consumer) {
        return new ConsumerDTO(
                consumer.getId(),
                consumer.getUsername(),
                consumer.getEmail(),
                consumer.getPassword(),
                consumer.getDeliveryUpdatesData(),
                consumer.getQualityInformationData(),
                consumer.getSecurityAlertData()
        );
    }

    private List<ConsumerDTO> toDTOs(List<Consumer> consumers) {
        return consumers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ConsumerDTO> getAllConsumers() {
        return toDTOs(consumerBean.getAllConsumers());
    }

    @GET
    //esta rota funciona??
    @Path("/{id}")
    public Response getConsumer(@PathParam("id") int id)
            throws MyEntityNotFoundException {
        Consumer consumer = consumerBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(consumer)).build();
    }

    @POST
    @Path("/")
    public Response createConsumer(ConsumerDTO consumerDTO)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        consumerBean.create(
                consumerDTO.getId(),
                consumerDTO.getUsername(),
                consumerDTO.getEmail(),
                consumerDTO.getPassword(),
                UserRole.CONSUMER,
                consumerDTO.getDeliveryUpdates(),
                consumerDTO.getQualityInformation(),
                consumerDTO.getSecurityAlerts()
        );
        Consumer newConsumer = consumerBean.find(consumerDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(newConsumer)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateConsumer(@PathParam("id") int id, ConsumerDTO consumerDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        consumerBean.update(
                id,
                consumerDTO.getUsername(),
                consumerDTO.getEmail(),
                consumerDTO.getPassword(),
                UserRole.CONSUMER,
                consumerDTO.getDeliveryUpdates(),
                consumerDTO.getQualityInformation(),
                consumerDTO.getSecurityAlerts()
        );
        Consumer updatedConsumer = consumerBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(updatedConsumer)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteConsumer(@PathParam("id") int id)
            throws MyEntityNotFoundException {
        consumerBean.delete(id);
        return Response.status(Response.Status.OK).build();
    }
}