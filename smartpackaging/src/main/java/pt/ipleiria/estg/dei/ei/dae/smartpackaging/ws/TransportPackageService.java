package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.TransportPackageDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.TransportPackageBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.TransportPackage;

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
                transportPackage.getOrder().getId()
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

}
