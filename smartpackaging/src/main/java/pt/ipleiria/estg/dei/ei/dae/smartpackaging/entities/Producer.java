package pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("PRODUCER")
public class Producer extends User {
    private String qualityControlData; // e.g., environmental conditions
    private String productResponsibilityCost; // e.g., cost of re-entry of a product

    // Getters and setters for Producer-specific attributes
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
