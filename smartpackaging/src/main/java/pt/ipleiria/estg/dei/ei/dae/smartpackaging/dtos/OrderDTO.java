package pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos;

import java.util.LinkedList;
import java.util.List;

public class OrderDTO {
    int id;
    String orderDate;
    String extDeliveryDate;
    List<SmartPackageDTO> smartPackagesDTOs;
    String consumerName;


    public OrderDTO() { this.smartPackagesDTOs = new LinkedList<>(); }

    public OrderDTO(int id, String orderDate, String extDeliveryDate, String consumerName) {
        this.id = id;
        this.orderDate = orderDate;
        this.extDeliveryDate = extDeliveryDate;
        this.consumerName = consumerName;
        smartPackagesDTOs = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getExtDeliveryDate() {
        return extDeliveryDate;
    }

    public void setExtDeliveryDate(String extDeliveryDate) {
        this.extDeliveryDate = extDeliveryDate;
    }

    public List<SmartPackageDTO> getSmartPackagesDTOs() {
        return smartPackagesDTOs;
    }

    public void setSmartPackagesDTOs(List<SmartPackageDTO> smartPackagesDTOs) {
        this.smartPackagesDTOs = smartPackagesDTOs;
    }

    public void addSmartPackage(SmartPackageDTO smartPackageDTO) {
        smartPackagesDTOs.add(smartPackageDTO);
    }

    public void removeSmartPackage(SmartPackageDTO smartPackageDTO) {
        smartPackagesDTOs.remove(smartPackageDTO);
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }
}
