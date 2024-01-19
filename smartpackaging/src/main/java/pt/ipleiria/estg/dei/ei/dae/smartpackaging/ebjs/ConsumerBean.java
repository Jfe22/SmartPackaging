package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Consumer;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;

import java.util.List;

@Stateless
public class ConsumerBean {
    @PersistenceContext
    private EntityManager em;

    // find consumer
    public Consumer find(Long id) {
        return em.find(Consumer.class, id);
    }

    // create consumer
    public void create(int consumer_id, String username, String email, String password, UserRole role, String deliveryUpdatesData, String qualityInformationData, String securityAlertData) {
        Consumer consumer = new Consumer(consumer_id, username, email, hashPassword(password), role, deliveryUpdatesData, qualityInformationData, securityAlertData);
        System.out.println("Creating consumer: " + consumer); // TODO: remove this
        em.persist(consumer);
    }

    public List<Consumer> getAllConsumers() {
        return em.createNamedQuery("getAllConsumers", Consumer.class).getResultList();
    }

    // Consumer-specific business logic methods
    public void update(Long consumerId, Consumer consumerDetails) {
        Consumer consumer = em.find(Consumer.class, consumerId);
        if (consumer == null) return;

        consumer.setUsername(consumerDetails.getUsername());
        consumer.setEmail(consumerDetails.getEmail());
        consumer.setPassword(hashPassword(consumerDetails.getPassword()));
        consumer.setDeliveryUpdatesData(consumerDetails.getDeliveryUpdatesData());
        consumer.setQualityInformationData(consumerDetails.getQualityInformationData());
        consumer.setSecurityAlertData(consumerDetails.getSecurityAlertData());
        em.merge(consumer);
    }

    public void delete(Long id) {
        Consumer consumer = find(id);
        if (consumer == null) return;

        em.remove(consumer);
    }

    private String hashPassword(String password) {
        // Implement password hashing here
        return password; // Replace this with actual hashed password
    }
}
