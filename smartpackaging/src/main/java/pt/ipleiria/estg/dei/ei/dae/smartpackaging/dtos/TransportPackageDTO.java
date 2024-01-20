package pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos;

public class TransportPackageDTO {
    int id;
    String currentLocation;
    int orderId;
    String operatorName;

    public TransportPackageDTO() {}
    public TransportPackageDTO(int id, String currentLocation, int orderId, String operatorName) {
        this.id = id;
        this.currentLocation = currentLocation;
        this.orderId = orderId;
        this.operatorName = operatorName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}
