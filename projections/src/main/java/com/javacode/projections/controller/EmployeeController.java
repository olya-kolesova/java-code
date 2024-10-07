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

    @GetMapping("/api/projections/employee")
    public ResponseEntity<List<EmployeeDto>> findAllBySurname(@RequestParam("position") String position) {
        return new ResponseEntity<>(employeeService.findAllByPosition(position), HttpStatus.OK);
    }

    @DeleteMapping("/api/projections/employee/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) {
        employeeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
