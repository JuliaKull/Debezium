package com.knits.enterprise.mapper.location;

import com.knits.enterprise.dto.data.location.LocationDto;
import com.knits.enterprise.mapper.common.AddressMapper;
import com.knits.enterprise.mapper.common.EntityMapper;
import com.knits.enterprise.model.location.Location;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",uses = {AddressMapper.class},unmappedTargetPolicy = ReportingPolicy.WARN)
public interface LocationMapper extends EntityMapper<Location,LocationDto> {
}

