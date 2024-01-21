package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.ConsumerDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.ProducerDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.SmartPackageDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.ProducerBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Consumer;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Producer;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.SmartPackage;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.security.Authenticated;

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
                producer.getUsername(),
                producer.getEmail(),
                producer.getPassword()
        );
    }

    private List<ProducerDTO> toDTOs(List<Producer> producers) {
        return producers.stream().map(this::toDTO).collect(Collectors.toList());
    }


    private SmartPackageDTO smartPackageToDTO(SmartPackage smartPackage) {
        return new SmartPackageDTO(
                smartPackage.getId(),
                smartPackage.getType().toString(),
                smartPackage.getMaterial(),
                smartPackage.getProduct().getId(),
                smartPackage.getProduct().getName(),
                smartPackage.getProducer().getUsername(),
                smartPackage.getCurrentAtmPressure(),
                smartPackage.getCurrentHumidity(),
                smartPackage.getCurrentTemperature(),
                smartPackage.getMaxGForce()
        );
    }

    private List<SmartPackageDTO> smartPackageToDTOs(List<SmartPackage> smartPackages) {
        return smartPackages.stream().map(this::smartPackageToDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ProducerDTO> getAllProducers() {
        return toDTOs(producerBean.getAllProducers());
    }

    @GET
    @Path("/{username}")
    public Response getProducer(@PathParam("username") String username)
            throws MyEntityNotFoundException {
        Producer producer = producerBean.find(username);
        return Response.status(Response.Status.OK).entity(toDTO(producer)).build();
    }

    @GET
    @Path("{username}/smartpackages")
    public Response getProducerPackages(@PathParam("username") String username)
    throws MyEntityNotFoundException {
        Producer producer =  producerBean.getProducerSmartPackages(username);
        List<SmartPackageDTO> smartPackageDTOs = smartPackageToDTOs(producer.getSmartPackages());
        return Response.ok(smartPackageDTOs).build();
    }

    @POST
    @Path("/")
    public Response createProducer(ProducerDTO producerDTO)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        producerBean.create(
                producerDTO.getUsername(),
                producerDTO.getEmail(),
                producerDTO.getPassword(),
                UserRole.PRODUCER
        );
        Producer newProducer = producerBean.find(producerDTO.getUsername());
        return Response.status(Response.Status.CREATED).entity(toDTO(newProducer)).build();
    }

    @PUT
    @Path("/{username}")
    public Response updateProducer(@PathParam("username") String username, ProducerDTO producerDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        producerBean.update(
                producerDTO.getUsername(),
                producerDTO.getEmail(),
                producerDTO.getPassword(),
                UserRole.PRODUCER
        );
        Producer updatedProducer = producerBean.find(username);
        return Response.status(Response.Status.OK).entity(toDTO(updatedProducer)).build();
    }

    @DELETE
    @Path("/{username}")
    public Response deleteProducer(@PathParam("username") String username)
            throws MyEntityNotFoundException {
        producerBean.delete(username);
        return Response.status(Response.Status.OK).build();
    }
}