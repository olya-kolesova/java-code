package com.javacode.projections.controller;

import com.javacode.projections.model.Department;
import com.javacode.projections.service.DepartmentService;
import com.javacode.projections.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DepartmentController {

    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @PostMapping("/api/projections/department")
    public ResponseEntity<Object> create(@RequestBody Department department) {
        return new ResponseEntity<>(departmentService.save(department), HttpStatus.CREATED);
    }

    @GetMapping("/api/projections/department/{id}")
    public ResponseEntity<Object> getById(@PathVariable long id) {
        return new ResponseEntity<>(departmentService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/api/projections/department/{id}")
    @Transactional
    public ResponseEntity<Object> delete(@PathVariable long id) {
        employeeService.deleteAllInDepartment(id);
        departmentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
