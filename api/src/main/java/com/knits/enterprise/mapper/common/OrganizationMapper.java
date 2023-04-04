package com.knits.enterprise.mapper.common;

import com.knits.enterprise.dto.data.common.OrganizationDto;
import com.knits.enterprise.model.common.Organization;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMapper extends EntityMapper<Organization, OrganizationDto>{
}
