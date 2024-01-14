package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;

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
}
