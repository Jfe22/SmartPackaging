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
    public boolean exists(int id) {
        Query query = em.createQuery("SELECT COUNT(u.id) FROM User u WHERE u.id = :id", Long.class);
        query.setParameter("id", id);
        return (long) query.getSingleResult() > 0L;
    }

    // find user
    public User find(int id)
            throws MyEntityNotFoundException {
        User user = em.find(User.class, id);
        if (user == null)
            throw new MyEntityNotFoundException("User with id: " + id + " doesn't exist");

        return em.find(User.class, id);
    }

    // find user by name
    public User findByName(String username)
            throws MyEntityNotFoundException {
        // Fetch the user ID where username matches the provided username
        TypedQuery<Integer> query = em.createQuery("SELECT u.id FROM User u WHERE u.username = :username", Integer.class);
        query.setParameter("username", username);

        Integer userId;
        try {
            userId = query.getSingleResult();
        } catch (NoResultException e) {
            // No user found with the given username
            throw new MyEntityNotFoundException("User with username: " + username + " doesn't exist");
        }

        // find user with the given ID
        User user = find(userId);
        return user;
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
        var user = findByName(username);
        return user != null && user.getPassword().equals(hasher.hash(password));
    }

    // get all users
    public List<User> getAllUsers() {
        return em.createNamedQuery("getAllUsers", User.class).getResultList();
    }

    // create user
    public void create(int user_id, String username, String email, String password, UserRole role)
            throws MyEntityExistsException, MyConstraintViolationException {
        if (exists(user_id))
            throw new MyEntityExistsException("User with id: " + user_id + " alredy exists");

        try {
            User newUser = new User(user_id, username, email, hasher.hash(password), role);
            em.persist(newUser);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    // update user
    public void update(int id, String username, String email, String password, UserRole role)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        User user = find(id);
        if (user == null)
            throw new MyEntityNotFoundException("User with id: " + id + " doesn't exist");

        try {
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(hasher.hash(password));
            user.setRole(role);
            em.merge(user);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    // delete user
    public void delete(int id)
            throws MyEntityNotFoundException {
        User user = find(id);
        em.remove(user);
    }

    private String hashPassword(String password) {
        // Implement password hashing here
        return password; // Replace this with actual hashed password
    }

    // authenticate user
    public User authenticateUser(String username, String password) {
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password); // Password should be hashed
        List<User> users = query.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }
}
