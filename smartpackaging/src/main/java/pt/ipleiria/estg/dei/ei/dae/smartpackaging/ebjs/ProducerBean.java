package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Consumer;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Producer;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.security.Hasher;

import java.util.List;

@Stateless
public class ProducerBean {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private Hasher hasher;

    // check if producer exists
    public boolean exists(String username) {
        Query query = em.createQuery("SELECT COUNT(p.id) FROM Producer p WHERE p.username = :username", Long.class);
        query.setParameter("username", username);
        return (long) query.getSingleResult() > 0L;
    }

    // find producer
    public Producer find(String username)
            throws MyEntityNotFoundException {
        Producer producer = em.find(Producer.class, username);
        if (producer == null)
            throw new MyEntityNotFoundException("Producer with name: " + username + " doesn't exist");

        return producer;
    }

    // get all producers
    public List<Producer> getAllProducers() {
        return em.createNamedQuery("getAllProducers", Producer.class).getResultList();
    }

    // create producer
    public void create(String username, String email, String password, UserRole role, String qualityControlData, String productResponsibilityCost)
    throws MyEntityExistsException, MyConstraintViolationException {
        if (exists(username))
            throw new MyEntityExistsException("Producer with username: " + username + " alredy exists");

        try {
            Producer newProducer = new Producer(username, email, hasher.hash(password), role, qualityControlData, productResponsibilityCost);
            em.persist(newProducer);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    // update producer
    public void update(String username, String email, String password, UserRole role, String qualityControlData, String productResponsibilityCost)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        Producer producer = find(username);
        em.lock(producer, LockModeType.OPTIMISTIC);

        try {
            producer.setEmail(email);
            producer.setPassword(hasher.hash(password));
            producer.setRole(role);
            producer.setQualityControlData(qualityControlData);
            producer.setProductResponsibilityCost(productResponsibilityCost);
            em.merge(producer);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    // delete producer
    public void delete(String username)
            throws MyEntityNotFoundException {
        Producer producer = find(username);
        em.remove(producer);
    }

    // hash password
    private String hashPassword(String password) {
        // Implement password hashing here
        return password; // Replace this with actual hashed password
    }

    public Producer getProducerSmartPackages(String username)
    throws MyEntityNotFoundException {
        Producer producer = find(username);
        Hibernate.initialize(producer.getSmartPackages());
        return producer;
    }
}