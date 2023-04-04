package com.knits.enterprise.controller.company;

import com.knits.enterprise.dto.api.PaginatedResponseDto;
import com.knits.enterprise.dto.data.company.EmployeeDto;
import com.knits.enterprise.dto.search.company.EmployeeSearchDto;
import com.knits.enterprise.service.company.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/employees", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<EmployeeDto> createNewEmployee(@RequestBody EmployeeDto employeeDto) {
        log.debug("REST request to create Employee");
        return ResponseEntity
                .ok()
                .body(employeeService.create(employeeDto));
    }

    @GetMapping(value = "/employees/{id}", produces = {"application/json"})
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable(value = "id") final Long id) {
        log.debug("REST request to get Employee : {}", id);
        EmployeeDto employeeFound = employeeService.findEmployeeById(id);
        return ResponseEntity
                .ok()
                .body(employeeFound);

    }

    @PatchMapping(value = "/employees", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable(value = "id") @RequestBody EmployeeDto employeeDto) {
        EmployeeDto employeeFound = employeeService.partialUpdate(employeeDto);
        return ResponseEntity
                .ok()
                .body(employeeFound);
    }

    @PutMapping(value = "/employees", produces = {"application/json"})
    public ResponseEntity<Long> deleteEmployee(@PathVariable(value = "id") final Long id) {
        log.debug("REST request to delete Employee : {}", id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().body(id);
    }

    @GetMapping(value = "/employees", produces = {"application/json"})
    public ResponseEntity<PaginatedResponseDto<EmployeeDto>> getEmployees(EmployeeSearchDto searchDto) {
        PaginatedResponseDto<EmployeeDto> paginatedResponse = employeeService.search(searchDto);
        return ResponseEntity
                .ok()
                .body(paginatedResponse);
    }


    @GetMapping(value = "/employees/search", produces = {"application/json"})
    public ResponseEntity<PaginatedResponseDto<EmployeeDto>> searchEmployees(EmployeeSearchDto searchDto) {

        log.debug("Http Get request to Search Employees with pagination with data : {}",searchDto.toString());
        PaginatedResponseDto<EmployeeDto> paginatedResponse = employeeService.search(searchDto);
        return ResponseEntity
                .ok()
                .body(paginatedResponse);
    }
}
