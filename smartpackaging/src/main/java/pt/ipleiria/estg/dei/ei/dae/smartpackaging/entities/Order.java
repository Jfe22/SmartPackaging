package pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllOrders",
                query = "SELECT o FROM Order o ORDER BY o.id"
        )
})
@Table(name = "orders")
public class Order {
    @Id
    int id;
    @NotNull
    Date orderDate;
    @NotNull
    Date estDeleviryDate;
    @NotNull
    @OneToMany (fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.REMOVE)
    List<SmartPackage> smartPackages;

    public Order() {}
    public Order(int id, Date orderDate, Date estDeleviryDate) {
        this.id = id;
        this.orderDate = orderDate;
        this.estDeleviryDate = estDeleviryDate;
        smartPackages = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getEstDeleviryDate() {
        return estDeleviryDate;
    }

    public void setEstDeleviryDate(Date estDeleviryDate) {
        this.estDeleviryDate = estDeleviryDate;
    }

    public List<SmartPackage> getSmartPackages() {
        return smartPackages;
    }

    public void setSmartPackages(List<SmartPackage> smartPackages) {
        this.smartPackages = smartPackages;
    }

    public void addSmartPackage(SmartPackage smartPackage) {
        this.smartPackages.add(smartPackage);
    }

    public void removeSmartPackage(SmartPackage smartPackage) {
        this.smartPackages.remove(smartPackage);
    }
}
