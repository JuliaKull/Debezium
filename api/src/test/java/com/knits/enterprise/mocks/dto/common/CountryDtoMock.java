package com.knits.enterprise.mocks.dto.common;

import com.knits.enterprise.dto.data.common.CountryDto;

public class CountryDtoMock {

    public static CountryDto shallowCountryDto(Long id){

        return CountryDto.builder()
                .id(id)
                .name("A mock name")
                .build();
    }

}
