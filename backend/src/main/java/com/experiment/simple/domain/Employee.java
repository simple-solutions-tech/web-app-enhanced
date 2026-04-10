package com.experiment.simple.domain;

import com.experiment.simple.application.dto.AddEmployeeRequest;
import com.experiment.simple.application.mapper.EmployeeMapper;
import com.experiment.simple.data.entity.DepartmentEntity;
import com.experiment.simple.data.entity.EmployeeEntity;
import com.experiment.simple.data.repository.DepartmentRepository;
import com.experiment.simple.data.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class Employee {

  @Autowired EmployeeRepository employeeRepository;
  @Autowired DepartmentRepository departmentRepository;
  @Autowired Audit audit;
  @Autowired EmployeeMapper mapper;
  @Autowired PhotoStorage photoStorage;

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

  public Optional<EmployeeEntity> uploadPhoto(Long employeeId, MultipartFile file) throws IOException {
    return employeeRepository.findById(employeeId).map(entity -> {
      try {
        String url = photoStorage.upload(employeeId, file.getContentType(), file.getInputStream(), file.getSize());
        entity.setPhotoUrl(url);
        return employeeRepository.save(entity);
      } catch (IOException e) {
        throw new RuntimeException("Photo upload failed", e);
      }
    });
  }

  private static String fullName(AddEmployeeRequest request) {
    return request.middleName != null && !request.middleName.isBlank()
        ? String.format("%s %s %s", request.firstName, request.middleName, request.lastName)
        : String.format("%s %s", request.firstName, request.lastName);
  }
}
