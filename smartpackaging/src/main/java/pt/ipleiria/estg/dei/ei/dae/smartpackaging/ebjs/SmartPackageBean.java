package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.SmartPackageDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.SmartPackage;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.PackType;

import java.util.List;

@Stateless
public class SmartPackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(int id, PackType type, String material, int product_id) {
        Product product = entityManager.find(Product.class, product_id);
        if (product == null) return;

        SmartPackage smartPackage = new SmartPackage(id, type, material, product);
        entityManager.persist(smartPackage);

        // adds smart package to corresponding product
        product.setSmartPackage(smartPackage);
        entityManager.persist(product);
    }

    public List<SmartPackage> getAllSmartPackages() {
        return entityManager.createNamedQuery("getAllSmartPackages", SmartPackage.class).getResultList();
    }

    public SmartPackage find(int id) {
        return entityManager.find(SmartPackage.class, id);
    }

    public void update(int id, String packType, String material, int productId, double currentAtmPressure, double currentHumidity, double currentTemperature,  double maxGForce) {
        SmartPackage smartPackage = find(id);
        //if (smartPackage == null) return;

        Product product = entityManager.find(Product.class, productId);
        //if (product == null) return;

        smartPackage.setType(PackType.valueOf(packType));
        smartPackage.setMaterial(material);
        smartPackage.setProduct(product);

        smartPackage.setCurrentAtmPressure(currentAtmPressure);
        smartPackage.setCurrentHumidity(currentHumidity);
        smartPackage.setCurrentTemperature(currentTemperature);
        smartPackage.setMaxGForce(maxGForce);

        System.out.println(smartPackage.getCurrentAtmPressure());
        entityManager.persist(smartPackage);
        System.out.println(smartPackage.getCurrentAtmPressure());
    }

    public void delete(int id) {
        SmartPackage smartPackage = find(id);
        if (smartPackage == null) return;

        entityManager.remove(smartPackage);
    }

    public void addPackageToOrder(int pack_id, int order_id) {
        SmartPackage smartPackage = entityManager.find(SmartPackage.class, pack_id);
        if (smartPackage == null) return;

        Order order = entityManager.find(Order.class, order_id);
        if (order == null) return;

        order.addSmartPackage(smartPackage);
        smartPackage.setOrder(order);
    }

}
