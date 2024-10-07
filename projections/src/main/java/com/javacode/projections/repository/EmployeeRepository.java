package com.javacode.projections.repository;

import com.javacode.projections.model.Employee;
import com.javacode.projections.projection.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<EmployeeProjection> findAllByPosition(String position);

}
