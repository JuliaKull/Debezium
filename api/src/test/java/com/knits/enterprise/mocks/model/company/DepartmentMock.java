package com.knits.enterprise.mocks.model.company;

import com.knits.enterprise.model.company.Department;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DepartmentMock {

    public static Department shallowDepartment(Long counter) {
        return Department.builder()
                .id(counter)
                .name("Mock Department name" + counter)
                .description("Mock Department description" + counter)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(12))
                .active(true)
                .build();
    }

    public static List<Department> shallowListOfDepartments(int howMany) {
        List<Department> mockDepartments = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            mockDepartments.add(shallowDepartment(Long.valueOf(i)));
        }
        return mockDepartments;
    }
}
