package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Consumer;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.SmartPackage;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.User;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.security.Hasher;

import java.util.List;

@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private Hasher hasher;

    // check if user exists
    public boolean exists(String username) {
        Query query = em.createQuery("SELECT COUNT(u.username) FROM User u WHERE u.username = :username", Long.class);
        query.setParameter("username", username);
        return (long) query.getSingleResult() > 0L;
    }

    // find user
    public User find(String username)
    throws MyEntityNotFoundException {
        User user = em.find(User.class, username);
        if (user == null)
            throw new MyEntityNotFoundException("User " + username + " doesn't exist");

        return em.find(User.class, username);
    }

    // find or fail user -> authentication
    public User findOrFail(String username) {
        var user = em.getReference(User.class, username);
        Hibernate.initialize(user);
        return user;
    }

    // can login
    public boolean canLogin(String username, String password)
    throws MyEntityNotFoundException {
        var user = find(username);
        return user != null && user.getPassword().equals(hasher.hash(password));
    }

    // get all users
    public List<User> getAllUsers() {
        return em.createNamedQuery("getAllUsers", User.class).getResultList();
    }

    // create user
    public void create(String username, String email, String password, String role)
    throws MyEntityExistsException, MyConstraintViolationException {
        if (exists(username))
            throw new MyEntityExistsException("User " + username + " already exists");

        try {
            User newUser = new User(username, email, hasher.hash(password), UserRole.valueOf(role));
            em.persist(newUser);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    // update user
    public void update(String username, String email, String password, String role)
    throws MyEntityNotFoundException, MyConstraintViolationException {
        User user = find(username);
        if (user == null)
            throw new MyEntityNotFoundException("User " + username + " doesn't exist");

        try {
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(hasher.hash(password));
            user.setRole(UserRole.valueOf(role));
            em.merge(user);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    // delete user
    public void delete(String username)
    throws MyEntityNotFoundException {
        User user = find(username);
        em.remove(user);
    }
}
