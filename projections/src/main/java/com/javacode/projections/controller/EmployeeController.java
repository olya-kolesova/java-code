package com.javacode.projections.controller;

import com.javacode.projections.dto.EmployeeDto;
import com.javacode.projections.model.Employee;
import com.javacode.projections.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/api/projections/employee")
    public ResponseEntity<Object> create(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.save(employee), HttpStatus.CREATED);
    }

    @GetMapping("/api/projections/employee/position")
    public ResponseEntity<List<EmployeeDto>> findAllByPosition(@RequestParam("position") String position) {
        return new ResponseEntity<>(employeeService.findAllByPosition(position), HttpStatus.OK);
    }


    @GetMapping("/api/projections/employee/department_name")
    public ResponseEntity<List<EmployeeDto>> findAllByDepartmentName(@RequestParam("department_name") String departmentName) {
        return new ResponseEntity<>(employeeService.findAllByDepartmentName(departmentName), HttpStatus.OK);
    }

    @GetMapping("/api/projections/employee/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/api/projections/employee/{id}")
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.update(id, employee), HttpStatus.OK);
    }


    @DeleteMapping("/api/projections/employee/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) {
        employeeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
