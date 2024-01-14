package pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllTransportPackages",
                query = "SELECT t FROM TransportPackage t ORDER BY t.id"
        )
})
@Table(name = "transportpackages")
public class TransportPackage {
    @Id
    int id;
    @NotNull
    String currentLocation;
    @NotNull
    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    Order order;

    public TransportPackage() {}
    public TransportPackage(int id, String currentLocation, Order order) {
        this.id = id;
        this.currentLocation = currentLocation;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}