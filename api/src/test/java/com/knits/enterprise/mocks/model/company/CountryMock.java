package com.knits.enterprise.mocks.model.company;

import com.knits.enterprise.model.common.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryMock {

    public static Country shallowCountry(Long id){

        return Country.builder()
                .id(id)
                .name("A mock Country name")
                .build();
    }

    public static List<Country> shallowListOfCountry(int howMany){
        List<Country> mockCountry = new ArrayList<>();
        for (int i=0;i<howMany; i++){
            mockCountry.add(shallowCountry(Long.valueOf(i)));
        }
        return mockCountry;
    }
}
