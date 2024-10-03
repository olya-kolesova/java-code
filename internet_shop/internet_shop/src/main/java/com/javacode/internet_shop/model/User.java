package com.javacode.internet_shop.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.javacode.internet_shop.jview.Views;
import jakarta.persistence.*;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @JsonView(Views.UserSummary.class)
    private long id;

    @JsonView(Views.UserSummary.class)
    private String name;

    @JsonView(Views.UserSummary.class)
    private String email;

    @JsonView(Views.UserDetails.class)
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
