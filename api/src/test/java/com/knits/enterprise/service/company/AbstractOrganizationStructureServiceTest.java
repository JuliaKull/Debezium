package com.knits.enterprise.service.company;

import com.knits.enterprise.dto.data.company.AbstractOrganizationStructureDto;
import com.knits.enterprise.mapper.company.AbstractOrganizationStructureMapper;
import com.knits.enterprise.mocks.dto.security.UserDtoMock;
import com.knits.enterprise.model.company.AbstractOrganizationStructure;
import com.knits.enterprise.model.company.Employee;
import com.knits.enterprise.service.security.UserService;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public abstract class AbstractOrganizationStructureServiceTest {

    protected abstract <E extends AbstractOrganizationStructure, D extends AbstractOrganizationStructureDto> AbstractOrganizationStructureMapper<E,D> getMapper();

    protected abstract JpaRepository getRepository();

    protected abstract UserService getUserService();

    protected abstract <T> Class<T> getEntityClass();

    protected abstract <E> ArgumentCaptor<E> getEntityCaptor();


    protected <E extends AbstractOrganizationStructure> void saveSuccessTemplate(AbstractOrganizationStructureDto toSaveDto) {

        when(getRepository().save(Mockito.any(getEntityClass())))
                .thenAnswer(invocation -> {
                    AbstractOrganizationStructure organizationStructure = (AbstractOrganizationStructure)invocation.getArgument(0);
                    organizationStructure.setId(100L);
                    return organizationStructure;
                });
        when(getUserService().getCurrentUserAsDto()).thenReturn(UserDtoMock.shallowUserDto(1L));

        AbstractOrganizationStructureDto savedDto = saveInternal(toSaveDto);

        ArgumentCaptor<E> captor = getEntityCaptor();
        verify(getRepository()).save(captor.capture());
        E toSaveEntity = (E)captor.getValue();

        verify(getMapper(), times(1)).toEntity(toSaveDto);
        verify(getRepository(), times(1)).save(toSaveEntity);
        verify(getMapper(), times(1)).toDto(toSaveEntity);

        assertThat(savedDto.getId()).isNotNull();
        assertThat(savedDto.getName()).isEqualTo(toSaveDto.getName());
        assertThat(savedDto.getDescription()).isEqualTo(toSaveDto.getDescription());
        assertThat(savedDto.getStartDate()).isEqualTo(toSaveDto.getStartDate());
        assertThat(savedDto.getEndDate()).isEqualTo(toSaveDto.getEndDate());
    }


    protected <E extends AbstractOrganizationStructure, D extends AbstractOrganizationStructureDto> void partialUpdateSuccessTemplate(
            E foundEntity) {

        D toUpdateDto = (D)getMapper().toDto(foundEntity);
        toUpdateDto.setName("partialUpdateName");

        when(getRepository().findById(foundEntity.getId())).thenReturn(Optional.of(foundEntity));
        when(getRepository().save(Mockito.any(getEntityClass())))
                .thenAnswer(i -> i.getArguments()[0]);

        D updatedDto = partialUpdateInternal(toUpdateDto);

        ArgumentCaptor<E> captor = getEntityCaptor();
        verify(getRepository()).save(captor.capture());
        E toUpdateEntity = captor.getValue();

        verify(getMapper(), times(1)).partialUpdate(toUpdateEntity, toUpdateDto);
        verify(getRepository(), times(1)).save(foundEntity);
        verify(getMapper(), times(2)).toDto(foundEntity); //count also .toDto above on same object

        assertThat(updatedDto.getId()).isEqualTo(toUpdateDto.getId());
        assertThat(updatedDto.getName()).isEqualTo(toUpdateDto.getName());
        assertThat(updatedDto.getDescription()).isEqualTo(toUpdateDto.getDescription());
        assertThat(updatedDto.getStartDate()).isEqualTo(toUpdateDto.getStartDate());
        assertThat(updatedDto.getEndDate()).isEqualTo(toUpdateDto.getEndDate());
    }

    protected <E extends AbstractOrganizationStructure, D extends AbstractOrganizationStructureDto> void updateSuccessTemplate(E foundEntity) {

        D toUpdateDto = (D)getMapper().toDto(foundEntity);
        toUpdateDto.setName("updatedName");
        toUpdateDto.setDescription(null);

        when(getRepository().findById(foundEntity.getId())).thenReturn(Optional.of(foundEntity));
        when(getRepository().save(Mockito.any(getEntityClass())))
                .thenAnswer(i -> i.getArguments()[0]);

        D updatedDto = updateInternal(toUpdateDto);

        ArgumentCaptor<E> captor = getEntityCaptor();
        verify(getRepository()).save(captor.capture());
        E toUpdateEntity = captor.getValue();

        verify(getMapper(), times(1)).update(toUpdateEntity, toUpdateDto);
        verify(getRepository(), times(1)).save(foundEntity);
        verify(getMapper(), times(2)).toDto(foundEntity);

        assertThat(updatedDto.getId()).isEqualTo(toUpdateDto.getId());
        assertThat(updatedDto.getName()).isEqualTo(toUpdateDto.getName());
        assertThat(updatedDto.getDescription()).isEqualTo(toUpdateDto.getDescription());
        assertThat(updatedDto.getDescription()).isNull();
        assertThat(updatedDto.getStartDate()).isEqualTo(toUpdateDto.getStartDate());
        assertThat(updatedDto.getEndDate()).isEqualTo(toUpdateDto.getEndDate());

    }

    protected <E extends AbstractOrganizationStructure, D extends AbstractOrganizationStructureDto> void deleteSuccessTemplate
            (E foundEntity) {
        deleteInternal(foundEntity.getId());
        verify(getRepository(), times(1)).deleteById(foundEntity.getId());
    }

    protected abstract AbstractOrganizationStructureDto saveInternal(AbstractOrganizationStructureDto toSaveDto);

    protected abstract <D extends AbstractOrganizationStructureDto> D partialUpdateInternal(D toSaveDto);

    protected abstract <D extends AbstractOrganizationStructureDto> D updateInternal(D toSaveDto);

    protected abstract void deleteInternal(Long id);


}
