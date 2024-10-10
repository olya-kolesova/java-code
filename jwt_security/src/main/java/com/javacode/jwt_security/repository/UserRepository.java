package com.javacode.jwt_security.repository;


import com.javacode.jwt_security.model.Role;
import com.javacode.jwt_security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByRole(Role role);

    User save(User user);

}
