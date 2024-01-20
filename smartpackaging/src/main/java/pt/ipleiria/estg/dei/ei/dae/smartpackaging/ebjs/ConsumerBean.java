package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Consumer;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Stateless
public class ConsumerBean {
    @PersistenceContext
    private EntityManager em;

    // check if consumer exists
    public boolean exists(Long id) {
        Query query = em.createQuery("SELECT COUNT(c.id) FROM Consumer c WHERE c.id = :id", Long.class);
        query.setParameter("id", id);
        return (long) query.getSingleResult() > 0L;
    }

    // find consumer
    public Consumer find(Long id)
            throws MyEntityNotFoundException {
        Consumer consumer = em.find(Consumer.class, id);
        if (consumer == null)
            throw new MyEntityNotFoundException("Consumer with id: " + id + " doesn't exist");

        return em.find(Consumer.class, id);
    }

    // get all consumers
    public List<Consumer> getAllConsumers() {
        return em.createNamedQuery("getAllConsumers", Consumer.class).getResultList();
    }

    // create consumer
    public void create(Long consumer_id, String username, String email, String password, UserRole role, String deliveryUpdatesData, String qualityInformationData, String securityAlertData)
            throws MyEntityExistsException, MyConstraintViolationException {
        if (exists(consumer_id))
            throw new MyEntityExistsException("Consumer with id: " + consumer_id + " alredy exists");

        try {
            Consumer newConsumer = new Consumer(consumer_id, username, email, hashPassword(password), role, deliveryUpdatesData, qualityInformationData, securityAlertData);
            em.persist(newConsumer);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    // update consumer
    public void update(Long consumerId, String username, String email, String password, UserRole role, String deliveryUpdatesData, String qualityInformationData, String securityAlertData)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        Consumer consumer = find(consumerId);
        em.lock(consumer, LockModeType.OPTIMISTIC);

        try {
            consumer.setUsername(username);
            consumer.setEmail(email);
            consumer.setPassword(hashPassword(password));
            consumer.setRole(role);
            consumer.setDeliveryUpdatesData(deliveryUpdatesData);
            consumer.setQualityInformationData(qualityInformationData);
            consumer.setSecurityAlertData(securityAlertData);
            em.merge(consumer);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    // delete consumer
    public void delete(Long id)
            throws MyEntityNotFoundException {
        Consumer consumer = find(id);
        em.remove(consumer);
    }

    // hash password
    private String hashPassword(String password) {
        // Implement password hashing here
        return password; // Replace this with actual hashed password
    }
}
