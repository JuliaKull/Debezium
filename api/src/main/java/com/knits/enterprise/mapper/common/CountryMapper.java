
package com.knits.enterprise.mapper.common;

import com.knits.enterprise.model.common.Country;
import com.knits.enterprise.dto.data.common.CountryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper extends EntityMapper<Country,CountryDto> {

}

