package com.javacode.oauthsocialapp.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "app_users")
public class AppUser {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password = "{noop}12345";

    private String email;

    private String bio;

    private String role = "USER";

    public AppUser(String username, String password, String email, String bio, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.bio = bio;
        this.role = role;
    }

}
