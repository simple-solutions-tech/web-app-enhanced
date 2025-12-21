package com.experiment.simple.data.entity;

import java.time.ZonedDateTime;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "address")
public class AddressEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Field 'streetAddress1' is required.")
  @Column(name = "street_address_1")
  private String streetAddress1;

  @Column(name = "street_address_2")
  private String streetAddress2;
  
  @NotBlank(message = "Field 'city' is required.")
  @Column(name = "city")
  private String city;

  @NotBlank(message = "Field 'state' is required.")
  @Column(name = "state")
  private String state;

  @NotBlank(message = "Field 'zipCode' is required.")
  @Column(name = "zip")
  private String zipCode;

  @Column(name = "zip_ext")
  private String zipCodeExtension;
}
