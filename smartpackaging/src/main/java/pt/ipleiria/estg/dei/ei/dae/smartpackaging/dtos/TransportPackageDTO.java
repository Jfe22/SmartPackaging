package pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos;

public class TransportPackageDTO {
    int id;
    String currentLocation;
    int orderId;

    public TransportPackageDTO() {}
    public TransportPackageDTO(int id, String currentLocation, int orderId) {
        this.id = id;
        this.currentLocation = currentLocation;
        this.orderId = orderId;
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
}
