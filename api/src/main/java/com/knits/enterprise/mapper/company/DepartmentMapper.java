package com.knits.enterprise.mapper.company;

import com.knits.enterprise.dto.data.company.DepartmentDto;
import com.knits.enterprise.mapper.security.UserMapper;
import com.knits.enterprise.model.company.Department;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class})
public interface DepartmentMapper extends AbstractOrganizationStructureMapper<Department, DepartmentDto> {

}
