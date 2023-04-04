package com.knits.enterprise.service.dashboard;

import com.knits.enterprise.dto.projection.AggregationResultDto;
import com.knits.enterprise.repository.company.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class EmployeeKpiService {
    private final EmployeeRepository employeeRepository;

    public List<AggregationResultDto> countEmployeeByGender() {
        return employeeRepository.countByGender();
    }
    public List<AggregationResultDto> countEmployeeByBusinessUnit(){
        return employeeRepository.countByBusinessUnit();
    }
    public List<AggregationResultDto> countEmployeeByDepartment() {
        return employeeRepository.countByDepartment();
    }
    public List<AggregationResultDto> countEmployeeByJobTitle (){
        return employeeRepository.countByJobTitle();
    }
    public List<AggregationResultDto> countEmployeeByCountry(){
        return employeeRepository.countByCountry();
    }

    public List<AggregationResultDto> countEmployeeBySeniority(){
        return employeeRepository.countBySeniority(LocalDateTime.now().getYear());
    }

    public List<AggregationResultDto> countEmployeeByAge(){
        return employeeRepository.countByAge(LocalDateTime.now().getYear());
    }
}
