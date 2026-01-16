package com.employee.employee_management.controller;

import com.employee.employee_management.model.Employee;
import com.employee.employee_management.model.Status;
import com.employee.employee_management.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setup() {
        employeeRepository.deleteAll();
    }

    @Test
    void createEmployee_success() throws Exception {
        Employee employee = new Employee();
        employee.setName("Ankur");
        employee.setEmail("ankur@test.com");
        employee.setDepartment("IT");
        employee.setSalary(50000.0);
        employee.setStatus(Status.ACTIVE);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("ankur@test.com"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void getEmployeeById_success() throws Exception {
        Employee employee = new Employee();
        employee.setName("Ravi");
        employee.setEmail("ravi@test.com");
        employee.setDepartment("HR");
        employee.setSalary(40000.0);
        employee.setStatus(Status.ACTIVE);

        Employee saved = employeeRepository.save(employee);

        mockMvc.perform(get("/api/employees/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.email").value("ravi@test.com"));
    }

    @Test
    void getActiveEmployees_onlyActiveReturned() throws Exception {
        Employee activeEmployee = new Employee();
        activeEmployee.setName("Active One");
        activeEmployee.setEmail("active@test.com");
        activeEmployee.setDepartment("IT");
        activeEmployee.setSalary(60000.0);
        activeEmployee.setStatus(Status.ACTIVE);

        Employee inactiveEmployee = new Employee();
        inactiveEmployee.setName("Inactive One");
        inactiveEmployee.setEmail("inactive@test.com");
        inactiveEmployee.setDepartment("IT");
        inactiveEmployee.setSalary(30000.0);
        inactiveEmployee.setStatus(Status.INACTIVE);

        employeeRepository.save(activeEmployee);
        employeeRepository.save(inactiveEmployee);

        mockMvc.perform(get("/api/employees/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].email").value("active@test.com"));
    }

    @Test
    void updateEmployee_success() throws Exception {
        Employee employee = new Employee();
        employee.setName("Old Name");
        employee.setEmail("update@test.com");
        employee.setDepartment("IT");
        employee.setSalary(45000.0);
        employee.setStatus(Status.ACTIVE);

        Employee saved = employeeRepository.save(employee);

        Employee updated = new Employee();
        updated.setName("New Name");
        updated.setEmail("update@test.com");
        updated.setDepartment("Finance");
        updated.setSalary(70000.0);
        updated.setStatus(Status.ACTIVE);

        mockMvc.perform(put("/api/employees/{id}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"))
                .andExpect(jsonPath("$.department").value("Finance"))
                .andExpect(jsonPath("$.salary").value(70000.0));
    }

    @Test
    void deleteEmployee_softDelete_success() throws Exception {
        Employee employee = new Employee();
        employee.setName("To Delete");
        employee.setEmail("delete@test.com");
        employee.setDepartment("IT");
        employee.setSalary(50000.0);
        employee.setStatus(Status.ACTIVE);

        Employee saved = employeeRepository.save(employee);

        mockMvc.perform(delete("/api/employees/{id}", saved.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/employees/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
