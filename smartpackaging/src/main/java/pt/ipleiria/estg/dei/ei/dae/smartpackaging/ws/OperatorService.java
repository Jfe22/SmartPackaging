package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.ConsumerDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.TransportPackageDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.OperatorBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.OperatorDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Consumer;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Operator;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.TransportPackage;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;


@Path("/operators")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OperatorService {
    @EJB
    private OperatorBean operatorBean;

    private OperatorDTO toDTO(Operator operator) {
        return new OperatorDTO(
                operator.getUsername(),
                operator.getEmail(),
                operator.getPassword(),
                operator.getLocationAndTrackingData(),
                operator.getEnvironmentalConditionsData(),
                operator.getSecurityAlertData()
        );
    }

    private List<OperatorDTO> toDTOs(List<Operator> operators) {
        return operators.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private TransportPackageDTO transportPackageToDTO(TransportPackage transportPackage) {
        return new TransportPackageDTO(
                transportPackage.getId(),
                transportPackage.getCurrentLocation(),
                transportPackage.getOrder().getId(),
                transportPackage.getOperator().getUsername()
        );
    }

    private List<TransportPackageDTO> transportPackageToDTOs(List<TransportPackage> transportPackages) {
        return transportPackages.stream().map(this::transportPackageToDTO).collect(Collectors.toList());
    }


    @GET
    @Path("/")
    public List<OperatorDTO> getAllOperators() {
        return toDTOs(operatorBean.getAllOperators());
    }

    @GET
    @Path("/{username}")
    public Response getOperator(@PathParam("username") String username)
            throws MyEntityNotFoundException {
        Operator operator = operatorBean.find(username);
        return Response.status(Response.Status.OK).entity(toDTO(operator)).build();
    }

    @GET
    @Path("{username}/transportpackages")
    public Response getOperatorTransportPackages(@PathParam("username") String username)
    throws MyEntityNotFoundException {
        Operator operator = operatorBean.getOperatorTransportPackages(username);
        List<TransportPackageDTO> transportPackageDTOs = transportPackageToDTOs(operator.getTransportPackages());
        return Response.ok(transportPackageDTOs).build();
    }

    @POST
    @Path("/")
    public Response createNewOperator(OperatorDTO operatorDTO)
            throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        operatorBean.create(
                operatorDTO.getUsername(),
                operatorDTO.getEmail(),
                operatorDTO.getPassword(),
                UserRole.OPERATOR,
                operatorDTO.getLocationAndTracking(),
                operatorDTO.getEnvironmentalConditions(),
                operatorDTO.getSecurityAlerts()
        );
        Operator operator = operatorBean.find(operatorDTO.getUsername());
        return Response.status(Response.Status.CREATED).entity(toDTO(operator)).build();
    }

    @PUT
    @Path("/{username}")
    public Response updateOperator(@PathParam("username") String username, OperatorDTO operatorDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        operatorBean.update(
                operatorDTO.getUsername(),
                operatorDTO.getEmail(),
                operatorDTO.getPassword(),
                UserRole.OPERATOR,
                operatorDTO.getLocationAndTracking(),
                operatorDTO.getEnvironmentalConditions(),
                operatorDTO.getSecurityAlerts()
        );
        Operator operator = operatorBean.find(username);
        return Response.status(Response.Status.OK).entity(toDTO(operator)).build();
    }

    @DELETE
    @Path("/{username}")
    public Response deleteOperator(@PathParam("username") String username)
            throws MyEntityNotFoundException {
        operatorBean.delete(username);
        return Response.status(Response.Status.OK).build();
    }
}