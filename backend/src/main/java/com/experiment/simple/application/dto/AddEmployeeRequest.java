package com.experiment.simple.application.dto;

import com.experiment.simple.domain.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddEmployeeRequest {
  @NotBlank(message = "firstName is required")
  public String firstName;
  public String middleName;
  @NotBlank(message = "lastName is required")
  public String lastName;
  @NotNull(message = "salary is required")
  public Integer salary;
  public Address address;
  @NotNull(message = "departmentId is required")
  public Long departmentId;
}
