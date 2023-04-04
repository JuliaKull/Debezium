package com.knits.enterprise.mocks.model.company;

import com.knits.enterprise.model.company.Employee;
import com.knits.enterprise.model.company.Group;
import com.knits.enterprise.model.security.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.knits.enterprise.mocks.model.company.EmployeeMock.shallowEmployeeMockSet;

public class GroupMock {

    public static Group shallowGroup(Long counter) {
        return Group.builder()
                .id(counter)
                .name("Mock name"+counter)
                .description("Mock description"+counter)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(12))
                .active(true)
                .build();
    }

    public static Group shallowGroupWithEmployee(Long counter, Set<Employee> employeeSet,User user) {
        return Group.builder()
                .name("Mock name"+counter)
                .description("Mock description"+counter)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(12))
                .active(true)
                .employees(employeeSet)
                .createdBy(user)
                .build();
    }

    public static Group shallowGroupWithEmployee(Long counter, Set<Employee> employeeSet) {
        return Group.builder()
                .id(counter)
                .name("Mock name"+counter)
                .description("Mock description"+counter)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(12))
                .active(true)
                .employees(employeeSet)
                .build();
    }

    public static List<Group> shallowListOfGroups(int howMany) {
        List<Group> mockUsers = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            mockUsers.add(shallowGroup(Long.valueOf(i)));
        }
        return mockUsers;
    }
}
