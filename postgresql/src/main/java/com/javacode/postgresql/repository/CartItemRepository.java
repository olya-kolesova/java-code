package com.javacode.postgresql.repository;

import com.javacode.postgresql.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {


    @Query(
            value = "SELECT SUM(cart_items.quantity * products.price) AS total_cost\n" +
                    "FROM cart_items\n" +
                    "INNER JOIN products ON cart_items.product_id = products.id\n" +
                    "INNER JOIN orders ON cart_items.user_id = orders.user_id\n" +
                    "WHERE orders.status = 'active'\n" +
                    "AND orders.user_id = 1",
            nativeQuery = true)
    int countPriceWithCartItems();


    @Query(
            value = "SELECT SUM(cart_items.quantity * products.price) AS total_cost\n" +
                    "FROM cart_items\n" +
                    "INNER JOIN orders ON cart_items.user_id = orders.user_id\n" +
                    "INNER JOIN products ON cart_items.product_id = products.id\n" +
                    "WHERE orders.status = 'active'\n" +
                    "AND orders.user_id = 1",
            nativeQuery = true)
    int countPriceWithCartItemsDifferentOrder();
}