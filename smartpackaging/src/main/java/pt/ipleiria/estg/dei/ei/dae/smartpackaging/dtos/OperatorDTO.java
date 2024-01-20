package pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos;

import java.io.Serializable;

public class OperatorDTO implements Serializable {
    private String username;
    private String email;
    private String password;
    private String locationAndTracking;
    private String environmentalConditions;
    private String securityAlerts;

    public OperatorDTO() {}

    public OperatorDTO(String username, String email, String password, String locationAndTracking, String environmentalConditions, String securityAlerts) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.locationAndTracking = locationAndTracking;
        this.environmentalConditions = environmentalConditions;
        this.securityAlerts = securityAlerts;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
