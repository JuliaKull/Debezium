package com.knits.enterprise.mocks.model.security;

import com.knits.enterprise.model.security.User;

public class UserMock {

    public static User shallowUser(Long counter) {

        return User.builder()
                .firstName("A mock firstName "+counter)
                .lastName("A mock lastName"+counter)
                .username("A mock username"+counter)
                .password("A mock password"+counter)
                .email("aMockName.aMockSurname@aMockDomain.com"+counter)
                .build();
    }
}
