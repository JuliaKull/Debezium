package com.knits.enterprise.service.company;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.knits.enterprise.dto.api.PaginatedResponseDto;
import com.knits.enterprise.dto.data.company.EmployeeDto;
import com.knits.enterprise.dto.search.company.EmployeeSearchDto;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.mapper.company.EmployeeMapper;
import com.knits.enterprise.model.common.Organization;
import com.knits.enterprise.model.company.*;
import com.knits.enterprise.model.location.Location;
import com.knits.enterprise.repository.common.ContactRepository;
import com.knits.enterprise.repository.company.*;
import com.knits.enterprise.repository.location.LocationRepository;
import io.debezium.data.Envelope;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static io.debezium.data.Envelope.Operation.DELETE;


@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;
    private final BusinessUnitRepository businessUnitRepository;
    private final DepartmentRepository departmentRepository;
    private final JobTitleRepository jobTitleRepository;
    private final CostCenterRepository costCenterRepository;
    private final DivisionRepository divisionRepository;
    private final LocationRepository locationRepository;

    private OrganizationRepository organizationRepository;

    @Transactional
    public EmployeeDto create(EmployeeDto employeeDto) {

        BusinessUnit businessUnit = businessUnitRepository.findById(employeeDto.getBusinessUnit().getId())
                .orElseThrow(() -> new UserException("Business Unit #" + employeeDto.getBusinessUnit().getId() + " not found"));
        CostCenter costCenter = costCenterRepository.findById(employeeDto.getCostCenter().getId())
                .orElseThrow(() -> new UserException("Cost Center #" + employeeDto.getCostCenter().getId() + " not found"));
        Division division = divisionRepository.findById(employeeDto.getDivision().getId())
                .orElseThrow(() -> new UserException("Division #" + employeeDto.getDivision().getId() + " not found"));
        JobTitle jobTitle = jobTitleRepository.findById(employeeDto.getJobTitle().getId())
                .orElseThrow(() -> new UserException("Job Title #" + employeeDto.getJobTitle().getId() + " not found"));
        Department department = departmentRepository.findById(employeeDto.getDepartment().getId())
                .orElseThrow(() -> new UserException("Department #" + employeeDto.getDepartment().getId() + " not found"));
        Location location = locationRepository.findById(employeeDto.getOffice().getId())
                .orElseThrow(() -> new UserException("Location #" + employeeDto.getOffice().getId() + " not found"));
        Organization organization = organizationRepository.findById(employeeDto.getOffice().getId())
                .orElseThrow(() -> new UserException("Organization #" + employeeDto.getOrganization().getId() + " not found"));

        Employee employee = employeeMapper.toEntity(employeeDto);
        employee.setBusinessUnit(businessUnit);
        employee.setCostCenter(costCenter);
        employee.setDivision(division);
        employee.setJobTitle(jobTitle);
        employee.setDepartment(department);
        employee.setOffice(location);
        employee.setOrganization(organization);
        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    @Transactional
    public EmployeeDto findEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new UserException("User#" + id + " not found"));
        return employeeMapper.toDto(employee);
    }

    @Transactional
    public EmployeeDto partialUpdate(EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(employeeDto.getId()).orElseThrow(() -> new UserException("User#" + employeeDto.getId() + " not found"));

        employeeMapper.partialUpdate(employee, employeeDto);
        employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        log.debug("Delete Employee by id : {}", id);
        employeeRepository.deleteById(id);
    }


    public PaginatedResponseDto<EmployeeDto> search(EmployeeSearchDto searchDto) {

        Page<Employee> employeesPage = employeeRepository.findAll(searchDto.getSpecification(), searchDto.getPageable());
        List<EmployeeDto> employeeDtos = employeeMapper.toDtos(employeesPage.getContent());

        return PaginatedResponseDto.<EmployeeDto>builder()
                .page(searchDto.getPage())
                .size(employeeDtos.size())
                .sortingFields(searchDto.getSort())
                .sortDirection(searchDto.getDir().name())
                .data(employeeDtos)
                .build();
    }


    public void replicateData(Map<String, Object> payload, Envelope.Operation operation) {
        final ObjectMapper mapper = new ObjectMapper();
        final Employee employee = mapper.convertValue(payload, Employee.class);

        if (DELETE == operation) {
            employeeRepository.deleteById(employee.getId());
        } else {
            employeeRepository.save(employee);
        }
    }
}



