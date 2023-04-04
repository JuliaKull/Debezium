package com.knits.enterprise.mapper.common;

import com.knits.enterprise.dto.data.common.AddressDto;
import com.knits.enterprise.model.common.Address;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CountryMapper.class})
public interface AddressMapper extends EntityMapper<Address, AddressDto> {
}
