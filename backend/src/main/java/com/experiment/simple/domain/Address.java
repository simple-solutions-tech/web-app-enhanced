package com.experiment.simple.domain;

import lombok.Data;

@Data
public class Address {
  String streetAddress1;
  String streetAddress2;
  String city;
  String state;
  String zipCode;
  String zipCodeExtension;
}
