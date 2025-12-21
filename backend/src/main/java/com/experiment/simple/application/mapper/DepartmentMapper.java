package com.experiment.simple.application.mapper;

import com.experiment.simple.data.entity.DepartmentEntity;
import com.experiment.simple.domain.Department;
import org.mapstruct.Mapper;

@Mapper
public interface DepartmentMapper {
  DepartmentEntity toEntity(Department department);
  Department toDepartment(DepartmentEntity entity);
}
