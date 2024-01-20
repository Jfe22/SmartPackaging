package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Consumer;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.User;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.security.Hasher;

import java.time.LocalDate;
import java.util.List;

@Stateless
public class ConsumerBean {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private Hasher hasher;

    // check if consumer exists
    public boolean exists(String username) {
        Query query = em.createQuery("SELECT COUNT(c.username) FROM Consumer c WHERE c.username = :username", Long.class);
        query.setParameter("username", username);
        return (long) query.getSingleResult() > 0L;
    }

    // find consumer
    public Consumer find(String username)
            throws MyEntityNotFoundException {
        Consumer consumer = em.find(Consumer.class, username);
        if (consumer == null)
            throw new MyEntityNotFoundException("Consumer " + username + " doesn't exist");

        return em.find(Consumer.class, username);
    }

    // get all consumers
    public List<Consumer> getAllConsumers() {
        return em.createNamedQuery("getAllConsumers", Consumer.class).getResultList();
    }

    // create consumer
    public void create(String username, String email, String password, UserRole role, String deliveryUpdatesData, String qualityInformationData, String securityAlertData)
            throws MyEntityExistsException, MyConstraintViolationException {
        if (exists(username))
            throw new MyEntityExistsException("Consumer " + username + " already exists");

        try {
            Consumer newConsumer = new Consumer(username, email, hasher.hash(password), role, deliveryUpdatesData, qualityInformationData, securityAlertData);
            em.persist(newConsumer);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    // update consumer
    public void update(String username, String email, String password, UserRole role, String deliveryUpdatesData, String qualityInformationData, String securityAlertData)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        Consumer consumer = find(username);
        em.lock(consumer, LockModeType.OPTIMISTIC);

        try {
            consumer.setEmail(email);
            consumer.setPassword(hasher.hash(password));
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
    public void delete(String username)
            throws MyEntityNotFoundException {
        Consumer consumer = find(username);
        em.remove(consumer);
    }

    // hash password
    private String hashPassword(String password) {
        // Implement password hashing here
        return password; // Replace this with actual hashed password
    }

    private Consumer getConsumerOrders(String username)
    throws MyEntityNotFoundException {
        Consumer consumer = find(username);
        Hibernate.initialize(consumer.getOrders());
        return consumer;
    }
}
