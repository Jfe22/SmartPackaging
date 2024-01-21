package pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities;

import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
// @DiscriminatorValue("PRODUCER")
@NamedQueries({
        @NamedQuery(
                name = "getAllProducers",
                query = "SELECT p FROM Producer p ORDER BY p.username"
        )
})
public class Producer extends User implements Serializable {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "producer", cascade = CascadeType.REMOVE)
    List<SmartPackage> smartPackages;

    public Producer() {}

    public Producer(String username, String email, String password, UserRole role) {
        super(username, email, password, role);
        smartPackages = new LinkedList<>();
    }

    public List<SmartPackage> getSmartPackages() {
        return smartPackages;
    }

    public void setSmartPackages(List<SmartPackage> smartPackages) {
        this.smartPackages = smartPackages;
    }

    public void addSmartPackage(SmartPackage smartPackage) {
        smartPackages.add(smartPackage);
    }

    public void removeSmartPackage(SmartPackage smartPackage) {
        smartPackages.remove(smartPackage);
    }

}
