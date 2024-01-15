package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.SmartPackageDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.SmartPackageBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.SmartPackage;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.PackType;

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
            smartPackage.getOrder().getId()
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
    public Response getAllSmartPackages(@PathParam("id") int id) {
        SmartPackage smartPackage = smartPackageBean.find(id);
        if (smartPackage == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(toDTO(smartPackage)).build();
    }

    @POST
    @Path("/")
    public Response createSmartPackage(SmartPackageDTO smartPackageDTO) {
        smartPackageBean.create(
                smartPackageDTO.getId(),
                PackType.valueOf(smartPackageDTO.getPackType()),
                smartPackageDTO.getMaterial(),
                smartPackageDTO.getProductId()
        );
        SmartPackage smartPackage = smartPackageBean.find(smartPackageDTO.getId());
        if (smartPackage == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        return Response.status(Response.Status.CREATED).entity(toDTO(smartPackage)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateSmartPackage(@PathParam("id") int id, SmartPackageDTO smartPackageDTO) {
        if (id != smartPackageDTO.getId())
            return Response.status(Response.Status.BAD_REQUEST).build();

        SmartPackage smartPackage = smartPackageBean.find(id);
        if (smartPackage == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        smartPackageBean.update(
                smartPackageDTO.getId(),
                smartPackageDTO.getPackType(),
                smartPackageDTO.getMaterial(),
                smartPackageDTO.getProductId(),
                smartPackageDTO.getOrderId(),
                smartPackageDTO.getCurrentAtmPressure(),
                smartPackageDTO.getCurrentHumidity(),
                smartPackageDTO.getCurrentTemperature(),
                smartPackageDTO.getMaxGForce()
        );

        SmartPackage updatedSmartPackage = smartPackageBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(smartPackage)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteSmartPackage(@PathParam("id") int id) {
        if (smartPackageBean.find(id) == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        smartPackageBean.delete(id);
        if (smartPackageBean.find(id) != null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        return Response.status(Response.Status.OK).build();
    }

}
