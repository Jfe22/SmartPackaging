package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.User;

import java.util.List;

@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager em;

    public void createUser(User user) {
        // Implement logic to create a new user
    }

    public User findUser(Long id) {
        // Implement logic to find a user by ID
        return null; // Replace this with the actual return value
    }

    // Additional methods for user authentication, updating user info, etc.
    // update user
    public User updateUser(Long id, User updatedUser) {
        User user = em.find(User.class, id);
        if (user != null) {
            // Update fields
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword()); // Ensure this is hashed
            user.setEmail(updatedUser.getEmail());
            user.setRole(updatedUser.getRole());
            em.merge(user);
        }
        return user;
    }

    // delete user
    public void deleteUser(Long id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
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
