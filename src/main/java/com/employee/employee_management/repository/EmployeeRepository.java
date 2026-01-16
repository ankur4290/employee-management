package com.employee.employee_management.repository;

import com.employee.employee_management.model.Employee;
import com.employee.employee_management.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Employee> findByStatus(Status status);
}
