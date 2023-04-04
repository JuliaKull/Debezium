package com.knits.enterprise.mocks.dto.company;

import com.knits.enterprise.config.Constants;
import com.knits.enterprise.dto.data.company.GroupDto;

import java.time.LocalDateTime;

public class GroupDtoMock {

    public static GroupDto shallowGroupDto(Long counter) {
        return GroupDto.builder()
                .id(counter)
                .name("Mock "+ counter)
                .description("Mock description "+counter)
                .startDate(LocalDateTime.now().format(Constants.TIME_FORMATTER))
                .endDate(LocalDateTime.now().plusMonths(12).format(Constants.TIME_FORMATTER))
                .active(true)
                .build();
    }
}
