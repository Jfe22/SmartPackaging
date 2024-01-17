package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Producer;

@Stateless
public class ProducerBean extends UserBean {

    @PersistenceContext
    private EntityManager em;

    public void updateProducerQualityReports(Long producerId, String newQualityControlData, String newProductResponsibilityCost) {
        Producer producer = em.find(Producer.class, producerId);
        if (producer != null) {
            producer.setQualityControlData(newQualityControlData);
            producer.setProductResponsibilityCost(newProductResponsibilityCost);
            em.merge(producer);
        } else {
            // Handle the case where the producer is not found
            throw new EntityNotFoundException("Producer with ID " + producerId + " not found.");
        }
    }

    public Producer getProducerDetails(Long producerId) {
        return em.find(Producer.class, producerId);
    }

    // Additional methods as required by your business logic
}