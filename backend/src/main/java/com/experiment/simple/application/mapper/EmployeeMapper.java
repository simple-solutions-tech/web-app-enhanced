package com.experiment.simple.application.mapper;

import com.experiment.simple.data.entity.EmployeeEntity;
import com.experiment.simple.domain.Employee;
import org.mapstruct.Mapper;

@Mapper
public interface EmployeeMapper {
  EmployeeEntity toEntity(Employee employee);
  Employee toEmployee(EmployeeEntity entity);
}
