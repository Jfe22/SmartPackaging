package pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities;

import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
// @DiscriminatorValue("CONSUMER")
@NamedQueries({
        @NamedQuery(
                name = "getAllConsumers",
                query = "SELECT c FROM Consumer c ORDER BY c.username"
        )
})
public class Consumer extends User implements Serializable {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "consumer", cascade = CascadeType.REMOVE)
    List<Order> orders;

    public Consumer() {
    }

    public Consumer(String username, String email, String password, UserRole role) {
        super(username, email, password, role);
        orders = new LinkedList<>();
    }

    // Getters and setters for Consumer-specific attributes

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }
}