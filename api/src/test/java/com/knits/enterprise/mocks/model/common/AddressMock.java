package com.knits.enterprise.mocks.model.common;

import com.knits.enterprise.model.common.Address;
import com.knits.enterprise.model.common.Country;

public class AddressMock {
    public static Address shallowAddressMock(Long id) {
        return Address.builder()
                .country(new Country())
                .city("city Mock")
                .postalCode("123456")
                .street("street Mock")
                .build();
    }
}
