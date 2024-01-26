package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.SmartPackageDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Producer;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.SmartPackage;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.PackType;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class SmartPackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(int id) {
        Query query = entityManager.createQuery("SELECT COUNT(s.id) FROM SmartPackage s WHERE s.id = :id", Long.class);
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }

    public SmartPackage find(int id)
            throws MyEntityNotFoundException {
        SmartPackage smartPackage = entityManager.find(SmartPackage.class, id);
        if (smartPackage == null)
            throw new MyEntityNotFoundException("Smartpackage with id " + id + " doesn't exist");

        return  smartPackage;
    }

    public List<SmartPackage> getAllSmartPackages() {
        return entityManager.createNamedQuery("getAllSmartPackages", SmartPackage.class).getResultList();
    }

    public void create(int id, PackType type, String material, int product_id, String producerName)
    throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        if (exists(id))
            throw new MyEntityExistsException("SmartPackage with id " + id + " already exists");

        Product product = entityManager.find(Product.class, product_id);
        if (product == null)
            throw new MyEntityNotFoundException("Prodcut with id " + id + " doesn't exist");

        Producer producer = entityManager.find(Producer.class, producerName);
        if (producer == null)
            throw new MyEntityNotFoundException("Producer with name " + producerName + " doesn't exist");

        try {
            SmartPackage smartPackage = new SmartPackage(id, type, material, product, producer);
            product.setSmartPackage(smartPackage);
            producer.addSmartPackage(smartPackage);
            entityManager.persist(smartPackage);
            //entityManager.flush();
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

    }

    public void update(int id, String packType, String material, int productId, double currentAtmPressure, double currentHumidity, double currentTemperature,  double maxGForce)
    throws MyEntityNotFoundException, MyConstraintViolationException {
        SmartPackage smartPackage = find(id);

        Product product = entityManager.find(Product.class, productId);
        if (product == null)
            throw new MyEntityNotFoundException("Product with id " + productId + " doesn't exist");

        entityManager.lock(smartPackage, LockModeType.OPTIMISTIC);

        try {
            smartPackage.setType(PackType.valueOf(packType));
            smartPackage.setMaterial(material);
            smartPackage.setProduct(product);
            smartPackage.setCurrentAtmPressure(currentAtmPressure);
            smartPackage.setCurrentHumidity(currentHumidity);
            smartPackage.setCurrentTemperature(currentTemperature);
            smartPackage.setMaxGForce(maxGForce);
            product.setSmartPackage(smartPackage);
            entityManager.merge(smartPackage);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void delete(int id)
    throws MyEntityNotFoundException {
        SmartPackage smartPackage = find(id);
        entityManager.remove(smartPackage);
    }

    //DEPRECATED USE orderBean.addPackageToOrder instead
    public void addPackageToOrder(int pack_id, int order_id)
    throws MyEntityNotFoundException {
        SmartPackage smartPackage = find(pack_id);

        Order order = entityManager.find(Order.class, order_id);
        if (order == null)
            throw new MyEntityNotFoundException("Order with id " + order_id + " doesn't exist");

        order.addSmartPackage(smartPackage);
        smartPackage.setOrder(order);
    }

    //DEPRECATED USE orderBean.removePackageFromOrder instead
    public void removePackageFromOrder(int pack_id, int order_id)
    throws MyEntityNotFoundException {
        SmartPackage smartPackage = find(pack_id);

        Order order = entityManager.find(Order.class, order_id);
        if (order == null)
            throw new MyEntityNotFoundException("Order with id " + order_id + " doesn't exist");

        order.removeSmartPackage(smartPackage);
        smartPackage.setOrder(null);
    }

}
