package com.experiment.simple.web.controller;

import com.experiment.simple.application.dto.AddEmployeeRequest;
import com.experiment.simple.application.dto.EmployeeResponse;
import com.experiment.simple.application.mapper.EmployeeMapper;
import com.experiment.simple.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

  @Autowired Employee employee;
  @Autowired EmployeeMapper mapper;

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
    return employee.getEmployee(id)
        .map(mapper::toResponse)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<EmployeeResponse> addEmployee(@RequestBody AddEmployeeRequest request) {
    return ResponseEntity.ok(mapper.toResponse(employee.addEmployee(request)));
  }
}
