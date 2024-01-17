package pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CONSUMER")
public class Consumer extends User {
    private String deliveryUpdatesData; // e.g., real-time delivery updates, including estimated times
    private String qualityInformationData; // e.g., environmental conditions during transport
    private String securityAlertData; // e.g., detection of unauthorized opening

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
}