package pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos;

import java.io.Serializable;

public class ProducerDTO implements Serializable {
    private int id;
    private String username;
    private String email;
    private String password;
    private String qualityControlData;
    private String productResponsibilityCost;

    public ProducerDTO() {}

    public ProducerDTO(int id, String username, String email, String password, String qualityControlData, String productResponsibilityCost) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.qualityControlData = qualityControlData;
        this.productResponsibilityCost = productResponsibilityCost;
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

    public String getQualityControlData() {
        return qualityControlData;
    }

    public void setQualityControlData(String qualityControlData) {
        this.qualityControlData = qualityControlData;
    }

    public String getProductResponsibilityCost() {
        return productResponsibilityCost;
    }

    public void setProductResponsibilityCost(String productResponsibilityCost) {
        this.productResponsibilityCost = productResponsibilityCost;
    }
}

