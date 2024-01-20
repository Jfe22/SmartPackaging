package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.TransportPackageDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.TransportPackageBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.SmartPackage;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.TransportPackage;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Path("transportpackages")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class TransportPackageService {
    @EJB
    private TransportPackageBean transportPackageBean;

    private TransportPackageDTO toDTO(TransportPackage transportPackage) {
        return new TransportPackageDTO(
                transportPackage.getId(),
                transportPackage.getCurrentLocation(),
                transportPackage.getOrder().getId(),
                transportPackage.getOperator().getUsername()
        );

    }

    private List<TransportPackageDTO> toDTOs(List<TransportPackage> transportPackages) {
        return transportPackages.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<TransportPackageDTO> getAllTransportPackages() {
        return toDTOs(transportPackageBean.getAllTransportPackages());
    }

    @GET
    @Path("{id}")
    public Response getTransportPackage(@PathParam("id") int id)
    throws MyEntityNotFoundException {
        TransportPackage transportPackage = transportPackageBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(transportPackage)).build();
    }

    @POST
    @Path("/")
    public Response createTransportPackage(TransportPackageDTO transportPackageDTO)
    throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        transportPackageBean.create(
                transportPackageDTO.getId(),
                transportPackageDTO.getCurrentLocation(),
                transportPackageDTO.getOrderId(),
                transportPackageDTO.getOperatorName()
        );
        TransportPackage transportPackage = transportPackageBean.find(transportPackageDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(transportPackage)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateTransportPackage(@PathParam("id") int id, TransportPackageDTO transportPackageDTO)
    throws MyEntityNotFoundException, MyConstraintViolationException {
        transportPackageBean.update(
                id,
                transportPackageDTO.getCurrentLocation(),
                transportPackageDTO.getOrderId()
        );
        TransportPackage transportPackage = transportPackageBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(transportPackage)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteTransportPackage(@PathParam("id") int id)
    throws MyEntityNotFoundException {
        transportPackageBean.delete(id);
        return Response.status(Response.Status.OK).build();
    }
}
