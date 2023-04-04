package com.knits.enterprise.mapper.company;

import com.knits.enterprise.mapper.security.UserMapper;
import com.knits.enterprise.model.company.BusinessUnit;
import com.knits.enterprise.dto.data.company.BusinessUnitDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class})
public interface BusinessUnitMapper extends AbstractOrganizationStructureMapper<BusinessUnit, BusinessUnitDto> {

}
