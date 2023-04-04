package com.knits.enterprise.service.company;

import com.knits.enterprise.dto.data.company.AbstractOrganizationStructureDto;
import com.knits.enterprise.dto.data.company.DepartmentDto;
import com.knits.enterprise.mapper.company.AbstractOrganizationStructureMapper;
import com.knits.enterprise.mapper.company.DepartmentMapper;
import com.knits.enterprise.mapper.company.DepartmentMapperImpl;
import com.knits.enterprise.mocks.dto.company.DepartmentDtoMock;
import com.knits.enterprise.mocks.model.company.DepartmentMock;
import com.knits.enterprise.model.company.AbstractOrganizationStructure;
import com.knits.enterprise.model.company.Department;
import com.knits.enterprise.repository.company.DepartmentRepository;
import com.knits.enterprise.service.security.UserService;
import com.knits.enterprise.utils.TestConsts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest extends AbstractOrganizationStructureServiceTest {

    @Mock
    private DepartmentRepository repository;

    @Mock
    private UserService userService;

    @Spy
    private DepartmentMapper mapper = new DepartmentMapperImpl();

    @InjectMocks
    private DepartmentService departmentService;

    @Captor
    private ArgumentCaptor<Department> captor;

    @Test
    @DisplayName("Department save Success")
    void saveSuccess() {
        saveSuccessTemplate(DepartmentDtoMock.shallowDepartmentDto(TestConsts.NO_COUNTER));
    }

    @Test
    @DisplayName("Department partial Update success")
    void partialUpdate() {
        partialUpdateSuccessTemplate(DepartmentMock.shallowDepartment(1L));
    }

    @Test
    @DisplayName("Department Update success")
    void testUpdate() {
        updateSuccessTemplate(DepartmentMock.shallowDepartment(1L));
    }

    @Test
    @DisplayName("Department delete success")
    void deleteSuccess() {
        deleteSuccessTemplate(DepartmentMock.shallowDepartment(1L));
    }

    @Override
    protected <E extends AbstractOrganizationStructure, D extends AbstractOrganizationStructureDto> AbstractOrganizationStructureMapper<E, D> getMapper() {
        return (AbstractOrganizationStructureMapper<E, D>) mapper;
    }

    @Override
    protected JpaRepository getRepository() {
        return (JpaRepository) repository;
    }

    @Override
    protected UserService getUserService() {
        return userService;
    }

    @Override
    protected <T> Class<T> getEntityClass() {
        return (Class<T>) Department.class;
    }

    @Override
    protected <E> ArgumentCaptor<E> getEntityCaptor() {
        return (ArgumentCaptor<E>) captor;
    }

    @Override
    protected AbstractOrganizationStructureDto saveInternal(AbstractOrganizationStructureDto toSaveDto) {
        return departmentService.create((DepartmentDto) toSaveDto);
    }

    @Override
    protected <D extends AbstractOrganizationStructureDto> D partialUpdateInternal(D toSaveDto) {
        return (D) departmentService.partialUpdate((DepartmentDto) toSaveDto);
    }

    @Override
    protected <D extends AbstractOrganizationStructureDto> D updateInternal(D toSaveDto) {
        return (D) departmentService.update((DepartmentDto) toSaveDto);
    }

    @Override
    protected void deleteInternal(Long id) {
        departmentService.delete(id);
    }
}
