package com.knits.enterprise.mapper.security;

import com.knits.enterprise.dto.data.security.RoleDto;
import com.knits.enterprise.mapper.common.EntityMapper;
import com.knits.enterprise.model.security.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface RolerMapper extends EntityMapper<Role, RoleDto> {



}