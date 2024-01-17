package pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos;

public class OperatorDTO extends UserDTO {
    private String locationAndTracking;
    private String environmentalConditions;
    private String securityAlerts;

    // Getters and Setters
    public String getLocationAndTracking() {
        return locationAndTracking;
    }

    public void setLocationAndTracking(String locationAndTracking) {
        this.locationAndTracking = locationAndTracking;
    }

    public String getEnvironmentalConditions() {
        return environmentalConditions;
    }

    public void setEnvironmentalConditions(String environmentalConditions) {
        this.environmentalConditions = environmentalConditions;
    }

    public String getSecurityAlerts() {
        return securityAlerts;
    }

    public void setSecurityAlerts(String securityAlerts) {
        this.securityAlerts = securityAlerts;
    }
}
