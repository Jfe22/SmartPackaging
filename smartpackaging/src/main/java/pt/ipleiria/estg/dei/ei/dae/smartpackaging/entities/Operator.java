package pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@DiscriminatorValue("OPERATOR")
@NamedQueries({
        @NamedQuery(
                name = "getAllOperators",
                query = "SELECT o FROM Operator o ORDER BY o.id"
        )
})
public class Operator extends User implements Serializable {
    private String locationAndTrackingData; // e.g., real-time location and tracking
    private String environmentalConditionsData; // e.g., temperature, humidity
    private String securityAlertData; // e.g., detection of opening and access alerts

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
}