package pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.PackType;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllSmartPackages",
                query = "SELECT s FROM SmartPackage s ORDER BY s.id"
        )
})
@Table(name = "smartpackages")
public class SmartPackage extends Versionable {
    @Id
    int id;
    @NotNull
    PackType type;
    @NotNull
    String material;
    @NotNull
    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    Product product;
    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    double currentTemperature;
    double currentHumidity;
    double currentAtmPressure;
    double maxGForce;

    public SmartPackage() {}
    public SmartPackage(int id, PackType type, String material, Product product) {
        this.id = id;
        this.type = type;
        this.material = material;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PackType getType() {
        return type;
    }

    public void setType(PackType type) {
        this.type = type;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public double getCurrentHumidity() {
        return currentHumidity;
    }

    public void setCurrentHumidity(double currentHumidity) {
        this.currentHumidity = currentHumidity;
    }

    public double getCurrentAtmPressure() {
        return currentAtmPressure;
    }

    public void setCurrentAtmPressure(double currentAtmPressure) {
        this.currentAtmPressure = currentAtmPressure;
    }

    public double getMaxGForce() {
        return maxGForce;
    }

    public void setMaxGForce(double maxGForce) {
        this.maxGForce = maxGForce;
    }
}
