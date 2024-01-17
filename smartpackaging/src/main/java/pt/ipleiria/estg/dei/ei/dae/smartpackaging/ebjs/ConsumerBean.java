package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Consumer;

@Stateless
public class ConsumerBean extends UserBean {
    @PersistenceContext
    private EntityManager em;

    // Consumer-specific business logic methods
    public void updateConsumerProfile(Long consumerId, Consumer consumerDetails) {
        Consumer consumer = em.find(Consumer.class, consumerId);
        if (consumer != null) {
            consumer.setDeliveryUpdatesData(consumerDetails.getDeliveryUpdatesData());
            consumer.setQualityInformationData(consumerDetails.getQualityInformationData());
            consumer.setSecurityAlertData(consumerDetails.getSecurityAlertData());

            em.merge(consumer);
        } else {
            // Handle the case where the consumer is not found
            throw new EntityNotFoundException("Consumer with ID " + consumerId + " not found.");
        }
    }

    public Consumer find(Long id) {
        return em.find(Consumer.class, id);
    }

    public Consumer getConsumerDetails(Long consumerId) {
        return em.find(Consumer.class, consumerId);
    }

    // Additional methods as required by your business logic
}
