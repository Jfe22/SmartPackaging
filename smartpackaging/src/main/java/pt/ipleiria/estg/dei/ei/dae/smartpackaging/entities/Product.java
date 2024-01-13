package pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Product {
    @Id
    int id;
    @NotNull
    String name;
    @NotNull
    String expireDate;
    @NotNull
    float weight;
    @NotNull
    String ingredients;
    @OneToOne(mappedBy = "product")
    SmartPackage smartPackage;

    public Product() {}
    public Product(int id, String name, String expireDate, float weight, String ingredients, Order order) {
        this.id = id;
        this.name = name;
        this.expireDate = expireDate;
        this.weight = weight;
        this.ingredients = ingredients;
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

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

}