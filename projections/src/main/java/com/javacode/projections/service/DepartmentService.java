package com.javacode.projections.service;

import com.javacode.projections.model.Department;
import com.javacode.projections.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    public Department findById(Long id) throws NoSuchElementException {
        return departmentRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public void deleteById(long id) throws NoSuchElementException {
        Department department = departmentRepository.findById(id).orElseThrow(NoSuchElementException::new);
        departmentRepository.deleteById(department.getId());
    }


}
