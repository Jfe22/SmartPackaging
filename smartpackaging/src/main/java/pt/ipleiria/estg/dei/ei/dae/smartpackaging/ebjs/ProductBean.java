package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.SmartPackage;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Stateless
public class ProductBean {

    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(int id) {
        Query query = entityManager.createQuery("SELECT COUNT(p.id) FROM Product p WHERE p.id = :id", Long.class);
        query.setParameter("id", id);
        return (long) query.getSingleResult() > 0L;
    }

    public void create(int id, String name, LocalDate expireDate, double weight, String ingredients)
    throws MyEntityExistsException, MyConstraintViolationException {
        if (exists(id))
            throw new MyEntityExistsException("Product with id: " + id + " alredy exists");

        try {
            Product newProduct = new Product(id, name, expireDate, weight, ingredients);
            entityManager.persist(newProduct);
            //entityManager.flush(); --> NOT USING HIBERNATE
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Product> getAllProducts() {
        return entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public Product find(int id)
    throws MyEntityNotFoundException {
        Product product = entityManager.find(Product.class, id);
        if (product == null)
            throw new MyEntityNotFoundException("Product with id: " + id + " doesn't exist");

        return entityManager.find(Product.class, id);
    }

    public void update(int id, String name, LocalDate expireDate, double weight, String ingredients)
    throws MyEntityNotFoundException, MyConstraintViolationException {
        Product product = find(id);
        entityManager.lock(product, LockModeType.OPTIMISTIC);

        try {
            product.setName(name);
            product.setExpireDate(expireDate);
            product.setWeight(weight);
            product.setIngredients(ingredients);
            entityManager.merge(product);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void delete(int id)
    throws MyEntityNotFoundException {
        Product product = find(id);
        entityManager.remove(product);
    }
}
