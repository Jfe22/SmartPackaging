package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Operator;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class OperatorBean {
    @PersistenceContext
    private EntityManager em;

    // check if operator exists
    public boolean exists(Long id) {
        Query query = em.createQuery("SELECT COUNT(o.id) FROM Operator o WHERE o.id = :id", Long.class);
        query.setParameter("id", id);
        return (long) query.getSingleResult() > 0L;
    }

    // find operator
    public Operator find(Long id)
            throws MyEntityNotFoundException {
        Operator operator = em.find(Operator.class, id);
        if (operator == null)
            throw new MyEntityNotFoundException("Operator with id: " + id + " doesn't exist");

        return em.find(Operator.class, id);
    }

    // get all operators
    public List<Operator> getAllOperators() {
        return em.createNamedQuery("getAllOperators", Operator.class).getResultList();
    }

    // create operator
    public void create(Long operator_id, String username, String email, String password, UserRole role, String locationAndTrackingData, String environmentalConditionsData, String securityAlertData)
            throws MyEntityExistsException, MyConstraintViolationException {
        if (exists(operator_id))
            throw new MyEntityExistsException("Operator with id: " + operator_id + " alredy exists");

        try {
            Operator newOperator = new Operator(operator_id, username, email, hashPassword(password), role, locationAndTrackingData, environmentalConditionsData, securityAlertData);
            em.persist(newOperator);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    // update operator
    public void update(Long operatorId, String username, String email, String password, UserRole role, String locationAndTrackingData, String environmentalConditionsData, String securityAlertData)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        Operator operator = find(operatorId);
        em.lock(operator, LockModeType.OPTIMISTIC);

        try {
            operator.setUsername(username);
            operator.setEmail(email);
            operator.setPassword(hashPassword(password));
            operator.setRole(role);
            operator.setLocationAndTrackingData(locationAndTrackingData);
            operator.setEnvironmentalConditionsData(environmentalConditionsData);
            operator.setSecurityAlertData(securityAlertData);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    // delete operator
    public void delete(Long operatorId)
            throws MyEntityNotFoundException {
        Operator operator = find(operatorId);
        em.remove(operator);
    }

    // hash password
    private String hashPassword(String password) {
        // Implement password hashing here
        return password; // Replace this with actual hashed password
    }
}
