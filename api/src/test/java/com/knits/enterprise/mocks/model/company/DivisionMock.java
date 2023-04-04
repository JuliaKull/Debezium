package com.knits.enterprise.mocks.model.company;

import com.knits.enterprise.model.company.Division;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class DivisionMock {

    public static Division shallowDivision(Long counter) {
        return Division.builder()
                .id(counter)
                .name("Mock Division name"+counter)
                .description("Mock Division description"+counter)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(12))
                .active(true)
                .build();
    }

    public static List<Division> shallowListOfUsers(int howMany) {
        List<Division> mockUsers = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            mockUsers.add(shallowDivision(Long.valueOf(i)));
        }
        return mockUsers;
    }
}
