package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;

import java.util.Date;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(int order_id, Date orderDate, Date estDeliveryDate ) {
        Order order = new Order(order_id, orderDate, estDeliveryDate);
        entityManager.persist(order);
    }
}
