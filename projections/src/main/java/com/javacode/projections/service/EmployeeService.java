package com.javacode.projections.service;

import com.javacode.projections.dto.EmployeeDto;
import com.javacode.projections.model.Department;
import com.javacode.projections.model.Employee;
import com.javacode.projections.projection.EmployeeProjection;
import com.javacode.projections.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentService departmentService;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentService departmentService) {
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
    }

    public Employee save(Employee employee) throws NoSuchElementException {
        Department department = departmentService.findById(employee.getDepartment().getId());
        employee.setDepartment(department);
        return employeeRepository.save(employee);
    }

    public List<EmployeeDto> findAllByPosition(String position) {
        List<EmployeeProjection> projections = employeeRepository.findAllByPosition(position);
        return projections.stream().map(x -> new EmployeeDto(x.getFullName(), x.getPosition(), x.getDepartment().getName())).toList();

    }

    public void deleteById(long id) throws NoSuchElementException {
        employeeRepository.findById(id).orElseThrow(NoSuchElementException::new);
        employeeRepository.deleteById(id);
    }







}
