package pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
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
public class Order extends Versionable {
    @Id
    int id;
    @NotNull
    LocalDate orderDate;
    @NotNull
    LocalDate estDeleviryDate;
    @NotNull
    @OneToMany (fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.REMOVE)
    List<SmartPackage> smartPackages;
    @OneToOne(mappedBy = "order")
    TransportPackage transportPackage;

    @ManyToOne
    @JoinColumn(name = "consumer_id")
    @NotNull
    Consumer consumer;

    public Order() { smartPackages = new LinkedList<>(); }
    public Order(int id, LocalDate orderDate, LocalDate estDeleviryDate, Consumer consumer) {
        this.id = id;
        this.orderDate = orderDate;
        this.estDeleviryDate = estDeleviryDate;
        this.consumer = consumer;
        smartPackages = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getEstDeleviryDate() {
        return estDeleviryDate;
    }

    public void setEstDeleviryDate(LocalDate estDeleviryDate) {
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

    public TransportPackage getTransportPackage() {
        return transportPackage;
    }

    public void setTransportPackage(TransportPackage transportPackage) {
        this.transportPackage = transportPackage;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }
}
