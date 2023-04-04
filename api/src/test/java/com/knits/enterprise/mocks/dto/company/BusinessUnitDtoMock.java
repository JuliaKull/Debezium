package com.knits.enterprise.mocks.dto.company;

import com.knits.enterprise.config.Constants;
import com.knits.enterprise.dto.data.company.BusinessUnitDto;

import java.time.LocalDateTime;

public class BusinessUnitDtoMock {

    public static BusinessUnitDto shallowBusinessUnitDto(Long id) {

        return BusinessUnitDto.builder()
                .id(id)
                .name("Mock")
                .description("Mock description")
                .startDate(LocalDateTime.now().format(Constants.TIME_FORMATTER))
                .endDate(LocalDateTime.now().plusMonths(12).format(Constants.TIME_FORMATTER))
                .active(true)
                .build();
    }
}
