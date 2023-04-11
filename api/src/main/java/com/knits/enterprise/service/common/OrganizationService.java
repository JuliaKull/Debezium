package com.knits.enterprise.service.common;


import com.knits.enterprise.dto.api.PaginatedResponseDto;
import com.knits.enterprise.dto.data.common.OrganizationDto;
import com.knits.enterprise.dto.search.common.OrganizationSearchDto;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.mapper.common.OrganizationMapper;
import com.knits.enterprise.model.common.Organization;
import com.knits.enterprise.repository.company.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrganizationService extends GenericService{


    private final OrganizationRepository repository;
    private final OrganizationMapper mapper;

    public OrganizationDto create(OrganizationDto organizationDto) {
        log.debug("Request to create Organization : {}", organizationDto);

        String name = organizationDto.getName();
        Optional<Organization> existingOrganization = repository.findOneByName(name);
        if (existingOrganization.isPresent()) {
            throw new UserException("Organization with name " + name + " already exists");
        }

        Organization organization = mapper.toEntity(organizationDto);
        organization.setCreatedBy(getCurrentUserAsEntity());
        organization.setStartDate(LocalDateTime.now());
        organization.setActive(true);
        return mapper.toDto(repository.save(organization));
    }

    @Transactional
    public OrganizationDto update(OrganizationDto organizationDto) {
        log.debug("Request to update Organization : {}", organizationDto);

        Organization organization = repository.findById(organizationDto.getId()).orElseThrow(()
                -> new UserException("Organization#" + organizationDto.getId() + " not found"));
        mapper.update(organization, organizationDto);
        repository.save(organization);
        return mapper.toDto(organization);
    }

    @Transactional
    public OrganizationDto partialUpdate(OrganizationDto organizationDto) {
        log.debug("Request to update Organization : {}", organizationDto);

        Organization organization = repository.findById(organizationDto.getId()).orElseThrow(()
                -> new UserException("Organization#" + organizationDto.getId() + " not found"));
        mapper.partialUpdate(organization, organizationDto);
        repository.save(organization);
        return mapper.toDto(organization);
    }

    public void delete(Long id) {
        log.debug("Delete Organization by id : {}", id);
        repository.deleteById(id);
    }

    public Page<OrganizationDto> getActive(OrganizationSearchDto organizationSearchDto) {
        log.debug("Request to get all active Organizations");

        Page<Organization> organizationPage = repository.findAll(organizationSearchDto.getSpecification(), organizationSearchDto.getPageable());
        List<OrganizationDto> organizationDtos = mapper.toDtos(organizationPage.getContent());
        return new PageImpl<>(organizationDtos, organizationSearchDto.getPageable(), organizationPage.getTotalElements());
    }

    public PaginatedResponseDto<OrganizationDto> search(OrganizationSearchDto searchDto) {

        Page<Organization> organizationPage = repository.findAll(searchDto.getSpecification(),searchDto.getPageable());
        List<OrganizationDto> organizationDtos = mapper.toDtos(organizationPage.getContent());

        return PaginatedResponseDto.<OrganizationDto>builder()
                .page(searchDto.getPage())
                .size(organizationDtos.size())
                .sortingFields(searchDto.getSort())
                .sortDirection(searchDto.getDir().name())
                .data(organizationDtos)
                .build();
    }
}

