package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;

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
        return new OrderDTO(
            order.getId(),
            order.getOrderDate().toString(),
            order.getEstDeleviryDate().toString(),
            order.getTransportPackage().getId()
        );
    }

    private List<OrderDTO> toDTOs(List<Order> orders) {
        return orders.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<OrderDTO> getAllOrders() {
        return toDTOs(orderBean.getAllOrders());
    }

    @GET
    @Path("{id}")
    public Response getOrder(@PathParam("id") int id) {
        Order order = orderBean.find(id);
        if (order == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return  Response.status(Response.Status.CREATED).entity(toDTO(order)).build();
    }

    @POST
    @Path("/")
    public Response createOrder(OrderDTO orderDTO) {
        if (orderBean.find(orderDTO.getId()) != null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        orderBean.create(
                orderDTO.getId(),
                LocalDate.parse(orderDTO.getOrderDate()),
                LocalDate.parse(orderDTO.getExtDeliveryDate())
        );

        Order order = orderBean.find(orderDTO.getId());

        return  Response.status(Response.Status.CREATED).entity(toDTO(order)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateOrder(@PathParam("id") int id, OrderDTO orderDTO) {
        if (id != orderDTO.getId())
            return Response.status(Response.Status.BAD_REQUEST).build();

        Order order = orderBean.find(id);
        if (order == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        orderBean.update(
                id,
                LocalDate.parse(orderDTO.getOrderDate()),
                LocalDate.parse(orderDTO.getExtDeliveryDate()),
                orderDTO.getTransportPackageId()
        );

        //order vai estar upadated aqui ou temos de chamar o find de novo?
        return Response.status(Response.Status.OK).entity(toDTO(order)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteOrder(@PathParam("id") int id) {
        if (orderBean.find(id) == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        orderBean.delete(id);
        if (orderBean.find(id) != null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.OK).build();
    }


}
