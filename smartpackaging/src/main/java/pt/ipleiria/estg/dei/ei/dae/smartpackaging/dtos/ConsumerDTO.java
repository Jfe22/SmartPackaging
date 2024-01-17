package pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos;

public class ConsumerDTO extends UserDTO {
    private String deliveryUpdates;
    private String qualityInformation;
    private String securityAlerts;

    // Getters and Setters
    public String getDeliveryUpdates() {
        return deliveryUpdates;
    }

    public void setDeliveryUpdates(String deliveryUpdates) {
        this.deliveryUpdates = deliveryUpdates;
    }

    public String getQualityInformation() {
        return qualityInformation;
    }

    public void setQualityInformation(String qualityInformation) {
        this.qualityInformation = qualityInformation;
    }

    public String getSecurityAlerts() {
        return securityAlerts;
    }

    public void setSecurityAlerts(String securityAlerts) {
        this.securityAlerts = securityAlerts;
    }
}
