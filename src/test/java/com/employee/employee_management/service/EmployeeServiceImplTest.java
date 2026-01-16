package com.employee.employee_management.service;

import com.employee.employee_management.model.Employee;
import com.employee.employee_management.model.Status;
import com.employee.employee_management.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addEmployee_shouldSaveWhenEmailIsUnique() {
        Employee employee = Employee.builder()
                .name("Ankur")
                .email("ankur@test.com")
                .department("IT")
                .salary(50000.0)
                .build();

        when(repository.existsByEmail(employee.getEmail())).thenReturn(false);
        when(repository.save(employee)).thenReturn(employee);

        Employee saved = service.addEmployee(employee);

        assertThat(saved).isNotNull();
        verify(repository).save(employee);
    }

    @Test
    void addEmployee_shouldThrowWhenEmailExists() {
        Employee employee = Employee.builder()
                .email("ankur@test.com")
                .build();

        when(repository.existsByEmail(employee.getEmail())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> service.addEmployee(employee));
    }

    @Test
    void getEmployeeById_shouldReturnEmployee() {
        Employee employee = Employee.builder()
                .id(1L)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = service.getEmployeeById(1L);

        assertThat(result).isNotNull();
    }

    @Test
    void getEmployeeById_shouldThrowWhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getEmployeeById(1L));
    }

    @Test
    void getActiveEmployees_shouldReturnOnlyActive() {
        when(repository.findByStatus(Status.ACTIVE))
                .thenReturn(List.of(Employee.builder().status(Status.ACTIVE).build()));

        List<Employee> result = service.getActiveEmployees();

        assertThat(result).hasSize(1);
    }

    @Test
    void deleteEmployee_shouldSetStatusInactive() {
        Employee employee = Employee.builder()
                .id(1L)
                .status(Status.ACTIVE)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(employee));

        service.deleteEmployee(1L);

        assertThat(employee.getStatus()).isEqualTo(Status.INACTIVE);
        verify(repository).save(employee);
    }
}
