package com.experiment.simple.web.controller;

import com.experiment.simple.application.dto.EmployeeResponse;
import com.experiment.simple.application.mapper.EmployeeMapper;
import com.experiment.simple.data.entity.EmployeeEntity;
import com.experiment.simple.domain.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.never;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired MockMvc mockMvc;

    @MockitoBean Employee employeeService;
    @MockitoBean EmployeeMapper mapper;

    @Test
    void getEmployeeById_whenFound_returns200WithEmployee() throws Exception {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(1L);

        EmployeeResponse response = new EmployeeResponse();
        response.setFirstName("John");
        response.setLastName("Doe");
        response.setSalary(50000);

        when(employeeService.getEmployee(1L)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        mockMvc.perform(get("/api/employee/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("John"))
            .andExpect(jsonPath("$.lastName").value("Doe"))
            .andExpect(jsonPath("$.salary").value(50000));
    }

    @Test
    void getEmployeeById_whenNotFound_returns404() throws Exception {
        when(employeeService.getEmployee(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/employee/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void addEmployee_returns200WithCreatedEmployee() throws Exception {
        EmployeeEntity savedEntity = new EmployeeEntity();
        savedEntity.setId(2L);

        EmployeeResponse response = new EmployeeResponse();
        response.setFirstName("Jane");
        response.setLastName("Smith");
        response.setSalary(60000);

        when(employeeService.addEmployee(any())).thenReturn(savedEntity);
        when(mapper.toResponse(savedEntity)).thenReturn(response);

        mockMvc.perform(post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "firstName": "Jane",
                        "lastName": "Smith",
                        "salary": 60000,
                        "departmentId": 1
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("Jane"))
            .andExpect(jsonPath("$.lastName").value("Smith"))
            .andExpect(jsonPath("$.salary").value(60000));
    }

    @Test
    void addEmployee_withMissingRequiredFields_returns400() throws Exception {
        mockMvc.perform(post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());

        verify(employeeService, never()).addEmployee(any());
    }

    @Test
    void addEmployee_withMissingDepartmentId_returns400() throws Exception {
        mockMvc.perform(post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "firstName": "Jane",
                        "lastName": "Smith",
                        "salary": 60000
                    }
                    """))
            .andExpect(status().isBadRequest());

        verify(employeeService, never()).addEmployee(any());
    }

    @Test
    void addEmployee_withNonexistentDepartment_returns400() throws Exception {
        when(employeeService.addEmployee(any()))
            .thenThrow(new IllegalArgumentException("Department not found: 999"));

        mockMvc.perform(post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "firstName": "Jane",
                        "lastName": "Smith",
                        "salary": 60000,
                        "departmentId": 999
                    }
                    """))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Department not found: 999"));
    }
}
