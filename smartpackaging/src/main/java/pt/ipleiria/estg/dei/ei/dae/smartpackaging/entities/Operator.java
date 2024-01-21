package pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities;

import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
// @DiscriminatorValue("OPERATOR")
@NamedQueries({
        @NamedQuery(
                name = "getAllOperators",
                query = "SELECT o FROM Operator o ORDER BY o.username"
        )
})
public class Operator extends User implements Serializable {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "operator", cascade = CascadeType.REMOVE)
    List<TransportPackage> transportPackages;

    public Operator() {}

    public Operator(String username, String email, String password, UserRole role) {
        super(username, email, password, role);
        transportPackages = new LinkedList<>();
    }

    public List<TransportPackage> getTransportPackages() {
        return transportPackages;
    }

    public void setTransportPackages(List<TransportPackage> transportPackages) {
        this.transportPackages = transportPackages;
    }

    public void addTransportPackage(TransportPackage transportPackage) {
        transportPackages.add(transportPackage);
    }

    public void removeTransportPackage(TransportPackage transportPackage) {
        transportPackages.remove(transportPackage);
    }
}