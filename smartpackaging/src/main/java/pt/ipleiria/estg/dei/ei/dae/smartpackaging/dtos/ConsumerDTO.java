package pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ConsumerDTO implements Serializable {
    private int id;
    private String username;
    private String email;
    private String password;
    private String deliveryUpdates;
    private String qualityInformation;
    private String securityAlerts;

    List<OrderDTO> ordersDTOs;


    public ConsumerDTO() {}

    public ConsumerDTO(int id, String username, String email, String password, String deliveryUpdates, String qualityInformation, String securityAlerts) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.deliveryUpdates = deliveryUpdates;
        this.qualityInformation = qualityInformation;
        this.securityAlerts = securityAlerts;
        ordersDTOs = new LinkedList<>();
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

    public String getDeliveryUpdates() { return deliveryUpdates; }

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

    public List<OrderDTO> getOrdersDTOs() {
        return ordersDTOs;
    }

    public void setOrdersDTOs(List<OrderDTO> ordersDTOs) {
        this.ordersDTOs = ordersDTOs;
    }

    public void addOrderDTO(OrderDTO orderDTO) {
        ordersDTOs.add(orderDTO);
    }

    public void removeOrderDTO(OrderDTO orderDTO) {
        ordersDTOs.remove(orderDTO);
    }
}
