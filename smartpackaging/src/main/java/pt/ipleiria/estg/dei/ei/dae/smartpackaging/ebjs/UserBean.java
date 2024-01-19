package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.SmartPackage;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.User;

import java.util.List;

@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager em;

    // find user
    public User find(Long id) {
        return em.find(User.class, id);
    }

    // create user
    public void create(int user_id, String username, String email, String password) {
        User user = new User(user_id, username, email, hashPassword(password));
        em.persist(user);
    }

    // update user
    public void updateUser(Long id, User updatedUser) {
        User user = find(id);
        if (user == null) return;

        // Update fields
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword()); // Ensure this is hashed
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());
        em.persist(user);
    }

    // delete user
    public void deleteUser(Long id) {
        User user = find(id);
        if (user == null) return;

        em.remove(user);
    }

    public List<User> getAllUsers() {
        return em.createNamedQuery("getAllUsers", User.class).getResultList();
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
