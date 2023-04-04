package com.knits.enterprise.mocks.model.company;

import com.knits.enterprise.model.company.Team;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TeamMock {

    public static Team shallowTeam(Long counter) {
        return Team.builder()
                .id(counter)
                .name("Mock Team name"+counter)
                .description("Mock Team description"+counter)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(12))
                .active(true)
                .build();
    }

    public static List<Team> shallowListOfTeams(int howMany) {
        List<Team> mockTeams = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            mockTeams.add(shallowTeam(Long.valueOf(i)));
        }
        return mockTeams;
    }
}
