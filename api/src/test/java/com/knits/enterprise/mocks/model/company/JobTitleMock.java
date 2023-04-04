package com.knits.enterprise.mocks.model.company;

import com.knits.enterprise.model.company.JobTitle;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class JobTitleMock {

    public static JobTitle shallowJobTitle(Long counter) {
        return JobTitle.builder()
                .id(counter)
                .name("Mock JobTitle name"+counter)
                .description("Mock JobTitle description"+counter)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(12))
                .active(true)
                .build();

    }

    public static List<JobTitle> shallowListOfUsers(int howMany) {
        List<JobTitle> mockUsers = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            mockUsers.add(shallowJobTitle(Long.valueOf(i)));
        }
        return mockUsers;
    }
}
