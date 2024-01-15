package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.TransportPackage;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(int order_id, LocalDate orderDate, LocalDate estDeliveryDate ) {
        Order order = new Order(order_id, orderDate, estDeliveryDate);
        entityManager.persist(order);
    }

    public List<Order> getAllOrders() {
        return entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
    }

    public Order find(int id) {
        return entityManager.find(Order.class, id);
    }

    public void update(int orderId, LocalDate orderDate, LocalDate extDeliveryDate, int transportPackageId) {
        Order order = find(orderId);
        if (order == null) return;

        TransportPackage transportPackage = entityManager.find(TransportPackage.class, transportPackageId);
        //transport package can be null

        order.setOrderDate(orderDate);
        order.setEstDeleviryDate(extDeliveryDate);
        order.setTransportPackage(transportPackage);
        entityManager.persist(order);
    }

    public void delete(int id) {
        Order order = find(id);
        if (order == null) return;

        entityManager.remove(order);
    }

}
