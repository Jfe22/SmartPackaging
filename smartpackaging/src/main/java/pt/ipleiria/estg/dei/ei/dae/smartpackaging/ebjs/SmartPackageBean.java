package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.SmartPackage;

@Stateless
public class SmartPackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(int id, String name, String destination) {
        SmartPackage smartPackage = new SmartPackage(id, name, destination);
        entityManager.persist(smartPackage);
    }

}
