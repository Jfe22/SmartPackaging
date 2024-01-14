package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;

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


}
