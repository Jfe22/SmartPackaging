package pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllProducts",
                query = "SELECT p FROM Product p ORDER BY p.name"
        )
})
@Table(name = "products")
public class Product extends Versionable {
    @Id
    int id;
    @NotNull
    String name;
    @NotNull
    LocalDate expireDate;
    @NotNull
    double weight;
    @NotNull
    String ingredients;
    @OneToOne(mappedBy = "product")
    SmartPackage smartPackage;

    public Product() {
    }

    public Product(int id, String name, LocalDate expireDate, double weight, String ingredients) {
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

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public SmartPackage getSmartPackage() {
        return smartPackage;
    }

    public void setSmartPackage(SmartPackage smartPackage) {
        this.smartPackage = smartPackage;
    }
}