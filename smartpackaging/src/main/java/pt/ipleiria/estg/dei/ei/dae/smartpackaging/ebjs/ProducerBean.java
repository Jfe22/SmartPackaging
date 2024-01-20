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
    public boolean exists(int id) {
        Query query = em.createQuery("SELECT COUNT(p.id) FROM Producer p WHERE p.id = :id", Long.class);
        query.setParameter("id", id);
        return (long) query.getSingleResult() > 0L;
    }

    // find producer
    public Producer find(int id)
            throws MyEntityNotFoundException {
        Producer producer = em.find(Producer.class, id);
        if (producer == null)
            throw new MyEntityNotFoundException("Producer with id: " + id + " doesn't exist");

        return em.find(Producer.class, id);
    }

    // get all producers
    public List<Producer> getAllProducers() {
        return em.createNamedQuery("getAllProducers", Producer.class).getResultList();
    }

    // create producer
    public void create(int producer_id, String username, String email, String password, UserRole role, String qualityControlData, String productResponsibilityCost)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        if (exists(producer_id))
            throw new MyEntityNotFoundException("Producer with id: " + producer_id + " alredy exists");

        try {
            Producer newProducer = new Producer(producer_id, username, email, hasher.hash(password), role, qualityControlData, productResponsibilityCost);
            em.persist(newProducer);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    // update producer
    public void update(int producerId, String username, String email, String password, UserRole role, String qualityControlData, String productResponsibilityCost)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        Producer producer = find(producerId);
        em.lock(producer, LockModeType.OPTIMISTIC);

        try {
            producer.setUsername(username);
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
    public void delete(int id)
            throws MyEntityNotFoundException {
        Producer producer = find(id);
        em.remove(producer);
    }

    // hash password
    private String hashPassword(String password) {
        // Implement password hashing here
        return password; // Replace this with actual hashed password
    }

    public Producer getProducerSmartPackages(int id)
    throws MyEntityNotFoundException {
        Producer producer = find(id);
        Hibernate.initialize(producer.getSmartPackages());
        return producer;
    }
}