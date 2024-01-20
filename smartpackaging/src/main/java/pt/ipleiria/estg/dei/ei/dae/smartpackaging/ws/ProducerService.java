package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.ConsumerDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.ProducerDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.ProducerBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Consumer;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Producer;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Path("/producers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProducerService {
    @EJB
    private ProducerBean producerBean;

    private ProducerDTO toDTO(Producer producer) {
        return new ProducerDTO(
                producer.getId(),
                producer.getUsername(),
                producer.getEmail(),
                producer.getPassword(),
                producer.getQualityControlData(),
                producer.getProductResponsibilityCost()
        );
    }

    private List<ProducerDTO> toDTOs(List<Producer> producers) {
        return producers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ProducerDTO> getAllProducers() {
        return toDTOs(producerBean.getAllProducers());
    }

    @GET
    @Path("/{id}")
    public Response getProducer(@PathParam("id") Long id)
            throws MyEntityNotFoundException {
        Producer producer = producerBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(producer)).build();
    }

    @POST
    @Path("/")
    public Response createProducer(ProducerDTO producerDTO)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        producerBean.create(
                producerDTO.getId(),
                producerDTO.getUsername(),
                producerDTO.getEmail(),
                producerDTO.getPassword(),
                UserRole.PRODUCER,
                producerDTO.getQualityControlData(),
                producerDTO.getProductResponsibilityCost()
        );
        Producer newProducer = producerBean.find(producerDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(newProducer)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateProducer(@PathParam("id") Long id, ProducerDTO producerDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        producerBean.update(
                id,
                producerDTO.getUsername(),
                producerDTO.getEmail(),
                producerDTO.getPassword(),
                UserRole.PRODUCER,
                producerDTO.getQualityControlData(),
                producerDTO.getProductResponsibilityCost()
        );
        Producer updatedProducer = producerBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(updatedProducer)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProducer(@PathParam("id") Long id)
            throws MyEntityNotFoundException {
        producerBean.delete(id);
        return Response.status(Response.Status.OK).build();
    }
}
