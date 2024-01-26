package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Consumer;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.SmartPackage;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.TransportPackage;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exits(int id) {
        Query query = entityManager.createQuery("SELECT COUNT(o.id) FROM Order o WHERE o.id = :id", Long.class);
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }

    public Order find(int id)
    throws MyEntityNotFoundException {
        Order order = entityManager.find(Order.class, id);
        if (order == null)
            throw new MyEntityNotFoundException("Order with id " + id + " doesn't exist");

        return order;
    }

    //not used because currently we are using fetch type EAGER
    public Order findWithSmartPackages(int id)
    throws MyEntityNotFoundException {
        Order order = find(id);
        Hibernate.initialize(order.getSmartPackages());

        return order;
    }

    public List<Order> getAllOrders() {
        return entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
    }

    public void create(int id, LocalDate orderDate, LocalDate estDeliveryDate, String consumerName)
    throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        if (exits(id))
            throw new MyEntityExistsException("Order with id " + id + " already exists");

        Consumer consumer = entityManager.find(Consumer.class, consumerName);
        if (consumer == null)
            throw new MyEntityNotFoundException("Consumer with name " + consumerName + " doesn't exists");

        try {
            Order order = new Order(id, orderDate, estDeliveryDate, consumer);
            entityManager.persist(order);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void update(int orderId, LocalDate orderDate, LocalDate extDeliveryDate)
    throws MyEntityNotFoundException, MyConstraintViolationException {
        Order order = find(orderId);
        entityManager.lock(order, LockModeType.OPTIMISTIC);

        try {
            order.setOrderDate(orderDate);
            order.setEstDeleviryDate(extDeliveryDate);
            entityManager.persist(order);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void delete(int id)
    throws MyEntityNotFoundException {
        Order order = find(id);
        entityManager.remove(order);
    }

    public void addOrderToConsumer(int orderId, String consumerName)
    throws MyEntityNotFoundException {
        Order order = find(orderId);

        Consumer consumer = entityManager.find(Consumer.class, consumerName);
        if (consumer == null)
            throw new MyEntityNotFoundException("Consumer with name " + consumerName + " doesn't exist");

        consumer.addOrder(order);
        order.setConsumer(consumer);
    }

    public void removeOrderFromConsumer(int orderId, String consumerName)
    throws MyEntityNotFoundException {
        Order order = find(orderId);

        Consumer consumer = entityManager.find(Consumer.class, consumerName);
        if (consumer == null)
            throw new MyEntityNotFoundException("Consumer with id " + consumerName + " doesn't exist");

        consumer.removeOrder(order);
        order.setConsumer(null);
    }

    public void addPackageToOrder(int orderId, int packageId)
    throws MyEntityNotFoundException {
        Order order = find(orderId);

        SmartPackage smartPackage = entityManager.find(SmartPackage.class, packageId);
        if (smartPackage == null)
            throw new MyEntityNotFoundException("Smartpackage with id " + packageId + " doesn't exist");

        order.addSmartPackage(smartPackage);
        smartPackage.setOrder(order);
    }

    public void removePackageToOrder(int orderId, int packageId)
    throws MyEntityNotFoundException {
        Order order = find(orderId);

        SmartPackage smartPackage = entityManager.find(SmartPackage.class, packageId);
        if (smartPackage == null)
            throw new MyEntityNotFoundException("Smartpackage with id " + packageId + " doesn't exist");

        order.removeSmartPackage(smartPackage);
        smartPackage.setOrder(null);
    }
}
