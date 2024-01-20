package pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities;

import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;

import java.io.Serializable;

@Entity
// @DiscriminatorValue("PRODUCER")
@NamedQueries({
        @NamedQuery(
                name = "getAllProducers",
                query = "SELECT p FROM Producer p ORDER BY p.id"
        )
})
public class Producer extends User implements Serializable {
    private String qualityControlData; // e.g., environmental conditions
    private String productResponsibilityCost; // e.g., cost of re-entry of a product

    public Producer() {}

    public Producer(int id, String username, String email, String password, UserRole role, String qualityControlData, String productResponsibilityCost) {
        super(id, username, email, password, role);
        this.qualityControlData = qualityControlData;
        this.productResponsibilityCost = productResponsibilityCost;
    }

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
