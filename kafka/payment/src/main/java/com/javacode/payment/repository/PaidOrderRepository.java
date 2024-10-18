package com.javacode.payment.repository;

import com.javacode.payment.model.PaidOrder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaidOrderRepository extends JpaRepository<PaidOrder, Long> {

}
