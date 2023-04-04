package com.knits.enterprise.service.company;


import com.knits.enterprise.dto.api.BulkUpdateResponseDto;
import com.knits.enterprise.dto.api.UpdateReportDto;
import com.knits.enterprise.dto.data.company.AbstractOrganizationStructureDto;
import com.knits.enterprise.dto.data.company.GroupDto;
import com.knits.enterprise.mapper.company.AbstractOrganizationStructureMapper;
import com.knits.enterprise.mapper.company.GroupMapper;
import com.knits.enterprise.mapper.company.GroupMapperImpl;
import com.knits.enterprise.mocks.dto.company.GroupDtoMock;
import com.knits.enterprise.model.company.AbstractOrganizationStructure;
import com.knits.enterprise.model.company.Employee;
import com.knits.enterprise.model.company.Group;
import com.knits.enterprise.repository.company.EmployeeRepository;
import com.knits.enterprise.repository.company.GroupRepository;
import com.knits.enterprise.service.security.UserService;
import com.knits.enterprise.utils.TestConsts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.internal.util.collections.Sets;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

import static com.knits.enterprise.mocks.model.company.GroupMock.shallowGroup;
import static com.knits.enterprise.mocks.model.company.GroupMock.shallowGroupWithEmployee;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
class GroupServiceTest extends AbstractOrganizationStructureServiceTest {
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private GroupService groupService;
    @Spy
    private GroupMapper mapper = new GroupMapperImpl();
    @Captor
    private ArgumentCaptor<Group> captor;

    @Test
    @DisplayName("Group save BusinessUnit Success")
    void saveSuccess() {
        saveSuccessTemplate(GroupDtoMock.shallowGroupDto(TestConsts.NO_COUNTER));
    }

    @Test
    @DisplayName("Group partial Update success")
    void partialUpdate() {
        partialUpdateSuccessTemplate(shallowGroup(1L));
    }

    @Test
    @DisplayName("Group Update success")
    void testUpdate() {
        updateSuccessTemplate(shallowGroup(1L));
    }

    @Test
    @DisplayName("delete success")
    void deleteSuccess() {
        deleteSuccessTemplate(shallowGroup(1L));
    }

    @Override
    protected <E extends AbstractOrganizationStructure, D extends AbstractOrganizationStructureDto> AbstractOrganizationStructureMapper<E, D> getMapper() {
        return (AbstractOrganizationStructureMapper<E, D>)mapper;
    }

    @Override
    protected JpaRepository getRepository() {
        return (JpaRepository)groupRepository;
    }

    @Override
    protected UserService getUserService() {
        return userService;
    }

    @Override
    protected <T> Class<T> getEntityClass() {
        return (Class<T>)Group.class;
    }

    @Override
    protected <E> ArgumentCaptor<E> getEntityCaptor() {
        return (ArgumentCaptor<E>)captor;
    }

    @Override
    protected AbstractOrganizationStructureDto saveInternal(AbstractOrganizationStructureDto toSaveDto) {
        return groupService.create((GroupDto) toSaveDto);
    }

    @Override
    protected <D extends AbstractOrganizationStructureDto> D partialUpdateInternal(D toSaveDto) {
        return (D) groupService.partialUpdate((GroupDto) toSaveDto);
    }

    @Override
    protected <D extends AbstractOrganizationStructureDto> D updateInternal(D toSaveDto) {
        return (D) groupService.update((GroupDto) toSaveDto);
    }

    @Override
    protected void deleteInternal(Long id) {
        groupService.delete(id);
    }

    @Test
    void addEmployeeToExistingGroup() {

        Long groupId=10L;
        Long newEmployeeToAddId=100L;
        Long employeeNotFoundInEmployeeTableId=200L;
        Long employeeDuplicateId=300L;
        Set<Long> employeeIds = Sets.newSet(newEmployeeToAddId,employeeNotFoundInEmployeeTableId,employeeDuplicateId);

        Employee newEmployeeToAdd = Employee.builder().id(newEmployeeToAddId).build();
        Employee employeeDuplicate = Employee.builder().id(employeeDuplicateId).build();
        Employee employeeNotFound = Employee.builder().id(employeeNotFoundInEmployeeTableId).build();
        Group group =Group.builder().id(groupId).build();
        group.setEmployees(Sets.newSet(employeeDuplicate));

        when(groupRepository.findByIdWithEmployees(group.getId())).thenReturn(Optional.of(group));
        when(employeeRepository.findAllByIdAsSet(any())).thenReturn(Sets.newSet(newEmployeeToAdd,employeeDuplicate));

        BulkUpdateResponseDto<GroupDto> response = groupService.addEmployeeToGroup(group.getId(), employeeIds);
        assertThat(response.getParent()).isNotNull();
        assertThat(response.getReports().size()).isEqualTo(3);

//        Map<Long, UpdateReportDto> reportsAsMap = new HashMap<>();
//        response.getReports().forEach( (report)-> {
//            reportsAsMap.put(report.getId(),report);
//        });
        assertThat(response.getReports().get(newEmployeeToAddId).getCode()).isEqualTo(UpdateReportDto.SUCCESS);
        assertThat(response.getReports().get(employeeDuplicateId).getCode()).isEqualTo(UpdateReportDto.DUPLICATED);
        assertThat(response.getReports().get(employeeNotFoundInEmployeeTableId).getCode()).isEqualTo(UpdateReportDto.NOT_FOUND);

    }
}
