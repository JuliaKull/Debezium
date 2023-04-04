package com.knits.enterprise.mocks.model.company;

import com.knits.enterprise.model.company.Employee;
import com.knits.enterprise.model.enums.Gender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.knits.enterprise.mocks.model.company.GroupMock.shallowGroupWithEmployee;

public class EmployeeMock {

        public static Employee shallowEmployeeMock(Long id) {
            return Employee.builder()
                    .firstName("Mock Employee firstName "+id)
                    .lastName("Mock Employee lastName "+id)
                    .email("Mock Employee email "+id)
                    .birthDate(LocalDate.now().minusYears(24))
                    .gender(Gender.MALE)
                    .startDate(LocalDate.now().minusMonths(7))
                    .companyPhone("123456789"+id)
                    .companyMobileNumber("123456789"+id)
                    .build();
        }


//    public static Employee shallowEmployeeWithGroupMock(Long id) {
//        return Employee.builder()
//                .firstName("Mock Employee firstName "+id)
//                .lastName("Mock Employee lastName "+id)
//                .email("Mock Employee email "+id)
//                .birthDate(LocalDateTime.now().minusYears(24))
//                .gender(Gender.Male)
//                .startDate(LocalDateTime.now().minusMonths(7))
//                .companyPhone("123456789"+id)
//                .companyMobileNumber("123456789"+id)
//                .groups(Set.of(shallowGroupWithEmployee(1L,3)))
//                .build();
//    }


        public static List<Employee> shallowEmployeeMockList(int howMany) {
            return shallowEmployeeMockList(howMany,0);
        }

    public static Set<Employee> shallowEmployeeMockSet(int howMany) {
        return shallowEmployeeMockSet(howMany,0);
    }

    public static Set<Employee> shallowEmployeeMockSet(int howMany, long startId) {
        Set<Employee> mockEmployees = new HashSet<>();
        for (int i = 0; i < howMany; i++) {
            mockEmployees.add(shallowEmployeeMock((long) startId+i));
        }
        return mockEmployees;
    }

    public static List<Employee> shallowEmployeeMockList(int howMany, long startId) {
        List<Employee> mockEmployees = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            mockEmployees.add(shallowEmployeeMock((long) startId+i));
        }
        return mockEmployees;
    }
}
