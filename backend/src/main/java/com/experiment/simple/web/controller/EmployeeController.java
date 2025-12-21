package com.experiment.simple.web.controller;

import com.experiment.simple.application.dto.AddEmployeeRequest;
import com.experiment.simple.application.mapper.EmployeeMapper;
import com.experiment.simple.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.experiment.simple.data.entity.EmployeeEntity;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

  @Autowired
  Employee employee;

  @Autowired
  EmployeeMapper mapper;

  @GetMapping("/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
    return employee.getEmployee(id)
        .map(mapper::toEmployee)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Employee> addEmployee(@RequestBody AddEmployeeRequest request) {
    return ResponseEntity.ok(mapper.toEmployee(employee.addEmployee(request)));
  }
}
