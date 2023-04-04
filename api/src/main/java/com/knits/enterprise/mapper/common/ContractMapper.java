package com.knits.enterprise.mapper.common;

import com.knits.enterprise.dto.data.company.ContractDto;
import com.knits.enterprise.mapper.company.EmployeeMapper;
import com.knits.enterprise.mapper.security.UserMapper;
import com.knits.enterprise.model.company.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = {UserMapper.class, EmployeeMapper.class})
public interface ContractMapper extends EntityMapper<Contract, ContractDto> {
}
