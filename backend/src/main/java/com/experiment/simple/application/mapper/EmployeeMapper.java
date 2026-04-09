package com.experiment.simple.application.mapper;

import com.experiment.simple.application.dto.AddEmployeeRequest;
import com.experiment.simple.application.dto.EmployeeResponse;
import com.experiment.simple.data.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, DepartmentMapper.class})
public interface EmployeeMapper {

  @Mapping(target = "department", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  EmployeeEntity fromRequest(AddEmployeeRequest request);

  EmployeeResponse toResponse(EmployeeEntity entity);
}
