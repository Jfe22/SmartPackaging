package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.TransportPackage;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    public Order find(int id) {
        return entityManager.find(Order.class, id);
    }

    public Order findWithSmartPackages(int id) {
        Order order = find(id);
        Hibernate.initialize(order.getSmartPackages());

        return order;
    }

    public void create(int order_id, LocalDate orderDate, LocalDate estDeliveryDate ) {
        Order order = new Order(order_id, orderDate, estDeliveryDate);
        entityManager.persist(order);
    }

    public List<Order> getAllOrders() {
        return entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
    }

    public void update(int orderId, LocalDate orderDate, LocalDate extDeliveryDate) {
        Order order = find(orderId);
        if (order == null) return;

        entityManager.lock(order, LockModeType.OPTIMISTIC);

        order.setOrderDate(orderDate);
        order.setEstDeleviryDate(extDeliveryDate);
        entityManager.persist(order);
    }

    public void delete(int id) {
        Order order = find(id);
        if (order == null) return;

        entityManager.remove(order);
    }

}
