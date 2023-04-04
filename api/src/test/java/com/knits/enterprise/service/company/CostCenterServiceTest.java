package com.knits.enterprise.service.company;

import com.knits.enterprise.dto.data.company.AbstractOrganizationStructureDto;
import com.knits.enterprise.dto.data.company.CostCenterDto;
import com.knits.enterprise.mapper.company.AbstractOrganizationStructureMapper;
import com.knits.enterprise.mapper.company.CostCenterMapper;
import com.knits.enterprise.mapper.company.CostCenterMapperImpl;
import com.knits.enterprise.mocks.dto.company.CostCenterDtoMock;
import com.knits.enterprise.mocks.model.company.CostCenterMock;
import com.knits.enterprise.model.company.AbstractOrganizationStructure;
import com.knits.enterprise.model.company.CostCenter;
import com.knits.enterprise.repository.company.CostCenterRepository;
import com.knits.enterprise.service.security.UserService;
import com.knits.enterprise.utils.TestConsts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

@ExtendWith(MockitoExtension.class)
class CostCenterServiceTest extends AbstractOrganizationStructureServiceTest {

    @Mock
    private CostCenterRepository repository;

    @Mock
    private UserService userService;

    @Spy
    private CostCenterMapper mapper = new CostCenterMapperImpl();

    @InjectMocks
    private CostCenterService costCenterService;

    @Captor
    private ArgumentCaptor<CostCenter> captor;

    @Test
    @DisplayName("CostCenter save Success")
    void saveSuccess() {
        saveSuccessTemplate(CostCenterDtoMock.shallowCostCenterDto(TestConsts.NO_COUNTER));
    }

    @Test
    @DisplayName("CostCenter partial Update success")
    void partialUpdate() {
        partialUpdateSuccessTemplate(CostCenterMock.shallowCostCenter(1L));
    }

    @Test
    @DisplayName("CostCenter Update success")
    void testUpdate() {
        updateSuccessTemplate(CostCenterMock.shallowCostCenter(1L));
    }

    @Test
    @DisplayName("CostCenter delete success")
    void deleteSuccess() {
        deleteSuccessTemplate(CostCenterMock.shallowCostCenter(1L));
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
        return (Class<T>) CostCenter.class;
    }

    @Override
    protected <E> ArgumentCaptor<E> getEntityCaptor() {
        return (ArgumentCaptor<E>) captor;
    }

    @Override
    protected AbstractOrganizationStructureDto saveInternal(AbstractOrganizationStructureDto toSaveDto) {
        return costCenterService.create((CostCenterDto) toSaveDto);
    }

    @Override
    protected <D extends AbstractOrganizationStructureDto> D partialUpdateInternal(D toSaveDto) {
        return (D) costCenterService.partialUpdate((CostCenterDto) toSaveDto);
    }

    @Override
    protected <D extends AbstractOrganizationStructureDto> D updateInternal(D toSaveDto) {
        return (D) costCenterService.update((CostCenterDto) toSaveDto);
    }

    @Override
    protected void deleteInternal(Long id) {
        costCenterService.delete(id);
    }
}
