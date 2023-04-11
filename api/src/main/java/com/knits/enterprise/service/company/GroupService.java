package com.knits.enterprise.service.company;

import com.knits.enterprise.dto.api.BulkUpdateResponseDto;
import com.knits.enterprise.dto.api.PaginatedResponseDto;
import com.knits.enterprise.dto.data.company.GroupDto;
import com.knits.enterprise.dto.api.EmployeeResponseDto;
import com.knits.enterprise.dto.search.company.GroupSearchDto;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.mapper.company.EmployeeMapper;
import com.knits.enterprise.mapper.company.GroupMapper;
import com.knits.enterprise.model.company.CostCenter;
import com.knits.enterprise.model.company.Employee;
import com.knits.enterprise.model.company.Group;
import com.knits.enterprise.repository.company.EmployeeRepository;
import com.knits.enterprise.repository.company.GroupRepository;
import com.knits.enterprise.service.common.GenericService;
import com.knits.enterprise.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.knits.enterprise.utils.Constant.*;
import static java.lang.String.format;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GroupService extends GenericService {


    private final GroupMapper groupMapper;
    private final GroupRepository groupRepository;

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper mapper;

    public GroupDto create(GroupDto groupDto) {

        String operationLog =format("Request to save CostCenter : %s", groupDto.toString());
        logCurrentUser(operationLog);
        String costCenterName =groupDto.getName();
        if (groupRepository.findOneByName(costCenterName).isPresent()){
            throw new UserException(format("Group with name %s already exists",costCenterName));
        }
        Group group = groupMapper.toEntity(groupDto);
        group.setCreatedBy(getCurrentUserAsEntity());
        group.setStartDate(LocalDateTime.now());
        group.setActive(true);
        return groupMapper.toDto(groupRepository.save(group));
    }

    public GroupDto update(GroupDto groupDto) {
        log.debug("Request to update Group : {}", groupDto);
        Group group = groupRepository.findById(groupDto.getId()).orElseThrow(() ->
                new UserException("Group#" + groupDto.getId() + " not found"));
        groupMapper.update(group, groupDto);
        groupRepository.save(group);
        return groupMapper.toDto(group);
    }

    public GroupDto partialUpdate(GroupDto groupDto) {
        log.debug("Request to update Group : {}", groupDto);

        Group group = groupRepository.findById(groupDto.getId()).orElseThrow(() ->
                new UserException("Group#" + groupDto.getId() + " not found"));
        groupMapper.partialUpdate(group, groupDto);
        groupRepository.save(group);
        return groupMapper.toDto(group);
    }


    public void delete(Long id) {
        log.debug("Delete Group by id : {}", id);
        groupRepository.deleteById(id);
    }

    public BulkUpdateResponseDto<GroupDto> addEmployeeToGroup(Long group, Set<Long> employeeIds) {

        Group foundGroup =  groupRepository.findByIdWithEmployees(group).orElseThrow(() -> new UserException("Group is not found"));
        Set<Employee> employeeToAdd = employeeRepository.findAllByIdAsSet(employeeIds);
        if (employeeToAdd.size()==0){
            throw new UserException(String.format("None of the Employee ids (%s) is existing",employeeIds.toString()));
        }

        Set<Long> employeeIdsExistingInGroup = foundGroup.getEmployees().stream().map(e -> e.getId()).collect(Collectors.toSet());
        Set<Long> employeeIdsFoundInDb = employeeToAdd.stream().map(e -> e.getId()).collect(Collectors.toSet());

        foundGroup.getEmployees().addAll(employeeToAdd);
        BulkUpdateResponseDto<GroupDto> response = BulkUpdateResponseDto.<GroupDto>builder().parent(groupMapper.toDto(foundGroup)).build();
        response.setReports( ApiUtils.calculateUpdateReports(employeeIds,employeeIdsFoundInDb,employeeIdsExistingInGroup));
        return response;
    }


    public PaginatedResponseDto<GroupDto> search(GroupSearchDto searchDto) {

        Page<Group> groupPage = groupRepository.findAll(searchDto.getSpecification(), searchDto.getPageable());
        List<GroupDto> groupDtos = groupMapper.toDtos(groupPage.getContent());

        return PaginatedResponseDto.<GroupDto>builder()
                .page(searchDto.getPage())
                .size(groupDtos.size())
                .sortingFields(searchDto.getSort())
                .sortDirection(searchDto.getDir().name())
                .data(groupDtos)
                .build();
    }



    private Set<EmployeeResponseDto> duplicateIds(Set<Long> employees,  Set<Long>employeeInGroups) {
        Set<EmployeeResponseDto> response = employees.stream()
                .filter(id -> employeeInGroups.contains(id))
                .map(employee -> new EmployeeResponseDto(employee, CODE100, DUPLICATE))
                .collect(Collectors.toSet());
        return response;
    }


    private Set<EmployeeResponseDto> existIds(Set<Employee> employees, Long groupId) {
        Set<EmployeeResponseDto> response = employees.stream()
                .filter(employee -> !(employee.getGroups().contains(groupRepository.findById(groupId).get())))
                .map(employee -> {
                    employee.getGroups().add(groupRepository.getById(groupId));
                    return new EmployeeResponseDto(employee.getId(), CODE1024, INSERTED);
                })
                .collect(Collectors.toSet());
        return response;
    }

    private Set<EmployeeResponseDto> notExistIds(Set<Employee> employees, Set<Long> employeesId) {
        return employeesId.stream()
                .filter(employee -> employees.stream().noneMatch(e -> e.getId().equals(employee)))
                .map(id -> new EmployeeResponseDto(id, CODE200, NOT_EXIST))
                .collect(Collectors.toSet());
    }
}


