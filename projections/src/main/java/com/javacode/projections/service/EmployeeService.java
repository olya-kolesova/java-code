package com.javacode.projections.service;

import com.javacode.projections.dto.EmployeeDto;
import com.javacode.projections.model.Department;
import com.javacode.projections.model.Employee;
import com.javacode.projections.projection.EmployeeProjection;
import com.javacode.projections.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
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

    public List<EmployeeDto> findAllByDepartmentName(String departmentName) {
        List<EmployeeProjection> projections = employeeRepository.findAllByDepartmentName(departmentName);
        return projections.stream().map(x -> new EmployeeDto(x.getFullName(), x.getPosition(), x.getDepartment().getName())).toList();
    }

    public Employee findById(long id) throws NoSuchElementException {
        return employeeRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public Employee update(long id, Employee employee) throws NoSuchElementException {
        Employee updatedEmployee = employeeRepository.findById(id).orElseThrow(NoSuchElementException::new);
        if (employee.getLastName() != null) {
            updatedEmployee.setLastName(employee.getLastName());
        }
        updatedEmployee.setPosition(employee.getPosition());
        updatedEmployee.setSalary(employee.getSalary());
        Department department = departmentService.findById(employee.getDepartment().getId());
        updatedEmployee.setDepartment(department);
        return employeeRepository.save(updatedEmployee);
    }


    public void deleteById(long id) throws NoSuchElementException {
        employeeRepository.findById(id).orElseThrow(NoSuchElementException::new);
        employeeRepository.deleteById(id);
    }

    public void deleteAllInDepartment(long id) {
        employeeRepository.deleteAllByDepartmentId(id);
    }


}
