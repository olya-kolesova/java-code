package com.javacode.shipping.repository;

import com.javacode.shipping.model.ShippedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippedOrderRepository extends JpaRepository<ShippedOrder, Long> {

}
