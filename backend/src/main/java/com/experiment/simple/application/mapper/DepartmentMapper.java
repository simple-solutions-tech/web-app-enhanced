package com.experiment.simple.application.mapper;

import com.experiment.simple.application.dto.DepartmentResponse;
import com.experiment.simple.data.entity.DepartmentEntity;
import com.experiment.simple.domain.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
  DepartmentEntity toEntity(Department department);
  Department toDepartment(DepartmentEntity entity);
  DepartmentResponse toResponse(DepartmentEntity entity);
}
