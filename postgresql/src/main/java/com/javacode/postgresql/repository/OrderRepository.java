package com.javacode.postgresql.repository;

import com.javacode.postgresql.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}