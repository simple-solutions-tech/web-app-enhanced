package com.experiment.simple.application.dto;

import com.experiment.simple.domain.Address;
import lombok.Data;

@Data
public class EmployeeResponse {
  private String firstName;
  private String middleName;
  private String lastName;
  private Integer salary;
  private Address address;
  private DepartmentResponse department;
  private String photoUrl;

}
