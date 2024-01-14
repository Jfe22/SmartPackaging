package pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos;

import java.io.Serializable;

public class ProductDTO implements Serializable {
    int id;
    String name;
    String date;
    double weight;
    String ingredients;
    int smartPackageId;

    public ProductDTO() {}
    public ProductDTO(int id, String name, String date, double weight, String ingredients, int smartPackageId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.weight = weight;
        this.ingredients = ingredients;
        this.smartPackageId = smartPackageId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getSmartPackageId() {
        return smartPackageId;
    }

    public void setSmartPackageId(int smartPackageId) {
        this.smartPackageId = smartPackageId;
    }
}
