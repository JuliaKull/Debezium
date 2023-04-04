package com.knits.enterprise.controller.company;

import com.knits.enterprise.dto.projection.AggregationResultDto;
import com.knits.enterprise.service.dashboard.EmployeeKpiService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/employees/dashboard/kpi")
@Slf4j
public class EmployeeKpiController {

    private final EmployeeKpiService service;

    @GetMapping(value = "/pie-by-gender", produces = {"application/json"})
    public ResponseEntity<List<AggregationResultDto>> employeesByGender() {
        log.debug("REST request to find all employee by gender");
        return ResponseEntity.ok().body(service.countEmployeeByGender());
    }

    @GetMapping(value = "/pie-by-job-title", produces = {"application/json"})
    public ResponseEntity<List<AggregationResultDto>> employeesByJobTitle() {
        log.debug("REST request to find all employee by JobTitle");
        return ResponseEntity.ok().body(service.countEmployeeByJobTitle());
    }

    @GetMapping(value = "/pie-by-department", produces = {"application/json"})
    public ResponseEntity<List<AggregationResultDto>> employeesByDepartment() {
        log.debug("REST request to find all employee by Department");
        return ResponseEntity.ok().body(service.countEmployeeByDepartment());
    }

    @GetMapping(value = "/pie-by-country", produces = {"application/json"})
    public ResponseEntity<List<AggregationResultDto>> employeesByOfficeCountry() {
        log.debug("REST request to find all employee by Country");
        return ResponseEntity.ok().body(service.countEmployeeByCountry());
    }

    @GetMapping(value = "/pie-by-business-unit", produces = {"application/json"})
    public ResponseEntity<List<AggregationResultDto>> employeesByBusinessUnits() {
        log.debug("REST request to find all employee by Business Units");
        return ResponseEntity.ok().body(service.countEmployeeByBusinessUnit());
    }

    @GetMapping(value = "/by-seniority", produces = {"application/json"})
    public ResponseEntity<List<AggregationResultDto>> employeesBySeniority() {
        log.debug("REST request to find all employee by Seniority");
        return ResponseEntity.ok().body(service.countEmployeeBySeniority());
    }

    @GetMapping(value = "/by-age", produces = {"application/json"})
    public ResponseEntity<List<AggregationResultDto>> employeesByAge() {
        log.debug("REST request to find all employee by Age");
        return ResponseEntity.ok().body(service.countEmployeeByAge());
    }

}
