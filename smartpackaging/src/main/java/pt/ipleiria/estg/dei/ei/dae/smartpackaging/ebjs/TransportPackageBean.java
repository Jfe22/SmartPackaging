package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.SmartPackage;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.TransportPackage;

import java.util.List;

@Stateless
public class TransportPackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(int id, String currentLocation, int order_id) {
        Order order = entityManager.find(Order.class, order_id);
        if (order == null) return;

        TransportPackage transportPackage = new TransportPackage(id, currentLocation, order);
        entityManager.persist(transportPackage);
    }

    public List<TransportPackage> getAllTransportPackages() {
        return entityManager.createNamedQuery("getAllTransportPackages", TransportPackage.class).getResultList();
    }

    public TransportPackage find (int id) {
        return entityManager.find(TransportPackage.class, id);
    }

}
