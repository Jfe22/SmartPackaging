package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.ConsumerDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.OperatorBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.OperatorDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Consumer;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Operator;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;

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
                operator.getId(),
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

    @GET
    @Path("/")
    public List<OperatorDTO> getAllOperators() {
        return toDTOs(operatorBean.getAllOperators());
    }

    @GET
    @Path("/{id}")
    public Response getOperator(@PathParam("id") Long id)
            throws MyEntityNotFoundException {
        Operator operator = operatorBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(operator)).build();
    }

    @POST
    @Path("/")
    public Response createNewOperator(OperatorDTO operatorDTO)
            throws MyEntityExistsException, MyConstraintViolationException {
        operatorBean.create(
                operatorDTO.getId(),
                operatorDTO.getUsername(),
                operatorDTO.getEmail(),
                operatorDTO.getPassword(),
                UserRole.OPERATOR,
                operatorDTO.getLocationAndTracking(),
                operatorDTO.getEnvironmentalConditions(),
                operatorDTO.getSecurityAlerts()
        );
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateOperator(@PathParam("id") Long id, OperatorDTO operatorDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        operatorBean.update(
                id,
                operatorDTO.getUsername(),
                operatorDTO.getEmail(),
                operatorDTO.getPassword(),
                UserRole.OPERATOR,
                operatorDTO.getLocationAndTracking(),
                operatorDTO.getEnvironmentalConditions(),
                operatorDTO.getSecurityAlerts()
        );
        Operator updatedOperator = operatorBean.find(id);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOperator(@PathParam("id") Long id)
            throws MyEntityNotFoundException {
        operatorBean.delete(id);
        return Response.status(Response.Status.OK).build();
    }
}
