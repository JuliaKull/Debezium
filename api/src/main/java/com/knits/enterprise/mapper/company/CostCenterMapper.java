package com.knits.enterprise.mapper.company;

import com.knits.enterprise.mapper.security.UserMapper;
import com.knits.enterprise.model.company.CostCenter;
import com.knits.enterprise.dto.data.company.CostCenterDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class})
public interface CostCenterMapper extends AbstractOrganizationStructureMapper<CostCenter, CostCenterDto> {

}
