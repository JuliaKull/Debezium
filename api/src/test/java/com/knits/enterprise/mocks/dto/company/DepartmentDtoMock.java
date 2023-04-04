package com.knits.enterprise.mocks.dto.company;


import com.knits.enterprise.config.Constants;
import com.knits.enterprise.dto.data.company.DepartmentDto;

import java.time.LocalDateTime;


public class DepartmentDtoMock {

        public static DepartmentDto shallowDepartmentDto(Long id) {
            return DepartmentDto.builder()
                    .id(id)
                    .name("Mock Department"+id)
                    .startDate(LocalDateTime.now().format(Constants.TIME_FORMATTER))
                    .endDate(LocalDateTime.now().plusMonths(12).format(Constants.TIME_FORMATTER))
                    .active(true)
                    .build();
        }
}
