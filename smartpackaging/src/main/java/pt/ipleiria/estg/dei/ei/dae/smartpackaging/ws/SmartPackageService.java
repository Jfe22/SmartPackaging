package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.SmartPackageDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.SmartPackageBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.SmartPackage;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.PackType;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Path("smartpackages")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class SmartPackageService {

    @EJB
    private SmartPackageBean smartPackageBean;

    private SmartPackageDTO toDTO(SmartPackage smartPackage) {
        return new SmartPackageDTO(
            smartPackage.getId(),
            smartPackage.getType().toString(),
            smartPackage.getMaterial(),
            smartPackage.getProduct().getId(),
            smartPackage.getProduct().getName(),
            smartPackage.getCurrentAtmPressure(),
            smartPackage.getCurrentHumidity(),
            smartPackage.getCurrentTemperature(),
            smartPackage.getMaxGForce()
        );
    }

    private List<SmartPackageDTO> toDTOs(List<SmartPackage> smartPackages) {
        return smartPackages.stream().map(this::toDTO).collect(Collectors.toList());
    }


    @GET
    @Path("/")
    public List<SmartPackageDTO> getAllSmartPackages() {
        return toDTOs(smartPackageBean.getAllSmartPackages());
    }

    @GET
    @Path("{id}")
    public Response getSmartPackage(@PathParam("id") int id)
    throws MyEntityNotFoundException {
        SmartPackage smartPackage = smartPackageBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(smartPackage)).build();
    }

    @POST
    @Path("/")
    public Response createSmartPackage(SmartPackageDTO smartPackageDTO)
    throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        smartPackageBean.create(
                smartPackageDTO.getId(),
                PackType.valueOf(smartPackageDTO.getPackType()),
                smartPackageDTO.getMaterial(),
                smartPackageDTO.getProductId()
        );
        SmartPackage smartPackage = smartPackageBean.find(smartPackageDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(smartPackage)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateSmartPackage(@PathParam("id") int id, SmartPackageDTO smartPackageDTO)
    throws MyEntityNotFoundException, MyConstraintViolationException {
        smartPackageBean.update(
                smartPackageDTO.getId(),
                smartPackageDTO.getPackType(),
                smartPackageDTO.getMaterial(),
                smartPackageDTO.getProductId(),
                smartPackageDTO.getCurrentAtmPressure(),
                smartPackageDTO.getCurrentHumidity(),
                smartPackageDTO.getCurrentTemperature(),
                smartPackageDTO.getMaxGForce()
        );
        SmartPackage smartPackage = smartPackageBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(smartPackage)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteSmartPackage(@PathParam("id") int id)
    throws MyEntityNotFoundException {
        smartPackageBean.delete(id);
        return Response.status(Response.Status.OK).build();
    }
}
