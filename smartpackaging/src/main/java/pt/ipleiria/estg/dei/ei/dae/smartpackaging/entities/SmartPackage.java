package pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.PackType;

@Entity
public class SmartPackage {
    @Id
    int id;
    @NotNull
    PackType type;
    @NotNull
    String material;
    @NotNull
    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    Product product;
    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;



    float currentTemperature;
    float currentHumidity;
    float currentAtmPressure;
    String currentLocation;
    float maxGForce;


    public SmartPackage() {}
    public SmartPackage(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
