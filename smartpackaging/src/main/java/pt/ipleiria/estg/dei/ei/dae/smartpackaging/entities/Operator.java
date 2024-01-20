package pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities;

import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
// @DiscriminatorValue("OPERATOR")
@NamedQueries({
        @NamedQuery(
                name = "getAllOperators",
                query = "SELECT o FROM Operator o ORDER BY o.username"
        )
})
public class Operator extends User implements Serializable {
    private String locationAndTrackingData; // e.g., real-time location and tracking
    private String environmentalConditionsData; // e.g., temperature, humidity
    private String securityAlertData; // e.g., detection of opening and access alerts

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "operator", cascade = CascadeType.REMOVE)
    List<TransportPackage> transportPackages;

    public Operator() {}

    public Operator(String username, String email, String password, UserRole role, String locationAndTrackingData, String environmentalConditionsData, String securityAlertData) {
        super(username, email, password, role);
        this.locationAndTrackingData = locationAndTrackingData;
        this.environmentalConditionsData = environmentalConditionsData;
        this.securityAlertData = securityAlertData;
        transportPackages = new LinkedList<>();
    }

    // Getters and setters for Operator-specific attributes
    public String getLocationAndTrackingData() {
        return locationAndTrackingData;
    }

    public void setLocationAndTrackingData(String locationAndTrackingData) {
        this.locationAndTrackingData = locationAndTrackingData;
    }

    public String getEnvironmentalConditionsData() {
        return environmentalConditionsData;
    }

    public void setEnvironmentalConditionsData(String environmentalConditionsData) {
        this.environmentalConditionsData = environmentalConditionsData;
    }

    public String getSecurityAlertData() {
        return securityAlertData;
    }

    public void setSecurityAlertData(String securityAlertData) {
        this.securityAlertData = securityAlertData;
    }

    public List<TransportPackage> getTransportPackages() {
        return transportPackages;
    }

    public void setTransportPackages(List<TransportPackage> transportPackages) {
        this.transportPackages = transportPackages;
    }

    public void addTransportPackage(TransportPackage transportPackage) {
        transportPackages.add(transportPackage);
    }

    public void removeTransportPackage(TransportPackage transportPackage) {
        transportPackages.remove(transportPackage);
    }
}