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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}
