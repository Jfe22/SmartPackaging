package pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos;

import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Consumer;

import java.util.List;
import java.util.stream.Collectors;

public class ConsumerDTO {
    private int id;
    private String username;
    private String email;
    private String password;
    private String deliveryUpdates;
    private String qualityInformation;
    private String securityAlerts;

    public ConsumerDTO() {}

    public ConsumerDTO(int id, String username, String email, String password, String deliveryUpdates, String qualityInformation, String securityAlerts) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.deliveryUpdates = deliveryUpdates;
        this.qualityInformation = qualityInformation;
        this.securityAlerts = securityAlerts;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
