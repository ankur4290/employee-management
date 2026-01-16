package com.employee.employee_management.service;

import com.employee.employee_management.model.Employee;
import com.employee.employee_management.model.Status;
import com.employee.employee_management.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Employee addEmployee(Employee employee) {
        if (repository.existsByEmail(employee.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return repository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    public List<Employee> getActiveEmployees() {
        return repository.findByStatus(Status.ACTIVE);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!existing.getEmail().equals(employee.getEmail())
                && repository.existsByEmail(employee.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        existing.setName(employee.getName());
        existing.setEmail(employee.getEmail());
        existing.setDepartment(employee.getDepartment());
        existing.setSalary(employee.getSalary());

        return repository.save(existing);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existing.setStatus(Status.INACTIVE);
        repository.save(existing);
    }
}
