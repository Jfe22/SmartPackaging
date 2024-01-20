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
    private String deliveryUpdatesData; // e.g., real-time delivery updates, including estimated times
    private String qualityInformationData; // e.g., environmental conditions during transport
    private String securityAlertData; // e.g., detection of unauthorized opening

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "consumer", cascade = CascadeType.REMOVE)
    List<Order> orders;

    public Consumer() {
    }

    public Consumer(String username, String email, String password, UserRole role, String deliveryUpdatesData, String qualityInformationData, String securityAlertData) {
        super(username, email, password, role);
        this.deliveryUpdatesData = deliveryUpdatesData;
        this.qualityInformationData = qualityInformationData;
        this.securityAlertData = securityAlertData;
        orders = new LinkedList<>();
    }

    // Getters and setters for Consumer-specific attributes
    public String getDeliveryUpdatesData() {
        return deliveryUpdatesData;
    }

    public void setDeliveryUpdatesData(String deliveryUpdatesData) {
        this.deliveryUpdatesData = deliveryUpdatesData;
    }

    public String getQualityInformationData() {
        return qualityInformationData;
    }

    public void setQualityInformationData(String qualityInformationData) {
        this.qualityInformationData = qualityInformationData;
    }

    public String getSecurityAlertData() {
        return securityAlertData;
    }

    public void setSecurityAlertData(String securityAlertData) {
        this.securityAlertData = securityAlertData;
    }

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