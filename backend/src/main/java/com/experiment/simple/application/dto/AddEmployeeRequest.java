package com.experiment.simple.application.dto;

import com.experiment.simple.domain.Address;
import com.experiment.simple.domain.Department;
import lombok.Data;

@Data
public class AddEmployeeRequest {
  public String firstName;
  public String middleName;
  public String lastName;
  public Integer salary;
  public Address address;
  public Department department;
}
