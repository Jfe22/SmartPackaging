package pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class SmartPackage {
    @Id
    int id;
    @NotNull
    String name;
    @NotNull
    String destination;

    public SmartPackage() {}
    public SmartPackage(int id, String name, String destination) {
        this.id = id;
        this.name = name;
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
