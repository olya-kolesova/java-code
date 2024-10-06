package com.javacode.onlineshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor(force = true)
@Getter
@Setter
@Table(name = "orders")
@Validated
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
       name = "ordered_products",
       joinColumns = @JoinColumn(name = "order_id"),
       inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    private LocalDateTime orderDate;

    @NotBlank
    private String shippingAddress;

    private double totalPrice;

    public enum OrderStatus {
        ORDERED,
        DELIVERED,
        RECEIVED,
    }

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;


    public void removeProduct(Product product) {
        products.remove(product);
    }
}
