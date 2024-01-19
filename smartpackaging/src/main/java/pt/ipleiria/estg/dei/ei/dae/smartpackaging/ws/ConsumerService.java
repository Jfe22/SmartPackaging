package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ejb.EJB;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.ConsumerBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.ConsumerDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Consumer;

import java.util.List;
import java.util.stream.Collectors;

@Path("/consumers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsumerService {
    @EJB
    private ConsumerBean consumerBean;

    private ConsumerDTO toDTO(Consumer consumer) {
        ConsumerDTO dto = new ConsumerDTO();

        dto.setId(consumer.getId());
        dto.setUsername(consumer.getUsername());
        dto.setEmail(consumer.getEmail());
        dto.setPassword(consumer.getPassword());
        dto.setDeliveryUpdates(consumer.getDeliveryUpdatesData());
        dto.setQualityInformation(consumer.getQualityInformationData());
        dto.setSecurityAlerts(consumer.getSecurityAlertData());
        return dto;
    }

    // Converts a list of entities to a list of DTOs
    private List<ConsumerDTO> toDTOs(List<Consumer> consumers) {
        // Conversion logic, possibly using streams
        return consumers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ConsumerDTO> getAllConsumers() {
        return toDTOs(consumerBean.getAllConsumers());
    }

    @GET
    @Path("/{id}")
    public Response getConsumer(@PathParam("id") Long id) {
        Consumer consumer = consumerBean.find(id);
        if (consumer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(toDTO(consumer)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteConsumer(@PathParam("id") Long id) {
        consumerBean.delete(id);
        return Response.noContent().build();
    }
}