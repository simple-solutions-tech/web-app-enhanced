package com.experiment.simple.domain;

import com.experiment.simple.application.dto.AddEmployeeRequest;
import com.experiment.simple.application.mapper.EmployeeMapper;
import com.experiment.simple.data.entity.DepartmentEntity;
import com.experiment.simple.data.entity.EmployeeEntity;
import com.experiment.simple.data.repository.DepartmentRepository;
import com.experiment.simple.data.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class Employee {

  @Autowired EmployeeRepository employeeRepository;
  @Autowired DepartmentRepository departmentRepository;
  @Autowired Audit audit;
  @Autowired EmployeeMapper mapper;

  public Optional<EmployeeEntity> getEmployee(Long employeeId) {
    return employeeRepository.findById(employeeId);
  }

  public EmployeeEntity addEmployee(AddEmployeeRequest request) {
    DepartmentEntity department = departmentRepository.findById(request.departmentId)
        .orElseThrow(() -> new IllegalArgumentException("Department not found: " + request.departmentId));

    EmployeeEntity entity = mapper.fromRequest(request);
    entity.setDepartment(department);

    EmployeeEntity savedEntity = employeeRepository.save(entity);

    audit.log("CREATE", "EMPLOYEE", savedEntity.getId().toString(), "",
        Map.of("name", fullName(request), "salary", savedEntity.getSalary()));

    return savedEntity;
  }

  private static String fullName(AddEmployeeRequest request) {
    return request.middleName != null && !request.middleName.isBlank()
        ? String.format("%s %s %s", request.firstName, request.middleName, request.lastName)
        : String.format("%s %s", request.firstName, request.lastName);
  }
}
