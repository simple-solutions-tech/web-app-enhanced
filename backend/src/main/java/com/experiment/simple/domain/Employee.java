package com.experiment.simple.domain;

import com.experiment.simple.application.dto.AddEmployeeRequest;
import com.experiment.simple.application.mapper.EmployeeMapper;
import com.experiment.simple.data.entity.AddressEntity;
import com.experiment.simple.data.entity.DepartmentEntity;
import com.experiment.simple.data.entity.EmployeeEntity;
import com.experiment.simple.data.repository.EmployeeRepository;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.Optional;

@Data
@Builder
public class Employee {
  public String firstName;
  public String middleName;
  public String lastName;
  public Integer salary;
  public Address address;
  public Department department;

  public String getFullName() {
    return String.format("%s %s %s",
        firstName,
        middleName != null ? middleName + " " : "",
        lastName
    ).trim();
  }

  @Autowired
  EmployeeRepository employeeRepository;

  @Autowired
  Audit audit;

  @Autowired
  EmployeeMapper mapper;

  public Optional<EmployeeEntity> getEmployee(final Long employeeId) {
    return employeeRepository.findById(employeeId);
  }

  public EmployeeEntity addEmployee(AddEmployeeRequest request) {
    Employee employee = Employee.builder()
        .firstName(request.firstName)
        .middleName(request.middleName)
        .lastName(request.lastName)
        .salary(request.salary)
        .address(request.address)
        .department(request.department)
        .build();

    EmployeeEntity savedEntity = employeeRepository.save(mapper.toEntity(employee));
    audit.log(
        "CREATE",
        "EMPLOYEE",
        savedEntity.getId().toString(),
        "",
        Map.of("name", employee.getFullName(), "salary", savedEntity.getSalary())
    );

    return savedEntity;
  }
}
