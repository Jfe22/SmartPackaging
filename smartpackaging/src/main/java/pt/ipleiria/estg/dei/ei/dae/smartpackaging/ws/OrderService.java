package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.SmartPackageDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.SmartPackage;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Path("orders")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class OrderService {
    @EJB
    private OrderBean orderBean;

    private OrderDTO toDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO(
            order.getId(),
            order.getOrderDate().toString(),
            order.getEstDeleviryDate().toString(),
            order.getConsumer().getId()
        );
       orderDTO.setSmartPackagesDTOs(smartPackageToDTOs(order.getSmartPackages()));
       return orderDTO;
    }

    private List<OrderDTO> toDTOs(List<Order> orders) {
        return orders.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private SmartPackageDTO smartPackageToDTO(SmartPackage smartPackage) {
        return new SmartPackageDTO(
                smartPackage.getId(),
                smartPackage.getType().toString(),
                smartPackage.getMaterial(),
                smartPackage.getProduct().getId(),
                smartPackage.getProducer().getId(),
                smartPackage.getProduct().getName(),
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
    public List<OrderDTO> getAllOrders() {
        return toDTOs(orderBean.getAllOrders());
    }

    @GET
    @Path("{id}")
    public Response getOrder(@PathParam("id") int id)
    throws MyEntityNotFoundException {
        Order order = orderBean.find(id);
        return  Response.status(Response.Status.CREATED).entity(toDTO(order)).build();
    }

    @POST
    @Path("/")
    public Response createOrder(OrderDTO orderDTO)
    throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        orderBean.create(
                orderDTO.getId(),
                LocalDate.parse(orderDTO.getOrderDate()),
                LocalDate.parse(orderDTO.getExtDeliveryDate()),
                orderDTO.getConsumerId()
        );
        Order order = orderBean.find(orderDTO.getId());
        return  Response.status(Response.Status.CREATED).entity(toDTO(order)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateOrder(@PathParam("id") int id, OrderDTO orderDTO)
    throws MyEntityNotFoundException, MyConstraintViolationException {
        orderBean.update(
                id,
                LocalDate.parse(orderDTO.getOrderDate()),
                LocalDate.parse(orderDTO.getExtDeliveryDate())
        );
        Order updatedOrder = orderBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(updatedOrder)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteOrder(@PathParam("id") int id)
    throws MyEntityNotFoundException {
        orderBean.delete(id);
        return Response.status(Response.Status.OK).build();
    }
}
