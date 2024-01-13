package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.SmartPackage;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.PackType;

@Stateless
public class SmartPackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(int id, PackType type, String material, int product_id) {

        Product product = entityManager.find(Product.class, product_id);
        if (product == null) return;

        SmartPackage smartPackage = new SmartPackage(id, type, material, product);
        entityManager.persist(smartPackage);
    }

}
