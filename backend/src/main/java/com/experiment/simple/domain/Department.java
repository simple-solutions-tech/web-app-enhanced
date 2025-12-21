package com.experiment.simple.domain;

import com.experiment.simple.data.entity.DepartmentEntity;
import com.experiment.simple.data.repository.DepartmentRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Data
public class Department {
  public String name;
  public String description;

  @Autowired
  DepartmentRepository departmentRepository;

  public Optional<DepartmentEntity> getDepartment(final Long departmentId) {
    return departmentRepository.findById(departmentId);
  }
}
