package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.SmartPackage;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.TransportPackage;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class TransportPackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(int id) {
        Query query = entityManager.createQuery("SELECT COUNT(t.id) FROM TransportPackage t WHERE t.id = :id", Long.class);
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public TransportPackage find (int id)
    throws MyEntityNotFoundException {
        TransportPackage transportPackage = entityManager.find(TransportPackage.class, id);
        if (transportPackage == null)
            throw new MyEntityNotFoundException("TransportPackage with id " + id + " doesn't exist");

        return transportPackage;
    }

    public List<TransportPackage> getAllTransportPackages() {
        return entityManager.createNamedQuery("getAllTransportPackages", TransportPackage.class).getResultList();
    }

    public void create(int id, String currentLocation, int orderId)
    throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        if (exists(id))
            throw new MyEntityExistsException("TransportPackage with id " + id + " already exists");

        Order order = entityManager.find(Order.class, orderId);
        if (order == null)
            throw new MyEntityNotFoundException("Order with id " + orderId + " doesn't exist");

        try {
            TransportPackage transportPackage = new TransportPackage(id, currentLocation, order);
            order.setTransportPackage(transportPackage);
            entityManager.persist(transportPackage);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void update(int id, String currentLocation, int orderId)
    throws MyEntityNotFoundException, MyConstraintViolationException {
        TransportPackage transportPackage = find(id);

        Order order = entityManager.find(Order.class, orderId);
        if (order == null)
            throw new MyEntityNotFoundException("Order with id " + orderId + " doesn't exist");

        entityManager.lock(transportPackage, LockModeType.OPTIMISTIC);

        try {
            transportPackage.setCurrentLocation(currentLocation);
            transportPackage.setOrder(order);
            order.setTransportPackage(transportPackage);
            entityManager.merge(transportPackage);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void delete(int id)
    throws MyEntityNotFoundException {
        TransportPackage transportPackage = find(id);
        entityManager.remove(transportPackage);
    }

}
