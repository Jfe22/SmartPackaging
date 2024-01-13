package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.SmartPackage;

import java.util.Date;

@Stateless
public class ProductBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(int id, String name, String expireDate, double weight, String ingredients) {
        Product product = new Product(id, name, expireDate, weight, ingredients);
        entityManager.persist(product);
    }
}
