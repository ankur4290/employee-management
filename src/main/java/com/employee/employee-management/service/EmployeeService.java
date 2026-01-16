package com.employee.employee_management.service;

import com.employee.employee_management.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee addEmployee(Employee employee);

    Employee getEmployeeById(Long id);

    List<Employee> getActiveEmployees();

    Employee updateEmployee(Long id, Employee employee);

    void deleteEmployee(Long id);
}
