package com.javacode.postgresql.repository;

import com.javacode.postgresql.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(
            value = "SELECT SUM(products.price * cart_items.quantity) AS total_cost\n" +
                    "FROM products\n" +
                    "JOIN cart_items ON products.id = cart_items.product_id\n" +
                    "JOIN orders ON orders.user_id = cart_items.user_id\n" +
                    "WHERE orders.status = 'active'\n" +
                    "AND orders.user_id = 1",
            nativeQuery = true)
    int countPrice();


    @Query(
            value = "SELECT SUM(products.price * cart_items.quantity) AS total_cost\n" +
                    "FROM products\n" +
                    "INNER JOIN cart_items ON products.id = cart_items.product_id\n" +
                    "INNER JOIN orders ON orders.user_id = cart_items.user_id\n" +
                    "WHERE orders.status = 'active'\n" +
                    "AND orders.user_id = 1",
            nativeQuery = true)
    int countPriceOptimized();

}