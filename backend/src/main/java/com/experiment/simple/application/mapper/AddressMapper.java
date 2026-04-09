package com.experiment.simple.application.mapper;

import com.experiment.simple.data.entity.AddressEntity;
import com.experiment.simple.domain.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
  AddressEntity toEntity(Address address);
  Address toAddress(AddressEntity entity);
}
