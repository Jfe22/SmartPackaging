package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Operator;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.security.Hasher;

import java.util.List;

@Stateless
public class OperatorBean {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private Hasher hasher;

    // check if operator exists
    public boolean exists(String username) {
        Query query = em.createQuery("SELECT COUNT(o.username) FROM Operator o WHERE o.username = :username", Long.class);
        query.setParameter("username", username);
        return (long) query.getSingleResult() > 0L;
    }

    // find operator
    public Operator find(String username)
            throws MyEntityNotFoundException {
        Operator operator = em.find(Operator.class, username);
        if (operator == null)
            throw new MyEntityNotFoundException("Operator " + username + " doesn't exist");

        return em.find(Operator.class, username);
    }

    // get all operators
    public List<Operator> getAllOperators() {
        return em.createNamedQuery("getAllOperators", Operator.class).getResultList();
    }

    // create operator
    public void create(String username, String email, String password, UserRole role)
            throws MyEntityExistsException, MyConstraintViolationException {
        if (exists(username))
            throw new MyEntityExistsException("Operator " + username + " already exists");

        try {
            Operator newOperator = new Operator(username, email, hasher.hash(password), role);
            em.persist(newOperator);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    // update operator
    public void update(String username, String email, String password, UserRole role)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        Operator operator = find(username);
        em.lock(operator, LockModeType.OPTIMISTIC);

        try {
            operator.setUsername(username);
            operator.setEmail(email);
            operator.setPassword(hasher.hash(password));
            operator.setRole(role);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    // delete operator
    public void delete(String username)
            throws MyEntityNotFoundException {
        Operator operator = find(username);
        em.remove(operator);
    }

    public Operator getOperatorTransportPackages(String username)
    throws MyEntityNotFoundException {
        Operator operator = find(username);
        Hibernate.initialize(operator.getTransportPackages());
        return operator;
    }
}
