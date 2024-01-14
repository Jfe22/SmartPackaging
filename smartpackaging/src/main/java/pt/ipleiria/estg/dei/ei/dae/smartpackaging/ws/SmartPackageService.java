package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.SmartPackageDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.SmartPackageBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.SmartPackage;

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


}
