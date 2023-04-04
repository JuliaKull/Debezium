package com.knits.enterprise.service.company;

import com.knits.enterprise.dto.data.company.AbstractOrganizationStructureDto;
import com.knits.enterprise.dto.data.company.JobTitleDto;
import com.knits.enterprise.mapper.company.AbstractOrganizationStructureMapper;
import com.knits.enterprise.mapper.company.JobTitleMapper;
import com.knits.enterprise.mapper.company.JobTitleMapperImpl;
import com.knits.enterprise.mocks.dto.company.JobTitleDtoMock;
import com.knits.enterprise.mocks.model.company.JobTitleMock;
import com.knits.enterprise.model.company.AbstractOrganizationStructure;
import com.knits.enterprise.model.company.JobTitle;
import com.knits.enterprise.repository.company.JobTitleRepository;
import com.knits.enterprise.service.security.UserService;
import com.knits.enterprise.utils.TestConsts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

@ExtendWith(MockitoExtension.class)
class JobTitleServiceTest extends AbstractOrganizationStructureServiceTest {

    @Mock
    private JobTitleRepository repository;

    @Mock
    private UserService userService;

    @Spy
    private JobTitleMapper mapper = new JobTitleMapperImpl();

    @InjectMocks
    private JobTitleService jobTitleService;

    @Captor
    private ArgumentCaptor<JobTitle> captor;

    @Test
    @DisplayName("JobTitle save Success")
    void saveSuccess() {
        saveSuccessTemplate(JobTitleDtoMock.shallowJobTitleDto(TestConsts.NO_COUNTER));
    }

    @Test
    @DisplayName("JobTitle partial Update success")
    void partialUpdate() {
        partialUpdateSuccessTemplate(JobTitleMock.shallowJobTitle(1L));
    }

    @Test
    @DisplayName("JobTitle Update success")
    void testUpdate() {
        updateSuccessTemplate(JobTitleMock.shallowJobTitle(1L));
    }

    @Test
    @DisplayName("JobTitle delete success")
    void deleteSuccess() {
        deleteSuccessTemplate(JobTitleMock.shallowJobTitle(1L));
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
        return (Class<T>) JobTitle.class;
    }

    @Override
    protected <E> ArgumentCaptor<E> getEntityCaptor() {
        return (ArgumentCaptor<E>) captor;
    }

    @Override
    protected AbstractOrganizationStructureDto saveInternal(AbstractOrganizationStructureDto toSaveDto) {
        return jobTitleService.create((JobTitleDto) toSaveDto);
    }

    @Override
    protected <D extends AbstractOrganizationStructureDto> D partialUpdateInternal(D toSaveDto) {
        return (D) jobTitleService.partialUpdate((JobTitleDto) toSaveDto);
    }

    @Override
    protected <D extends AbstractOrganizationStructureDto> D updateInternal(D toSaveDto) {
        return (D) jobTitleService.update((JobTitleDto) toSaveDto);
    }

    @Override
    protected void deleteInternal(Long id) {
        jobTitleService.delete(id);
    }
}
