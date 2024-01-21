package pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos;

import java.io.Serializable;

public class ProducerDTO implements Serializable {
    private String username;
    private String email;
    private String password;

    public ProducerDTO() {}

    public ProducerDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

