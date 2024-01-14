package pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos;

public class SmartPackageDTO {
    int id;
    String packType;
    String material;
    String productName;
    int orderId;

    double currentTemperature;
    double currentHumidity;
    double currentAtmPressure;
    double maxGForce;

    public SmartPackageDTO() {}

    public SmartPackageDTO(int id, String packType, String material, String productName, int orderId) {
        this.id = id;
        this.packType = packType;
        this.material = material;
        this.productName = productName;
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPackType() {
        return packType;
    }

    public void setPackType(String packType) {
        this.packType = packType;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public double getCurrentHumidity() {
        return currentHumidity;
    }

    public void setCurrentHumidity(double currentHumidity) {
        this.currentHumidity = currentHumidity;
    }

    public double getCurrentAtmPressure() {
        return currentAtmPressure;
    }

    public void setCurrentAtmPressure(double currentAtmPressure) {
        this.currentAtmPressure = currentAtmPressure;
    }

    public double getMaxGForce() {
        return maxGForce;
    }

    public void setMaxGForce(double maxGForce) {
        this.maxGForce = maxGForce;
    }
}