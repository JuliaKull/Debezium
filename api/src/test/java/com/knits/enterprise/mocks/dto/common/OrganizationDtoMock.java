package com.knits.enterprise.mocks.dto.common;


import com.knits.enterprise.dto.data.common.OrganizationDto;

public class OrganizationDtoMock {
    public static OrganizationDto shallowOrganizationDto(Long id) {
        return OrganizationDto.builder()
                .id(id)
                .name("Mock Organization"+id)
                .build();
    }
}
