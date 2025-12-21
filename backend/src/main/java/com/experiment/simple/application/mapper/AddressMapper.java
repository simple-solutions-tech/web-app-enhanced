package com.experiment.simple.application.mapper;

import com.experiment.simple.data.entity.AddressEntity;
import com.experiment.simple.domain.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {
  AddressEntity toEntity(Address address);
  Address toAddress(AddressEntity entity);
}
