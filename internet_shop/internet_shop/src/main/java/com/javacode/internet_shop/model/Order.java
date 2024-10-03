package com.javacode.internet_shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double cost;

    @ManyToMany(mappedBy = "ordersWithItems")
    private List<Item> items;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    enum Status {
        ORDERED,
        READY,
        DELIVERED
    }

    @Enumerated(EnumType.STRING)
    private Status status;


    public Order() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
