package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.SmartPackage;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Stateless
public class ProductBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(int id, String name, LocalDate expireDate, double weight, String ingredients) {
        Product product = new Product(id, name, expireDate, weight, ingredients);
        entityManager.persist(product);
    }

    public List<Product> getAllProducts() {
        return entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public Product find(int id) {
        return entityManager.find(Product.class, id);
    }

    public void update(int id, String name, LocalDate expireDate, double weight, String ingredients) {
        Product product = find(id);
        if (product == null) return;

        //product.setId(id);
        product.setName(name);
        product.setExpireDate(expireDate);
        product.setWeight(weight);
        product.setIngredients(ingredients);
        entityManager.persist(product);
    }

    public void delete(int id) {
        Product product = entityManager.find(Product.class, id);
        if (product == null) return;

        entityManager.remove(product);
    }
}
